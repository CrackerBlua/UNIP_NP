package NPPackage;

import java.util.*;

import NPPackage.Utils.NotaValorException;
import NPPackage.Utils.PatternErrorException;


public abstract class Rendimento {

	private Double np1 = 0.0;
	private Double np2 = 0.0;
	private Double reposicao = 0.0;
	private Double exame = 0.0;
	private Double media = 0.0;
	private Aluno aluno = new Aluno();
	private Curso curso = new Curso();
	private static boolean aprovado = false;
	private static Map<String, List<Rendimento>> mapRendimentos = new HashMap<String, List<Rendimento>>();
	private static Map<String, Rendimento> mapSecretKeyRendimentos = new HashMap<String, Rendimento>();

	public abstract void calcMedia();
	public abstract void calcFinalMedia();
	public abstract void loadCalcMedia();
	public abstract String isApproved();

	public Double getNp1() {
		return this.np1;
	}
	
	public void SetNp1(String np1) throws NotaValorException{
		this.setNp1(Double.valueOf(np1));
	}

	public void setNp1(Double np1) throws NotaValorException {
		boolean breakSetNP1 = false;
		
		while(!breakSetNP1) {
			if(np1 > 10 || np1 < 0) throw new NotaValorException("Valor entrado não é aplicavel a uma nota");
			this.np1 = np1; breakSetNP1 = true; break;
		}
	}
	
	public Double getNp2() {
		return this.np2;
	}
	
	public void setNp2(String np1) throws NotaValorException {
		this.setNp2(Double.valueOf(np1));
	}

	public void setNp2(Double np2) throws NotaValorException{
		boolean breakSetNP2 = false;
		
		while(!breakSetNP2) {
			if(np2 > 10 || np2 < 0) throw new NotaValorException("Valor entrado não é aplicavel a uma nota");
			this.np2 = np2; breakSetNP2 = true; break;
		}
	}
	
	public Double getReposicao() {
		return this.reposicao;
	}
	
	public void setReposicao(String reposicao) throws NotaValorException {
		this.setReposicao(Double.valueOf(reposicao));
	}
	
	public void setReposicao(Double reposicao) throws NotaValorException {
		boolean breakSetReposicao = false;
		
		while(!breakSetReposicao) {
			if(reposicao > 10 || reposicao < 0) throw new NotaValorException("Valor entrado não é aplicavel a uma nota");
			this.reposicao = reposicao; breakSetReposicao = true; break;
		}
	}
	
	public Double getMedia() {
		return this.media;
	}
	
	public void setMedia(String media) {
		this.setMedia(Double.valueOf(media));
	}

	public void setMedia(Double media) {
		this.media = media;
	}
	
	public Double getExame() {
		return this.exame;
	}
	
	public void setExame(String exame) throws NotaValorException {
		this.setExame(Double.valueOf(exame));
	}

	public void setExame(Double exame) throws NotaValorException {
		boolean breakSetExame = false;
		
		while(!breakSetExame) {
			if(exame > 10 || exame < 0) throw new NotaValorException("Valor entrado não é aplicavel a uma nota");
			this.exame = exame; breakSetExame = true; break;
		}
	}

