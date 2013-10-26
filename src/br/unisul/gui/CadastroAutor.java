package br.unisul.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	CadastroAutor() {
		super("Cadastro Autor");
		setResizable(false);
		setType(Type.UTILITY);

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(20);
		flowLayout.setHgap(30);
		getContentPane().setLayout(flowLayout);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);

		this.abreTela();

	}

	private class TrataEventoSalvar implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (!txtNomeAutor.getText().isEmpty() || txtNomeAutor != null) {
				AutorDAO autorDAO = new AutorDAO();
				int sexoAutor = 0;
				if (rdbtnFeminino != null) {
					sexoAutor = 1;
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
		}
	}

	private void abreTela() {

		lblNomeAutor = new JLabel("Nome");

		txtNomeAutor = new JTextField(20);
		txtNomeAutor.setToolTipText("Nome do Autor");

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setPreferredSize(new Dimension(90, 23));

		btnSalvar = new JButton("Salvar");
		btnSalvar.setToolTipText("Salvar Autor");
		btnSalvar.setPreferredSize(new Dimension(80, 23));
		TrataEventoSalvar trataEventoSalvar = new TrataEventoSalvar();
		btnSalvar.addActionListener(trataEventoSalvar);

		getContentPane().add(lblNomeAutor);
		getContentPane().add(txtNomeAutor);

		rdbtnMasculino = new JRadioButton("Masculino");
		getContentPane().add(rdbtnMasculino);
		rdbtnFeminino = new JRadioButton("Feminino");
		getContentPane().add(rdbtnFeminino);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);

	}

	public void fecharTela() {
		this.dispose();
	}

	public static void main(String[] args) {
		CadastroAutor cadastroAutor = new CadastroAutor();
		cadastroAutor.setVisible(true);
	}
}
