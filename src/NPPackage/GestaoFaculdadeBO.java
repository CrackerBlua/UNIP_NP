package NPPackage;

import java.util.*;

public class GestaoFaculdadeBO {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void loadData() {
		FileService.loadData();
	}
	
	public static void executeMenu() {
		int choiceMenu = 0;
		try (Scanner sc = new Scanner(System.in)) {
			while (choiceMenu != 5) {
				Menu.mainMenu();
				System.out.println("\nEscolha uma opção: ");
				choiceMenu = sc.nextInt();
				Menu.menuOperation(choiceMenu);
			}
		}	
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
	
	public static Aluno createCurso() {
		List<String> answers = new ArrayList<String>();  

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Entre com o Nome do Curso: " );
			answers.add(sc.next());
			
			System.out.println("Entre com o Nível do Curso: \n 1 - Graduação\n 2 - Pós-Graduação\n");
			answers.add(sc.next());
			
			System.out.println("Entre com o Nome do aluno: " );
			answers.add(sc.next());
		}
		
		if(!Aluno.hasAlunoById(answers.get(0))) {
			return new Aluno(answers.get(0), answers.get(1));
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
	


}
