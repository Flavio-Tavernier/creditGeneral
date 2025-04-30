package cgb.classesMetier.log;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long logNumber;
	private long numLot;
	private LocalDate dateLog;
	private String description;
	private String status;
	
	
	public long getLogNumber() {
		return logNumber;
	}
	public void setLogNumber(long logNumber) {
		this.logNumber = logNumber;
	}
	public LocalDate getDateLog() {
		return dateLog;
	}
	public void setDateLog(LocalDate dateLog) {
		this.dateLog = dateLog;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getNumLot() {
		return numLot;
	}
	public void setNumLot(long numLot) {
		this.numLot = numLot;
	}
}
