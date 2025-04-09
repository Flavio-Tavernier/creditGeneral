package cgb.classesMetier.transfer.lot;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cgb.classesMetier.log.LogService;

/**
 * classe de transfer de lot
 * 
 */
@RestController
@RequestMapping("/transfersLots")
public class TransferLotController {

    @Autowired
    private TransferLotService transferLotService;
    @Autowired
    private TransferLotRequestRepository transferLotRequestRepository;
    @Autowired
    private LogService logService;

    
    @GetMapping("/getAllTransferLot")
	public ResponseEntity<?> getAllTransferLot() {
		List<TransferLot> transfersLots = this.transferLotService.getAllTransferLot();
		
		if (transfersLots.isEmpty()) {
			return new ResponseEntity<String>("Aucun logs", HttpStatusCode.valueOf(404));
		} else {
			return new ResponseEntity<List<TransferLot>>(transfersLots, HttpStatus.OK);
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
    @PostMapping("/createTransferLot")
    public ResponseEntity<?> createTransferLot(@RequestBody TransferLotRequest transferLotRequest) throws Exception {
        try {
        	//long numLot = ThreadLocalRandom.current().nextLong(1000000000L, 9999999999999L); 
        	//transferLotRequest.setNumLot(numLot);
        	
        	return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(this.transferLotService.createTransferLot(transferLotRequest));
    	
        }catch (RuntimeException e) {
        	e.printStackTrace();
        	TransferLotResponse errorResponse = new TransferLotResponse("FAILURE", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}


class TransferLotResponse {
    private String status;
    private String message;

    // Constructeur
    public TransferLotResponse(String status, String message) {
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


