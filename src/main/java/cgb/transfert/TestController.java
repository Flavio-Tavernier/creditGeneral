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
@RequestMapping("/test")
public class TestController {
	private final AccountService accountService;
	
	@Autowired
	 public TestController(AccountService accountService) {
		this.accountService = accountService;
	 }
	
	
	@GetMapping("/{id}")
	public String obtenirTache(@PathVariable int id) {
		
		return "Recu : " + id ; // Si application properties avec spring.thymeleaf.prefix=classpath:/vues/

	}
	
	@GetMapping("/")
	public String testVide() {
		
		return "Racine sous test "  ; // Si application properties avec spring.thymeleaf.prefix=classpath:/vues/

	}
	
	
	@GetMapping("/accounts")
	public ResponseEntity<?> getAllAccounts() {
		List<Account> accounts = accountService.obtenirAllAccounts();
		
		if (accounts.isEmpty()) {
			return new ResponseEntity<String>("Aucun compte", HttpStatusCode.valueOf(500));
		} else {
			return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
		}
	}
	
}
