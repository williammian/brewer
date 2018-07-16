package br.com.wm.brewer.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class Utils {

	public static boolean in(Object value, Object ... in) {
		if(value == null || in == null)throw new NullPointerException("Impossível executar comparação IN com objetos nulos");

		for(Object val : in) {
			if(val == null)continue;
			if(val.equals(value))return true;
		}

		return false;
	}

	public static boolean inIgnoreCase(String value, String ... in) {
		if(value == null || in == null)throw new NullPointerException("Impossível executar comparação IN com objetos nulos");

		for(String val : in) {
			if(val.equalsIgnoreCase(value))return true;
		}

		return false;
	}

	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}
	
	public static boolean jsBoolean(Object value) {
		if(value == null)return false;
		if(value instanceof String)return inIgnoreCase((String)value, "", "false", "f");
		if(value instanceof Number)return ((Number)value).intValue() != 0;
		
		return Boolean.valueOf(value.toString());
	}

	public static String createMD5(String chave) {
		return createMD5(chave, 1);
	}

	public static String createMD5(String chave, int fases) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			for(int i = 0; i < fases; i++) {
				byte[] strSnh = chave.getBytes();
				md.update(strSnh);
				chave = new BigInteger(1, md.digest()).toString(16);
			}

			return chave;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criptografar senha", e);
		}
	}

	/**
	 * Cria um MAP baseado nos valores passados, os valores devem ser informados em pares, o primeiro será a Key e o segundo será o value
	 * @param keyAndValues
	 * @return
	 */
	public static Map<String, String> mapString(Object ... keyAndValues){
		if(keyAndValues == null || keyAndValues.length == 0)return null;
		if(keyAndValues.length % 2 != 0)throw new RuntimeException("Deve ser informado um número par de parâmetros para gerar o MAP");

		Map<String, String> retorno = new TreeMap<String, String>();
		for(int i = 0; i < keyAndValues.length; i+=2) {
			Object key = keyAndValues[i];
			Object value = keyAndValues[i+1];
			retorno.put(StringUtils.formatByClass(key), StringUtils.formatByClass(value));
		}

		return retorno;
	}

	/**
	 * Cria um map onde as chaves serão obtidas a partir das Keys que é uma String separada por vírgula
	 * @param keys
	 * @param values
	 * @return
	 */
	public static Map<String, Object> mapByPattern(String keys, Object ... values){
		if(Strings.isNullOrEmpty(keys))return null;
		List<String> keysList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(keys);
		if(keysList.size() != values.length)throw new RuntimeException("Deve ser informado um número de parâmetros igual a quanitade de chaves para gerar o MAP");

		Map<String, Object> retorno = new TreeMap<>();
		for(int i = 0; i < keysList.size(); i++) {
			String key = keysList.get(i);
			Object value = values[i];
			retorno.put(key, value);
		}

		return retorno;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Map<K, V> map(Object ... keyAndValues){
		if(keyAndValues == null || keyAndValues.length == 0)return null;
		if(keyAndValues.length % 2 != 0)throw new RuntimeException("Deve ser informado um número par de parâmetros para gerar o MAP");

		Map retorno = new TreeMap<>();
		for(int i = 0; i < keyAndValues.length; i+=2) {
			Object key = keyAndValues[i];
			Object value = keyAndValues[i+1];
			retorno.put(key, value);
		}

		return retorno;
	}
	
	public static <T> List<T> list(T ... ts){
		List<T> result = new ArrayList<>();
		result.addAll(Arrays.asList(ts));
		
		return result;
	}

	public static boolean isAllNull(Object ... values) {
		for (Object object : values) {
			if(object != null)return false;
		}
		return true;
	}

	public static boolean isAllNotNull(Object ... values) {
		for (Object object : values) {
			if(object == null)return false;
		}
		return true;
	}

	public static boolean orAllisNullOrNotNull(Object ... values) {
		if(isAllNull(values))return true;
		if(isAllNotNull(values))return true;
		return false;
	}

	@SafeVarargs
	public static <T> T firstNotNull(T ... in) {
		if(in == null)return null;

		for(T val : in) {
			if(val != null)return val;
		}

		return null;
	}

	public static <T> List<T> joinLists(List<T> listA, List<T> listB){
		List<T> retorno = new ArrayList<>();

		if(listA != null && listA.size() > 0)retorno.addAll(listA);
		if(listB != null && listB.size() > 0)retorno.addAll(listB);

		if(retorno.size() == 0)return null;
		return retorno;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int compare(Object v1, Object v2) {
		if(v1 == null && v2 == null)return 0;
		if(v1 == null)return -1;
		if(v2 == null)return 1;

		return ((Comparable)v1).compareTo((Comparable)v2);
	}
}
