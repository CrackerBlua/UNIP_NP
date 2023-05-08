package NPPackage;

import java.util.regex.Pattern;

public class Utils {
	
	//Pattern para a entrada de dados
	static Pattern pattern = Pattern.compile("\\d+");
	static Pattern yearPattern = Pattern.compile("^{4}$");
	
	//Utils para padronizar o throw de erro
	static void throwMessageToUser(Exception err, String title) {
		System.out.println("\n => " +title + " <= \n");
		System.out.println("Erro: " + err.getMessage() + "\n");
		CommandUtils.awaitUntil();
	}

	//Utils para padronizar o throw de erro
	static void throwMessageToUser(Exception err, String title, boolean clearScreen) {
		System.out.println("\n => " +title + " <= \n");
		System.out.println("Erro: " + err.getMessage() + "\n\n" + err.getStackTrace());
		CommandUtils.awaitUntil(clearScreen);
	}
	
	/* Error handlers */
	
	public static class PatternErrorException extends Exception {
		/******/
		private static final long serialVersionUID = 1L;
		public PatternErrorException(String errorMsg) { super(errorMsg); }
	}
	
	public static class MenuErrorException extends Exception {
		/******/
		private static final long serialVersionUID = 1L;
		public MenuErrorException(String errorMsg) { super(errorMsg); }
	}
	
	public static class NotaValorException extends Exception {
		/******/
		private static final long serialVersionUID = 1L;
		public NotaValorException(String errorMsg) { super(errorMsg); }
	}
}
