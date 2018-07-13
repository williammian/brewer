package br.com.wm.brewer.common.util;

public class Validations {
	/**
	 * Este método valida o C.P.F 
	 * @param num - número do cpf
	 * @return retorna true se cpf for válido, e false caso contrário
	 */
    public boolean validaCPF(String cpf) {
    	cpf = StringUtils.extractNumbers(cpf);
        if (cpf.length() != 11)
            return false;
        
        String numDig = cpf.substring(0, 9);
        return calcDigVerif(numDig).equals(cpf.substring(9, 11));
    }

	private String calcDigVerif(String num) {
		Integer primDig, segDig;
	    int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++)
                soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

        if (soma % 11 == 0 | soma % 11 == 1)
            primDig = new Integer(0);
        else
            primDig = new Integer(11 - (soma % 11));

        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++)
                soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        
        soma += primDig.intValue() * 2;
        if (soma % 11 == 0 | soma % 11 == 1)
            segDig = new Integer(0);
        else
            segDig = new Integer(11 - (soma % 11));

        return primDig.toString() + segDig.toString();
    }
    
    /**
     * Este método valida o C.N.P.J
     * @param cnpj - Número do cnpj a ser verificado
     * @return retorna true se cnpj for válido e false caso contrário
     */
    public boolean validaCNPJ(String cnpj ){
       cnpj = StringUtils.extractNumbers(cnpj);
       
       if ( cnpj.length() != 14 )
           return false;
       
       if(cnpj.equals("11111111111111"))return true; //CNPJ genérico, não valida
       
       int soma = 0, dig;
       String cnpj_calc = cnpj.substring(0,12);

       char[] chr_cnpj = cnpj.toCharArray();

       /* Primeira parte */
       for( int i = 0; i < 4; i++ )
         if ( chr_cnpj[i]-48 >=0 && chr_cnpj[i]-48 <=9 )
           soma += (chr_cnpj[i] - 48 ) * (6 - (i + 1)) ;
       for( int i = 0; i < 8; i++ )
         if ( chr_cnpj[i+4]-48 >=0 && chr_cnpj[i+4]-48 <=9 )
           soma += (chr_cnpj[i+4] - 48 ) * (10 - (i + 1)) ;
       dig = 11 - (soma % 11);

       cnpj_calc += ( dig == 10 || dig == 11 ) ?
                      "0" : Integer.toString(dig);

       /* Segunda parte */
       soma = 0;
       for ( int i = 0; i < 5; i++ )
         if ( chr_cnpj[i]-48 >=0 && chr_cnpj[i]-48 <=9 )
           soma += (chr_cnpj[i] - 48 ) * (7 - (i + 1)) ;
       for ( int i = 0; i < 8; i++ )
         if ( chr_cnpj[i+5]-48 >=0 && chr_cnpj[i+5]-48 <=9 )
           soma += (chr_cnpj[i+5] - 48 ) * (10 - (i + 1)) ;
       dig = 11 - (soma % 11);
       cnpj_calc += ( dig == 10 || dig == 11 ) ?
                      "0" : Integer.toString(dig);

       return cnpj.equals(cnpj_calc);
    }
}
