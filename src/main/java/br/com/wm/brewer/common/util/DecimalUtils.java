package br.com.wm.brewer.common.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DecimalUtils {
	private BigDecimal value;
	private MathContext scale = Scale.ROUND_34;
	
	private DecimalUtils(Object value) {
		this.value = convert(value);
	}
	
	public static DecimalUtils create(Object value) {
		return new DecimalUtils(value);
	}
	
	public DecimalUtils sum(Object ... values) {
		if(values == null || values.length == 0)return this;
		for(Object val : values) {
			BigDecimal valDec = convert(val) ;
			value = value.add(valDec);
		}
		return this;
	}
	
	public DecimalUtils subtract(Object ... values) {
		if(values == null || values.length == 0)return this;
		for(Object val : values) {
			BigDecimal valDec = convert(val);
			value = value.subtract(valDec);
		}
		
		return this;
	}

	public DecimalUtils multiply(Object ... values) {
		if(values == null || values.length == 0)return this;
		for(Object val : values) {
			BigDecimal valDec = convert(val);
			value = value.multiply(valDec);
		}
		
		return this;
	}
	
	public DecimalUtils divide(Object ... values) {
		if(values == null || values.length == 0)return this;
		for(Object val : values) {
			BigDecimal valDec = convert(val);
			value = value.divide(valDec, scale.getPrecision(), scale.getRoundingMode());
		}
		
		return this;
	}

	public DecimalUtils round(int casasDecimais) {
		value = value.setScale(casasDecimais, RoundingMode.HALF_EVEN);
		return this;
	}
	
	public DecimalUtils trunc(int casasDecimais) {
		value = value.setScale(casasDecimais, RoundingMode.FLOOR);
		return this;
	}
	
	public DecimalUtils negateIfPositive() {
		if(value.compareTo(new BigDecimal(0)) < 0)return this;
		this.value = this.value.negate();
		
		return this;
	}

	public DecimalUtils setScale(MathContext scale) {
		this.scale = scale;
		return this;
	}

	private BigDecimal convert(Object val) {
		if(val instanceof Double)throw new InvalidParameterException("Double is not valid type to Decimal");
		return (val instanceof BigDecimal ? (BigDecimal)val : val instanceof Number ? new BigDecimal(((Number)val).toString()) : new BigDecimal(val.toString()));
	}
	
	
	
	//------------------------------------------------
	//--- Metodos finais para obtenção dos valores --- 
	//------------------------------------------------
	public BigDecimal get() {
		return value;
	}
	public List<BigDecimal> part(int numeroDeParcelas){
		return part(numeroDeParcelas, numeroDeParcelas);
	}
	public List<BigDecimal> part(int numeroDeParcelas, int parcToAddRest){
		if(parcToAddRest < 1 || parcToAddRest > numeroDeParcelas)throw new RuntimeException("Parâmetros inválidos para o parcelamento");
		
		List<BigDecimal> retorno = new ArrayList<>(numeroDeParcelas);
		BigDecimal parcela = value.divide(new BigDecimal(numeroDeParcelas), Scale.ROUND_34.getPrecision(), Scale.ROUND_34.getRoundingMode());
		BigDecimal acumuladoDasParcela = new BigDecimal(0);
		
		for(int i = 0; i < numeroDeParcelas; i++) {
			acumuladoDasParcela = acumuladoDasParcela.add(parcela);
			retorno.add(parcela.setScale(scale.getPrecision(), scale.getRoundingMode()));
			
			if(i == numeroDeParcelas-1) {
				BigDecimal dif = acumuladoDasParcela.subtract(value);
				if(dif.compareTo(new BigDecimal(0)) > 0) {
					retorno.set(parcToAddRest-1, parcela.subtract(dif).setScale(scale.getPrecision(), scale.getRoundingMode()));
				}else if(dif.compareTo(new BigDecimal(0)) < 0) {
					retorno.set(parcToAddRest-1, parcela.add(dif.abs()).setScale(scale.getPrecision(), scale.getRoundingMode()));
				}
			}
		}
		
		return retorno;
	}
	
	public int compareTo(Object value) {
		BigDecimal val = convert(value);
		
		return this.value.compareTo(val);
	}
	
	//-------------------
	//------AUX----------
	//-------------------
	public static DecimalUtils parse(String value) {
		try {
			DecimalFormat format = new DecimalFormat("#,###.##");
			format.setGroupingUsed(true);
			format.setParseBigDecimal(true);
			return new DecimalUtils(format.parse(value));
		} catch (Exception e) {
			throw new RuntimeException("Falha ao converter " + value + " para Decimal", e);
		}
	}
	public String format() {
		return format(2, 2);
	}
	public String format(int maxFractionDigits, int minFractionDigits) {
		NumberFormat format = DecimalFormat.getInstance(new Locale("pt", "BR"));
		format.setGroupingUsed(true);
		format.setMinimumFractionDigits(minFractionDigits);
		format.setMaximumFractionDigits(maxFractionDigits);
		
		return format.format(value);
	}
	
	@Override
	public String toString() {
		return format();
	}
}
