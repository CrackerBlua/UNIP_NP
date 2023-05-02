package NPPackage;

import java.util.*;

public class Curso {
	
	private enum Formacao { 
		GRADUACAO, POS_GRADUACAO;
		
		public String getFormacao() {
			switch(this) {
				case GRADUACAO: return "graduacao";
				case POS_GRADUACAO: return "pos_graduacao";
				default: return null;
			}
		}
	}
	
	private String nome 	= "";
	private int ano 		= 0;
	private String nivel 	= "";
	private static Map<String, Curso> mapCursos = new HashMap<String, Curso>();
	
	public Curso() {}

	public Curso(String nome, String nivel, int ano) {
		this.nome 	= nome;
		this.ano 	= ano;
		this.nivel	= nivel;
	}

//	private String getFormacaoNivel(String nivel) {
//		if(Formacao.GRADUACAO.getFormacao().equalsIgnoreCase(nivel)) return Formacao.GRADUACAO.getFormacao();	
//		else if(Formacao.POS_GRADUACAO.getFormacao().equalsIgnoreCase(nivel)) return Formacao.POS_GRADUACAO.getFormacao();
//		return null;
//	}
	
	public static String getFormacaoNivel(int option) {
		if(option == 1) {
			return Formacao.GRADUACAO.name();
		} else if (option == 2) {
			return Formacao.POS_GRADUACAO.name();
		}
		
		return "";
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	public static void saveAlunoRecord() {
		
	}
	
	public static String createCursoKey(String nome, String nivel, String ano) {
		return nome + ";" + nivel + ";" + String.valueOf(ano) + ";";
	}
	
	public static String getCursoKey(Curso curso) {
		return curso.nome + ";" + curso.nivel + ";" + curso.ano + ";";
	}

	public static Map<String, Curso> getMapCursos() {
		return mapCursos;
	}

	public static void setMapCursos(String key, Curso curso) {
		if(!mapCursos.containsKey(key)) {
			mapCursos.put(key, new Curso());
		}

		mapCursos.put(key, curso);
	}
	
	public static boolean hasCursoByKey(String key) {
		return getMapCursos().containsKey(key);
	}
	
	public static void upsertCursos() {
		FileService.upsertCurso(getMapCursos());
	}
	
	public static void showAllCursos() {
		System.out.println("Cursos cadastrados: ");
		Map<String, Curso> mapCursos = getMapCursos();

		for(String str: mapCursos.keySet()) {
			System.out.println(String.format(
					"Nome: %s - Tipo: %s - Ano: %s", 
					 mapCursos.get(str).getNome(),
					 mapCursos.get(str).getNivel(),
					 mapCursos.get(str).getAno()
				)
			);
		}
		CommandUtils.awaitUntil();
	}

	@Override
	public String toString() {
		return "Nome: " + getNome() + "\nNível: " + getNivel() + "\nAno: " + getAno();
	}
}
