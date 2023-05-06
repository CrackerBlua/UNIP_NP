package NPPackage;

import java.util.*;

public class GestaoFaculdade {

	public static FileService fileService = FileService.getInstance();
	static Scanner sc = new Scanner(System.in);
	static boolean breakLoop = false;
	
	public static void main(String[] args) {
		FileService.checkExistsFolder();
		GestaoFaculdadeBO.loadData();
		GestaoFaculdadeBO.executeMenu();
	}
}
