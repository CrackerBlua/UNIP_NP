package NPPackage;

import java.io.IOException; 
import NPPackage.Utils.*;

public class GestaoFaculdadeBO {
		
	public static void loadData() throws NotaValorException, IOException {
		FileService.loadData();
	}
	
	public static void executeMenu() throws NotaValorException {
		MenuBO.executeMainMenu();
	}
}
