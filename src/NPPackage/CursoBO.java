package NPPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import NPPackage.Curso.Formacao;

public class CursoBO {

	public CursoBO() { /* TODO Auto-generated constructor stub */ }
	
	static String createCursoKey(String nome, String nivel, String ano) {
		return nome + ";" + nivel + ";" + ano;
	}
	
	static String createCursoKey(String nome, String nivel, int ano) {
		return nome + ";" + nivel + ";" + String.valueOf(ano) + ";";
	}
	
	static String getCursoKey(Curso curso) {
		return curso.getNome() + ";" + curso.getNivel() + ";" + curso.getAno();
	}
	
	static boolean hasCursoByKey(String key) {
		return Curso.getMapCursos().containsKey(key);
	}
	
	static void showAllCursos() {
		System.out.println("Cursos cadastrados: ");

		for(String str: Curso.getMapCursos().keySet()) 
			System.out.println(Curso.getMapCursos().get(str));
		
		CommandUtils.awaitUntil();
	}
	
	static void showCursosByYear(int year) {
		CommandUtils.clearScreen(15);
		System.out.println("Cursos no ano de " + String.valueOf(year) + " :");
		
		for(Curso curso: Curso.getMapCursos().values())
			if(curso.getAno() == year) System.out.println(curso);
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

		System.out.println("Entre com o Nome do Curso: " );
		answers.add(sc.next());
		
		MenuBO.createCursoMenu(answers);
		
		System.out.println("Entre com o Ano do Curso: " );
		answers.add(String.valueOf(MenuBO.sc.next()));
		
		if(!CursoBO.hasCursoByKey(CursoBO.createCursoKey(answers.get(0), answers.get(1), answers.get(2)))) {
			return new Curso(answers.get(0), answers.get(1), answers.get(2));
		}
		
		return null;
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
