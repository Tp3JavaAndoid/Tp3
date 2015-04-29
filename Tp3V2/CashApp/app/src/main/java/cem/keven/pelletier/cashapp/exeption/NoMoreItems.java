package cem.keven.pelletier.cashapp.exeption;

import cem.keven.pelletier.cashapp.money.Money;

public class NoMoreItems extends RuntimeException {

	public NoMoreItems(Money m){
		
		//System.out.println("Il n'y � plus d'items pour " + m.prettyPrint);
	}
	
}
