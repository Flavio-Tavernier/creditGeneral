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
    
    
    @GetMapping("/getAllTransfers")
	public ResponseEntity<?> getAllTransfers() {
		List<Transfer> transfers = this.transferService.getAllTransfers();
		
		if (transfers.isEmpty()) {
			return new ResponseEntity<String>("Aucun transfer", HttpStatusCode.valueOf(404));
		} else {
			return new ResponseEntity<List<Transfer>>(transfers, HttpStatus.OK);
		}
	}
    
    
    @GetMapping("/getTransfersByRefLotAndStatut/{refLot}/{statut}")
	public ResponseEntity<?> getTransfersByNumLotAndStatut(@PathVariable String refLot, @PathVariable String statut) {
    	
		List<Transfer> transfers = this.transferService.getTransfersByRefLotAndStatut(refLot, statut);
		
		if (transfers.isEmpty()) {
			return new ResponseEntity<String>("Aucun transfer", HttpStatusCode.valueOf(404));
		} else {
			return new ResponseEntity<List<Transfer>>(transfers, HttpStatus.OK);
		}
	}
    
    @GetMapping("/getTransfersByTransferDateBetween/{dateDebut}/{dateFin}")
	public ResponseEntity<?> getTransfersByTransferDateBetween(@PathVariable LocalDate dateDebut, @PathVariable LocalDate dateFin) {
    	
		List<Transfer> transfers = this.transferService.getTransfersByTransferDateBetween(dateDebut, dateFin);
		
		if (transfers.isEmpty()) {
			return new ResponseEntity<String>("Aucun transfer", HttpStatusCode.valueOf(404));
		} else {
			return new ResponseEntity<List<Transfer>>(transfers, HttpStatus.OK);
		}
	}
    
    
    @GetMapping("/getTransferByDestinationAccountNumber/{destinationAccountNumber}")
	public ResponseEntity<?> getTransferByDestinationAccountNumber(@PathVariable String destinationAccountNumber) {
    	
		List<Transfer> transfers = this.transferService.getTransferByDestinationAccountNumber(destinationAccountNumber);
		
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
                transferRequest.getDescription(),
                "");
	    	
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
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	TransferResponse errorResponse = new TransferResponse("FAILURE", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    
    @GetMapping("/rejouerVirementCanceledByRefLot/{refLot}/{sourceEmail}")
	public ResponseEntity<?> rejouerVirementCanceledByRefLot(@PathVariable String refLot, @PathVariable String sourceEmail) {
        try {
        	
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(this.transferService.rejouerVirementCanceledByRefLot(refLot, sourceEmail));
        	
    	
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
