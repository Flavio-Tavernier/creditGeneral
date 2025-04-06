package cgb.classesMetier.iban;

public abstract class ExceptionInvalideIBAN extends Exception {
	public ExceptionInvalideIBAN(String errorMessage) {
        super(errorMessage);
    }
}
