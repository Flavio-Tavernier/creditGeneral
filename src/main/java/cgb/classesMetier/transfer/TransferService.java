package cgb.classesMetier.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cgb.classesMetier.account.*;
import cgb.classesMetier.mail.EmailService;
import cgb.classesMetier.transfer.lot.TransfersLot;
import cgb.classesMetier.transfer.lot.UnTransferDuLot;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private EmailService emailService;
    
    
    
    
    public List<Transfer> getAllTransfers()
    {
    	return this.transferRepository.findAll();
    }
    
    public List<Transfer> getTransfersByRefLot(String refLot)
    {
    	return this.transferRepository.getTransfersByRefLot(refLot);
    }

    public List<Transfer> getTransfersByRefLotAndStatut(String refLot, String statut)
    {
    	return this.transferRepository.getTransfersByRefLotAndStatut(refLot, statut);
    }

    public List<Transfer> getTransfersByTransferDateBetween(LocalDate dateDebut, LocalDate dateFin)
    {
    	return this.transferRepository.getTransfersByTransferDateBetween(dateDebut, dateFin);
    }
    
    public List<Transfer> getTransferByDestinationAccountNumber(String destinationAccountNumber)
    {
    	return this.transferRepository.getTransferByDestinationAccountNumber(destinationAccountNumber);
    }
    
    
    /*
     * Rappel du cours sur les transactions... Tout ou rien
     */
    @Transactional
    public Transfer createTransfer(String sourceAccountNumber, String destinationAccountNumber,
                                   Double amount, String description, String refLot) 
    {
    	Account sourceAccount = accountRepository.findById(sourceAccountNumber)
				.orElseThrow(() -> new RuntimeException("Source account not found"));
    	Account destinationAccount = accountRepository.findById(destinationAccountNumber)
				.orElseThrow(() -> new RuntimeException("Destination account not found"));

		Transfer transfer = new Transfer();
        transfer.setSourceAccountNumber(sourceAccountNumber);
        transfer.setDestinationAccountNumber(destinationAccountNumber);
        transfer.setAmount(amount);
        transfer.setTransferDate(LocalDate.now());
        transfer.setDescription(description);
        transfer.setRefLot(refLot);

        if (sourceAccount.getSolde().compareTo(amount) < 0) {
        	transfer.setStatut("canceled");
        } else if (!sourceAccount.getBeneficiaires().contains(destinationAccountNumber)) {	
	        transfer.setStatut("canceled");
        }
        else {

	        sourceAccount.setSolde(sourceAccount.getSolde()-(amount)); 
	        destinationAccount.setSolde(destinationAccount.getSolde()+(amount));
	
	        accountRepository.save(sourceAccount);
	        accountRepository.save(destinationAccount);
	
	        transfer.setStatut("success");
        }
        
        return transferRepository.save(transfer);
    }
    
    @Transactional
    public Transfer updateTransfer(Long id, String sourceAccountNumber) {
        Transfer transfer = transferRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Transfer not found"));
        transfer.setSourceAccountNumber(sourceAccountNumber);
        
        Account sourceAccount = accountRepository.findById(transfer.getSourceAccountNumber())
				.orElseThrow(() -> new RuntimeException("Source account not found"));
    	Account destinationAccount = accountRepository.findById(transfer.getDestinationAccountNumber())
				.orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (sourceAccount.getSolde().compareTo(transfer.getAmount()) < 0) {
        	transfer.setStatut("canceled");
        } else if (!sourceAccount.getBeneficiaires().contains(transfer.getDestinationAccountNumber())) {	
	        transfer.setStatut("canceled");
        }
        else {

	        sourceAccount.setSolde(sourceAccount.getSolde()-(transfer.getAmount())); 
	        destinationAccount.setSolde(destinationAccount.getSolde()+(transfer.getAmount()));
	
	        accountRepository.save(sourceAccount);
	        accountRepository.save(destinationAccount);
	
	        transfer.setStatut("success");
        }
        
        return transferRepository.save(transfer);
    }
    
    @Async
    public String createTransferLot(TransfersLot transfersLot) throws Exception {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            
                List<Transfer> lesTransfersDuLot = transfersLot.getLesTransfers();
                ArrayList<Transfer> lesTransfersEnrichis = new ArrayList<Transfer>();
    	
                String refLot = transfersLot.getRefLot();
                
                for (Transfer unTransfer : lesTransfersDuLot) {
                    lesTransfersEnrichis.add(this.updateTransfer(unTransfer.getId(), transfersLot.getSourceAccountNumber()));
                }
                transfersLot.setLesTransfers(lesTransfersEnrichis);
        
                String rapportVirementLot = "Numéro de lot : " + refLot +
                                            "\nDate : " + LocalDate.now() +
                                            "\nNombre de transactions échouées : " + this.getTransfersByRefLotAndStatut(refLot, "canceled").size() +
                                            "\nNombre de transactions réussies : " + this.getTransfersByRefLotAndStatut(refLot, "success").size();
        
                this.emailService.sendSimpleMessage(transfersLot.getSourceEmail(), "Rapport virement lot", rapportVirementLot);
                

        }).start();
    	
		return "refLot";
    }

    public String rejouerVirementCanceledByRefLot(String refLot, String sourceEmail)
    {
        List<Transfer> transfersCanceled = this.getTransfersByRefLotAndStatut(refLot, "canceled");
        
        

        for (Transfer unTransfer : transfersCanceled) {
			this.rejouerTransfer(unTransfer.getId());
    	}

        String rapportVirementLot = "Numéro de lot : " + refLot +
                                    "\nDate : " + LocalDate.now() +
                                    "\nNombre de transactions échouées : " + this.getTransfersByRefLotAndStatut(refLot, "canceled").size() +
                                    "\nNombre de transactions réussies : " + this.getTransfersByRefLotAndStatut(refLot, "success").size();

        this.emailService.sendSimpleMessage(sourceEmail, "Rapport virement lot", rapportVirementLot);
        return rapportVirementLot;
    }
    
    public void rejouerTransfer(long idTransfer)
    {
    	Transfer transfer = this.transferRepository.findById(idTransfer)
				.orElseThrow(() -> new RuntimeException("Transfer not found"));
    	Account sourceAccount = accountRepository.findById(transfer.getSourceAccountNumber())
				.orElseThrow(() -> new RuntimeException("Source account not found"));
    	Account destinationAccount = accountRepository.findById(transfer.getDestinationAccountNumber())
				.orElseThrow(() -> new RuntimeException("Destination account not found"));
    	
    	
    	if (sourceAccount.getSolde().compareTo(transfer.getAmount()) < 0) {
        	transfer.setStatut("canceled");
        } else if (!sourceAccount.getBeneficiaires().contains(transfer.getDestinationAccountNumber())) {	
	        transfer.setStatut("canceled");
        }
        else {

        	sourceAccount.setSolde(sourceAccount.getSolde()-(transfer.getAmount()));
	        destinationAccount.setSolde(destinationAccount.getSolde()+(transfer.getAmount()));
	
	        accountRepository.save(sourceAccount);
	        accountRepository.save(destinationAccount);
	
	        transfer.setStatut("success");
	        transferRepository.save(transfer);
        }
    }
    	
}  
    
    
    
    

