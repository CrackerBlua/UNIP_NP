package NPPackage;

import java.util.*;

public class GestaoFaculdade {

	public static FileService fileService = FileService.getInstance();
	static Scanner sc = new Scanner(System.in);
	static boolean breakLoop = false;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			GestaoFaculdadeBO.executeMenu();
			GestaoFaculdadeBO.loadData();
			FileService.checkExistsFolder();
		}catch (InputMismatchException ex) {
			System.out.println("Valor entrado não é um número, entre com um valor válido!");
		}
	}
}
