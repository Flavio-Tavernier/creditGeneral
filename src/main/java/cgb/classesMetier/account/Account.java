package cgb.classesMetier.account;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Vector;

@Entity
@Data
public class Account {
    @Id
    private String accountNumber;
	private Double solde;
	private Vector<String> beneficiaires = new Vector<String>();
	
	public Account() {}
	
    /**
	 * Renvoi le solde d'un compte bancaire
	 * @return Double
	 */
	public Double getSolde() {
		return solde;
	}
	
	/**
	 * Affecte la valeur du paramètre à la propriété solde 
	 * @param solde
	 */
	public void setSolde(Double solde) {
		this.solde = solde;
	}
	
	/**
	 * Renvoi le numéro d'un compte bancaire
	 * @return String
	 */
    public String getAccountNumber() {
		return accountNumber;
	}
    
	/**
	 * Affecte la valeur du paramètre à la propriété accountNumber 
	 * @param solde
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public Vector<String> getBeneficiaires()
	{
		return this.beneficiaires;
	}

	public void setBeneficiaires(Vector<String> beneficiaires)
	{
		this.beneficiaires = beneficiaires;
	}

	/**
	 * Permet d'ajouter le bénéficiaire passé en paramètre
	 * à la liste du compte
	 * 
	 * @param addBeneficiaire
	 */
	public void addBeneficiaire(String beneficiaire) throws Exception
	{
		if (this.beneficiaires.contains(beneficiaire)) {
			throw new Exception("Bénéficiaire " + beneficiaire + 
								" déjà existant");
		} else {
			this.beneficiaires.add(beneficiaire);
		}		
	}
	
}