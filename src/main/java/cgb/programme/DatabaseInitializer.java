package cgb.programme;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cgb.classesMetier.account.*;
// import cgb.classesMetier.iban.ToolsIban;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        // Vérifiez si la base de données est vide avant d'insérer des données
        if (accountRepository.count() == 0) {
           insertSampleData(accountRepository);
        }
    }

    /**
     * Permet d'insérer des données dans la base H2
     * 
     * @param accountRepository
     */
    public static void insertSampleData(AccountRepository accountRepository) {
        Account account1 = new Account();
        account1.setAccountNumber("123456789");
        account1.setSolde(300.00);
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setAccountNumber("987654321");
        account2.setSolde(500.00);
        accountRepository.save(account2);

        Account gsbAccount = new Account();
        gsbAccount.setAccountNumber("456789123");
        gsbAccount.setSolde(20000.00);
        accountRepository.save(gsbAccount);

        for (int i = 0; i < 20; i++) {
            Account account = new Account();
        	account.setAccountNumber(generateRandomIban());	
            account.setSolde(generateRandomSolde());
            accountRepository.save(account);
        }
    }
    
    /**
     * Génère des Ibans aléatoirement
     * 
     * @return String
     */
    public static String generateRandomIban() {
        Random random = new Random();
        
        String countryCode = "FR";
        int checkDigits = random.nextInt(90) + 10; 
        String bankCode = String.format("%04d", random.nextInt(10000)); 
        String branchCode = String.format("%04d", random.nextInt(10000)); 
        String accountNumber = String.format("%011d", random.nextLong() % 100000000000L); 
        String key = String.format("%02d", random.nextInt(100)); 
        
        String iban = countryCode + checkDigits + bankCode + branchCode + accountNumber + key;
        
        return iban;
    }
    
    /**
     * Génère des soldes de comptes en banque aléatoirement
     * 
     * @return double
     */
    public static double generateRandomSolde() {
        // Random random = new Random();
        double solde = 1000 + (Math.random() * 9000);  
        return Math.round(solde * 100.0) / 100.0;  
    }
}















