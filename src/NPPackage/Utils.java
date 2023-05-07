package NPPackage;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}
	
	static void throwMessageToUser(Exception err, String title) {
		System.out.println(title);
		System.out.println("Erro: \n" + err.getMessage() + "\n" + err.getStackTrace());
		CommandUtils.awaitUntil();
	}

}
