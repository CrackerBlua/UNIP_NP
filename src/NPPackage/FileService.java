package NPPackage;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import NPPackage.Rendimento.NotaValorException;

public class FileService {
	
	private static String pathFolder = "c:\\faculBD\\";
	private static String alunoCSV = "alunos.csv";
	private static String cursoCSV = "cursos.csv";
	private static List<String> filesToLoad = Arrays.asList(new String[]{"alunos.csv", "cursos.csv"});


	public FileService() {	}

	public static FileService getInstance() {
		return new FileService();
	}

	public static void loadData() throws NotaValorException, IOException {
		for(String str: filesToLoad) {
			int indexAux = 0;
			if(!checkIfHasFiles(pathFolder + str)) continue;
			
			if(str.equalsIgnoreCase(alunoCSV)) loadAlunoData();
			if(str.equalsIgnoreCase(cursoCSV)) loadCursoData();
		}

		Path reportFolder = Paths.get(pathFolder + "Relatorios");
		
		if(!reportFolder.toFile().exists())Files.createFile(reportFolder);

		for(File file: reportFolder.toFile().listFiles()) {
			if(file.isDirectory()) continue;
			loadRendimentoData(file);
		}
	}
	
	public static void loadAlunoData() throws FileNotFoundException {
		int index = 0;
		List<List<String>> records = getAlunoData();
		for(List<String> alunos: records) {
			if(index == 0) { index++; continue; }
			Aluno.setMapAlunos(alunos.get(0), new Aluno(alunos.get(0), alunos.get(1)));
		}
		
		System.out.println();
	}
	
	public static void loadCursoData() throws FileNotFoundException {
		int index = 0;
		List<List<String>> records = getCursoData();
		
		for(List<String> cursos: records) {
			if(index == 0) { index++; continue; }
			
			String key = Curso.createCursoKey(cursos.get(0), cursos.get(1), cursos.get(2));
			Curso.setMapCursos(key, new Curso(cursos.get(0), cursos.get(1), Integer.valueOf(cursos.get(2))));
		}
	}
	
