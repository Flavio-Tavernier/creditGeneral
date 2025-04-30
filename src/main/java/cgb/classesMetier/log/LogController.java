package cgb.classesMetier.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {

	@Autowired
    private LogService logService;
	
	
	/**
	 * Route de la classe LogController permettant de récupérer
	 * tous les logs
	 * @param id
	 * @return ResponseEntity<?>
	 */
    @GetMapping("/getAllLogs")
	public ResponseEntity<?> getAllLogs() {
		List<Log> logs = logService.getAllLogs();
		
		if (logs.isEmpty()) {
			return new ResponseEntity<String>("Aucun logs", HttpStatusCode.valueOf(404));
		} else {
			return new ResponseEntity<List<Log>>(logs, HttpStatus.OK);
		}
	}
    
    /**
	 * Route de la classe LogController permettant d'ajouter un log
	 * @param id
	 * @return ResponseEntity<?>
	 */
    @PostMapping("/addLog")
	public ResponseEntity<?> addLog(@RequestBody Log log) {
		try {
	    	logService.addLog(log);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(log);
	    } catch (RuntimeException e) {
	    	LogResponse errorResponse = new LogResponse("FAILURE", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	}
}

class LogResponse {
    private String status;
    private String message;

    // Constructeur
    public LogResponse(String status, String message) {
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


