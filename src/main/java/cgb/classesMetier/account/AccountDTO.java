package cgb.classesMetier.account;

import java.util.Vector;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AccountDTO {
	@Id
    private String accountNumber;
	
	public AccountDTO() {}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}	
}
