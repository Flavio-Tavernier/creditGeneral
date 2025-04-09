package cgb.classesMetier.transfer.lot;

import java.time.LocalDate;
import java.util.Vector;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TransferLotRequest {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long numLot;
	private String sourceAccountNumber;
    private Vector<TransferLot> lesTransfers;
    private LocalDate transferDate;
    private String description;
    
    
	public long getNumLot() {
		return numLot;
	}
	public void setNumLot(long numLot) {
		this.numLot = numLot;
	}
	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}
	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}
	public Vector<TransferLot> getLesTransfers() {
		return lesTransfers;
	}
	public void setLesTransfers(Vector<TransferLot> lesTransfers) {
		this.lesTransfers = lesTransfers;
	}
	public LocalDate getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
