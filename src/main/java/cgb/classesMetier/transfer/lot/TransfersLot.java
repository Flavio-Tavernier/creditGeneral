package cgb.classesMetier.transfer.lot;

import java.time.LocalDate;
import java.util.List;

import cgb.classesMetier.transfer.Transfer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class TransfersLot {
    @Id
    private String refLot;
    private String sourceAccountNumber;
    private String sourceEmail;
    private String description;
    private LocalDate dateLot = LocalDate.now();
    private String state = "receive";
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Transfer> lesTransfers;
    

    public String getRefLot() {
        return refLot;
    }

    public void setRefLot(String refLot) {
        this.refLot = refLot;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getSourceEmail() {
        return sourceEmail;
    }

    public void setSourceEmail(String sourceEmail) {
        this.sourceEmail = sourceEmail;
    }

    public List<Transfer> getLesTransfers() {
        return lesTransfers;
    }

    public void setLesTransfers(List<Transfer> lesTransfers) {
        this.lesTransfers = lesTransfers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
