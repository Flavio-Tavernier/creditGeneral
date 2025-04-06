package cgb.classesMetier.account;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
	
	private final AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	/**
	 * Méthode appelée par la route /{id} de AccountController 
	 * permettant de renvoyer un compte bancaire à partir d'un id
	 * @param id
	 * @return Account
	 */
	public Account getAccountById(String id) {
		Optional<Account> oaccount=accountRepository.findById(id);
		return oaccount.orElse(null);
	}

	/**
	 * Méthode appelée par la route /getAllAccounts de AccountController 
	 * permettant de renvoyer tous les comptes bancaires
	 * @param id
	 * @return Account
	 */
	public List<Account> obtenirAllAccounts() {
		return accountRepository.findAll();
	}
}






