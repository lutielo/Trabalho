package br.unisul.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import br.unisul.dados.Autor;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;

public class CadastroAutor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNomeAutor;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JLabel lblNomeAutor;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFeminino;
	private JLabel lblCadatroDeAutor;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	CadastroAutor() {
		super("Cadastro Autor");
		setResizable(false);
		setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);

		this.abreTela();

	}

	private class TrataEventoSalvar implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (!txtNomeAutor.getText().isEmpty() || txtNomeAutor != null) {
				AutorDAO autorDAO = new AutorDAO();
				Integer sexoAutor = null;
				if (rdbtnFeminino.isSelected()) {
					sexoAutor = 2;
					CadastrarAutor(autorDAO, sexoAutor);
				} else if (rdbtnMasculino.isSelected()){
					sexoAutor = 1;
					CadastrarAutor(autorDAO, sexoAutor);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um sexo.");
				}
			}
		}

		private void CadastrarAutor(AutorDAO autorDAO, Integer sexoAutor) {
			Autor autor = new Autor(null, txtNomeAutor.getText(), sexoAutor);
			try {
				autorDAO.cadastreAutor(autor);
				JOptionPane.showMessageDialog(null, "Autor " + autor.getNome() + " cadastrado com sucesso.");
				fecharTela();
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao precessar sua requisição.");
				e.printStackTrace();
			}
		}
	}


	private void abreTela() {

		lblNomeAutor = new JLabel("Nome*:");
		lblNomeAutor.setBounds(32, 75, 46, 14);

		txtNomeAutor = new JTextField(20);
		txtNomeAutor.setBounds(88, 72, 249, 20);
		txtNomeAutor.setToolTipText("Nome do Autor");

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(199, 212, 90, 23);
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setPreferredSize(new Dimension(90, 23));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fecharTela();
			}
		});
		

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(82, 212, 80, 23);
		btnSalvar.setToolTipText("Salvar Autor");
		btnSalvar.setPreferredSize(new Dimension(80, 23));
		TrataEventoSalvar trataEventoSalvar = new TrataEventoSalvar();
		btnSalvar.addActionListener(trataEventoSalvar);

		JLabel lblSexo = new JLabel("Sexo*:");
		lblSexo.setBounds(32, 108, 46, 14);
		getContentPane().add(lblSexo);

		rdbtnMasculino = new JRadioButton("Masculino");
		buttonGroup.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(91, 104, 90, 23);
		rdbtnFeminino = new JRadioButton("Feminino");
		buttonGroup.add(rdbtnFeminino);
		rdbtnFeminino.setBounds(91, 130, 90, 23);
		getContentPane().setLayout(null);
		getContentPane().add(lblNomeAutor);
		getContentPane().add(txtNomeAutor);
		getContentPane().add(rdbtnMasculino);
		getContentPane().add(rdbtnFeminino);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);
		
		lblCadatroDeAutor = new JLabel("Cadatro de Autor");
		lblCadatroDeAutor.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCadatroDeAutor.setBounds(119, 11, 150, 20);
		getContentPane().add(lblCadatroDeAutor);
		

	}

	public void fecharTela() {
		this.dispose();
	}

	public static void main(String[] args) {
		CadastroAutor cadastroAutor = new CadastroAutor();
		cadastroAutor.setVisible(true);
	}
}
