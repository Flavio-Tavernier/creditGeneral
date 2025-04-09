package cgb.classesMetier.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cgb.classesMetier.account.Account;
import cgb.classesMetier.account.AccountService;
import cgb.classesMetier.log.LogService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

/**
 * classe de transfer
 * 
 */
@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;
    
    @Autowired
    private LogService logService;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    public TransferController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    
    
    @GetMapping("/getAllTransfers")
	public ResponseEntity<?> getAllTransfers() {
		List<Transfer> transfers = this.transferService.getAllTransfers();
		
		if (transfers.isEmpty()) {
			return new ResponseEntity<String>("Aucun transfer", HttpStatusCode.valueOf(404));
		} else {
			return new ResponseEntity<List<Transfer>>(transfers, HttpStatus.OK);
		}
	}


    /**
     * Fonction qui prend un objet de type TransferRequest en parametre
     * afin de realiser un transfer entre 2 comptes bancaires
     * 
     * @param transferRequest
     * @return json 
     */
    @PostMapping("/createTransfer")
    public ResponseEntity<?> createTransfer(@RequestBody TransferRequest transferRequest) {
    	Transfer transfer = new Transfer();
    	
        try {
        	transfer = transferService.createTransfer(
                transferRequest.getSourceAccountNumber(),
                transferRequest.getDestinationAccountNumber(),
                transferRequest.getAmount(),
                transferRequest.getTransferDate(),
                transferRequest.getDescription(),
                0L);
	    	
	    	return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(transfer);
        } catch (RuntimeException e) {
        	transfer.setStatut("canceled");
            TransferResponse errorResponse = new TransferResponse("FAILURE", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }   
    
    
    
    /**
     * Fonction qui prend un objet de type TransferLotRequest en parametre
     * afin de realiser un transfer de lot
     * 
     * @param TransferLotRequest
     * @return json 
     * @throws Exception 
     */
    @PostMapping("/lot")
    public ResponseEntity<?> createTransferLot(@RequestBody TransfersLot transferLot ) throws Exception {
        try {
        	
        	return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(this.transferService.createTransferLot(transferLot));
    	
        }catch (RuntimeException e) {
        	e.printStackTrace();
        	TransferResponse errorResponse = new TransferResponse("FAILURE", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    
    
    
    
    
    
}






class TransferResponse {
    private String status;
    private String message;

    // Constructeur
    public TransferResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters et Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
