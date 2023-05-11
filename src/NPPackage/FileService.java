package NPPackage;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import NPPackage.Utils.*;

public class FileService {
	
	private static String pathFolder = "c:\\faculBD\\";
	private static String alunoCSV = "alunos.csv";
	private static String cursoCSV = "cursos.csv";
	private static List<String> filesToLoad = Arrays.asList(new String[]{"alunos.csv", "cursos.csv"});

	public FileService() { /* TODO Auto-generated constructor stub */ }

	public static void loadData() throws NotaValorException, IOException {
		Path reportFolder = Paths.get(pathFolder + "Relatorios");

		for(String str: filesToLoad) {
			if(!checkIfHasFiles(pathFolder + str)) continue;			
			if(str.equalsIgnoreCase(alunoCSV)) loadAlunoData(str);
			if(str.equalsIgnoreCase(cursoCSV)) loadCursoData(str);
		}
		
		if(!reportFolder.toFile().exists()) Files.createDirectories(reportFolder);

		if(reportFolder.toFile().listFiles().length > 0) {
			for(File file: reportFolder.toFile().listFiles()) {
				if(file.isDirectory()) continue;
				loadRendimentoData(file);
			}
		}
	}
	
	public static void loadAlunoData(String fileName) throws FileNotFoundException {
		int index = 0;
		List<List<String>> records = getData(fileName);
		
		for(List<String> alunos: records) {
			if(index == 0) { index++; continue; }
			Aluno.setMapAlunos(alunos.get(0), new Aluno(alunos.get(0), alunos.get(1)));
		}
	}
	
	public static void loadCursoData(String fileName) throws FileNotFoundException {
		int index = 0;
		List<List<String>> records = getData(fileName);
		
		for(List<String> cursos: records) {
			if(index == 0) { index++; continue; }
			
			String key = Curso.createCursoKey(cursos.get(0), cursos.get(1), cursos.get(2));
			Curso.setMapCursos(key, new Curso(cursos.get(0), cursos.get(1), Integer.valueOf(cursos.get(2))));
		}
	}
	
