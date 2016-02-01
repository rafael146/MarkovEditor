package exception;

public class PesoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1766869276440092080L;

	public PesoException(){
		super("A soma das probabilidades de cada grupo deve ser menor ou igual a 1");
	}
}
