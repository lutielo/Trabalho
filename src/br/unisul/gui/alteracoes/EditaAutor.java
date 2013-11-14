package br.unisul.gui.alteracoes;

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

public class EditaAutor extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField tfNomeAutor;
	private JLabel lblAlteracaoDeAutor;
	private JLabel lblCodigo;
	private JLabel lblNomeAutor;
	private JLabel lblSexo;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFeminino;
	private ButtonGroup btnGroupSexo;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JTextField tfCodigo;

	public EditaAutor(Autor autor) {
		super("Edita Autor");
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.abreTela(autor);
	}

	private void abreTela(Autor autor) {
		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(30, 66, 46, 14);

		lblNomeAutor = new JLabel("Nome*:");
		lblNomeAutor.setBounds(30, 101, 46, 14);

		tfCodigo = new JTextField();
		tfCodigo.setEditable(false);
		tfCodigo.setBounds(86, 63, 67, 20);
		tfCodigo.setColumns(10);
		tfCodigo.setText(autor.getCodigo().toString());

		tfNomeAutor = new JTextField(20);
		tfNomeAutor.setBounds(86, 98, 249, 20);
		tfNomeAutor.setToolTipText("Ex: João da Silva");
		tfNomeAutor.setText(autor.getNome());

		btnEditar = new JButton("Salvar");
		btnEditar.setBounds(82, 212, 80, 23);
		btnEditar.setToolTipText("Salvar Autor");
		btnEditar.setPreferredSize(new Dimension(80, 23));
		TrataEventoSalvar trataEventoSalvar = new TrataEventoSalvar();
		btnEditar.addActionListener(trataEventoSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(199, 212, 90, 23);
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setPreferredSize(new Dimension(90, 23));
		TrataEventoCancelar trataEventoCancelar = new TrataEventoCancelar();
		btnCancelar.addActionListener(trataEventoCancelar);

		lblSexo = new JLabel("Sexo*:");
		lblSexo.setBounds(30, 134, 46, 14);

		btnGroupSexo = new ButtonGroup();

		rdbtnMasculino = new JRadioButton("Masculino");
		btnGroupSexo.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(89, 130, 90, 23);
		rdbtnFeminino = new JRadioButton("Feminino");
		btnGroupSexo.add(rdbtnFeminino);
		rdbtnFeminino.setBounds(89, 156, 90, 23);

		if (autor.getSexo().getNome().equalsIgnoreCase("masculino")) {
			rdbtnMasculino.setSelected(true);
		} else if (autor.getSexo().getNome().equalsIgnoreCase("feminino")) {
			rdbtnFeminino.setSelected(true);
		}

		lblAlteracaoDeAutor = new JLabel("Altera\u00E7\u00E3o de Autor");
		lblAlteracaoDeAutor.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblAlteracaoDeAutor.setBounds(108, 11, 195, 20);

		getContentPane().add(lblCodigo);
		getContentPane().add(lblSexo);
		getContentPane().add(lblNomeAutor);
		getContentPane().add(tfCodigo);
		getContentPane().add(tfNomeAutor);
		getContentPane().add(rdbtnMasculino);
		getContentPane().add(rdbtnFeminino);
		getContentPane().add(btnEditar);
		getContentPane().add(btnCancelar);
		getContentPane().add(lblAlteracaoDeAutor);

		this.tabOrder();

	}

	private void tabOrder() {
		IndexedFocusTraversalPolicy policy = new IndexedFocusTraversalPolicy();
		policy.addIndexedComponent(tfNomeAutor);
		policy.addIndexedComponent(rdbtnMasculino);
		policy.addIndexedComponent(rdbtnFeminino);
		policy.addIndexedComponent(btnEditar);
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
					editarAutor(sexo);
				} else if (rdbtnMasculino.isSelected()) {
					sexoAutor = 1;
					Sexo sexo = new Sexo(sexoAutor, null);
					editarAutor(sexo);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um sexo.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Digite um nome válido");
			}
		}

		private void editarAutor(Sexo sexo) {
			Integer codigo = Integer.parseInt(tfCodigo.getText());
			Autor autor = new Autor(codigo, tfNomeAutor.getText(), sexo);
			AutorDAO autorDAO = new AutorDAO();
			try {
				autorDAO.alterarAutor(autor);
				JOptionPane.showMessageDialog(null, "Autor " + autor.getNome() + " editado com sucesso.");
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao precessar sua requisição.");
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

}
