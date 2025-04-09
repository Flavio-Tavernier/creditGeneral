package cgb.classesMetier.transfer.lot;

import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cgb.classesMetier.account.Account;
import cgb.classesMetier.account.AccountService;
import cgb.classesMetier.log.Log;
import cgb.classesMetier.log.LogService;
import cgb.classesMetier.transfer.TransferService;


@Service
public class TransferLotService {
    @Autowired
    private TransferLotRepository transferLotRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LogService logService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferLotRequestRepository transferLotRequestRepository;
    
    
    
    public List<TransferLot> getAllTransferLot()
	{
		return transferLotRepository.findAll();
	}
    
    
    @Async
    public long createTransferLot(TransferLotRequest transferLotRequest) throws Exception {
    	Vector<TransferLot> lesTransfers = transferLotRequest.getLesTransfers();
    	
    	for (TransferLot unTransfer : lesTransfers) {
    		String sourceAccountNumber = transferLotRequest.getSourceAccountNumber();
    		String destAccountNumber = unTransfer.getIbanDest();
    		
    		Account sourceAccount = this.accountService.getAccountById(sourceAccountNumber);
    		
    		if (sourceAccount.getBeneficiaires().contains(destAccountNumber)) {
    			transferService.createTransfer(
    				sourceAccountNumber,
    				destAccountNumber,
    				unTransfer.getAmount(),
    				transferLotRequest.getTransferDate(),
                    unTransfer.getDescription()
				);
    			
    			 
    			Log log = new Log();
					log.setNumLot(transferLotRequest.getNumLot());
					log.setDateLog(LocalDate.now());
					log.setDescription("Transfer de : " + unTransfer.getAmount() + "€ du compte : " + sourceAccountNumber + 
										" vers : " + destAccountNumber);
					log.setStatus("success");
    			this.logService.addLog(log);
    		} else {
    			Log log = new Log();
	    			log.setNumLot(transferLotRequest.getNumLot());
					log.setDateLog(LocalDate.now());
					log.setDescription("compte : " + destAccountNumber + " non bénéficiaire de : " + sourceAccountNumber);
					log.setStatus("canceled");
					this.logService.addLog(log);
    		}
    	}
		transferLotRequestRepository.save(transferLotRequest);
		return transferLotRequest.getNumLot();
    }
}
















