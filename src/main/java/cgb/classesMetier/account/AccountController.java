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


	@GetMapping("/addBeneficiaire/{iban}")
	public ResponseEntity<?> addBeneficiaire(@RequestBody String addBeneficiaireRequest) {
		try {
			String sourceAccountNumber = addBeneficiaireRequest.getSourceAccountNumber();

			Account accountEmetteur = AccountService.getAccountById(sourceAccountNumber);

			;


    	// accountService.addBeneficiaire();


    	// return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(transfer);

        }catch (RuntimeException e) {
        //     TransferResponse errorResponse = new TransferResponse("FAILURE", e.getMessage());
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
	}

}
