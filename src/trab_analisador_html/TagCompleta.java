package trab_analisador_html;

public class TagCompleta extends Tag{
	private int quantidade;

	public TagCompleta(String nomeTag, int quantidade) {
		super(nomeTag);
		this.quantidade = quantidade;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int qtd) {
		this.quantidade = qtd;
	}
}
