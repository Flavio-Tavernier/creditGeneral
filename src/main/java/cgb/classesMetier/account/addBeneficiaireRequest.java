package cgb.classesMetier.account;

import java.time.LocalDate;

public class addBeneficiaireRequest {
    private String sourceAccountNumber;
    private String beneficiaireAccountNumber;
    private LocalDate addDate;
    private String description;

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getBeneficiaireAccountNumber() {
        return beneficiaireAccountNumber;
    }

    public void setBeneficiaireAccountNumber(String beneficiaireAccountNumber) {
        this.beneficiaireAccountNumber = beneficiaireAccountNumber;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
