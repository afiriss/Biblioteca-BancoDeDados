package tela;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dominio.Livros;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.JTextField;


@SuppressWarnings("unused")
public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 577, 347);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Minha Biblioteca Virtual");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Papyrus", Font.BOLD, 25));
		lblNewLabel.setBounds(142, 11, 284, 40);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("projeto criado por: Ellen Iris");
		lblNewLabel_1.setFont(new Font("Papyrus", Font.BOLD, 15));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(165, 49, 241, 29);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Cadastrar Livro");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastrarLivro ca = null;
				try {
					ca = new CadastrarLivro();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ca.setLocationRelativeTo(null);
				ca.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				ca.setVisible(true);
				
			
			}
		});
		btnNewButton_1.setBounds(195, 144, 143, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Exibir Livros Cadastrados");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaLivros ac = null;
				try {
					ac = new ListaLivros();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ac.setLocationRelativeTo(null);
				ac.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				ac.setVisible(true);

			}
			
		});
		btnNewButton_2.setBounds(165, 178, 204, 23);
		panel.add(btnNewButton_2);
		
		JLabel lblNewLabelImagem = new JLabel("");
		lblNewLabelImagem.setIcon(new ImageIcon(Principal.class.getResource("/img/Studioghibli1.jpg")));
		lblNewLabelImagem.setBounds(0, 0, 577, 347);
		panel.add(lblNewLabelImagem);
		
		
			JButton btnNewButton = new JButton("Cadastrar Livro");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CadastrarLivro ca = null;
					try {
						ca = new CadastrarLivro();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ca.setLocationRelativeTo(null);
					ca.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					ca.setVisible(true);
					
				}
			});
			
}
}
