package NPPackage;

import java.util.*;

import NPPackage.Rendimento.NotaValorException;

public class MenuBO {

	public MenuBO() {/* Constructor*/}

	static boolean breakMainMenu 				= false;
	static boolean breakListAlunoMenu 			= false;
	static boolean breakListCursoMenu 			= false;
	static boolean breakListRelatorioMenu 		= false;
	static boolean breakListNivelCursoMenu 		= false;
	static boolean breakListhasReposicao 		= false;
	static Scanner sc 							= new Scanner(System.in);
	
	public static void executeMainMenu() throws NotaValorException {
		mainMenu();
	}
	
	private static void mainMenu() throws NotaValorException {
		try {
			while(!breakMainMenu) {
				MenuDesigner.drawMainMenu();
				mainMenuOptions(sc.nextInt());				
			}
		} catch (InputMismatchException err) {
			Utils.throwMessageToUser(err, "Erro entrar com dados!");
		}
	}
	
	private static void mainMenuOptions(int option) throws NotaValorException {
		try {
			switch(option) {
				case 1: AlunoBO.cadastrarAluno(AlunoBO.createAluno(sc)); break;
				case 2: listingAlunoMenu(); break;
				case 3: CursoBO.cadastrarCurso(CursoBO.createCurso(sc)); break;
				case 4: listingCursoMenu(); break;
				case 5: RendimentoBO.cadastrarRendimento(RendimentoBO.createRendimento(sc)); break;
				case 6: listingRelatorioMenu(); break;
				case 7: breakMainMenu = true; break;
				default: throw new MenuErrorException("Valor entrado para o menu principal é inválido, escolha um novo valor válido");
			}
		} catch (MenuErrorException err) {
			Utils.throwMessageToUser(err, "Erro ao entrar dados no menu!");
		}
	}
	
	private static void listingRelatorioMenu() {
		while(!breakListRelatorioMenu) {
			CommandUtils.clearScreen(15);
			MenuDesigner.drawListagemRelatorioMenu();
			listingRelatorioMenuOption(sc.nextInt());
		}
	}
	
	private static void listingRelatorioMenuOption(int option) {
		try {
			switch(option) {
				case 1: printReportByAluno(); break;
				case 2: printReportByCurso(); break;
				case 3: breakListRelatorioMenu = true; CommandUtils.clearScreen(20); break;
				default: throw new MenuErrorException("Valor entrado para o menu de listagem de alunos esta incorreto, escolher um valor válido!");
			}
		} catch (MenuErrorException err) {
			Utils.throwMessageToUser(err, "Erro ao entrar dados no menu!");
		}
	}
	
	private static void printReportByAluno() {
		System.out.println("\nEntre com o RA do aluno:");
		String ra = sc.next();
		Rendimento.listRendimentosByAluno(ra);
	}
	
	private static void printReportByCurso() {
		CommandUtils.clearScreen(15);
		
		boolean hasCurso = false;
		String cursoKey = "";
		List<String> answers = new ArrayList<String>();
		
		while(!hasCurso) {
			System.out.println("\nEntre com o Nome do Curso: " );
			answers.add(sc.next());
			
			MenuBO.createCursoMenu(answers);
			
			System.out.println("\nEntre com o Ano do Curso: " );
			answers.add(String.valueOf(MenuBO.sc.next()));
			
			cursoKey = CursoBO.createCursoKey(answers.get(0), answers.get(1), answers.get(2));
			
			if(!CursoBO.hasCursoByKey(cursoKey)) {
				System.out.println("Não foi encontrado nenhum curso com esses dados!\n Entre com dados de um curso válido!");
				CommandUtils.awaitUntil();
				continue;
			}
			
			break;
		}

		Rendimento.listRendimentosByCurso(cursoKey);
	}
	
	private static void listingCursoMenu() {
		while(!breakListCursoMenu) {
			CommandUtils.clearScreen(15);
			MenuDesigner.drawListagemCursoMenu();
			listingCursoMenuOptions(sc.nextInt());
		}
		
		breakListCursoMenu = false;
	}
	
	private static void listingCursoMenuOptions(int option) {
		try {
			switch(option) {
				case 1: printAllCursos(); break;
				case 2: printCursosByYear(); break;
				case 3: breakListCursoMenu = true; CommandUtils.clearScreen(20); break;
				default: throw new MenuErrorException("Valor entrado para o menu de listagem de alunos esta incorreto, escolher um valor válido!");
			}
		} catch (MenuErrorException err) {
			Utils.throwMessageToUser(err, "Erro ao entrar dados no menu!");
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
	
	public static void createCursoMenu(List<String> answers) {
		while(!breakListNivelCursoMenu) {
			MenuDesigner.drawNivelCurso();
			int option = sc.nextInt();
			createCursoMenuOptions(option,answers);
		}
		
		breakListNivelCursoMenu = false;
	}
	
	private static void createCursoMenuOptions(int option, List<String> answers) {
		try {
			switch(option) {
				case 1: setGraduacao(answers); break;
				case 2: setPosGraduacao(answers); break;
				default: throw new MenuErrorException("Valor entrado para o menu de listagem de cursos esta incorreto, escolher um valor válido!");
			}
		} catch (MenuErrorException err) {
			Utils.throwMessageToUser(err, "Erro ao entrar dados no menu!");
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
		
		breakListAlunoMenu = false;
	}
	
	private static void listingAlunoMenuOptions(int option) {
		try {
			switch(option) {
				case 1: printAllAlunos(); break;
				case 2: printAlunoByRA(); break;
				case 3: breakListAlunoMenu = true; CommandUtils.clearScreen(20); break;
				default: throw new MenuErrorException("Valor entrado para o menu de listagem de alunos esta incorreto, escolher um valor válido!");
			}
		} catch (MenuErrorException err) {
			Utils.throwMessageToUser(err, "Erro ao entrar dados no menu!");
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
	
	public static void listingHasReposicao(Rendimento rendimento) {
		while(!breakListhasReposicao) {
			MenuDesigner.drawHasReposicao();
			listinghasReposicaoOptions(sc.nextInt(), rendimento);
		}
		
		breakListhasReposicao = false;
	}
	
	private static void listinghasReposicaoOptions(int option, Rendimento rendimento) {
		try {
			switch(option) {
				case 1: setReposicao(rendimento); breakListhasReposicao = true; break;
				case 2: breakListhasReposicao = true; CommandUtils.clearScreen(3); break;
				default: throw new MenuErrorException("Valor entrado para o menu de listagem esta incorreto, escolher um valor válido!");
			}
		} catch (MenuErrorException err) {
			Utils.throwMessageToUser(err, "Erro ao entrar dados no menu!");
		} catch (NotaValorException err) {
			Utils.throwMessageToUser(err, "Erro ao inserir uma nota!");
		}
	}
	
	private static void setReposicao(Rendimento rendimento) throws NotaValorException {
		System.out.println("Qual foi a nota na Reposição?");
		rendimento.setReposicao(sc.nextDouble());
	}
	
	public static class MenuErrorException extends Exception {
		/******/
		private static final long serialVersionUID = 1L;
		public MenuErrorException(String errorMsg) { super(errorMsg); }
	}
}
