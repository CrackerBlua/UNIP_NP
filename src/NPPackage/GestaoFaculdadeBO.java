package NPPackage;

import java.io.IOException;

import NPPackage.Rendimento.NotaValorException;

public class GestaoFaculdadeBO {
		
	public static void loadData() throws NotaValorException, IOException {
		FileService.loadData();
	}
	
	public static void executeMenu() throws NotaValorException {
		MenuBO.executeMainMenu();
	}
}
