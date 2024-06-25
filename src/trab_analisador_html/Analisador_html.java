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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import java.awt.Label;
import java.awt.ScrollPane;
import javax.swing.JTextPane;

public class Analisador_html {

    private JFrame frame;
    private JTextField textField;
    private JTable table;
    private DefaultTableModel tableModel;

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

        textField = new JTextField();
        textField.setBounds(72, 11, 254, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

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
        
        Label label = new Label("Arquivo:");
        label.setBounds(10, 10, 59, 21);
        frame.getContentPane().add(label);
        
        JTextPane textPane = new JTextPane();
        textPane.setBounds(10, 41, 415, 101);
        frame.getContentPane().add(textPane);
    }

    private void analisarArquivo() {
        String caminhoArquivo = textField.getText();
        Map<String, Integer> tagCount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tags = line.split("<|>");
                for (String tag : tags) {
                    if (!tag.trim().isEmpty() && !tag.startsWith("/")) {
                        tagCount.put(tag, tagCount.getOrDefault(tag, 0) + 1);
                    }
                }
            }

            // Limpa a tabela antes de adicionar novas linhas
            tableModel.setRowCount(0);

            for (Map.Entry<String, Integer> entry : tagCount.entrySet()) {
                tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
