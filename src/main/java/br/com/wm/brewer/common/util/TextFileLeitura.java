package br.com.wm.brewer.common.util;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Splitter;

public class TextFileLeitura {

	private List<String> registros;
	
	private int index = 0;
	
	private String separador;
	
	private String linha;
	
	private List<String> registro; //Variável que armazena a linha dividida conforme o separador
	
	public TextFileLeitura(List<String> registros) {
		this.registros = registros;
	}
	
	public TextFileLeitura(List<String> registros, String separador) {
		this.registros = registros;
		this.separador = separador;
	}
	
	public static List<String> obterRegistrosArquivo(String path) {
		try {
			File file = new File(path);
			return FileUtils.readLines(file, "UTF-8");
		}catch (Exception err) {
			throw new RuntimeException(err);
		}
	}
	
	public boolean nextLine() {
		linha = lerLinha();
		
		if(linha == null) return false;
		if(separador != null) {
			registro = Splitter.on(separador).splitToList(linha);
		}
		return true;
	}
	
	private String lerLinha() {
		if(registros == null || registros.size() == 0)return null;
		if(index >= registros.size())return null;
		
		String linha = registros.get(index);
		index++;
		return linha;
	}

	/**
	 * Retorna uma String através do número do campo. Somente para arquivos com Separador exemplo "|"
	 * @param campo int número do campo
	 * @return
	 */
	public String getCampo(int campo) {
		if(separador == null) throw new RuntimeException("Não é possivel obter campos por número quando o TXT não tem separador.");
		return registro.get(campo);
	}
	
	/**
	 * Retorna uma SubString da linha que esta posicionada no arquivo 
	 * @param indexInicial
	 * @param indexFinal
	 * @return
	 */
	public String getSubString(int indexInicial, int indexFinal) {
		return linha.substring(indexInicial, indexFinal);
	}
	
	public String getSubString(int index) {
		return linha.substring(index);
	}
	public String getLinha() {
		return linha;
	}
	public List<String> getRegistro() {
		return registro;
	}

	public List<String> getRegistros() {
		return registros;
	}
	
	public int getQtdLinhas() {
		return registros.size();
	}
	
}
