package cgb.transfert.iban;

public class CGBIbanValidator {
	private static CGBIbanValidator instance;
	
	private CGBIbanValidator() {
		
	}
	
	public static CGBIbanValidator getInstance() {
		return instance != null ? instance : new CGBIbanValidator();
	}
}
