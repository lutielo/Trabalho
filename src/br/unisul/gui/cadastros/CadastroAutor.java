package br.unisul.gui.cadastros;

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
import br.unisul.dados.Sexo;
import br.unisul.dao.AutorDAO;
import br.unisul.dao.DAOException;
import br.unisul.util.IndexedFocusTraversalPolicy;
import br.unisul.util.StringUtils;

public class CadastroAutor extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField tfNomeAutor;
	private JLabel lblNomeAutor;
	private JLabel lblSexo;
	private JLabel lblCadatroDeAutor;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFeminino;
	private ButtonGroup btnGroupSexo;
	private JButton btnSalvar;
	private JButton btnCancelar;

	public CadastroAutor() {
		super("Cadastro Autor");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela();
	}

	private void abreTela() {

		lblNomeAutor = new JLabel("Nome*:");
		lblNomeAutor.setBounds(32, 75, 46, 14);

		tfNomeAutor = new JTextField(20);
		tfNomeAutor.setBounds(88, 72, 249, 20);
		tfNomeAutor.setToolTipText("Ex: João da Silva");

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(82, 212, 80, 23);
		btnSalvar.setToolTipText("Salvar Autor");
		btnSalvar.setPreferredSize(new Dimension(80, 23));
		TrataEventoSalvar trataEventoSalvar = new TrataEventoSalvar();
		btnSalvar.addActionListener(trataEventoSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(199, 212, 90, 23);
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setPreferredSize(new Dimension(90, 23));
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);

		lblSexo = new JLabel("Sexo*:");
		lblSexo.setBounds(32, 108, 46, 14);

		btnGroupSexo = new ButtonGroup();

		rdbtnMasculino = new JRadioButton("Masculino");
		btnGroupSexo.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(91, 104, 90, 23);
		rdbtnFeminino = new JRadioButton("Feminino");
		btnGroupSexo.add(rdbtnFeminino);
		rdbtnFeminino.setBounds(91, 130, 90, 23);

		lblCadatroDeAutor = new JLabel("Cadatro de Autor");
		lblCadatroDeAutor.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCadatroDeAutor.setBounds(119, 11, 150, 20);

		getContentPane().add(lblSexo);
		getContentPane().add(lblNomeAutor);
		getContentPane().add(tfNomeAutor);
		getContentPane().add(rdbtnMasculino);
		getContentPane().add(rdbtnFeminino);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnCancelar);
		getContentPane().add(lblCadatroDeAutor);

		this.tabOrder();

	}

	private void tabOrder() {
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(tfNomeAutor);
		policy.addIndexedComponent(rdbtnMasculino);
		policy.addIndexedComponent(rdbtnFeminino);
		policy.addIndexedComponent(btnSalvar);
		policy.addIndexedComponent(btnCancelar);
		setFocusTraversalPolicy(policy);
	}

	public void fecharTela() {
		this.dispose();
	}

	private class TrataEventoSalvar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (!StringUtils.isNuloOuBranco(tfNomeAutor.getText())) {
				Integer sexoAutor = null;
				if (rdbtnFeminino.isSelected()) {
					sexoAutor = 2;
					Sexo sexo = new Sexo(sexoAutor, null);
					cadastrarAutor(sexo);
				} else if (rdbtnMasculino.isSelected()) {
					sexoAutor = 1;
					Sexo sexo = new Sexo(sexoAutor, null);
					cadastrarAutor(sexo);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um sexo.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Digite um nome válido");
			}
		}

		private void cadastrarAutor(Sexo sexo) {
			Autor autor = new Autor(null, tfNomeAutor.getText(), sexo);
			AutorDAO autorDAO = new AutorDAO();
			try {
				autorDAO.cadastrarAutor(autor);
				JOptionPane.showMessageDialog(null, "Autor " + autor.getNome() + " cadastrado com sucesso.");
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Sua requisição não foi processada.");
				e.printStackTrace();
			} finally {
				fecharTela();
			}
		}
	}

	private class TrataEventoCancelar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			fecharTela();
		}
	}

	public static void main(String[] args) {
		CadastroAutor cadastroAutor = new CadastroAutor();
		cadastroAutor.setVisible(true);
	}
}
