package br.unisul.gui.relatorios.tablemodels;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unisul.dados.Receita;

/**
 * Implementa��o de Table Model para exibir as Receitas.
 */
public class ReceitaTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* Lista de Receitas que representam as linhas. */
	private List<Receita> linhas;

	/* Array de Strings com o nome das colunas. */
	private String[] colunas = new String[] { "C�digo", "Nome", "Autor", "Data Cria��o"};

	/* Cria um ReceitaTableModel vazio. */
	public ReceitaTableModel() {
		linhas = new ArrayList<Receita>();
	}

	/*
	 * Cria um ReceitaTableModel carregado com a lista de receitas especificada.
	 */
	public ReceitaTableModel(List<Receita> listaDeReceitas) {
		linhas = new ArrayList<Receita>(listaDeReceitas);
	}

	/* Retorna a quantidade de colunas. */
	@Override
	public int getColumnCount() {
		// Est� retornando o tamanho do array "colunas".
		// Mas como o array � fixo, vai sempre retornar 3.
		return colunas.length;
	}

	/* Retorna a quantidade de linhas. */
	@Override
	public int getRowCount() {
		// Retorna o tamanho da lista de receitas.
		return linhas.size();
	}

	/*
	 * Retorna o nome da coluna no �ndice especificado. Este m�todo � usado pela JTable para saber o texto do cabe�alho.
	 */
	@Override
	public String getColumnName(int columnIndex) {
		// Retorna o conte�do do Array que possui o nome das colunas
		// no �ndice especificado.
		return colunas[columnIndex];
	};

	/*
	 * Retorna a classe dos elementos da coluna especificada. Este m�todo � usado pela JTable na hora de definir o editor da c�lula.
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// Retorna a classe referente a coluna especificada.
		// Aqui � feito um switch para verificar qual � a coluna
		// e retornar o tipo adequado. As colunas s�o as mesmas
		// que foram especificadas no array "colunas".
		switch (columnIndex) {
			case 0: // Primeira coluna � o C�digo, que � um Integer.
				return Integer.class;
			case 1: // Segunda coluna � o Nome, que � uma String..
				return String.class;
			case 2: // Terceira coluna � o nome do Autor,
					// que tamb�m � uma String
				return String.class;
			case 3: // Quarta coluna � a data de cria��o,
					// que tamb�m � uma Date
				return Date.class;
			default:
				// Se o �ndice da coluna n�o for v�lido, lan�a um
				// IndexOutOfBoundsException (Exce��o de �ndice fora dos limites).
				// N�o foi necess�rio verificar se o �ndice da linha � inv�lido,
				// pois o pr�prio ArrayList lan�a a exce��o caso seja inv�lido.
				throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	/*
	 * Retorna o valor da c�lula especificada pelos �ndices da linha e da coluna.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// Pega a receita da linha especificada.
		Receita receita = linhas.get(rowIndex);

		// Retorna o campo referente a coluna especificada.
		// Aqui � feito um switch para verificar qual � a coluna
		// e retornar o campo adequado. As colunas s�o as mesmas
		// que foram especificadas no array "colunas".
		switch (columnIndex) {
			case 0: // Primeira coluna � o c�digo.
				return receita.getCodigo();
			case 1: // Segunda coluna � o nome.
				return receita.getNome();
			case 2: // Terceira colina � o nome do .
				return receita.getAutor().getNome();
			case 3: // Quarta coluna � a data de cria��o.
				return receita.getDt_criacao();
			default:
				// Se o �ndice da coluna n�o for v�lido, lan�a um
				// IndexOutOfBoundsException (Exce��o de �ndice fora dos limites).
				// N�o foi necess�rio verificar se o �ndice da linha � inv�lido,
				// pois o pr�prio ArrayList lan�a a exce��o caso seja inv�lido.
				throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	/*
	 * Seta o valor da c�lula especificada pelos �ndices da linha e da coluna. Aqui ele est� implementado para n�o fazer nada, at� porque este
	 * table model n�o � edit�vel.
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	};

	/*
	 * Retorna um valor booleano que define se a c�lula em quest�o pode ser editada ou n�o. Este m�todo � utilizado pela JTable na hora de
	 * definir o editor da c�lula. Neste caso, estar� sempre retornando false, n�o permitindo que nenhuma c�lula seja editada.
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	// //////////////////////////////////////////////////////////
	// Os m�todos declarados at� aqui foram as implementa��es  //
	// de TableModel, que s�o continuamente utilizados 		   //
	// pela JTable para definir seu comportamento, 			   //
	// por isso o nome Table Model (Modelo da Tabela). 		   //
	// 														   //
	// A partir de agora, os m�todos criados ser�o 			   //
	// particulares desta classe. Eles ser�o �teis 			   //
	// em algumas situa��es. 								   //
	// //////////////////////////////////////////////////////////

	/* Retorna a receita da linha especificada. */
	public Receita getreceita(int indiceLinha) {
		return linhas.get(indiceLinha);
	}

	/* Adiciona um registro. */
	public void addReceita(Receita receita) {
		// Adiciona o registro.
		linhas.add(receita);

		// Pega a quantidade de registros e subtrai um para achar
		// o �ltimo �ndice. � preciso subtrair um, pois os �ndices
		// come�am pelo zero.
		int ultimoIndice = getRowCount() - 1;

		// Reporta a mudan�a. O JTable recebe a notifica��o
		// e se redesenha permitindo que visualizemos a atualiza��o.
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	/* Remove a linha especificada. */
	public void removeReceita(int indiceLinha) {
		// Remove a receita da linha especificada.
		linhas.remove(indiceLinha);

		// Reporta a mudan�a. O JTable recebe a notifica��o
		// e se redesenha permitindo que visualizemos a atualiza��o.
		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	/* Adiciona uma lista de receitas ao final dos registros. */
	public void addListaDeReceitas(List<Receita> receitas) {
		// Pega o tamanho antigo da tabela.
		int tamanhoAntigo = getRowCount();

		// Adiciona os registros.
		linhas.addAll(receitas);

		// Reporta a mudan�a. O JTable recebe a notifica��o
		// e se redesenha permitindo que visualizemos a atualiza��o.
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	/* Remove todos os registros. */
	public void limpar() {
		// Remove todos os elementos da lista de receitas.
		linhas.clear();

		// Reporta a mudan�a. O JTable recebe a notifica��o
		// e se redesenha permitindo que visualizemos a atualiza��o.
		fireTableDataChanged();
	}

	/* Verifica se este table model est� vazio. */
	public boolean isEmpty() {
		return linhas.isEmpty();
	}

}
