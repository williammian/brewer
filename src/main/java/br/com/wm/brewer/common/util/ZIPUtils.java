package br.com.wm.brewer.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class ZIPUtils {
	public static byte[] compress(byte[] content) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try{
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
			gzipOutputStream.write(content);
			gzipOutputStream.close();
		} catch(IOException e){
			throw new RuntimeException(e);
		}
		return byteArrayOutputStream.toByteArray();
	}

	public static byte[] decompress(byte[] conteudo) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try{
			IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(conteudo)), out);
		} catch(IOException e){
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}

	public static void zipFiles(File[] arquivos, String arquivoDestino, int level) {
	    try {
	    	ZipOutputStream arqZip = new ZipOutputStream(new FileOutputStream(arquivoDestino));
	        arqZip.setLevel(level);
	        
	        for (File arquivo : arquivos) {
	            FileInputStream arqAtual = new FileInputStream(arquivo);
	            
	            arqZip.putNextEntry(new ZipEntry(arquivo.getName()));
	    
	            byte[] buf = new byte[arqAtual.available()];
	            int len;
	            while (true) {
	            	len = arqAtual.read(buf);
	            	if(len <= 0)break;
	                arqZip.write(buf, 0, len);
	            }
	            
	            arqZip.closeEntry();
	            arqAtual.close();
	        }
	        
	        arqZip.finish();
	        arqZip.close();
	    } catch (Exception err) {
	    	throw new RuntimeException("Erro ao compactar arquivo para o destino informado: " + arquivoDestino, err);
	    }
	}
}
