package cgb.transfert;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cgb.transfert.iban.ToolsIban;
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

    public static void insertSampleData(AccountRepository accountRepository) {
        for (int i = 0; i < 20; i++) {
            Account account = new Account();
        	account.setAccountNumber(generateRandomIban());	
            account.setSolde(generateRandomSolde());  // Solde aléatoire entre 1000 et 10000
            accountRepository.save(account);
        }
    }
    
    public static String generateRandomIban() {
        Random random = new Random();
        
        String countryCode = "FR";
        int checkDigits = random.nextInt(90) + 10; // Chiffres de contrôle entre 10 et 99
        String bankCode = String.format("%04d", random.nextInt(10000)); // Code banque sur 4 chiffres
        String branchCode = String.format("%04d", random.nextInt(10000)); // Code guichet sur 4 chiffres
        String accountNumber = String.format("%011d", random.nextLong() % 100000000000L); // Numéro de compte sur 11 chiffres
        String key = String.format("%02d", random.nextInt(100)); // Clé RIB (clé de contrôle)
        
        String iban = countryCode + checkDigits + bankCode + branchCode + accountNumber + key;
        
        return iban;
    }
    
    public static double generateRandomSolde() {
        Random random = new Random();
        double solde = 1000 + (Math.random() * 9000);  // Solde entre 1000 et 10000
        return Math.round(solde * 100.0) / 100.0;  // Limite à 2 chiffres après la virgule
    }
}















