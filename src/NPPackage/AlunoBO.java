package NPPackage;

import java.util.*;

public class AlunoBO {

	public AlunoBO() { /* TODO Auto-generated constructor stub */ }
	
	static boolean hasAlunoById(String id) {
		return Aluno.getMapAlunos().containsKey(id);
	}
	
	public static Aluno createAluno(Scanner sc) {
		List<String> answers = new ArrayList<String>();  

		System.out.println("Entre com o RA do aluno: " );
		answers.add(String.valueOf(sc.next()));
			
		System.out.println("Entre com o Nome do aluno: " );
		String name = sc.next();
		answers.add(name += sc.hasNextLine()? sc.nextLine():"");
		
		if(!Aluno.hasAlunoById(answers.get(0))) return new Aluno(answers.get(0), answers.get(1));

		return null;
	}
	
	static void cadastrarAluno(Aluno aluno) {
		if(aluno == null) {
			System.out.println("Não é possível criar um aluno com esse RA, pois já está em uso por outro aluno!");
			CommandUtils.awaitUntil();
		} else {
			Aluno.setMapAlunos(aluno.getId(), aluno);
			Aluno.upsertAluno();
		}
	}
	
	static Aluno getAlunoById(String RA) {
		return Aluno.getMapAlunos().get(RA);
	}
	
	static void upsertAluno() {
		FileService.upsertAlunoRecord(Aluno.getMapAlunos());
	}
	
	static void showAlunosCadastrados(String ra) {
		System.out.println("Aluno: ");
		System.out.println(Aluno.getMapAlunos().get(ra));
		
		CommandUtils.awaitUntil();
	}
	
	static void showAlunosCadastrados() {
		CommandUtils.clearScreen(15);
		System.out.println("Alunos cadastrados: ");

		for(String str: Aluno.getMapAlunos().keySet()) 
			System.out.println(Aluno.getMapAlunos().get(str));
		
		CommandUtils.awaitUntil();
	}
}
