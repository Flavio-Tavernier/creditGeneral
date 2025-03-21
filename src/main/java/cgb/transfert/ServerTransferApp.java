package cgb.transfert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cgb.transfert.iban.ToolsIban;

@SpringBootApplication
public class ServerTransferApp {

	public static void main(String[] args) {
		SpringApplication.run(ServerTransferApp.class, args);    	
		// TODO Auto-generated method stub	
		//Tester chargement...
		
		ToolsIban outilsIban = ToolsIban.getInstance();
		
		String iban = "DE44123412341234123400";
		
		System.out.println(outilsIban.getCountryCode(iban));
		System.out.println(outilsIban.getControlNumber(iban));
		System.out.println(outilsIban.getBBAN(iban));
		System.out.println(outilsIban.isIbanStructureValide(iban));
		System.out.println(outilsIban.isIbanValide(iban));
	}
}

