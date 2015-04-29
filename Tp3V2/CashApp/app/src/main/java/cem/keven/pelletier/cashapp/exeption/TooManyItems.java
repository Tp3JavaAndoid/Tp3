package cem.keven.pelletier.cashapp.exeption;

import cem.keven.pelletier.cashapp.money.Money;

public class TooManyItems extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Money devise ;
	
	public TooManyItems(Money m){
		 
		devise = m;
		//System.out.println("L'item " + devise.prettyPrint + " � atteint la capacit� maximal");
	}
	
	
	
	
	
}
