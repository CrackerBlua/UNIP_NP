package NPPackage;

import java.util.*;
import java.util.stream.Collectors;

import NPPackage.Rendimento.NotaValorException;

public class RendimentoBO {
	
	static boolean breakGetCurso = false;
	static boolean breakGetAluno = false;
	static boolean breakSetNP2   = false;
	public RendimentoBO() {
		// TODO Auto-generated constructor stub
	}
	
	public static Rendimento createRendimento(Scanner sc) throws NotaValorException {
		List<String> answers = new ArrayList<String>();
		boolean byPass = false;
		CommandUtils.clearScreen(8);
		
		Rendimento rend = chooseCurso(sc);
		rend.setAluno(chooseAluno(sc));
		
		while(!byPass) {
			try {
				System.out.println("\nQual foi a nota na NP1?");
				rend.setNp1(sc.nextDouble());

				System.out.println("\nQual foi a nota na NP2?");
				rend.setNp2(sc.nextDouble());
				
				byPass = true;
			} catch(NotaValorException err) {
				byPass = false; Utils.throwMessageToUser(err, "Erro ao inserir a nota");
			}
		}
		
		MenuBO.listingHasReposicao(rend);
		rend.calcMedia();
	
		if(!Rendimento.getMapSecretKeyRendimentos().containsKey(rend.generateSecretKey())) {
			System.out.println(rend.generateKey());
			Rendimento.setMapRendimentos(rend.generateKey(), rend);
			return rend;
		}
		
		return null;
	}
	
	static void cadastrarRendimento(Rendimento rendimento) {
		if(rendimento == null) {
			System.out.println("\nJá existe um relatório com esse aluno para esse mesmo curso!"); CommandUtils.awaitUntil();
		} else {
			Rendimento.upsertRendimento();
		}
	}
	
	private static Aluno chooseAluno(Scanner sc) {
		List<String> answers; 
		
		while(!breakGetAluno) {
			answers = new ArrayList<String>(); 

			System.out.println("\nEntre com o RA do aluno: " );
			answers.add(String.valueOf(sc.next()));
			
			if(AlunoBO.hasAlunoById(answers.get(0))) {
				return AlunoBO.getAlunoById(answers.get(0));
			}
			CommandUtils.awaitUntil("Não foi encontrado esse Aluno, tente novamente");
		}
		
		return null;
	}
	
	private static Rendimento chooseCurso(Scanner sc) {
		List<String> answers; 
		
		while(!breakGetCurso) {
			answers = new ArrayList<String>(); 
			MenuDesigner.drawCursoRendimento();
			
			System.out.println("\nEntre com o Nome do Curso: " );
			answers.add(sc.next().toUpperCase());
			
			MenuBO.createCursoMenu(answers);
			Rendimento rend = answers.get(1).equalsIgnoreCase("GRADUACAO")? new Rendimento.RendimentoGraduacao(): new Rendimento.RendimentoPosGraduacao();
			
			System.out.println("\nEntre com o Ano do Curso: " );
			answers.add(String.valueOf(MenuBO.sc.next()));
			
			String cursoKey = CursoBO.createCursoKey(answers.get(0), answers.get(1), answers.get(2));
			
			if(CursoBO.hasCursoByKey(cursoKey)) {
				rend.setCurso(CursoBO.getCursoByKey(cursoKey)); 
				return rend;
			}
			
			CommandUtils.awaitUntil("Não foi encontrado esse Curso, tente novamente");
		}		
		
		return null;
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
		System.out.println("Rendimento do Aluno: " + Aluno.getAlunoById(ra));
		for(Rendimento rend: rendAluno) {
			rend.calcMedia();
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
		Double totalMedia = 0.0;
		Curso curso =  Curso.getMapCursos().get(cursoKey);
		
		System.out.println("Relatório sobre o curso: " + curso);
		for(Rendimento rend: Rendimento.getMapRendimentos().get(Rendimento.generateKey(curso))) {
			rend.calcMedia();
			System.out.println("Aluno:" + rend.getAluno() + " - " + rend);
			System.out.println("Status: " + rend.isApproved());
			totalMedia += rend.getMedia();
		}
		
		System.out.println("Media das médias: " + String.valueOf(totalMedia / Rendimento.getMapRendimentos().get(cursoKey).size()));
		CommandUtils.awaitUntil();
	}
}
