package br.com.wm.brewer.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class MultiKeyMap {
	private List<String> colunas;
	private Map<Object, Object[]> dados;
	
	/**
	 * @param colunas String com os nomes das Colunas separadas por virgula
	 */
	public MultiKeyMap(String colunas){
		StringTokenizer strTok = new StringTokenizer(colunas, ",");
		this.colunas = new ArrayList<String>(strTok.countTokens());
		while(strTok.hasMoreTokens()){
			this.colunas.add(strTok.nextToken().toUpperCase().trim());
		}
		dados = new HashMap<Object, Object[]>();
	}
	
	public void put(Object key, String coluna, Object value){
		if(!colunas.contains(coluna.toUpperCase())){
			throw new RuntimeException("Coluna " + coluna + " não encontrada MultiKeyMap");
		}
		
		Object[] valores = dados.get(key);
		boolean existe = true;
		if(valores == null){
			existe = false;
			valores = new Object[colunas.size()];
		}
		valores[colunas.indexOf(coluna.toUpperCase())] = value;
		
		if(!existe){
			dados.put(key, valores);
		}
	}
	
	/**
	 * Insere os valores do array de objetos diretamento como v
	 * dados para chave
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object ... valores){
		if(valores.length != colunas.size()){
			throw new RuntimeException("A quantidade de valores informados para a inserção no MultiKeyMap está inválida");
		}
		
		dados.put(key, valores);
	}
	
	
	public Object getValueAt(Object key, String column){
		if(!colunas.contains(column.toUpperCase())){
			throw new RuntimeException("Coluna " + column + " não encontrada no MultiKeyMap");
		}
		Object[] valores = dados.get(key);
		if(valores == null)return null;
		return valores[colunas.indexOf(column.toUpperCase())];
	}
	
	public Object[] get(Object key){
		return dados.get(key);
	}
	
	
	public void remove(Object key){
		dados.remove(key);
	}

	public void clear() {
		dados.clear();
	}

	public boolean containsKey(Object key) {
		return dados.containsKey(key);
	}

	public Set<?> keySet() {
		return dados.keySet();
	}

	public int size() {
		return dados.size();
	}
	
	public List<String> getColunas(){
		return colunas;
	}
	
	public MultiMap getMultiMap(){
		StringBuilder strColunas = new StringBuilder("");
		
		for(String coluna : colunas){
			if(strColunas.length() == 0)strColunas.append(coluna);
			else strColunas.append(", ").append(coluna);
		}
		
		MultiMap map = new MultiMap(strColunas.toString());
		
		TreeSet<Object> setOrdenado = new TreeSet<Object>();//Ordena a chave
		setOrdenado.addAll(dados.keySet());
		
		int i = 0;
		for(Object key : setOrdenado){
			Object[] dadosLinha = get(key);
			map.addRow(i, dadosLinha);
			i++;
		}
		
		return map;
	}
	
}
