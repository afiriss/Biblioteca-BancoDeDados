package tela;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import banco.BibliotecaVirtual;
import dominio.Livros;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class DialogEditarLivro extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldTitulo;
	private JTextField textFieldAutor;
	private JTextField textFieldGenero;
	private int id_livro;

	public static void main(String[] args) {
		try {
			DialogEditarLivro dialog = new DialogEditarLivro();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLivroatual (Livros livroAtual) {
		textFieldTitulo.setText(livroAtual.getTitulo());
		textFieldAutor.setText(livroAtual.getAutor());
		textFieldGenero.setText(livroAtual.getGenero());
		this.id_livro = livroAtual.getId_livro();
	}

	public DialogEditarLivro() {
		setBounds(100, 100, 322, 376);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Editar livro", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel.setBounds(10, 11, 284, 316);
		getContentPane().add(panel);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setColumns(10);
		textFieldTitulo.setBounds(28, 46, 207, 20);
		panel.add(textFieldTitulo);
		
		textFieldAutor = new JTextField();
		textFieldAutor.setColumns(10);
		textFieldAutor.setBounds(28, 105, 207, 20);
		panel.add(textFieldAutor);
		
		textFieldGenero = new JTextField();
		textFieldGenero.setColumns(10);
		textFieldGenero.setBounds(28, 163, 207, 20);
		panel.add(textFieldGenero);
		
		JLabel lblTitulo = new JLabel("Título");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(28, 21, 58, 14);
		panel.add(lblTitulo);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setForeground(Color.WHITE);
		lblAutor.setBounds(28, 80, 68, 14);
		panel.add(lblAutor);
		
		JLabel lblGenero = new JLabel("Gênero");
		lblGenero.setForeground(Color.WHITE);
		lblGenero.setBounds(28, 141, 58, 14);
		panel.add(lblGenero);
		
		JButton btnNewButtonEditar = new JButton("Finalizar Edição");
		btnNewButtonEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					iniciarEdicaoLivros();
				} catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});
		btnNewButtonEditar.setBounds(28, 212, 207, 23);
		panel.add(btnNewButtonEditar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(DialogEditarLivro.class.getResource("/img/IMG_5362.jpeg")));
		lblNewLabel.setBounds(0, 0, 306, 337);
		getContentPane().add(lblNewLabel);
	}
	
	private void iniciarEdicaoLivros() throws ClassNotFoundException, SQLException {
	    // Verificação dos campos obrigatórios
	    if (textFieldTitulo.getText().isEmpty()) {
	        exibirMensagemErro("O título do livro não pode ser vazio.");
	        return;
	    }

	    if (textFieldAutor.getText().isEmpty()) {
	        exibirMensagemErro("O campo 'autor' não pode ser vazio.");
	        return;
	    }

	    if (textFieldGenero.getText().isEmpty()) {
	        exibirMensagemErro("O gênero do livro não pode ser vazio.");
	        return;
	    }
		
        // Editar o livro no banco
        Connection conexao = BibliotecaVirtual.criarConexao();
        String sql = "UPDATE Cadastrolivros SET titulo=?, autor=?, genero=? WHERE id_livro=?";

        Livros livro = new Livros();
        livro.setTitulo(textFieldTitulo.getText());
        livro.setAutor(textFieldAutor.getText());
        livro.setGenero(textFieldGenero.getText());
        livro.setId_livro(this.id_livro);

        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, livro.getTitulo());
        comando.setString(2, livro.getAutor());
        comando.setString(3, livro.getGenero());
        comando.setInt(4, livro.getId_livro());
        
        try {
            comando.execute();
        	ExibirMensagem("Livro editado com sucesso!");
        	
        	// Limpa os campos de texto após a operação
    	    textFieldTitulo.setText("");
    	    textFieldAutor.setText("");
    	    textFieldGenero.setText("");
    	    
    	    // Fechar a tela se der certo
    	    setVisible(false);
        } catch(Exception e) {
        	ExibirMensagem("Erro ao editar!");
        } finally {
            comando.close();
            conexao.close();
        }
	}
	
	protected void exibirMensagemErro(String msg) {
		JOptionPane.showMessageDialog(null, msg, "ERRO", JOptionPane.ERROR_MESSAGE);
	}
	
	protected void ExibirMensagem(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
}
