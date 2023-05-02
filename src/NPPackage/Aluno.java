package NPPackage;

import java.util.*;

public class Aluno {

	private String id = ""; //RA <--> ID
	private String name = "";
	private static Map<String, Aluno> mapAlunos = new HashMap<String, Aluno>();
	FileService fileService = new FileService();

	public Aluno() {}

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
		if(!mapAlunos.containsKey(ra)) {
			mapAlunos.put(ra, new Aluno());
		}
		
		mapAlunos.put(ra, aluno);
	}
	
	public static void showAlunosCadastrados() {
		System.out.println("Alunos cadastrados: ");
		Map<String, Aluno> mapAlunos = getMapAlunos();

		for(String str: mapAlunos.keySet()) {
			System.out.println(String.format(
					"RA: %s - Aluno: %s", 
					 mapAlunos.get(str).getId(),
					 mapAlunos.get(str).getName()
				)
			);
		}
		
		CommandUtils.awaitUntil();
	}
	
	public static void showAlunosCadastrados(String ra) {
		System.out.println("\nAluno: ");
		Map<String, Aluno> mapAlunos = getMapAlunos();

		System.out.println(String.format(
				"RA: %s - Aluno: %s", 
				 mapAlunos.get(ra).getId(),
				 mapAlunos.get(ra).getName()
			)
		);
		
		CommandUtils.awaitUntil();
	}
	
	public static void upsertAluno() {
		FileService.upsertAlunoRecord(getMapAlunos());
	}
	
	public static boolean hasAlunoById(String id) {
		return getMapAlunos().containsKey(id);
	}
}
