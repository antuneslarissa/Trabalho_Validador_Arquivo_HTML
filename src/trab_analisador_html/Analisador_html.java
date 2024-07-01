
package trab_analisador_html;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;

public class Analisador_html {

    private JFrame frame;
    private JTextField textField;
    private JTable table;
    private DefaultTableModel tableModel;
    private ListaEncadeada listaTagsFechadas;
    private JTextPane textPane;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Analisador_html window = new Analisador_html();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Analisador_html() {
        listaTagsFechadas = new ListaEncadeada();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel label = new JLabel("Arquivo:");
        label.setBounds(10, 14, 59, 14);
        frame.getContentPane().add(label);

        textField = new JTextField();
        textField.setBounds(72, 11, 254, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JButton btnAnalisar = new JButton("Analisar");
        btnAnalisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                analisarArquivo();
            }
        });
        btnAnalisar.setBounds(336, 10, 89, 23);
        frame.getContentPane().add(btnAnalisar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 146, 414, 104);
        frame.getContentPane().add(scrollPane);

        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Tag", "Número de ocorrências"}
        );
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        textPane = new JTextPane();
        textPane.setBounds(10, 41, 415, 101);
        frame.getContentPane().add(textPane);
        textPane.setText(null);
    }

    private void analisarArquivo() {
        String caminhoArquivo = textField.getText();
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
        	
        	textPane.setText("Arquivo não encontrado.");
            return;
        }

        Analisador analisador = new Analisador(arquivo, listaTagsFechadas);

        try {
            String resultado = analisador.analisarArquivo();
            if (resultado != null) {
            	textPane.setText(resultado);
            }

            // Limpa a tabela antes de adicionar novas linhas
            tableModel.setRowCount(0);

            for (int i = 0; i < listaTagsFechadas.obterComprimento(); i++) {
                Tag tag = (Tag) listaTagsFechadas.obterNo(i).getInfo();
                tableModel.addRow(new Object[]{tag.getNome(), tag.getQuantidade()});
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        listaTagsFechadas.liberar();
    }
}
