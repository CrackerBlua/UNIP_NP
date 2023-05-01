package NPPackage;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileService {
	
	private static String pathFolder = "c:\\faculBD";
	private static String alunoCSV = "alunos.csv";

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
	}
	
	public static boolean checkIfHasFile(String csvFile) {
		switch(csvFile) {
			case "aluno":
				Path filePath = Paths.get(pathFolder + "\\" + alunoCSV);
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
	
	public static void checkExistsFolder() {
		try {
			File folder = new File(pathFolder);
			
			if(folder.exists()) { System.out.println("Encontrado o diretório do BD!"); } 
			else {
				System.out.println("Criando diretório do banco de dados disco!");
				Files.createDirectories(Paths.get(pathFolder));
				CommandUtils.setTimeout(() -> { System.out.println("Diretório criado com sucesso! \n Chamando o menu..."); },  2000);
			}
		} catch ( IOException e) {
			System.out.println(
					"Ocorreu algum problema ao criar o diretório!\n" +
					"Erro: "  + e.getMessage() + "\n" +
					"Local: " + e.getStackTrace()
			);
		}
		System.out.println("Aguarde um instante... ");
		CommandUtils.setTimeout(() -> {  CommandUtils.clearScreen(); },  2000);
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

}
