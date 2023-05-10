package NPPackage;

import java.util.*;
import NPPackage.Utils.PatternErrorException;

public class AlunoBO {
	
	static boolean hasAlunoById(String id) { return Aluno.getMapAlunos().containsKey(id); }
	
	static Aluno getAlunoById(String RA) { return Aluno.getMapAlunos().get(RA); }
	
	static void upsertAluno() { FileService.upsertAlunoRecord(Aluno.getMapAlunos()); }
	
	public static Aluno createAluno(Scanner sc) {
		List<String> answers = new ArrayList<String>();  
		
		answers.add(askRA(sc));
		answers.add(askName(sc));
		if(!Aluno.hasAlunoById(answers.get(0))) return new Aluno(answers.get(0), answers.get(1));

		return null;
	}
	
	static String askRA(Scanner sc) {
		boolean byPass = false;
		String ra = "";
		
		while(!byPass) {
			try {
				ra = inputRA(sc);
				if(!Utils.pattern.matcher(ra).matches()) 
					throw new PatternErrorException("Valor entrado não consiste como RA, apenas valores numéricos!");
				byPass = true; break;
			} catch(PatternErrorException ex) { Utils.throwMessageToUser(ex, "\nErro na entrada do RA do Aluno!"); }				
		}
		
		return ra;
	}
	
	private static String inputRA(Scanner sc) {
		String ra = "";
		
		System.out.println("\nEntre com o RA do aluno: " ); 
		ra = sc.next();
		
		return ra;
	}
	
	static String askName(Scanner sc) {
		boolean byPass = false;
		String name = "";
		
		while(!byPass) {
			try {
				name = inputName(sc);
				
				if(Utils.pattern.matcher(name).matches()) 
					throw new PatternErrorException("Valor entrado não consiste como Nome para o Aluno, só é aceito caracteres!");
				
				byPass = true; break;
			} catch(PatternErrorException ex) { Utils.throwMessageToUser(ex, "\nErro na entrada do Nome do Aluno!"); }	
		}
		
		return name;
	}
	
	private static String inputName(Scanner sc) {
		String name = "";
		
		System.out.println("\nEntre com o Nome do aluno: " );
		name = sc.next(); name += sc.hasNextLine()? sc.nextLine(): "";
		
		return name;
	}
	
	static void cadastrarAluno(Aluno aluno) {
		if(aluno == null) {
			System.out.println("Não é possível criar um aluno com esse RA, pois já está em uso por outro aluno!"); CommandUtils.awaitUntil();
		} else {
			Aluno.setMapAlunos(aluno.getId(), aluno); Aluno.upsertAluno();
		}
	}
	
	static void showAlunosCadastrados(String ra) {
		System.out.println("\nResultado: ");
		System.out.println(Aluno.getMapAlunos().containsKey(ra)? "=> " + Aluno.getMapAlunos().get(ra): "Aluno não encontrado!");
		CommandUtils.awaitUntil();
	}
	
	static void showAlunosCadastrados() {
		CommandUtils.clearScreen(15);
		System.out.println("Alunos cadastrados:");

		for(String str: Aluno.getMapAlunos().keySet()) 
			System.out.println("=> " + Aluno.getMapAlunos().get(str));
		
		CommandUtils.awaitUntil();
	}
}
