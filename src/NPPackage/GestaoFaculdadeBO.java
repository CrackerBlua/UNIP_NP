package NPPackage;

import java.util.*;

public class GestaoFaculdadeBO {
		
	public static void loadData() {
		FileService.loadData();
	}
	
	public static void executeMenu() {
		Menu.executeMainMenu();
	}
	
	public static Aluno createAluno() {
		List<String> answers = new ArrayList<String>();  

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Entre com o RA do aluno: " );
			answers.add(String.valueOf(sc.next()));
			
			System.out.println("Entre com o Nome do aluno: " );
			answers.add(sc.next());
		}
		
		if(!Aluno.hasAlunoById(answers.get(0))) {
			return new Aluno(answers.get(0), answers.get(1));
		}

		return null;
	}
	
	public static Curso createCurso() {
		int optionChoice = 0;
		Boolean bypass = false;
		List<String> answers = new ArrayList<String>();  
	
		System.out.println("Entre com o Nome do Curso: " );
		answers.add(Menu.sc.next());
			
		while(!bypass) {
			System.out.println("Entre com o Nível do Curso: \n 1 - Graduação\n 2 - Pós-Graduação");
			optionChoice = Menu.sc.nextInt();
			bypass = optionChoice == 1 || optionChoice == 2? true: false;
			
			if(!bypass) System.out.println("Entre com um valor válido!\n");
		}
		answers.add(Menu.getCursoNivel(optionChoice));
			
		System.out.println("Entre com o Ano do Curso: " );
		answers.add(String.valueOf(Menu.sc.next()));
		
		if(!Curso.hasCursoByKey(Curso.createCursoKey(answers.get(0), answers.get(1), answers.get(2)))) {
			return new Curso(answers.get(0), answers.get(1), Integer.valueOf(answers.get(2)));
		}
		
		return null;
	}
	
	static void cadastrarAluno(Aluno aluno) {
		if(aluno == null) {
			System.out.println("Não é possível criar um aluno com esse RA, pois já está em uso por outro aluno!");
			CommandUtils.awaitUntil(5000);
		} else {
			Aluno.setMapAlunos(aluno.getId(), aluno);
			Aluno.upsertAluno();
		}
	}
	
	static void cadastrarCurso(Curso curso) {
		if(curso == null) {
			System.out.println("Já existe um curso dessa matéria, ano e nível!");
			CommandUtils.awaitUntil(5000);
		} else {
			Curso.setMapCursos(Curso.getCursoKey(curso), curso);
			Curso.upsertCursos();
		}
	}
}
