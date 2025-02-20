package cgb.transfert;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AccountService {
	
	private final AccountRepository accountRepository;


	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public List<Account> obtenirAllAccounts() {
		return accountRepository.findAll();
	}
	// Autres méthodes de service
}






