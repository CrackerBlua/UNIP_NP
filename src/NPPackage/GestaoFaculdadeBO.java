package NPPackage;

public class GestaoFaculdadeBO {
		
	public static void loadData() {
		FileService.loadData();
	}
	
	public static void executeMenu() {
		MenuBO.executeMainMenu();
	}
}
