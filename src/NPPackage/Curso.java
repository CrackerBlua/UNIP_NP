package NPPackage;

public class Curso {
	
	private enum Formacao { 
		GRADUACAO, POS_GRADUACAO;
		
		public String getFormacao() {
			switch(this) {
				case GRADUACAO: return "graduacao";
				case POS_GRADUACAO: return "pos_graduacao";
				default: return null;
			}
		}
	}
	
	private String nome 	= "";
	private int ano 		= 0;
	private String nivel 	= "";
	
	public Curso() {}

	public Curso(String nome, int ano, String nivel) {
		this.nome 	= nome;
		this.ano 	= ano;
		this.nivel	= getFormacaoNivel(nivel);
	}

	private String getFormacaoNivel(String nivel) {
		if(Formacao.GRADUACAO.getFormacao().equalsIgnoreCase(nivel)) return Formacao.GRADUACAO.getFormacao();	
		else if(Formacao.POS_GRADUACAO.getFormacao().equalsIgnoreCase(nivel)) return Formacao.POS_GRADUACAO.getFormacao();
		return null;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	public static void saveAlunoRecord() {
		
	}

}
