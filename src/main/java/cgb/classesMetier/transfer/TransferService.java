package cgb.classesMetier.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cgb.classesMetier.account.*;
import cgb.classesMetier.log.Log;
import cgb.classesMetier.log.LogService;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

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
    
    
    
    
    public List<Transfer> getAllTransfers()
    {
    	return this.transferRepository.findAll();
    }
    
    /*
     * Rappel du cours sur les transactions... Tout ou rien
     */
    @Transactional
    public Transfer createTransfer(String sourceAccountNumber, String destinationAccountNumber,
                                   Double amount, LocalDate transferDate, String description, long numLot) 
    {
        Account sourceAccount = accountRepository.findById(sourceAccountNumber)
                				.orElseThrow(() -> new RuntimeException("Source account not found"));
        Account destinationAccount = accountRepository.findById(destinationAccountNumber)
                				.orElseThrow(() -> new RuntimeException("Destination account not found"));

        Transfer transfer = new Transfer();
        transfer.setSourceAccountNumber(sourceAccountNumber);
        transfer.setDestinationAccountNumber(destinationAccountNumber);
        transfer.setAmount(amount);
        transfer.setTransferDate(transferDate);
        transfer.setDescription(description);
        transfer.setNumLot(numLot);
        
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
    
    
    @Async
    public long createTransferLot(TransfersLot transfersLot) throws Exception {
    	List<UnTransferDuLot> lesTransfers = transfersLot.lesTransfers();
    	
    	long numLot = ThreadLocalRandom.current().nextLong(1000000000L, 9999999999999L); 
    	
    	for (UnTransferDuLot unTransfer : lesTransfers) {
    		String sourceAccountNumber = transfersLot.sourceAccountNumber();
    		String destAccountNumber = unTransfer.ibanDest();
    		
			this.createTransfer(sourceAccountNumber, 
								destAccountNumber, 
								unTransfer.amount(), 
								transfersLot.transferDate(), 
								unTransfer.description(),
								numLot);
    	}
    	
		return numLot;
    }
    	
}  
    
    
    
    

