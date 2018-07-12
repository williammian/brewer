package br.com.wm.brewer.common;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

public class MultiMapDataSet implements JRRewindableDataSource {
	private final MultiMap dados;
	private int actualRow = -1;

	public MultiMapDataSet(MultiMap dados) {
		this.dados = dados;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		return dados.getValueAt(actualRow, field.getName());
	}

	@Override
	public boolean next() throws JRException {
		if(actualRow + 1 >= dados.size()) return false;
		actualRow++;
		return true;
	}

	@Override
	public void moveFirst() throws JRException {
	}
}