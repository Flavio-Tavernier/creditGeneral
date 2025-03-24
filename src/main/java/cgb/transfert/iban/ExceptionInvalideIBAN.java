package cgb.transfert.iban;

public abstract class ExceptionInvalideIBAN extends Exception {
	public ExceptionInvalideIBAN(String errorMessage) {
        super(errorMessage);
    }
}
