package NPPackage;

import java.util.*;

public class Curso {
	
	private int ano 		= 0;
	private String nome 	= "";
	private String nivel 	= "";
	private static Map<String, Curso> mapCursos = new HashMap<String, Curso>();
	public enum Formacao { GRADUACAO, POS_GRADUACAO;}

	public Curso() { /* TODO Auto-generated constructor stub */ }

	public Curso(String nome, String nivel, int ano) {
		this.nome 	= nome;
		this.ano 	= ano;
		this.nivel	= nivel;
	}
	
	public Curso(String nome, String nivel, String ano) {
		this.nome 	= nome;
		this.ano 	= Integer.valueOf(ano);
		this.nivel	= nivel;
	}
	
	public String getNome() { return nome; }

	public void setNome(String nome) { this.nome = nome.toUpperCase(); }

	public int getAno() { return ano; }

	public void setAno(int ano) { this.ano = ano; }

	public String getNivel() { return nivel; }

	public void setNivel(String nivel) { this.nivel = nivel; }
	
	public static Map<String, Curso> getMapCursos() { return mapCursos; }

	public static void setMapCursos(String key, Curso curso) {
		if(!mapCursos.containsKey(key)) mapCursos.put(key, new Curso()); mapCursos.put(key, curso);
	}
	
	public static String createCursoKey(String nome, String nivel, String ano) { return CursoBO.createCursoKey(nome, nivel, ano); }
	
	public static String createCursoKey(String nome, String nivel, int ano) { return CursoBO.createCursoKey(nome, nivel, ano); }
	
	public static String getCursoKey(Curso curso) { return CursoBO.getCursoKey(curso); }
	
	public static boolean hasCursoByKey(String key) { return CursoBO.hasCursoByKey(key); }
	
	public static void upsertCursos() { FileService.upsertCurso(getMapCursos()); }
	
	public static String getFormacaoNivel(int option) { return CursoBO.getFormacaoNivel(option); }
	
	public static void showAllCursos() { CursoBO.showAllCursos(); }
	
	public static void showCursosByYear(int year) { CursoBO.showCursosByYear(year); }

	public static void showCursosByYear(String year) { CursoBO.showCursosByYear(year); }

	@Override
	public String toString() { return "|Nome: " + getNome() + "| - |Nível: " + getNivel() + "| - |Ano: " + getAno() + "|"; }
}
