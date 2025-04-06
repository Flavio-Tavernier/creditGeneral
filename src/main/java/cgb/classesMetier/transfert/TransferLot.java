package cgb.classesMetier.transfert;

import lombok.Data;

@Data
public class TransferLot {
	private Double amount;
	private String ibanDest;
	private String description;
	
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getIbanDest() {
		return ibanDest;
	}
	public void setIbanDest(String ibanDest) {
		this.ibanDest = ibanDest;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
