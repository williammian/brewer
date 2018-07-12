package br.com.wm.brewer.common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class MultiMap {
	
	private final List<String> colunas;
	private final List<Object[]> dados;
	
	/**
	 * @param colunas String com os nomes das Colunas separadas por virgula.
	 */
	public MultiMap(String colunas) {
		StringTokenizer strTok = new StringTokenizer(colunas, ",");
		this.colunas = new ArrayList<String>(strTok.countTokens());
		while(strTok.hasMoreTokens()){
			this.colunas.add(strTok.nextToken().trim());
		}
		dados = new ArrayList<Object[]>();
	}
	
	public MultiMap(List<Map<String, Object>> list) {
		colunas = new ArrayList<String>();
		dados = new ArrayList<Object[]>();
		
		if(list == null || list.size() == 0)return;
		
		Map<String, Object> mapColunas = list.get(0);
		
		Map<String, Integer> campos = new HashMap<String, Integer>();
		int i = 0;
		for(String campo : mapColunas.keySet()) {
			campos.put(campo, i);
			i++;
		}
		
		colunas.addAll(Arrays.asList(new String[mapColunas.size()]));
		
		for(String campo : mapColunas.keySet()){
			colunas.set(campos.get(campo), campo);
		}
		
		for(i = 0; i < list.size(); i++) {
			Map<String, Object> mapDado = list.get(i);
			
			Object[] dado = new Object[colunas.size()];
			for(int j = 0; j < colunas.size(); j++) {
				Object obj = mapDado.get(colunas.get(j));
				
				if(obj instanceof BigInteger)obj = ((BigInteger)obj).longValue();
				
				dado[j] = obj;
			}
			dados.add(dado);
		}
	}
	
	public void addRow() {
		dados.add(new Object[colunas.size()]);
	}
	
	public void addRow(int index) {
		dados.add(index, new Object[colunas.size()]);
	}
	
	public void addRow(Object ... values) {
		if(values.length != colunas.size())throw new RuntimeException("Erro ao inserir valores no MultiMap. Foram enviados valores para " + values.length + " colunas, porém, o MultiMap possui " + colunas.size() + " colunas");
		dados.add(values);
	}

	public void addRow(int index, Object[] values) {
		if(values.length != colunas.size())throw new RuntimeException("Erro ao inserir valores no MultiMap. Foram enviados valores para " + values.length + " colunas, porém, o MultiMap possui " + colunas.size() + " colunas");
		dados.add(index, values);
	}
	
	public void removeRow(int index) {
		dados.remove(index);
	}

	public void setValueAt(Object value, String coluna) {
		dados.get(size()-1)[colunas.indexOf(coluna)] = value;
	}

	public void setValueAt(Object value, int row, String coluna) {
		dados.get(row)[colunas.indexOf(coluna)] = value;
	}
	
	public Object getValueAt(int row, String column) {
		int colIndex = colunas.indexOf(column);
		if(colIndex < 0){
			throw new RuntimeException("Coluna " + column + " não encontrada no MultiHashMap");
		}
		return dados.get(row)[colIndex];
	}

	public void clear() {
		dados.clear();
	}
	
	public Object[] getValueArray(int row) {
		return dados.get(row);
	}
	
	public List<String> getColunas() {
		return colunas;
	}
	
	public int size() {
		return dados.size();
	}
	
	public void addAll(MultiMap map) {
		if(!map.getColunas().equals(colunas))throw new RuntimeException("Colunas do MultiMap não são iguais para adição");
		dados.addAll(map.dados);
	}
}