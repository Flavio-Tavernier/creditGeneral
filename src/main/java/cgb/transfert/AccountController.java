package cgb.transfert;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/getAllAccounts")
	public ResponseEntity<?> getAllAccounts() {
		List<Account> accounts = accountService.obtenirAllAccounts();
		
		if (accounts.isEmpty()) {
			return new ResponseEntity<String>("Aucun compte", HttpStatusCode.valueOf(500));
		} else {
			return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
		}
	}

}
