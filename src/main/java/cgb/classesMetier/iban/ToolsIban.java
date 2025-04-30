package cgb.classesMetier.iban;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.IBANValidator;



public class ToolsIban {
	private static ToolsIban instance;
	private ToolsIban() {	
	}
	
	/**
	 * Singleton
	 * renvoie une instance de la calsse ToolsIban
	 * 
	 * @return ToolsIban
	 */
	public static ToolsIban getInstance() {
		return instance != null ? instance : new ToolsIban();
	}
	
	/**
	 * Recupere une instance en Singleton de la classe CGBIbanValidator
	 * 
	 * @return CGBIbanValidator
	 */
	public CGBIbanValidator getInstanceValidator() {
		return CGBIbanValidator.getInstance();
	}
	
	/**
	 * Prend un iban de type String en parametre
	 * renvoi true si la structure est valide, false sinon
	 * 
	 * @param iban
	 * @return boolean
	 */
	public boolean isIbanStructureValide(String iban) {
		String regex = "^[A-Za-z]{2}\\d{2}[A-Za-z0-9]{4,30}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(iban);
        
        return matcher.matches();
	}
	
	/**
	 * Prend un iban de type String en parametre
	 * renvoi true si l'iban est valide, false sinon
	 * 
	 * @param iban
	 * @return boolean
	 */
	public boolean isIbanValide(String iban) {
		IBANValidator ibanValidator = IBANValidator.getInstance();
		return ibanValidator.isValid(iban);
	}
	
	/**
	 * Prend un iban de type String en parametre
	 * renvoi le code pays ou "Aucun code pays trouvé"
	 * 
	 * @param iban
	 * @return String
	 */
	public String getCountryCode(String iban) {
		String regex = "^([A-Za-z]{2})";
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(iban);
        
		return matcher.find() ? matcher.group(1) : "Aucun code pays trouvé";
	}
	
	/**
	 * Prend un iban de type String en parametre
	 * renvoi le numero de controle ou "Aucun nombre de contrôle trouvé"
	 * 
	 * @param iban
	 * @return String
	 */
	public String getControlNumber(String iban) {
		String regex = "^([A-Za-z]{2})(\\d{2})";
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(iban);
        
		return matcher.find() ? matcher.group(2) : "Aucun nombre de contrôle trouvé";
	}
	
	/**
	 * Prend un iban de type String en parametre
	 * renvoi le BBAN ou "aucun BBAN trouvé"
	 * 
	 * @param iban
	 * @return String
	 */
	public String getBBAN(String iban) {
		String regex = "^([A-Za-z]{2})(\\d{2})(\\w+)$";
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(iban);
        
		return matcher.find() ? matcher.group(3) : "Aucun BBAN trouvé";
	}
}







