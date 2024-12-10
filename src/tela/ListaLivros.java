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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import banco.BibliotecaVirtual;
import dominio.Livros;

public class ListaLivros extends JFrame {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTextField textFieldTitulo;
		private JTextField textFieldAutor;
		private JTextField textFieldGenero;
		private JList listarLivros;
		private Livros EditarLivros;
		private JButton btnNewButtonCadastrar;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ListaLivros frame = new ListaLivros();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		/**
		 * Create the frame.
		 * 
		 * @throws SQLException
		 * @throws ClassNotFoundException
		 */
		@SuppressWarnings({ "rawtypes" })
		public ListaLivros() throws ClassNotFoundException, SQLException {
			setTitle("Cadastro de Livros");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 596, 383);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(128, 128, 192));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			JPanel panel_1 = new JPanel();
			panel_1.setBorder(
					new TitledBorder(null, "Livros disponíveis para consulta:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(0, 0, 580, 344);
			contentPane.add(panel_1);
			panel_1.setLayout(null);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(42, 31, 502, 226);
			panel_1.add(scrollPane);
			
					listarLivros = new JList();
					scrollPane.setViewportView(listarLivros);

			JButton btnNewButtonExibir = new JButton("Exibir Dados");
			btnNewButtonExibir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Livros livroSelecionado = (Livros) listarLivros.getSelectedValue();

					if (listarLivros.getSelectedIndex() == -1) {
						exibirMensagemErro("Selecione um Livro");
					}

					String msg = "Título: " + livroSelecionado.getTitulo() + "\nAutor: " + livroSelecionado.getAutor()
							+ "\nGênero: " + livroSelecionado.getGenero();
					
					ExibirMensagem(msg);
				}
			});
			btnNewButtonExibir.setBounds(55, 281, 145, 23);
			panel_1.add(btnNewButtonExibir);

			JButton btnNewButtonEditar = new JButton("Editar");
			btnNewButtonEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						iniciarEdicaoLivros();
						
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnNewButtonEditar.setBounds(210, 281, 182, 23);
			panel_1.add(btnNewButtonEditar);

			JButton btnNewButtonRemover = new JButton("Remover");
			btnNewButtonRemover.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						removerDados();
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			});
			btnNewButtonRemover.setBounds(402, 281, 155, 23);
			panel_1.add(btnNewButtonRemover);

			atualizarListagemLivros();
		}

		protected void removerDados() throws ClassNotFoundException, SQLException {

			if (listarLivros.getSelectedIndex() == -1) {
				exibirMensagemErro("Selecione um Livro");
			}

			EditarLivros = (Livros) listarLivros.getSelectedValue();
			
			Connection conexao = BibliotecaVirtual.criarConexao();

			String sql = "DELETE FROM CADASTROLIVROS WHERE id_livro=?";


			PreparedStatement comando = conexao.prepareStatement(sql);
			
			comando.setInt(1, EditarLivros.getId_livro());
			comando.executeUpdate();

			ExibirMensagem("Dados do livro Removidos!");

			atualizarListagemLivros();
			
			comando.close();
			conexao.close();
			
		}

		protected void iniciarEdicaoLivros() throws ClassNotFoundException, SQLException {
			if (listarLivros.getSelectedIndex() == -1) {
				exibirMensagemErro("Selecione um Livro");
				return;
			}	
			else {
			CadastrarLivro cl = new CadastrarLivro();

			EditarLivros = (Livros) listarLivros.getSelectedValue();
			cl.textFieldTitulo.setText(EditarLivros.getTitulo());
			cl.textFieldAutor.setText(EditarLivros.getAutor());
			cl.textFieldGenero.setText(EditarLivros.getGenero());
			this.btnNewButtonCadastrar = new JButton("Editar");
			btnNewButtonCadastrar.setText("Editar");


			EditarLivro ca = null;

			try {
				ca = new EditarLivro();
				ca.setLivroatual(EditarLivros);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ca.setLocationRelativeTo(null);
			ca.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			ca.setVisible(true);
			
		
			}
		}

		protected void ExibirMensagem(String msg) {
			JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);

		}

		@SuppressWarnings("unchecked")
		private void atualizarListagemLivros() throws ClassNotFoundException, SQLException {

			Connection conexao = BibliotecaVirtual.criarConexao();

			String sql = "SELECT * FROM CadastroLivros";

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

			listarLivros.setModel(modelo);

			comando.close();
			conexao.close();

		}

		protected void EditarLivro() throws ClassNotFoundException, SQLException {
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

		    
		    if (btnNewButtonCadastrar.getText().equals("Finalizar Edição")) {

		        // Cadastro do livro
		        Connection conexao = BibliotecaVirtual.criarConexao();
		        String sql = "INSERT INTO CadastroLivros (titulo, autor, genero) VALUES (?,?,?)";

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


				JOptionPane.showMessageDialog(null, "O livro foi cadastrado com sucesso", "Info",
						JOptionPane.INFORMATION_MESSAGE);
				
		    } 
		    
		    if (btnNewButtonCadastrar.getText().equals("Finalizar Edição")) {

				Connection conexao = BibliotecaVirtual.criarConexao();

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