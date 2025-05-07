package cgb.classesMetier.account;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cgb.classesMetier.iban.ToolsIban;
import cgb.classesMetier.transfer.TransferService;


@Service
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	@Autowired
    private TransferService transferService;

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
	
	public Account addBeneficiaire(Account accountEmetteur, String beneficiaireAccountNumber) throws Exception {
		accountEmetteur.addBeneficiaire(beneficiaireAccountNumber);
		return this.accountRepository.save(accountEmetteur);
	}
	
	public Account createAccount(AccountDTO accountDTO) throws Exception {	
		ToolsIban toolsIban = ToolsIban.getInstance();
		
		if (accountRepository.existsById(accountDTO.getAccountNumber())) {
			throw new Exception("Erreur création compte bancaire : " + accountDTO.getAccountNumber() + " déjà existant");
		} else if (!toolsIban.isIbanStructureValide(accountDTO.getAccountNumber())) {
			throw new Exception("Erreur création compte bancaire : " + accountDTO.getAccountNumber() + " IBAN invalide");
		} else {
			Account account = new Account();
			account.setAccountNumber(accountDTO.getAccountNumber());
			account.setSolde(0.0);
			
			return this.accountRepository.save(account);	
		}	
	}
	
	public boolean deleteAccount(String accountNumber) throws Exception {
		if (!this.transferService.getTransferByDestinationAccountNumber(accountNumber).isEmpty()) {
			throw new Exception("Erreur suppression compte bancaire : " + accountNumber + " virement existant");
		} else if (!this.accountRepository.existsById(accountNumber)) {
			throw new Exception("Erreur suppression compte bancaire : " + accountNumber + " compte inexistant");
		}
		else {
			this.accountRepository.deleteById(accountNumber);
		}
		return true;
	}
}




