	public Aluno getAluno() {
		return this.aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	public void setAluno(String ra) {
		this.aluno = AlunoBO.getAlunoById(ra);
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public void setCurso(String key) {
		this.curso = CursoBO.getCursoByKey(key);
	}

	public static Map<String, List<Rendimento>> getMapRendimentos() {
		return mapRendimentos;
	}

	public static void setMapRendimentos(String key, Rendimento rendimento) {
		if(!getMapRendimentos().containsKey(key)) {
			mapRendimentos.put(key, new ArrayList<Rendimento>());
		}
		
		mapRendimentos.get(key).add(rendimento);
	}
	
	public static boolean isAprovado() {
		return aprovado;
	}

	public static void setAprovado(boolean aprovado) {
		Rendimento.aprovado = aprovado;
	}
	
	public static Map<String, Rendimento> getMapSecretKeyRendimentos() {
		return mapSecretKeyRendimentos;
	}
	public static void setMapSecretKeyRendimentos(String secretKey, Rendimento rendimento) {
		if(!getMapSecretKeyRendimentos().containsKey(secretKey)) {
			getMapSecretKeyRendimentos().put(secretKey, null);
		}
		
		getMapSecretKeyRendimentos().put(secretKey, rendimento);
	}
	
	public String createReportName() {
		return this.getCurso().getNome() + "_" + this.getCurso().getNivel() + "_" + this.getCurso().getAno();
	}
	
	public String generateKey() {
		return this.getCurso().getNome() + "_" + this.getCurso().getNivel() + "_" + this.getCurso().getAno();
	}
	
	public static String generateKey(Curso curso) {
		return curso.getNome() + "_" + curso.getNivel() + "_" + curso.getAno();
	}
	
	public String generateSecretKey() {
		return this.getAluno().getId() + "_" + this.generateKey();
	}
	
	public static String generateSecretKey(Aluno aluno, Curso curso) {
		return aluno.getId() + "_" + curso.getNome() + "_" + curso.getNivel() + "_" + curso.getAno();
	}
	
	public static void upsertRendimento() {
		FileService.createRelatorioRendimento();
	}
	
	public static void listRendimentosByAluno(String ra) {
		RendimentoBO.listRendimentosByAluno(ra);
	}
	
	public static void listRendimentosByCurso(String cursoKey) {
		RendimentoBO.listRendimentosByCurso(cursoKey);
	}
	
	public void getExameDetails(Scanner sc) throws NotaValorException, PatternErrorException {
		this.setExame(RendimentoBO.askExame(sc));
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return 	"Rendimento : |NP1: " + getNp1() + "| - |NP2: " + getNp2() + 
				"| - |Reposição: " + getReposicao() + 
				"| - |Exame: " + getExame() + "| - |Média: " + getMedia() + "|";
	}
	
	/* Inner Classes de diferenciação do tipo do curso*/
	public static class RendimentoGraduacao extends Rendimento {
				
		@Override
		public void calcMedia() { super.setMedia(getMediaCalculated()); }

		@Override
		public void calcFinalMedia() {
			super.setMedia(getExamMedia());
			super.setAprovado(super.getMedia() >= 5? true: false);
		}
		
		@Override
		public String isApproved() { return super.getMedia() >= 7? "Passou!": "Reprovado!"; }
		
		@Override
		public void loadCalcMedia() {
			super.setMedia(getMediaCalculated());
			if(super.getMedia() < 7) super.setMedia(getExamMedia());
		}
		
		private Double getExamMedia() { return (super.getExame() + super.getMedia()) / 2; }
		
		public Double getMediaCalculated() {
			if(super.getNp1() < super.getNp2() && super.getNp1() < super.getReposicao()) return (super.getNp2() + super.getReposicao()) / 2;
			if(super.getNp2() < super.getNp1() && super.getNp2() < super.getReposicao()) return (super.getNp1() + super.getReposicao()) / 2;
			else return (super.getNp1() + super.getNp2()) / 2;
		}
	}
	
	public static class RendimentoPosGraduacao extends Rendimento {

		@Override
		public void calcMedia() { super.setMedia(getMediaCalculated()); }

		@Override
		public void calcFinalMedia() { super.setMedia(getExamMedia()); }
		
		@Override
		public String isApproved() { return super.getMedia() >= 5? "Passou!": "Reprovado!"; }
		
		public Double getMediaCalculated() { return (super.getNp1() + super.getNp2()) /2; }

		@Override
		public void loadCalcMedia() {
			calcMedia();
			if(super.getMedia() < 5) calcFinalMedia(); 
		}
		
		private Double getExamMedia() { return (super.getExame() + super.getMedia()) / 2; }
	}
}
