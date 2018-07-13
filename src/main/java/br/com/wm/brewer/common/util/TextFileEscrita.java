package br.com.wm.brewer.common.util;

import java.io.File;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class TextFileEscrita {
	
	//Variáveis para escrita das linhas do arquivo
	private StringBuilder builderText;
	
	private String separador;
	
	private boolean isNewLine = true;
	
	private int qtdLinhas = 0;
	
	public TextFileEscrita() {
		this.builderText = new StringBuilder();
	}
	
	public TextFileEscrita(String separador) {
		this.separador = separador;
		this.builderText = new StringBuilder();
	}

	public String getStringBytesBase64Zip() {
		try {
			byte[] dados = builderText.toString().getBytes("UTF-8");
			byte[] zipped = ZIPUtils.compress(dados);
			
			return Base64.getEncoder().encodeToString(zipped);
		}catch (Exception err) {
			throw new RuntimeException(err);
		}
	}
	
	public static void gerarArquivo(String dadosZip, String path) throws Exception {		
		byte[] unzipped = Base64.getDecoder().decode(dadosZip);
		byte[] dados = ZIPUtils.decompress(unzipped);
		
		String strDados = new String(dados, "UTF-8");
		
		File file = new File(path);
		FileUtils.write(file, strDados, "UTF-8");
	}
	
	public void newLine() {
		qtdLinhas++;
//		builderText.append("\n");
		builderText.append(Character.toChars(13));
		builderText.append(Character.toChars(10));
		
		isNewLine = true;
	}
	
	public String getLastLine() {
		int index = builderText.lastIndexOf("\n");
		return builderText.substring(index < 0 ? 0 : index+1, builderText.length());
	}
	
	public void replaceLastLine(String texto) {
		int index = builderText.lastIndexOf("\n");
		builderText.replace(index < 0 ? 0 : index+1, builderText.length(), texto);
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
	
	public String getTexto() {
		return builderText.toString();
	}
	
	private void escrever(String texto) {
		if(!isNewLine && separador != null) {
			texto = separador + texto;
		}
		isNewLine = false;
		
		builderText.append(texto);
	}

	public StringBuilder getBuilderText() {
		return builderText;
	}

	public int getQtdLinhas() {
		return qtdLinhas;
	}

}