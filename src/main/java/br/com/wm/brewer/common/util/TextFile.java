package br.com.wm.brewer.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.base.Splitter;

public class TextFile {
	public static enum TipoArquivo {LEITURA, ESCRITA};
	private TipoArquivo tipo;

	//Variáveis para escrita de arquivo
	private File arquivo;
	private PrintWriter write; 
	private boolean autoFlush;
	private StringBuilder builderText;
	private String separador;
	private boolean isNewLine = true;
	private int qtdLinhas = 0;
	
	//Variáveis para leitura de arquivos
	private BufferedReader reader;
	private String linha;
	private List<String> registro; //Veriável que armazena a linha dividida conforme o separador
	
	
	public TextFile(String path, TipoArquivo tipoArquivo) throws IOException {
		this(path, true, tipoArquivo);
	}
	
	public TextFile(File arquivo, TipoArquivo tipoArquivo) throws IOException {
		this(arquivo, true, tipoArquivo);
	}
	
	public TextFile(String path, boolean autoFlush, TipoArquivo tipoArquivo) throws IOException {
		this(new File(path), autoFlush, tipoArquivo);
	}
	
	public TextFile(File arquivo, boolean autoFlush, TipoArquivo tipoArquivo)throws IOException {
		tipo = tipoArquivo;
		if(tipoArquivo == TipoArquivo.ESCRITA) {
			arquivo.delete();
			arquivo.getParentFile().mkdirs();
			arquivo.createNewFile();
			this.arquivo = arquivo;
			write = new PrintWriter(new FileOutputStream(arquivo), true);
		}else {
			if(!arquivo.exists()) throw new IOException("Arquivo " + arquivo.getCanonicalPath() + " de leitura não encontrado");
			reader = new BufferedReader(new FileReader(arquivo));
		}
		
		this.autoFlush = autoFlush;
		this.builderText = new StringBuilder();
	}
	
	public TextFile(String path, boolean autoFlush, String separador, TipoArquivo tipoArquivo) throws IOException {
		this(path, autoFlush, tipoArquivo);
		this.separador = separador;
	}
	
	public TextFile(File arquivo, boolean autoFlush, String separador, TipoArquivo tipoArquivo) throws IOException {
		this(arquivo, autoFlush, tipoArquivo);
		this.separador = separador;
	}
	
	/**
	 * 
	 * @param charset String - ver valores permitidos na classe java.nio.charset.Charset
	 */
	public void setCharSet(String charset) throws IOException {
		write.close();
		write = null;
		write = new PrintWriter(new OutputStreamWriter(new FileOutputStream(arquivo), Charset.forName(charset)), true);
	}
	
	public void newLine() {
		if(autoFlush) {
			write.println();
		}else {
			qtdLinhas++;
//			builderText.append("\n");
			builderText.append(Character.toChars(13));
			builderText.append(Character.toChars(10));
		}
		isNewLine = true;
	}
	
	public String getLastLine() {
		if(autoFlush) {
			throw new RuntimeException("Não é possivel obter a última linha de um TextFile com o parâmetro autoFlush igual a true");
		}else {
			int index = builderText.lastIndexOf("\n");
			return builderText.substring(index < 0 ? 0 : index+1, builderText.length());
		}
	}
	
	public void replaceLastLine(String texto) {
		if(autoFlush) {
			throw new RuntimeException("Não é possivel substituir a última linha de um TextFile com o parâmetro autoFlush igual a true");
		}else {
			int index = builderText.lastIndexOf("\n");
			builderText.replace(index < 0 ? 0 : index+1, builderText.length(), texto);
		}
	}
	
	public void print(Object texto) {
		print(texto, true);
	}
	
	public void print(Object texto, boolean removePulaLinha) {
		if(texto==null) texto = "";
		if(removePulaLinha) texto = texto.toString().replace("\n", "");
		escrever(texto.toString());
	}
	
	public void print(Object texto, int tamanho) {
		print(texto, tamanho, true);
	}
	
	public void print(Object texto, int tamanho, boolean removePulaLinha) {
		if(texto==null) texto = "";
		if(texto instanceof String) {
			if(removePulaLinha) texto = texto.toString().replace("\n", "");	
		}
		texto = StringUtils.ajustString(texto, tamanho);
		escrever(texto.toString());
	}
	
	public void print(Object texto, int tamanho, char caracter, boolean concatenarAEsquerda) {
		print(texto, tamanho, caracter, concatenarAEsquerda, true);
	}
	
	public void print(Object texto, int tamanho, char caracter, boolean concatenarAEsquerda, boolean removePulaLinha) {
		if(texto==null) texto = "";
		texto = StringUtils.ajustString(texto, tamanho, caracter, concatenarAEsquerda);
		if(removePulaLinha) texto = texto.toString().replace("\n", "");
		escrever(texto.toString());
	}
	
	public void print(Object texto, int tamanho, boolean retirarAcento, boolean caixaAlta) {
		print(texto, tamanho, retirarAcento, caixaAlta, true);
	}
	
	public void print(Object texto, int tamanho, boolean retirarAcento, boolean caixaAlta, boolean removePulaLinha) {
		if(texto == null) texto = "";
		texto = StringUtils.ajustString(texto, tamanho);
		if(retirarAcento) texto = StringUtils.unaccented(texto.toString());
		if(caixaAlta) texto = texto.toString().toUpperCase();
		if(removePulaLinha) texto = texto.toString().replace("\n", "");
		escrever(texto.toString());
	}
	
	public void printNull(int qtdSeparadores) {
		if(separador == null) throw new RuntimeException("Arquivo texto não possui separador.");
		for(int i = 0; i < qtdSeparadores; i++) {
			escrever("");
		}
	}
	
	public void flush() {
		if(!autoFlush) {
			write.print(builderText.toString());
			builderText = new StringBuilder();
		}
		write.flush();
	}
	public String getTexto() {
		return builderText.toString();
	}
	
	public void close() {
		if(tipo == TipoArquivo.LEITURA) {
			try {
				reader.close();
			}catch(Exception err) {
				//Não precisa tratar
			}
		}else{
			write.close();
		}
	}
	
	private void escrever(String texto) {
		if(!isNewLine && separador != null) {
			texto = separador + texto;
		}
		isNewLine = false;
		if(autoFlush) {
			write.print(texto);
		}else {
			builderText.append(texto);
		}
	}

	/*-----------------
	 * Métodos para leitura do txt
	 ------------------*/
	public boolean nextLine() throws IOException {
		linha = lerLinha();
		
		if(linha == null) return false;
		if(separador != null) {
			registro = Splitter.on(separador).splitToList(linha);
		}
		return true;
	}
	
	private String lerLinha()throws IOException {
		String linha = reader.readLine();
		if(linha != null && linha.length() == 0) return lerLinha();
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
	
	public File getArquivo() {
		return arquivo;
	}
	public BufferedReader getReader() {
		return reader;
	}
}