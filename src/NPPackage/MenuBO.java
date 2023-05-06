package NPPackage;

import java.util.*;

public class MenuBO {

	public MenuBO() {/* Constructor*/}

	static boolean breakMainMenu 			= false;
	static boolean breakListAlunoMenu 		= false;
	static boolean breakListCursoMenu 		= false;
	static boolean breakListNivelCursoMenu 	= false;

	static Scanner sc 						= new Scanner(System.in);
	
	public static void executeMainMenu() {
		mainMenu();
	}
	
	private static void mainMenu() {
		try {
			while(!breakMainMenu) {
				MenuDesigner.drawMainMenu();
				mainMenuOptions(sc.nextInt());				
			}
		} catch (InputMismatchException ex) {
			System.out.println("Valor entrado não é um número, entre com um valor válido!");
		}
	}
	
	private static void mainMenuOptions(int option) {
		try {
			switch(option) {
				case 1: AlunoBO.cadastrarAluno(AlunoBO.createAluno(sc)); break;
				case 2: listingAlunoMenu(); break;
				case 3: CursoBO.cadastrarCurso(CursoBO.createCurso()); break;
				case 4: listingCursoMenu(); break;
				case 5: listingCursoMenu(); break;

				default: throw new MenuErrorException("Valor entrado para o menu principal é inválido, escolha um novo valor válido");
			}
		} catch (MenuErrorException err) {
			System.out.println("Erro: " + err.getMessage() + "\n");
			CommandUtils.awaitUntil();
		}
	}
	
	private static void listingCursoMenu() {
		while(!breakListCursoMenu) {
			CommandUtils.clearScreen(15);
			MenuDesigner.drawListagemCursoMenu();
			listingCursoMenuOptions(sc.nextInt());
		}
	}
	
	private static void listingCursoMenuOptions(int option) {
		try {
			switch(option) {
				case 1: printAllCursos(); break;
				case 2: printCursosByYear(); break;
				case 3: breakListAlunoMenu = true; break;
				default: throw new MenuErrorException("Valor entrado para o menu de listagem de alunos esta incorreto, escolher um valor válido!");
			}
		} catch (MenuErrorException err) {
			System.out.println("Erro: " + err.getMessage() + "\n");
			CommandUtils.awaitUntil();
		}
	}
	
	private static void printAllCursos() {
		CommandUtils.clearScreen(15);
		Curso.showAllCursos();
	}
	
	private static void printCursosByYear() {
		System.out.println("Digite o Ano do curso: ");
		Curso.showCursosByYear(sc.nextInt());
	}
	
	public static void createCursoMenu(int option, List<String> answers) {
		while(!breakListNivelCursoMenu) {
			MenuDesigner.drawNivelCurso();
			createCursoMenuOptions(sc.nextInt(), answers);
		}
	}
	
	private static void createCursoMenuOptions(int option, List<String> answers) {
		try {
			switch(option) {
				case 1: setGraduacao(answers); break;
				case 2: setPosGraduacao(answers); break;
				default: throw new MenuErrorException("Valor entrado para o menu de listagem de cursos esta incorreto, escolher um valor válido!");
			}
		} catch (MenuErrorException err) {
			System.out.println("Erro: " + err.getMessage() + "\n");
			CommandUtils.awaitUntil();
		}
	}
	
	private static void setGraduacao(List<String> answers) {
		answers.add(CursoBO.getFormacaoNivel(1));
		breakListNivelCursoMenu = true;
	}	
	
	private static void setPosGraduacao(List<String> answers) {
		answers.add(CursoBO.getFormacaoNivel(2));
		breakListNivelCursoMenu = true;
	}
	
	private static void listingAlunoMenu() {
		while(!breakListAlunoMenu) {
			CommandUtils.clearScreen(15);
			MenuDesigner.drawListagemAlunoMenu();
			listingAlunoMenuOptions(sc.nextInt());
		}
	}
	
	private static void listingAlunoMenuOptions(int option) {
		try {
			switch(option) {
				case 1: printAllAlunos(); break;
				case 2: printAlunoByRA(); break;
				case 3: breakListAlunoMenu = true; break;
				default: throw new MenuErrorException("Valor entrado para o menu de listagem de alunos esta incorreto, escolher um valor válido!");
			}
		} catch (MenuErrorException err) {
			System.out.println("Erro: " + err.getMessage() + "\n");
			CommandUtils.awaitUntil();
		}
	}
	
	private static void printAllAlunos() {
		CommandUtils.clearScreen(15);
		Aluno.showAlunosCadastrados();
	}
	
	private static void printAlunoByRA() {
		System.out.println("Digite o RA do aluno: ");
		Aluno.showAlunosCadastrados(sc.next());
	}
	
	public static class MenuErrorException extends Exception {
		/******/
		private static final long serialVersionUID = 1L;
		public MenuErrorException(String errorMsg) { super(errorMsg); }
	}
}
