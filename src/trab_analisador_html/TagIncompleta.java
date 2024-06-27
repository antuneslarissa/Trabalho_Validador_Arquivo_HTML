package trab_analisador_html;

public class TagIncompleta extends Tag{
	private int linha;
	
	public TagIncompleta(String nomeTag, int linha) {
		super(nomeTag);
		this.linha = linha;
	}

	public int getLinha() {
		return linha;
	}
}
