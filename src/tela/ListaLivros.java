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
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import banco.BibliotecaVirtual;
import dominio.Livros;
import javax.swing.border.EtchedBorder;

public class ListaLivros extends JFrame {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		@SuppressWarnings("rawtypes")
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
		@SuppressWarnings("rawtypes")
		public ListaLivros() throws ClassNotFoundException, SQLException {
			setTitle("Lista de livros disponíveis na sua Biblioteca Virtual");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 596, 383);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(128, 128, 192));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);

			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Livros dispon\u00EDveis para consulta:", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
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

				DialogEditarLivro del = null;
				try {
					del = new DialogEditarLivro();
					del.setLivroatual(EditarLivros);
					del.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					del.setModal(true);
					del.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				atualizarListagemLivros();
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

		private void exibirMensagemErro(String msg) {
			JOptionPane.showMessageDialog(null, msg, "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}