	private static List<List<String>> getData(String fileName)  throws FileNotFoundException{
		List<List<String>> records = new ArrayList<List<String>>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(pathFolder + fileName))) {
			String line;
			
			while((line = br.readLine()) != null) {
			    String[] values = line.split(";");
			    records.add(Arrays.asList(values));
			}
		} catch (IOException err) { Utils.throwMessageToUser(err, "Erro ao carregar registros do arquivo " + fileName); }
		
		return records;
	}
	
	public static void loadRendimentoData(File file) throws FileNotFoundException, NotaValorException {
		int index = 0;		
		String type = getType(file.getName().split(".csv")[0]);
		List<List<String>> records = getData("Relatorios\\" + file.getName());

		for(List<String> rendimentos: records) {
			if(index == 0) { index++; continue; }

			if(type.equalsIgnoreCase("GRADUACAO")) {
				Rendimento rendimento = new Rendimento.RendimentoGraduacao();

				createRendimento(rendimento, rendimentos);
				rendimento.setCurso(getGraduacaoKey(file.getName().split(".csv")[0]));

				Rendimento.setMapSecretKeyRendimentos(rendimento.generateSecretKey(), rendimento);
				Rendimento.setMapRendimentos(rendimento.generateKey(), rendimento);
			} else {
				Rendimento rendimento = new Rendimento.RendimentoPosGraduacao();
				
				createRendimento(rendimento, rendimentos);
				rendimento.setCurso(getPosGraduacaoKey(file.getName().split(".csv")[0]));
				
				Rendimento.setMapSecretKeyRendimentos(rendimento.generateSecretKey(), rendimento);
				Rendimento.setMapRendimentos(rendimento.generateKey(), rendimento);
			}
		}
	}
	
	private static void createRendimento(Rendimento rend, List<String> rendimentos) throws NotaValorException {
		rend.setAluno(rendimentos.get(0));
		rend.SetNp1(rendimentos.get(1));
		rend.setNp2(rendimentos.get(2));
		rend.setReposicao(rendimentos.get(3));
		rend.setExame(rendimentos.get(4));
	}
	
	private static String getType(String name) { return name.split("_")[1]; }
	
	private static String getGraduacaoKey(String name) { return String.join(";", Arrays.asList(name.split("_"))); }
	
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
				CommandUtils.awaitTimeAndRun(() -> {System.out.println("\nDiretório criado com sucesso! \nChamando o menu...");}, 0);
			}
		} catch ( IOException e) { Utils.throwMessageToUser(e, "Erro a criar o diretório de banco de dados!"); }
		
		System.out.println("\n\nAguarde um instante... ");
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
		    
		    bufferWrt.write(String.join(";", header));
		    bufferWrt.newLine();
		
			for(String str: mapAlunos.keySet()) {
				if(alunosUpserted.contains(str)) continue;
				
				rows = new ArrayList<String>();
				alunosUpserted.add(str);
				
				rows.add(mapAlunos.get(str).getId()); rows.add(mapAlunos.get(str).getName());
				
				bufferWrt.write(String.join(";", rows));
				bufferWrt.newLine();
			}

		    bufferWrt.close();
		    System.out.println("\nAluno salvo no arquivo alunos.csv");
			CommandUtils.awaitUntil(5000);
		} catch (IOException err) { Utils.throwMessageToUser(err, "Erro ao salvar/criar o arquivo alunos.csv!"); }
	}
	
	public static void upsertCurso(Map<String, Curso> mapCursos) {
		String[] header = {"NOME_CURSO", "NIVEL_GRADUACAO", "ANO_GRADUACAO"};

		Path filePath = Paths.get(pathFolder + "\\" + cursoCSV);
		List<String> rows;
        Set<String> cursosUpserted = new HashSet<String>();  

        try {
			if(!filePath.toFile().exists()) { Files.createFile(filePath); }

			FileWriter fileWrt = new FileWriter(filePath.toFile());
		    BufferedWriter bufferWrt = new BufferedWriter(fileWrt);
		    
		    bufferWrt.write(String.join(";", header));
		    bufferWrt.newLine();
		    
			for(String str: mapCursos.keySet()) {
				if(cursosUpserted.contains(str)) continue;
				
				rows = new ArrayList<String>();
				cursosUpserted.add(str);

				rows.add(mapCursos.get(str).getNome());
				rows.add(mapCursos.get(str).getNivel());
				rows.add(String.valueOf(mapCursos.get(str).getAno()));
				
				bufferWrt.write(String.join(";", rows));
				bufferWrt.newLine();
			}
			
		    bufferWrt.close();
		    System.out.println("\nCurso salvo no arquivo curso.csv");
			CommandUtils.awaitUntil(5000);
		} catch (IOException err) { Utils.throwMessageToUser(err, "Erro ao salvar/criar o arquivo cursos.csv!"); }
	}
	
	public static void createRelatorioRendimento() {
		String[] header = {"ID_ALUNO", "NOTA_NP1", "NOTA_NP2", "NOTA_REPOSICAO", "NOTA_EXAME"};
		String pathDir = pathFolder + "\\Relatorios\\";
        
		checkToCreateFolder(pathDir);

		try {
			
			for(String str: Rendimento.getMapRendimentos().keySet()) {
				String fileName = str + ".csv";
				
				checkToCreateFile(pathDir + fileName, fileName);

				FileWriter fileWrt = new FileWriter(Paths.get(pathDir + fileName).toFile());
				BufferedWriter bufferWrt = new BufferedWriter(fileWrt);

				bufferWrt.write(String.join(";", header));
				bufferWrt.newLine();
				
				for(Rendimento rend: Rendimento.getMapRendimentos().get(str)) {
					bufferWrt.write(String.join(";", generateCSVRow(rend)));
					bufferWrt.newLine();
				}
				
			    bufferWrt.flush();
			    bufferWrt.close();
		    }
		}catch(IOException e) {
			Utils.throwMessageToUser(e, "Erro ao salvar relatórios!");
		}

		System.out.println("\nRelatório(s) salvos)!");
		CommandUtils.awaitUntil();
	}
	
	private static List<String> generateCSVRow(Rendimento rend) {
		List<String> row = new ArrayList<String>();
		rend.loadCalcMedia();

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
	
	private static boolean checkIfHasFiles(String pathDir) { return Paths.get(pathDir).toFile().exists(); }
	
	private static void checkToCreateFile(String pathDir, String fileName) {
		Path filePath = Paths.get(pathDir);

		if(!filePath.toFile().exists()) {
			try { Files.createFile(filePath); }
			catch (IOException e) { Utils.throwMessageToUser(e, "Erro criar arquivo - " + fileName); }
		}
	}
}
