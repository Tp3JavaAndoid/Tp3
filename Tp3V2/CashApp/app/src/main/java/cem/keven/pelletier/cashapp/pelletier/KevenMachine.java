package cem.keven.pelletier.cashapp.pelletier;

import cem.keven.pelletier.cashapp.money.CashRegister;
import cem.keven.pelletier.cashapp.money.Change;
import cem.keven.pelletier.cashapp.money.Money;
import cem.keven.pelletier.cashapp.money.MoneyMachine;
import cem.keven.pelletier.cashapp.exeption.CashException;


public class KevenMachine implements MoneyMachine {

	@Override
	public Change computeChange(double amount, CashRegister register)
			throws CashException {
		if(register.totalValue() < amount || amount < 0)
		{
			throw new CashException();
		}
		else{
	Change c = new	KevenChange();
	double changeARendre = Math.round(amount / 0.05) * 0.05;	
		
		
		for (Money m : Money.values()) {
			if(m != Money.coin1s && m != Money.coin20s){
			while(changeARendre >= m.value()){
				if(changeARendre < m.value())
				{
					break;
				}
				else if(register.numberOfItemsFor(m) != 0 ){
					register.useItems(m, 1);
					double valeur =(changeARendre - m.value());
					valeur = Math.round(valeur * 100);
					changeARendre = valeur / 100;
					c.addItems(m, 1);
			  }
				else{
					break;
				}
			}
		  }
		}
		
		if(changeARendre > 0)
		{
			throw new CashException();
		}
			return c;
		}
			
	}



}
