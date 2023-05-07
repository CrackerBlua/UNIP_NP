package NPPackage;

import java.util.*;

public abstract class Rendimento {

	private Double np1 = 0.0;
	private Double np2 = 0.0;
	private Double reposição = 0.0;
	private Double exame = 0.0;
	private Double media = 0.0;
	private Aluno aluno = new Aluno();
	private Curso curso = new Curso();
	private static boolean aprovado = false;
	private static Map<String, Rendimento> mapRendimentos = new HashMap<String, Rendimento>();

	public abstract void calcMedia();
	public abstract void calcExam();
	public abstract void getExameDetails(Scanner sc, Rendimento rendimento) throws NotaValorException;
	
	public Double getReposição() {
		return reposição;
	}
	
	public void loadInfos(String key) {
		
	}
	
	public String createReportName() {
		return getCurso().getNome() + "_" + getCurso().getNivel() + "_" + getCurso().getAno();
	}

	public void setReposição(Double reposição) throws NotaValorException {
		boolean breakSetReposicao = false;
		
		while(!breakSetReposicao) {
			if(reposição > 10 || reposição < 0) throw new NotaValorException("Valor entrado não é aplicavel a uma nota");
			this.reposição = reposição; breakSetReposicao = true; break;
		}
	}

	public Double getExame() {
		return exame;
	}

	public void setExame(Double exame) throws NotaValorException {
		boolean breakSetExame = false;
		
		while(!breakSetExame) {
			if(exame > 10 || exame < 0) throw new NotaValorException("Valor entrado não é aplicavel a uma nota");
			this.exame = exame; breakSetExame = true; break;
		}
	}

	public Double getNp2() {
		return np2;
	}

	public void setNp2(Double np2) throws NotaValorException{
		boolean breakSetNP2 = false;
		
		while(!breakSetNP2) {
			if(np2 > 10 || np2 < 0) throw new NotaValorException("Valor entrado não é aplicavel a uma nota");
			this.np2 = np2; breakSetNP2 = true; break;
		}
	}

	public Double getNp1() {
		return np1;
	}

	public void setNp1(Double np1) throws NotaValorException {
		boolean breakSetNP1 = false;
		
		while(!breakSetNP1) {
			if(np1 > 10 || np1 < 0) throw new NotaValorException("Valor entrado não é aplicavel a uma nota");
			this.np1 = np1; breakSetNP1 = true; break;
		}
	}
	
	public Double getMedia() {
		return media;
	}

	public void setMedia(Double media) {
		this.media = media;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public static Map<String, Rendimento> getMapRendimentos() {
		return mapRendimentos;
	}

	public static void setMapRendimentos(String key, Rendimento rendimento) {
		if(!getMapRendimentos().containsKey(key)) {
			mapRendimentos.put(key, null);
		}
		
		mapRendimentos.put(key, rendimento);
	}
	
	public String generateKey() {
		return getAluno().getId() + Curso.getCursoKey(getCurso());
	}
	
	public static String generateKey(Aluno aluno, Curso curso) {
		return aluno.getId() + CursoBO.getCursoKey(curso);
	}

	public static boolean isAprovado() {
		return aprovado;
	}

	public static void setAprovado(boolean aprovado) {
		Rendimento.aprovado = aprovado;
	}

	public static class RendimentoGraduacao extends Rendimento {
		@Override
		public void getExameDetails(Scanner sc, Rendimento rendimento) throws NotaValorException {
			if(rendimento.getMedia() < 7) {
				CommandUtils.clearScreen(5);
				System.out.println("O Aluno não atingiu a média esperada!");
				
				System.out.println("Entre com a nota do Exame do aluno:");
				super.setExame(sc.nextDouble());
			}
		}
				
		@Override
		public void calcMedia() {
			boolean approved = false;			
			Double mediaInicial = getMediaCalculated();
			
			if(mediaInicial >= 7) {
				approved = true;
				super.setMedia(mediaInicial);
			}
			
			super.setAprovado(approved);
		}

		@Override
		public void calcExam() {
			boolean approved = false;
			
			approved = getExamMedia() >= 5? true: false;
			super.setAprovado(approved);
		}
		
		private Double getExamMedia() {
			return (super.getExame() + super.getMedia()) / 2;
		}
		
		public Double getMediaCalculated() {
			if(super.getNp1().equals(super.getReposição()) && super.getNp2().equals(super.getReposição())) 
				return (super.getNp1() + super.getNp2()) / 2;
			else if(super.getReposição() <= super.getNp1() && super.getReposição() <= super.getNp2())
				return (super.getNp1() + super.getNp2()) /2;
			else if(super.getReposição() > super.getNp1() && super.getReposição() <= super.getNp2())
				return (super.getReposição() + super.getNp2()) /2;
			else if(super.getReposição() > super.getNp1() && super.getReposição() <= super.getNp2())		
				return (super.getReposição() + super.getNp1()) /2;
			else 
				return (super.getNp2() + super.getNp2()) /2;
		}
	}
	
	public static class RendimentoPosGraduacao extends Rendimento {

		@Override
		public void calcMedia() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void calcExam() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getExameDetails(Scanner sc, Rendimento rendimento) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class NotaValorException extends Exception {
		/******/
		private static final long serialVersionUID = 1L;
		public NotaValorException(String errorMsg) { super(errorMsg); }
	}
}
