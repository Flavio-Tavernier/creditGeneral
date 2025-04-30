package cgb.classesMetier.iban;

public class CGBIbanValidator {
	private static CGBIbanValidator instance;
	
	private CGBIbanValidator() {
		
	}
	
	/**
	 * Singleton
	 * renvoie une instance de la calsse CGBIbanValidator
	 * 
	 * @return CGBIbanValidator
	 */
	public static CGBIbanValidator getInstance() {
		return instance != null ? instance : new CGBIbanValidator();
	}
}
