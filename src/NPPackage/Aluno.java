package NPPackage;

import java.util.*;

public class Aluno {

	private String id = ""; //RA <--> ID
	private String name = "";	
	private static Map<String, Aluno> mapAlunos = new HashMap<String, Aluno>();

	public Aluno() {/* TODO Auto-generated constructor stub */}

	public Aluno(String id, String name) {
		this.id 	= id;
		this.name 	= name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Map<String, Aluno> getMapAlunos() {
		return mapAlunos;
	}
	
	public static void setMapAlunos(String ra, Aluno aluno) {
		if(!mapAlunos.containsKey(ra)) mapAlunos.put(ra, new Aluno());
		
		mapAlunos.put(ra, aluno);
	}
	
	public static boolean hasAlunoById(String id) {
		return AlunoBO.hasAlunoById(id);
	}
	
	public static Aluno getAlunoById(String RA) {
		return AlunoBO.getAlunoById(RA);
	}
	
	public static void showAlunosCadastrados() {
		AlunoBO.showAlunosCadastrados();
	}
	
	public static void showAlunosCadastrados(String ra) {
		AlunoBO.showAlunosCadastrados(ra);
	}
	
	public static void upsertAluno() {
		AlunoBO.upsertAluno();
	}
	
	@Override
	public String toString() {
		return "Aluno: " + getName() + " - RA: " + getId();
	}
}
