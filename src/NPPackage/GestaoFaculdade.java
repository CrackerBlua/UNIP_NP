package NPPackage;

import java.io.IOException;
import java.util.*;
import NPPackage.Utils.*;

public class GestaoFaculdade {

	static Scanner sc = new Scanner(System.in);
	static boolean breakLoop = false;
	
	public static void main(String[] args) throws NotaValorException, IOException {
		FileService.checkExistsFolder();
		GestaoFaculdadeBO.loadData();
		GestaoFaculdadeBO.executeMenu();
	}
}
