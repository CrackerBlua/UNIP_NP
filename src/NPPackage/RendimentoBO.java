package NPPackage;

import java.util.*;

import NPPackage.Rendimento.NotaValorException;

public class RendimentoBO {
	
	static boolean breakGetCurso = false;
	static boolean breakGetAluno = false;
	static boolean breakSetNP2   = false;
	public RendimentoBO() {
		// TODO Auto-generated constructor stub
	}
	
	public static void createRendimento(Scanner sc, Rendimento rendimento) throws NotaValorException {
		List<String> answers = new ArrayList<String>();
		
		Curso curso = chooseCurso(sc);
		Aluno aluno = chooseAluno(sc);
		rendimento 	= castObject(curso.getNivel());
		
		rendimento.setAluno(aluno);
		rendimento.setCurso(curso);
		
		System.out.println("Qual foi a nota na NP1?");
		rendimento.setNp1(sc.nextDouble());

		System.out.println("Qual foi a nota na NP2?");
		rendimento.setNp1(sc.nextDouble());
		
		MenuBO.listingHasReposicao(rendimento);
		rendimento.calcMedia();
		rendimento.getExameDetails(sc, rendimento);
		rendimento.calcExam();		
		
		Rendimento.setMapRendimentos(Rendimento.generateKey(aluno, curso), rendimento);
	}
	
	private static Aluno chooseAluno(Scanner sc) {
		List<String> answers; 
		Aluno aluno = new Aluno();
		
		while(!breakGetAluno) {
			answers = new ArrayList<String>(); 

			System.out.println("Entre com o RA do aluno: " );
			answers.add(String.valueOf(sc.next()));
			
			if(AlunoBO.hasAlunoById(answers.get(0))) {
				aluno = AlunoBO.getAlunoById(answers.get(0));
				breakGetAluno = true; break;
			}
			
			CommandUtils.awaitUntil("Não foi encontrado esse Aluno, tente novamente");
		}
		
		CommandUtils.clearScreen(5);
		return aluno;
	}
	
	private static Curso chooseCurso(Scanner sc) {
		List<String> answers; 
		Curso curso = new Curso();
		
		while(!breakGetCurso) {
			answers = new ArrayList<String>(); 
			MenuDesigner.drawCursoRendimento();
			
			System.out.println("Entre com o Nome do Curso: " );
			answers.add(sc.next());
			
			MenuBO.createCursoMenu(answers);
			
			System.out.println("Entre com o Ano do Curso: " );
			answers.add(String.valueOf(MenuBO.sc.next()));

			if(CursoBO.hasCursoByKey(CursoBO.createCursoKey(answers.get(0), answers.get(1), answers.get(2)))) {
				curso = new Curso(answers.get(0), answers.get(1), answers.get(2));
				breakGetCurso = true; break;
			}
			
			CommandUtils.awaitUntil("Não foi encontrado esse Curso, tente novamente");
		}
		
		CommandUtils.clearScreen(5);
		return curso;
	} 
	
	private static Rendimento castObject(String nivel) {
		return nivel.equalsIgnoreCase("GRADUACAO")? new Rendimento.RendimentoGraduacao():
													new Rendimento.RendimentoPosGraduacao();
	}
}
