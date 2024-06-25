package trab_analisador_html;


public class PilhaVetor<T> implements Pilha<T> {
	private T[] info;
	private int limite;
	private int tamanho;

	@SuppressWarnings("unchecked")
	public PilhaVetor(int limite) {
		info = (T[]) new Object[limite];
		this.limite = limite;
		this.tamanho = 0;
	}

	@Override
	public void push(T info) {
		if (limite == tamanho) {
			throw new IndexOutOfBoundsException();
		}else {
			this.info[tamanho] = info;
			tamanho++;
		}
	}

	@Override
	public T pop() {
		T valor = peek();
		info[tamanho - 1] = null;
		tamanho--;
		return valor;
	}

	@Override
	public T peek() {
		if (estaVazia()) {
			 throw new PilhaVaziaException();
		}
		return info[tamanho - 1];
	}

	@Override
	public boolean estaVazia() {
		return (tamanho == 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void liberar() {
		info = (T[]) new Object[limite];
		tamanho = 0;
	}

	@Override
	public String toString() {
		String str = "";
		
		for(int i = tamanho - 1; i >= 0; i--) {
			str += info[i].toString();
			if(i > 0) {
				str = str + ";";
			}
		}
		return str;
	}
	    
}