	private static List<List<String>> getAlunoData() throws FileNotFoundException {
		List<List<String>> records = new ArrayList<List<String>>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(pathFolder + alunoCSV))) {
			String line;
			
			while((line = br.readLine()) != null) {
			    String[] values = line.split(",");
			    records.add(Arrays.asList(values));
			}
		}catch (IOException err) {
			Utils.throwMessageToUser(err, "Erro ao carregar registros do arquivo " + alunoCSV);
		}
		return records;
	}
	
	private static List<List<String>> getCursoData() throws FileNotFoundException {
		List<List<String>> records = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(pathFolder + "\\" + cursoCSV))) {
			String line;
			
			while((line = br.readLine()) != null) {
			    String[] values = line.split(",");
			    records.add(Arrays.asList(values));
			}
		} catch (IOException err) {
			Utils.throwMessageToUser(err, "Erro ao carregar registros do arquivo " + cursoCSV);
		}
		
		return records;
	}
	
	public static void loadRendimentoData(File file) throws FileNotFoundException, NotaValorException {
		List<List<String>> records = getRendimentosData(file);
		
		String type = getType(file.getName().split(".csv")[0]);
		String cursoKey;
		int index = 0;
		
		for(List<String> rendimentos: records) {
			if(index == 0) { index++; continue; }
			
			if(type.equalsIgnoreCase("GRADUACAO")) {
				Rendimento rendimento = new Rendimento.RendimentoGraduacao();
				rendimento.setAluno(rendimentos.get(0));
				rendimento.setCurso(getGraduacaoKey(file.getName().split(".csv")[0]));
				rendimento.SetNp1(rendimentos.get(1));
				rendimento.setNp2(rendimentos.get(2));
				rendimento.setReposicao(rendimentos.get(3));
				rendimento.setExame(rendimentos.get(4));
				Rendimento.setMapSecretKeyRendimentos(rendimento.generateSecretKey(), rendimento);
				Rendimento.setMapRendimentos(rendimento.generateKey(), rendimento);
			} else {
				Rendimento rendimento = new Rendimento.RendimentoPosGraduacao();
				rendimento.setAluno(rendimentos.get(0));
				rendimento.setCurso(getPosGraduacaoKey(file.getName().split(".csv")[0]));
				rendimento.SetNp1(rendimentos.get(1));
				rendimento.setNp2(rendimentos.get(2));
				rendimento.setReposicao(rendimentos.get(3));
				rendimento.setExame(rendimentos.get(4));
				Rendimento.setMapSecretKeyRendimentos(rendimento.generateSecretKey(), rendimento);
				Rendimento.setMapRendimentos(rendimento.generateKey(), rendimento);
			}
		}
	}
	
	private static List<List<String>> getRendimentosData(File file) throws FileNotFoundException {
		List<List<String>> records = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			
			while((line = br.readLine()) != null) {
			    String[] values = line.split(",");
			    records.add(Arrays.asList(values));
			}
		}catch (IOException err) {
			Utils.throwMessageToUser(err, "Erro ao carregar registros do arquivo " + alunoCSV);
		}		
		return records;
	}
	
	private static String getType(String name) {				
		return name.split("_")[1];
	}
	
	private static String getGraduacaoKey(String name) {		
		return String.join(";", Arrays.asList(name.split("_")));
	}
	
	private static String getPosGraduacaoKey(String name) {
		String strSplitter[] = name.split("_");
		
		return 	strSplitter[0] + ";" +
				strSplitter[1] + "_" + 
				strSplitter[2] + ";" +
				strSplitter[3];
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
				if(alunosUpserted.contains(str)) continue;
				
				rows = new ArrayList<String>();
				alunosUpserted.add(str);
				rows.add(mapAlunos.get(str).getId());
				rows.add(mapAlunos.get(str).getName());
				bufferWrt.write(String.join(",", rows));
				bufferWrt.newLine();
			}
			
		    bufferWrt.close();
		    System.out.println("\nAluno salvo no arquivo alunos.csv");
			CommandUtils.awaitUntil(5000);
		} catch (IOException err) {
			System.out.println(
					"IOException: \n" +
					err.getMessage() + "\n"
			);
		}
	}
	
	public static void upsertCurso(Map<String, Curso> mapCursos) {
		String[] header = {"NOME_CURSO", "NIVEL_GRADUACAO", "ANO_GRADUACAO"};

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
		    System.out.println("\nCurso salvo no arquivo curso.csv");
			CommandUtils.awaitUntil(5000);
        } catch (IOException err) {
			System.out.println(
					"IOException: \n" +
					err.getMessage() + "\n"
			);
		}
	}
	
	public static void createRelatorioRendimento() {
		String[] header = {"ID_ALUNO", "NOTA_NP1", "NOTA_NP2", "NOTA_REPOSICAO", "NOTA_EXAME"};
		String pathDir = pathFolder + "\\Relatorios\\";
		
		List<String> rows;
        Set<String> relatoriosUpserted = new HashSet<String>();  
        
		checkToCreateFolder(pathDir);

		for(String str: Rendimento.getMapRendimentos().keySet()) {
			String fileName = str + ".csv";
			checkToCreateFile(pathDir + fileName, fileName);
		}
			
		try {
			for(String str: Rendimento.getMapRendimentos().keySet()) {
				String fileName = str + ".csv";
				Path filePath = Paths.get(pathDir + fileName);
				
				FileWriter fileWrt = new FileWriter(filePath.toFile());
				BufferedWriter bufferWrt = new BufferedWriter(fileWrt);

				bufferWrt.write(String.join(",", header));
				bufferWrt.newLine();
				
				for(Rendimento rend: Rendimento.getMapRendimentos().get(str)) {
					bufferWrt.write(String.join(",", generateCSVRow(rend)));
					bufferWrt.newLine();
				}
				
			    bufferWrt.close();
			    System.out.println("Relatório salvo no arquivo " + str + ".csv");
				CommandUtils.awaitUntil();
		    }
		}catch(IOException e) {
			Utils.throwMessageToUser(e, "Erro ao salvar relatórios!");
		}
	}
	
	private static List<String> generateCSVRow(Rendimento rend) {
		List<String> row = new ArrayList<String>();
		rend.calcExam();
		
		row.add(rend.getAluno().getId());
		row.add(String.valueOf(rend.getNp1()));
		row.add(String.valueOf(rend.getNp2()));
		row.add(String.valueOf(rend.getReposicao()));
		row.add(String.valueOf(rend.getExame()));
		return row;
	}
	
	private static void checkToCreateFolder(String pathDir) {
		Path filePath = Paths.get(pathDir);

		if(!filePath.toFile().exists()) {
			try { Files.createDirectories(filePath); }
			catch (IOException e) { Utils.throwMessageToUser(e, "Erro criar diretório"); }
		}
	}
	
	private static boolean checkIfHasFiles(String pathDir) {
		Path filePath = Paths.get(pathDir);
		
		return filePath.toFile().exists();
	}
	
	private static void checkToCreateFile(String pathDir, String fileName) {
		Path filePath = Paths.get(pathDir);

		if(!filePath.toFile().exists()) {
			try { Files.createFile(filePath); }
			catch (IOException e) { Utils.throwMessageToUser(e, "Erro criar arquivo - " + fileName); }
		}
	}

}
