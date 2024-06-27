package trab_analisador_html;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Analisador {

    private File arquivo;
    private PilhaLista pilhaTagsAbertas;
    private ListaEncadeada listaTagsEncontradas

    public Analisador(File arquivo) {
        this.arquivo = arquivo;
        pilha = new PilhaLista();
    }

    public void analisarArquivo() throws FileNotFoundException {
        Scanner scan = new Scanner(arquivo);
        int numLinha = 0;
        
        while (scan.hasNextLine()) {
            numLinha++;
            String linha = scan.nextLine();
            if (!linha.equals("")) {
                acharTags(linha, numLinha);
                acharFechaTags(linha);
            }
        }
    }

    private void acharTags(String linha, int numLinha) {
        int indexInicioTag = -1;
        int indexFimTag = -1;
        boolean retiraTag = false;
        
        // Procura por abre tag '<'
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '<') {
                indexInicioTag = i + 1;
                retiraTag = false;
                
                // Procura por fecha tag '>'
                for (int j = i; j < linha.length(); j++) {
                    
                    // Verifica se há '/' após o '<'
                    if (j == (i + 1) && linha.charAt(j) == '/') {
                        retiraTag = true;
                        indexInicioTag++;
                    }
                    if (linha.charAt(j) == '>') {
                        indexFimTag = j;
                        
                        String tag = linha.substring(indexInicioTag, indexFimTag);
                        tag = formatarTag(tag);
                        if (!retiraTag) {
                            guardarTagAberta(tag, numLinha);
                        }
                        else {
                            retirarTag(tag);
                        }
                        break;
                    }
                }
            }
        }
    }
    
    // Retira eventuais atributos da tag
    private String formatarTag(String tag) {
        int fimTag = tag.length();
        for (int i = 0; i < tag.length(); i++) {
            if (tag.charAt(i) == ' ') {
                fimTag = i;
                break;
            }
        }
        return tag.substring(0, fimTag);
    }
    
    private void guardarTagAberta(String tag, int numLinha) {
        TagIncompleta novaTag = new TagIncompleta(tag, numLinha);
        pilhaTagsAbertas.push(novaTag);
    }
    
    private void retirarTag(String tag) {
        
    }
}
