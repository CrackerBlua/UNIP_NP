package NPPackage;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileService {
	
	private static String pathFolder = "c:\\faculBD";
	private static String alunoCSV = "alunos.csv";
	private static String cursoCSV = "cursos.csv";

	public FileService() {	}

	public static FileService getInstance() {
		return new FileService();
	}

	public static void loadData() {
		if(checkIfHasFile("aluno")) {
			try {
				int indexAux = 0;
				List<List<String>> records = FileService.getAlunoData();
				
				for(List<String> lstStr: records) {
					indexAux++;
					if(indexAux == 1) continue;
					Aluno.setMapAlunos(lstStr.get(0), new Aluno(lstStr.get(0), lstStr.get(1)));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(checkIfHasFile("curso")) {
			try {
				int indexAux = 0;
				List<List<String>> records = FileService.getCursoData();
				
				for(List<String> lstStr: records) {
					indexAux++;
					if(indexAux == 1) continue;
					String key = Curso.createCursoKey(lstStr.get(0), lstStr.get(1), lstStr.get(2));
					Curso.setMapCursos(key, new Curso(lstStr.get(0), lstStr.get(1), Integer.valueOf(lstStr.get(2))));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean checkIfHasFile(String csvFile) {
		Path filePath;
		switch(csvFile) {
			case "aluno":
				filePath = Paths.get(pathFolder + "\\" + alunoCSV);
				if(filePath.toFile().exists()) return true;
				else return false;
			case "curso":
				filePath = Paths.get(pathFolder + "\\" + cursoCSV);
				if(filePath.toFile().exists()) return true;
				else return false;
			default:
				return false;
		}
	}
	
	public static List<List<String>> getAlunoData() throws FileNotFoundException {
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(pathFolder + "\\" + alunoCSV))) {
		    String line;
		    try {
				while ((line = br.readLine()) != null) {
				    String[] values = line.split(",");
				    records.add(Arrays.asList(values));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		} catch (IOException err) {
			// TODO Auto-generated catch block
			err.printStackTrace();
		}
		
		return records;
	}
	
	public static List<List<String>> getCursoData() throws FileNotFoundException {
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(pathFolder + "\\" + cursoCSV))) {
		    String line;
		    try {
				while ((line = br.readLine()) != null) {
				    String[] values = line.split(",");
				    records.add(Arrays.asList(values));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		} catch (IOException err) {
			// TODO Auto-generated catch block
			err.printStackTrace();
		}
		
		return records;
	}
	
	public static void checkExistsFolder() {
		try {
			File folder = new File(pathFolder);
			
			if(folder.exists()) { System.out.println("Encontrado o diretório do BD!"); } 
			else {
				System.out.println("Criando diretório do banco de dados disco!");
				Files.createDirectories(Paths.get(pathFolder));
				CommandUtils.awaitTimeAndRun(() -> {System.out.println("Diretório criado com sucesso! \n Chamando o menu...");}, 0);
			}
		} catch ( IOException e) {
			System.out.println(
					"Ocorreu algum problema ao criar o diretório!\n" +
					"Erro: "  + e.getMessage() + "\n" +
					"Local: " + e.getStackTrace()
			);
		}
		System.out.println("Aguarde um instante... ");
		CommandUtils.awaitTimeAndRun(() -> {CommandUtils.clearScreen(12);},2000);
	}
	
	public static void upsertAlunoRecord(Map<String, Aluno> mapAlunos) {
		String[] header = {"ID_ALUNO", "NOME_ALUNO"};

	    Path filePath = Paths.get(pathFolder + "\\" + alunoCSV);
		List<String> rows;
        Set<String> alunosUpserted = new HashSet<String>();   

		try {
			if(!filePath.toFile().exists()) { Files.createFile(filePath); }
			
			FileWriter fileWrt = new FileWriter(filePath.toFile());
		    BufferedWriter bufferWrt = new BufferedWriter(fileWrt);
		    
		    bufferWrt.write(String.join(",", header));
		    bufferWrt.newLine();
		
			for(String str: mapAlunos.keySet()) {
				if(!alunosUpserted.contains(str)) {						
					rows = new ArrayList<String>();
					
					alunosUpserted.add(str);
					rows.add(mapAlunos.get(str).getId());
					rows.add(mapAlunos.get(str).getName());
					bufferWrt.write(String.join(",", rows));
					bufferWrt.newLine();
				}
			}
			
		    bufferWrt.close();
		    System.out.println("Aluno salvo no arquivo alunos.csv");
			CommandUtils.awaitUntil(5000);
		} catch (IOException err) {
			System.out.println(
					"IOException: \n" +
					err.getMessage() + "\n"
			);
		}
	}
	
	public static void upsertCurso(Map<String, Curso> mapCursos) {
		String[] header = {"NOME_CURSO", "NIVEL_GRADUACAO", "ANO GRADUACAO"};

		Path filePath = Paths.get(pathFolder + "\\" + cursoCSV);
		List<String> rows;
        Set<String> alunosUpserted = new HashSet<String>();  

        try {
			if(!filePath.toFile().exists()) { Files.createFile(filePath); }

			FileWriter fileWrt = new FileWriter(filePath.toFile());
		    BufferedWriter bufferWrt = new BufferedWriter(fileWrt);
		    
		    bufferWrt.write(String.join(",", header));
		    bufferWrt.newLine();
		    
			for(String str: mapCursos.keySet()) {
				if(!alunosUpserted.contains(str)) {						
					rows = new ArrayList<String>();
					
					alunosUpserted.add(str);
					rows.add(mapCursos.get(str).getNome());
					rows.add(mapCursos.get(str).getNivel());
					rows.add(String.valueOf(mapCursos.get(str).getAno()));
					bufferWrt.write(String.join(",", rows));
					bufferWrt.newLine();
				}
			}
			
		    bufferWrt.close();
		    System.out.println("Curso salvo no arquivo curso.csv");
			CommandUtils.awaitUntil(5000);
        } catch (IOException err) {
			System.out.println(
					"IOException: \n" +
					err.getMessage() + "\n"
			);
		}
	}

}
