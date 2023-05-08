package NPPackage;

public class MenuDesigner {

	public MenuDesigner() {	}
	
	public static void drawMainMenu() {
		System.out.println("_________________________________________________");
		System.out.println("|                      Menu                     |");
		System.out.println("|_______________________________________________|");
		System.out.println("|1 - Cadastrar alunos                           |");
		System.out.println("|2 - Listar alunos                              |");
		System.out.println("|3 - Cadastrar matéria                          |");
		System.out.println("|4 - Listar matéria                             |");
		System.out.println("|5 - Cadastrar rendimentos                      |");
		System.out.println("|6 - Relatórios                                 |");
		System.out.println("|7 - Sair                                       |");
		System.out.println("|_______________________________________________|");
		System.out.println("\nEscolha uma opção: ");
	}
	
	public static void drawListagemAlunoMenu() {
		System.out.println("_________________________________________________");
		System.out.println("|                Listar por                     |");
		System.out.println("|1 - Listar todos os alunos                     |");
		System.out.println("|2 - Listar alunos por RA                       |");
		System.out.println("|3 - Voltar                                     |");
		System.out.println("|_______________________________________________|");
		System.out.println("\nEscolha uma opção de listagem: ");
	}
	
	public static void drawListagemCursoMenu() {
		System.out.println("_________________________________________________");
		System.out.println("|                Listar por                     |");
		System.out.println("|1 - Todos as Materias/Cursos                   |");
		System.out.println("|2 - Por ano da Materias/Curso                  |");
		System.out.println("|3 - Voltar                                     |");
		System.out.println("|_______________________________________________|");
		System.out.println("\nEscolha uma opção de listagem: ");
	}
	
	public static void drawListagemRelatorioMenu() {
		System.out.println("_________________________________________________");
		System.out.println("|           Listar relatório por                |");
		System.out.println("|1 - Aluno                                      |");
		System.out.println("|2 - Curso                                      |");
		System.out.println("|3 - Voltar                                     |");
		System.out.println("|_______________________________________________|");
		System.out.println("\nEscolha uma opção de listagem: ");
	}
	
	public static void drawNivelCurso() {
		System.out.println("_________________________________________________");
		System.out.println("|                  Escolher                     |");
		System.out.println("|1 - Graduação                                  |");
		System.out.println("|2 - Pós Graduação                              |");
		System.out.println("|_______________________________________________|");
		System.out.println("\nEscolha o nível do curso: ");
	}
	
	public static void drawCursoRendimento() {
		System.out.println("         Entre com os dados do curso           ");
		System.out.println("_______________________________________________");
	}
	
	public static void drawAlunoRendimento() {
		System.out.println("         Entre com os dados do curso           ");
		System.out.println("_______________________________________________");
	}
	
	public static void drawHasReposicao() {
		System.out.println("_________________________________________________");
		System.out.println("|          Foi aplicado reposição?              |");
		System.out.println("|1 - Sim                                        |");
		System.out.println("|2 - Não                                        |");
		System.out.println("|_______________________________________________|");
		System.out.println("\nResposta: ");
	}

}
