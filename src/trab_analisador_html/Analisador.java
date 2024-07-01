package trab_analisador_html;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Analisador {

    private File arquivo;
    private ListaEncadeada listaTagsFechadas;
    private PilhaLista pilhaTagsAbertas;

    public Analisador(File arquivo, ListaEncadeada listaTags) {
        this.arquivo = arquivo;
        this.listaTagsFechadas = listaTags;
        this.pilhaTagsAbertas = new PilhaLista();
    }

    // Método para analisar o arquivo
    public String analisarArquivo() throws FileNotFoundException {
        Scanner scan = new Scanner(arquivo);
        // Percorre o arquivo
        while (scan.hasNextLine()) {
            String linha = scan.nextLine().trim();
          //Pula linhas vazias; analisa linhas não vazias;
            if (!linha.isEmpty()) {
                String wrongCloseTag = acharTags(linha);
                if (wrongCloseTag != null && !wrongCloseTag.isEmpty()) {
                    return "Foi encontrada uma tag " + wrongCloseTag + " inesperada."
                    		+ " (aguardava-se tag " + getPilhaTagsAbertas().peek().toString() +  " mas foi encontrada outra.)";
                }
            }
        }
        return "O arquivo está bem formatado.";
    }

    // Método para encontrar tags
    private String acharTags(String linha) {
        // Procura por abre tag '<'
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '<') {
                // Procura por fecha tag '>'
                for (int j = i + 1; j < linha.length(); j++) { // Começa busca imediatamente após o '<'
                    if (linha.charAt(j) == '>') { // Encontrou o caractere '>'
                        // Pega a tag formatada sem atributos
                        String tag = linha.substring(i + 1, j).split(" ")[0].toLowerCase();
                        if (!tag.startsWith("/")) { // Verifica se não é uma tag de fechamento
                            pilhaTagsAbertas.push(tag); // Empilha a tag de abertura
                        } else {
                            tag = tag.substring(1); // Remove o caractere '/'
                            if (pilhaTagsAbertasEstaVazia() || !pilhaTagsAbertas.peek().equals(tag)) {
                                return tag; // Retorna a tag de fechamento incorreta
                            }
                            pilhaTagsAbertas.pop(); // Desempilha a tag correta
                            addTagNaLista(tag); // Adiciona a tag à lista de tags fechadas
                        }
                        break; // Sai do loop interno ao encontrar o '>'
                    }
                }
            }
        }
        return null; // Retorna nulo se nenhuma tag incorreta foi encontrada
    }


    // Método para adicionar a tag na lista
    private void addTagNaLista(String tag) {
        for (int i = 0; i < listaTagsFechadas.obterComprimento(); i++) {
            Tag currentTag = (Tag) listaTagsFechadas.obterNo(i).getInfo();
            if (currentTag.getNome().equals(tag)) {
                currentTag.incrementQuantidade();
                return;
            }
        }
        listaTagsFechadas.inserir(new Tag(tag));
    }

    // Verifica se a pilha de tags abertas está vazia
    public boolean pilhaTagsAbertasEstaVazia() {
        return pilhaTagsAbertas.estaVazia();
    }

    // Retorna a pilha de tags abertas
    public PilhaLista getPilhaTagsAbertas() {
        return pilhaTagsAbertas;
    }
}
