package cgb.classesMetier.account;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    
	/**
	 * Route de la classe AccountController permettant de récupérer
	 * un compte par l'id renseigné en paramètre
	 * @param id
	 * @return ResponseEntity<?>
	 */
    @GetMapping("/{id}")
	public ResponseEntity<?> getAccountById(@PathVariable String id) {
		Account account = accountService.getAccountById(id);
		
		if (account == null) {
			return new ResponseEntity<String>("Aucun compte", HttpStatusCode.valueOf(404));
		} else {
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}
	}

	/**
	 * Route de la classe AccountController permettant de récupérer
	 * tous les comptes bancaires
	 * @param id
	 * @return ResponseEntity<?>
	 */
    @GetMapping("/getAllAccounts")
	public ResponseEntity<?> getAllAccounts() {
		List<Account> accounts = accountService.obtenirAllAccounts();
		
		if (accounts.isEmpty()) {
			return new ResponseEntity<String>("Aucun compte", HttpStatusCode.valueOf(404));
		} else {
			return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
		}
	}

    /**
     * Route de la classe AccountController permettant
     * d'ajouter un beneficiaire à un compte bancaire
     * @param addBeneficiaireRequest
     * @return
     * @throws Exception
     */
	@PostMapping("/addBeneficiaire")
	public ResponseEntity<?> addBeneficiaire(@RequestBody AddBeneficiaireRequest addBeneficiaireRequest) throws Exception {
		try {
			String sourceAccountNumber = addBeneficiaireRequest.getSourceAccountNumber();
			String beneficiaireAccountNumber = addBeneficiaireRequest.getBeneficiaireAccountNumber();
			Account accountEmetteur = this.accountService.getAccountById(sourceAccountNumber);			
			
			this.accountService.addBeneficiaire(accountEmetteur, beneficiaireAccountNumber);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(accountEmetteur);

        }catch (RuntimeException e) {
        	AccountResponse errorResponse = new AccountResponse("FAILURE", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	}
	
	

	@PostMapping("/createAccount")
	public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDTO) throws Exception {
		try {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(this.accountService.createAccount(accountDTO));
        } catch (Exception e) {
        	AccountResponse errorResponse = new AccountResponse("FAILURE", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	}
	
	@GetMapping("/deleteAcccount/{accountNumber}")
	public ResponseEntity<?> deleteAcccount(@PathVariable String accountNumber) throws Exception {
		try {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(this.accountService.deleteAccount(accountNumber));
        } catch (Exception e) {
        	AccountResponse errorResponse = new AccountResponse("FAILURE", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	}
	
	
	
}

class AccountResponse {
    private String status;
    private String message;

    // Constructeur
    public AccountResponse(String status, String message) {
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
