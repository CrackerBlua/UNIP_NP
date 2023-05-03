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
		System.out.println("|5 - Sair                                       |");
		System.out.println("|_______________________________________________|");
		System.out.println("Escolha uma opção: ");
	}
	
	public static void drawListagemAlunoMenu() {
		System.out.println("_________________________________________________");
		System.out.println("|                   Listar por                  |");
		System.out.println("|1 - Listar todos os alunos                     |");
		System.out.println("|2 - Listar alunos por RA                       |");
		System.out.println("|3 - Voltar                                     |");
		System.out.println("|_______________________________________________|");
		System.out.println("Escolha uma opção de listagem listar: ");
	}
	
	public static void drawListagemCursoMenu() {
		System.out.println("_________________________________________________");
		System.out.println("|                   Listar por                  |");
		System.out.println("|1 - Todos as Materias/Cursos                   |");
		System.out.println("|2 - Por ano da Materias/Curso                  |");
		System.out.println("|3 - Voltar                                     |");
		System.out.println("|_______________________________________________|");
		System.out.println("Escolha uma opção de listagem listar: ");
	}
	
	public static void drawNivelCurso() {
		System.out.println("_________________________________________________");
		System.out.println("|                   Listar por                  |");
		System.out.println("|1 - Graduação                                  |");
		System.out.println("|2 - Pós Graduação                              |");
		System.out.println("|_______________________________________________|");
		System.out.println("Escolha o nível do curso: ");
	}

}
