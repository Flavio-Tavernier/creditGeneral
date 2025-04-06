package cgb.classesMetier.transfert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cgb.classesMetier.account.*;
import cgb.classesMetier.log.Log;
import cgb.classesMetier.log.LogService;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Vector;

@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferRepository transferRepository;
    
    @Autowired
    private LogService logService;
    
    /*
     * Rappel du cours sur les transactions... Tout ou rien
     */
    @Transactional
    public Transfer createTransfer(String sourceAccountNumber, String destinationAccountNumber,
                                   Double amount, LocalDate transferDate, String description) {
        Account sourceAccount = accountRepository.findById(sourceAccountNumber)
                				.orElseThrow(() -> new RuntimeException("Source account not found"));
        Account destinationAccount = accountRepository.findById(destinationAccountNumber)
                				.orElseThrow(() -> new RuntimeException("Destination account not found"));

        /*Pas de découvert autorisé*/
        if (sourceAccount.getSolde().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }else {

        sourceAccount.setSolde(sourceAccount.getSolde()-(amount)); 
        destinationAccount.setSolde(destinationAccount.getSolde()+(amount));

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        Transfer transfer = new Transfer();
        transfer.setSourceAccountNumber(sourceAccountNumber);
        transfer.setDestinationAccountNumber(destinationAccountNumber);
        transfer.setAmount(amount);
        transfer.setTransferDate(transferDate);
        transfer.setDescription(description);

        return transferRepository.save(transfer);
        }
    }
    
    
    @Async
    public void gererTransferLot(TransferLotRequest transferLotRequest) throws Exception {
    	Vector<TransferLot> lesTransfers = transferLotRequest.getLesTransfers();
    	
    	for (TransferLot unTransfer : lesTransfers) {
    		String sourceAccountNumber = transferLotRequest.getSourceAccountNumber();
    		String destAccountNumber = unTransfer.getIbanDest();
    		
    		Account sourceAccount = this.accountService.getAccountById(sourceAccountNumber);
    		
    		Long lastLogNumber = logService.getLastLogNumber();
    		
    		if (sourceAccount.getBeneficiaires().contains(destAccountNumber)) {
    			this.createTransfer(
    				sourceAccountNumber,
    				destAccountNumber,
    				unTransfer.getAmount(),
    				transferLotRequest.getTransferDate(),
                    unTransfer.getDescription()
				);
    			
    			 
    			Log log = new Log();
					log.setLogNumber(lastLogNumber);
					log.setNumLot(transferLotRequest.getNumLot());
					log.setDateLog(LocalDate.now());
					log.setDescription("Transfer de : " + unTransfer.getAmount() + "€ du compte : " + sourceAccountNumber + 
										" vers : " + destAccountNumber);
					log.setStatus("success");
    			this.logService.addLog(log);
    		} else {
    			Log log = new Log();
	    			log.setLogNumber(lastLogNumber);
	    			log.setNumLot(transferLotRequest.getNumLot());
					log.setDateLog(LocalDate.now());
					log.setDescription("compte : " + destAccountNumber + " non bénéficiaire de : " + sourceAccountNumber);
					log.setStatus("canceled");
					this.logService.addLog(log);
    		}
    	}
        
    	
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
}
