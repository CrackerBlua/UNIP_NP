package NPPackage;

import java.util.*;
import java.util.stream.Collectors;
import NPPackage.Utils.*;

public class RendimentoBO {
	
	static boolean breakSetNP2   = false;
	
	public RendimentoBO() {
		// TODO Auto-generated constructor stub
	}
	
	public static Rendimento createRendimento(Scanner sc) throws NotaValorException, PatternErrorException {
		boolean byPass = false;
		CommandUtils.clearScreen(8);
		
		Rendimento rend = chooseCurso(sc);
		rend.setAluno(chooseAluno(sc));
		
		while(!byPass) {
			try {
				rend.setNp1(askNP1(sc));
				rend.setNp2(askNP2(sc));
				byPass = true; break;
			} catch(NotaValorException err) { Utils.throwMessageToUser(err, "Erro ao inserir a nota"); }
		}
		
		MenuBO.listingHasReposicao(rend);
		rend.calcMedia();
		
		if(rend.getMedia() < 7 && rend instanceof Rendimento.RendimentoGraduacao) {
			rend.getExameDetails(sc); rend.calcFinalMedia();
		} else if(rend.getMedia() < 5 && rend instanceof Rendimento.RendimentoPosGraduacao) {
			rend.getExameDetails(sc); rend.calcFinalMedia();
		}
	
		if(!Rendimento.getMapSecretKeyRendimentos().containsKey(rend.generateSecretKey())) {
			Rendimento.setMapSecretKeyRendimentos(rend.generateSecretKey(), rend);
			Rendimento.setMapRendimentos(rend.generateKey(), rend);
			return rend;
		}
		
		return null;
	}
	
	static Double askNP1(Scanner sc) throws NotaValorException {
		Double np1 = 0.0;
		boolean byPass = false;
		
		while(!byPass) {
			try {
				np1 = inputNP(sc, " NP1 ");
				
				if(np1 < 0 || np1 > 10)
					throw new NotaValorException("O valor entrado não é equivalente a uma nota, entre com um valor válido!");
				
				byPass = false; return np1;
			} catch(PatternErrorException ex) { Utils.throwMessageToUser(ex, "Erro na entrada da NP1!"); }	
		}
		
		return np1;
	}
	
	static Double askNP2(Scanner sc) throws NotaValorException {
		Double np1 = 0.0;
		boolean byPass = false;
		
		while(!byPass) {
			try {
				np1 = inputNP(sc, " NP2 ");
				
				if(np1 < 0 || np1 > 10)
					throw new NotaValorException("O valor entrado não é equivalente a uma nota, entre com um valor válido!");
				
				byPass = false; return np1;
			} catch(PatternErrorException ex) { Utils.throwMessageToUser(ex, "Erro na entrada da NP2!"); }	
		}
		
		return np1;
	}
	
	public static Double askReposicao(Scanner sc) throws PatternErrorException, NotaValorException {
		Double repo = 0.0;
		boolean byPass = false;
		
		while(!byPass) {
			try {
				repo = inputNP(sc, " Reposição ");
				
				if(repo < 0 || repo > 10)
					throw new NotaValorException("O valor entrado não é equivalente a uma nota, entre com um valor válido!");
				
				byPass = false; return repo;
			} catch(PatternErrorException ex) { Utils.throwMessageToUser(ex, "Erro na entrada da Reposição!"); }	
		}
		return 0.0;
	}
	
	private static Double inputNP(Scanner sc, String npToAsk) throws PatternErrorException{
	
		System.out.println("\nQual foi a nota na" + npToAsk + "?");
		String NPString = sc.next();

		if(!Utils.pattern.matcher(NPString).matches())
			throw new PatternErrorException("Valor entrado não é numérico, entre com um valor válido");
		
		return Double.valueOf(NPString);
	}
	
	static void cadastrarRendimento(Rendimento rendimento) {
		if(rendimento == null) {
			System.out.println("\nJá existe um relatório com esse aluno para esse mesmo curso!"); CommandUtils.awaitUntil();
		} else {
			Rendimento.upsertRendimento();
		}
	}
	
