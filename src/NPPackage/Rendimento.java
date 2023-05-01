package NPPackage;

public abstract class Rendimento {

	private Double np1 = 0.0;
	private Double np2 = 0.0;
	private Double reposição = 0.0;
	private Double exame = 0.0;
	private Double media = 0.0;
	private Aluno aluno = new Aluno();
	private Curso curso = new Curso();

	public abstract void calcMedia();

	public Double getReposição() {
		return reposição;
	}

	public void setReposição(Double reposição) {
		this.reposição = reposição;
	}

	public Double getExame() {
		return exame;
	}

	public void setExame(Double exame) {
		this.exame = exame;
	}

	public Double getNp2() {
		return np2;
	}

	public void setNp2(Double np2) {
		this.np2 = np2;
	}

	public Double getNp1() {
		return np1;
	}

	public void setNp1(Double np1) {
		this.np1 = np1;
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

	public class RendimentoGraduacao extends Rendimento {

		@Override
		public void calcMedia() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class RendimentoPosGraduacao extends Rendimento {

		@Override
		public void calcMedia() {
			// TODO Auto-generated method stub
			
		}
	}
}
