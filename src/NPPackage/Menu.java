package NPPackage;

import java.util.*;

public class Menu {

	static Scanner sc = new Scanner(System.in);
	
	public Menu() {
		// TODO Auto-generated constructor stub
	}
	
	public static void mainMenu() {
		System.out.println("_________________________________________________");
		System.out.println("|                      Menu                     |");
		System.out.println("|_______________________________________________|");
		System.out.println("|1 - Cadastrar alunos                           |");
		System.out.println("|2 - Listar alunos                              |");
		System.out.println("|3 - Cadastrar matéria                          |");
		System.out.println("|4 - Listar matéria                             |");
		System.out.println("|5 - Sair                                       |");
		System.out.println("|_______________________________________________|");
	}
	
	static void menuOperation(int choiceMenu) {
		try {
			switch(choiceMenu) {
				case 1:
					GestaoFaculdadeBO.cadastrarAluno(GestaoFaculdadeBO.createAluno());
					break;
				case 2:
					CommandUtils.clearScreen(10);
					listMenu("aluno");
					break;
				case 3:
					//To-do
				case 4:
					//To-do
				case 5:
					System.out.println("Encerrando o programa...");
					System.exit(0);
					break;
				default:
					throw new MenuErrorException("Valor entrado não consta na lista, escolha um novo valor válido");
			}
		} catch(MenuErrorException err) {
			System.out.println(
					"MenuErrorException: \n" +
					err.getMessage() + "\n" 
			);
		}
	}
	
	public static void listMenu(String type) {
		int listMenu = 0;
		
		while(listMenu != 3) {
			Menu.listagensMenu("aluno");
			System.out.println("Escolha uma opção de listagem listar: ");
			listMenu = sc.nextInt();
			listMenuHelper(listMenu);
		}
	}
	
	public static void listagensMenu(String type) {
		switch (type) {
			case "aluno":
				System.out.println("_________________________________________________");
				System.out.println("|                   Listar por                  |");
				System.out.println("|1 - Listar todos os alunos                     |");
				System.out.println("|2 - Listar alunos por RA                       |");
				System.out.println("|3 - Voltar                                     |");
				System.out.println("|_______________________________________________|");
				break;
		}
	}
	
	public static void listMenuHelper(int option) {
		try {
			switch (option) {
				case 1:
					CommandUtils.clearScreen(5);
					Aluno.showAlunosCadastrados();
					break;
				case 2:
					System.out.println("Digite o RA: ");
					String ra = sc.next();
					Aluno.showAlunosCadastrados(ra);
					break;
				case 3:
					break;
				default:
					throw new MenuErrorException("Valor entrado não consta na lista, escolha um novo valor válido");
			}
		} catch (MenuErrorException err) {
			System.out.println(
					"MenuErrorException: \n" +
					err.getMessage() + "\n"
			);
		}
	}
	
	public static class MenuErrorException extends Exception {
		/**
		 * 
		 * 
		 **/
		private static final long serialVersionUID = 1L;

		public MenuErrorException(String errorMsg) { super(errorMsg); }
	}

}
