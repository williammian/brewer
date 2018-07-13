package br.com.wm.brewer.common.util;


import java.math.MathContext;
import java.math.RoundingMode;

public class Scale {
	public static final RoundingMode ROUND = RoundingMode.HALF_EVEN;
	public static final RoundingMode TRUNC = RoundingMode.FLOOR;
	
	public static final MathContext ROUND_0 = new MathContext(0, RoundingMode.HALF_EVEN);
	public static final MathContext ROUND_1 = new MathContext(1, RoundingMode.HALF_EVEN);
	public static final MathContext ROUND_2 = new MathContext(2, RoundingMode.HALF_EVEN);
	public static final MathContext ROUND_3 = new MathContext(3, RoundingMode.HALF_EVEN);
	public static final MathContext ROUND_4 = new MathContext(4, RoundingMode.HALF_EVEN);
	public static final MathContext ROUND_5 = new MathContext(5, RoundingMode.HALF_EVEN);
	public static final MathContext ROUND_6 = new MathContext(6, RoundingMode.HALF_EVEN);
	public static final MathContext ROUND_34 = MathContext.DECIMAL128;

	public static final MathContext TRUNC_0 = new MathContext(0, RoundingMode.FLOOR);
	public static final MathContext TRUNC_1 = new MathContext(1, RoundingMode.FLOOR);
	public static final MathContext TRUNC_2 = new MathContext(2, RoundingMode.FLOOR);
	public static final MathContext TRUNC_3 = new MathContext(3, RoundingMode.FLOOR);
	public static final MathContext TRUNC_4 = new MathContext(4, RoundingMode.FLOOR);
	public static final MathContext TRUNC_5 = new MathContext(5, RoundingMode.FLOOR);
	public static final MathContext TRUNC_6 = new MathContext(6, RoundingMode.FLOOR);
}
