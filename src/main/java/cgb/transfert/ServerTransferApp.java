package cgb.transfert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cgb.transfert.iban.ToolsIban;

@SpringBootApplication
public class ServerTransferApp {

	public static void main(String[] args) {
		SpringApplication.run(ServerTransferApp.class, args);    	
	}
}

