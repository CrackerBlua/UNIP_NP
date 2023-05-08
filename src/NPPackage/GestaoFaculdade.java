package NPPackage;

import java.io.IOException;
import java.util.*;

import NPPackage.Rendimento.NotaValorException;

public class GestaoFaculdade {

	public static FileService fileService = FileService.getInstance();
	static Scanner sc = new Scanner(System.in);
	static boolean breakLoop = false;
	
	public static void main(String[] args) throws NotaValorException, IOException {
		FileService.checkExistsFolder();
		GestaoFaculdadeBO.loadData();
		GestaoFaculdadeBO.executeMenu();
	}
}
