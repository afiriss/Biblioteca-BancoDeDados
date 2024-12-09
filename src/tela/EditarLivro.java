package tela;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import banco.BibliotecaVirtual;
import dominio.Livros;
import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;

public class EditarLivro extends JFrame {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		JTextField textFieldTitulo;
		 JTextField textFieldAutor;
		 JTextField textFieldGenero;
		@SuppressWarnings({ "rawtypes", "unused" })
		private JList listarLivros;
		private JButton btnNewButtonCadastrar;
		
		private int id_livro;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						EditarLivro frame = new EditarLivro();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		public void setLivroatual (Livros livroAtual) {
			textFieldTitulo.setText(livroAtual.getTitulo());
			textFieldAutor.setText(livroAtual.getAutor());
			textFieldGenero.setText(livroAtual.getGenero());
			this.id_livro = livroAtual.getId_livro();
		}

		/**
		 * Create the frame.
		 * 
		 * @throws SQLException
		 * @throws ClassNotFoundException
		 */
		@SuppressWarnings({ })
		public EditarLivro() throws ClassNotFoundException, SQLException {
			setTitle("Edição de Livros");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 320, 377);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(128, 128, 192));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Editar livro", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 255, 255)));
			panel.setBounds(10, 11, 284, 316);
			contentPane.add(panel);
			panel.setLayout(null);
			panel.setOpaque(false);

			textFieldTitulo = new JTextField();
			textFieldTitulo.setBounds(28, 46, 207, 20);
			panel.add(textFieldTitulo);
			textFieldTitulo.setColumns(10);

			textFieldAutor = new JTextField();
			textFieldAutor.setColumns(10);
			textFieldAutor.setBounds(28, 105, 207, 20);
			panel.add(textFieldAutor);

			textFieldGenero = new JTextField();
			textFieldGenero.setColumns(10);
			textFieldGenero.setBounds(28, 163, 207, 20);
			panel.add(textFieldGenero);

			JLabel lblTitulo = new JLabel("Título");
			lblTitulo.setForeground(new Color(255, 255, 255));
			lblTitulo.setBounds(28, 21, 58, 14);
			panel.add(lblTitulo);

			JLabel lblAutor = new JLabel("Autor");
			lblAutor.setForeground(new Color(255, 255, 255));
			lblAutor.setBounds(28, 80, 68, 14);
			panel.add(lblAutor);

			JLabel lblGenero = new JLabel("Gênero");
			lblGenero.setForeground(new Color(255, 255, 255));
			lblGenero.setBounds(28, 141, 58, 14);
			panel.add(lblGenero);

			btnNewButtonCadastrar = new JButton("Finalizar Edição");
			btnNewButtonCadastrar.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        try {
				            iniciarEdicaoLivros(); 
				        } catch (ClassNotFoundException | SQLException e1) {
				            e1.printStackTrace();
				        }
				    }
				});
			

			btnNewButtonCadastrar.setBounds(28, 212, 207, 23);
			panel.add(btnNewButtonCadastrar);
				
			;
			
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(CadastrarLivro.class.getResource("/img/Studioghibli1.jpg")));
			lblNewLabel.setBounds(0, 0, 319, 338);
			contentPane.add(lblNewLabel);

			atualizarListagemLivros();
		}
		
		protected void iniciarEdicaoLivros() throws ClassNotFoundException, SQLException {
			if (listarLivros.getSelectedIndex() == -1) {
				exibirMensagemErro("Selecione um Livro");
				return;
			}	
		
			//Livros EditarLivros = (Livros) listarLivros.getSelectedValue();
			//textFieldTitulo.setText(EditarLivros.getTitulo());
			//textFieldAutor.setText(EditarLivros.getAutor());
			//textFieldGenero.setText(EditarLivros.getGenero());
					
			btnNewButtonCadastrar.setText("Finalizar Edição");
		}
		
		protected void ExibirMensagem(String msg) {
			JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);

		}

		private void atualizarListagemLivros() throws ClassNotFoundException, SQLException {

			Connection conexao = BibliotecaVirtual.criarConexao();

			String sql = "SELECT * FROM CADASTROLIVROS";

			PreparedStatement comando = conexao.prepareStatement(sql);

			ResultSet resultado = comando.executeQuery();

			List<Livros> LivrosCadastrados = new ArrayList<Livros>();

			while (resultado.next()) {
				Livros a = new Livros();

				a.setId_livro(resultado.getInt("id_livro"));
				a.setTitulo(resultado.getString("titulo"));
				a.setAutor(resultado.getString("autor"));
				a.setGenero(resultado.getString("genero"));

				LivrosCadastrados.add(a);
			}

			DefaultListModel<Livros> modelo = new DefaultListModel<>();

			for (int i = 0; i < LivrosCadastrados.size(); i++) {
				Livros a = LivrosCadastrados.get(i);
				modelo.addElement(a);
			}

			comando.close();
			conexao.close();

		}

		@SuppressWarnings("null")
		protected void Livro() throws ClassNotFoundException, SQLException {
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
		 if (btnNewButtonCadastrar.getText().equals("Cadastrar")) {

	        // Cadastro do livro
	        Connection conexao = BibliotecaVirtual.criarConexao();
	        String sql = "INSERT INTO CADASTROLIVROS (titulo, autor, genero) VALUES (?,?,?)";

	        Livros livro = new Livros();
	        livro.setTitulo(textFieldTitulo.getText());
	        livro.setAutor(textFieldAutor.getText());
	        livro.setGenero(textFieldGenero.getText());

	        PreparedStatement comando = conexao.prepareStatement(sql);
	        comando.setString(1, livro.getTitulo());
	        comando.setString(2, livro.getAutor());
	        comando.setString(3, livro.getGenero());
	        comando.execute();

	        ExibirMensagem("Livro cadastrado com sucesso!");

	        comando.close();
	        conexao.close();

	    }  else if (btnNewButtonCadastrar.getText().equals("Finalizar Edição")) {

			Connection conexao = BibliotecaVirtual.criarConexao();

	        Livros EditarLivros = null;
			EditarLivros.setTitulo(textFieldTitulo.getText());
	        EditarLivros.setAutor(textFieldAutor.getText());
	        EditarLivros.setGenero(textFieldGenero.getText());

			String sql = "UPDATE Cadastrolivros SET titulo=?, autor=?, genero=? WHERE id_livro=?";

	        
	        PreparedStatement comando = conexao.prepareStatement(sql);
	        comando.setString(1, EditarLivros.getTitulo());
	        comando.setString(2, EditarLivros.getAutor());
	        comando.setString(3, EditarLivros.getGenero());
	        comando.setInt(4, EditarLivros.getId_livro());
	        comando.executeUpdate();

	        ExibirMensagem("Dados do livro atualizados com sucesso!");

	        comando.close();
	        conexao.close();

	        // Resetando a edição
	        EditarLivros = null;
	    }

	    // Atualiza a lista de livros
	    atualizarListagemLivros();

	    // Limpa os campos de texto após a operação
	    textFieldTitulo.setText("");
	    textFieldAutor.setText("");
	    textFieldGenero.setText("");
	}


		private void exibirMensagemErro(String msg) {
			JOptionPane.showMessageDialog(null, msg, "ERRO", JOptionPane.ERROR_MESSAGE);

			}
	}