	private static Aluno chooseAluno(Scanner sc) {
		boolean breakGetAluno = false;
		List<String> answers; 
		
		while(!breakGetAluno) {
			answers = getAlunoAnswers(sc); 
			
			if(AlunoBO.hasAlunoById(answers.get(0))) {
				breakGetAluno = false; return AlunoBO.getAlunoById(answers.get(0));
			}

			CommandUtils.awaitUntil("Não foi encontrado esse Aluno, tente novamente");
		}
		
		return null;
	}
	
	
	private static List<String> getAlunoAnswers(Scanner sc) {
		List<String> answers = new ArrayList<String>();
			
		answers.add(AlunoBO.askRA(sc));
		
		return answers;
	}
	
	private static Rendimento chooseCurso(Scanner sc) {
		boolean breakGetCurso = false;
		List<String> answers; 
		
		while(!breakGetCurso) {
			MenuDesigner.drawCursoRendimento();
			answers = getCursoAnswers(sc); 

			Rendimento rend = answers.get(1)
									 .equalsIgnoreCase("GRADUACAO")? new Rendimento.RendimentoGraduacao(): 
																	 new Rendimento.RendimentoPosGraduacao();
			
			String cursoKey = getListCursoKey(answers);
			
			if(CursoBO.hasCursoByKey(cursoKey)) {
				rend.setCurso(CursoBO.getCursoByKey(cursoKey)); 
				breakGetCurso = true; return rend;
			}
			
			CommandUtils.awaitUntil("Não foi encontrado esse Curso, tente novamente");
		}		
		
		return null;
	} 
	
	private static String getListCursoKey(List<String> answers) {
		return CursoBO.createCursoKey(answers.get(0), answers.get(1), answers.get(2));
	}
	
	private static List<String> getCursoAnswers(Scanner sc) {
		List<String> answers = new ArrayList<String>();
		
		answers.add(CursoBO.askNomeCurso(sc));
		MenuBO.createCursoMenu(answers);
		answers.add(CursoBO.askYear(sc));
		
		return answers;
	}
	
	public static void listRendimentosByAluno(String ra) {
		Double totalMedia = 0.0;
		List<Rendimento> rendAluno = new ArrayList<Rendimento>();
		
		for(List<Rendimento> rend: Rendimento.getMapRendimentos().values()) {
			rendAluno.addAll(rend.stream()
					 .filter(rnd -> rnd.getAluno().getId().equalsIgnoreCase(ra))
					 .collect(Collectors.toList())
			);
		}
		
		CommandUtils.clearScreen(5);
		System.out.println("Rendimento do Aluno: " + Aluno.getAlunoById(ra).getName() + "\n");
		for(Rendimento rend: rendAluno) {
			rend.loadCalcMedia();
			if(rend.getExame() == 0)
			System.out.println(" -> Curso: " + rend.getCurso());
			System.out.println(" -> " + rend);
			System.out.println("Status: => " + rend.isApproved());
			System.out.println("\n");
			totalMedia += rend.getMedia();
		}
		
		System.out.println("Media do histórico: " + String.valueOf(totalMedia / rendAluno.size()));
		CommandUtils.awaitUntil();
	}
	
	public static void listRendimentosByCurso(String cursoKey) {
		CommandUtils.clearScreen(15);

		Double totalMedia = 0.0;
		Curso curso =  Curso.getMapCursos().get(cursoKey);

		System.out.println("Relatório sobre o curso: " + curso  + ":\n");
		
		for(Rendimento rend: Rendimento.getMapRendimentos().get(Rendimento.generateKey(curso))) {
			rend.loadCalcMedia();

			System.out.println(rend.getAluno() + " => " + rend);
			System.out.println("### Status => " + rend.isApproved());
			totalMedia += rend.getMedia();
		}
		
		System.out.println("\nMedia das médias: " + 
				String.valueOf(totalMedia / Rendimento.getMapRendimentos().get(Rendimento.generateKey(curso)).size())
		);
		CommandUtils.awaitUntil();
	}
}
