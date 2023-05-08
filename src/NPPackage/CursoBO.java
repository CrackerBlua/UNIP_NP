package NPPackage;

import NPPackage.Utils.PatternErrorException;
import java.util.*;
import NPPackage.Curso.Formacao;

public class CursoBO {

	public CursoBO() { /* TODO Auto-generated constructor stub */ }
	
	static String createCursoKey(String nome, String nivel, String ano) {
		return nome + ";" + nivel + ";" + ano;
	}
	
	static String createCursoKey(String nome, String nivel, int ano) {
		return nome + ";" + nivel + ";" + String.valueOf(ano);
	}
	
	static String getCursoKey(Curso curso) {
		return curso.getNome() + ";" + curso.getNivel() + ";" + curso.getAno();
	}
	
	static boolean hasCursoByKey(String key) {
		return Curso.getMapCursos().containsKey(key);
	}
	
	static Curso getCursoByKey(String key) {
		return Curso.getMapCursos().get(key);
	}
	
	static void showAllCursos() {
		CommandUtils.clearScreen(15);
		System.out.println("Cursos cadastrados: ");

		for(String str: Curso.getMapCursos().keySet()) 
			System.out.println(Curso.getMapCursos().get(str));
		
		CommandUtils.awaitUntil();
	}
	
	static void showCursosByYear(String year) {
		showCursosByYear(Integer.valueOf(year));
	}
	
	static void showCursosByYear(int year) {
		CommandUtils.clearScreen(15);
		System.out.println("Cursos no ano de " + String.valueOf(year) + " :");
		
		for(Curso curso: Curso.getMapCursos().values())
			if(curso.getAno() == year) System.out.println(curso);
		
		CommandUtils.awaitUntil();
	}
	
	static String getFormacaoNivel(int option) {
		switch(option) {
			case 1: return Formacao.GRADUACAO.toString();
			case 2: return Formacao.POS_GRADUACAO.toString();
			default: return "";
		}
	}
	
	static Curso createCurso(Scanner sc) {
		List<String> answers = new ArrayList<String>();  

		answers.add(askNomeCurso(sc));
		MenuBO.createCursoMenu(answers);
		answers.add(askYear(sc));
		
		if(!CursoBO.hasCursoByKey(CursoBO.createCursoKey(answers.get(0), answers.get(1), answers.get(2)))) {
			return new Curso(answers.get(0), answers.get(1), answers.get(2));
		}
		
		return null;
	}
	
	static String askNomeCurso(Scanner sc) {
		String name = "";
		boolean byPass = false;
		
		while(!byPass) {
			try {
				name = inputNomeCurso(sc);
				
				if(Utils.pattern.matcher(name).matches()) 
					throw new PatternErrorException("Valor entrado não consiste como Nome para o curso, só é aceito caracteres!");
				
				byPass = true; break;
			} catch(PatternErrorException ex) { Utils.throwMessageToUser(ex, "Erro na entrada do Nome do Curso!"); }				
		}
		
		return name;
	}
	
	private static String inputNomeCurso(Scanner sc) {
		String name = "";
				
		System.out.println("\nEntre com o Nome do Curso: " );
		name = sc.next(); name += sc.hasNextLine()? sc.nextLine(): "";
		
		return name.toUpperCase();
	}
	
	static String askYear(Scanner sc) {
		String year = "";
		boolean byPass = false;
		
		while(!byPass) {
			try {
				year = inputYear(sc); 
				
				if(year.length() != 4)
					throw new PatternErrorException("O valor do Ano do Curso não está correto, entre com um valor válido!");

				if(!Utils.pattern.matcher(year).matches()) 
					throw new PatternErrorException("Valor entrado não consiste como Ano para o curso, só é aceito caracteres!");
				
				byPass = true; break;
			} catch(PatternErrorException ex) { Utils.throwMessageToUser(ex, "Erro na entrada do Ano do Curso!"); }				
		}
		
		return year;
	}
	
	private static String inputYear(Scanner sc) {
		String year = "";
		
		System.out.println("\nEntre com o Ano do Curso: " );
		year = sc.next();
		
		return year;
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
