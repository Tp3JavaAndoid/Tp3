package cem.keven.pelletier.cashapp.pelletier;

import java.util.EnumMap;
import cem.keven.pelletier.cashapp.money.CashRegister;
import cem.keven.pelletier.cashapp.money.Money;
import cem.keven.pelletier.cashapp.exeption.TooManyItems;
import cem.keven.pelletier.cashapp.exeption.NoMoreItems;

public class KevenReg implements CashRegister {

	public EnumMap<Money, Integer> caisse = new EnumMap<Money, Integer>(Money.class);
	public EnumMap<Money, Integer> capacite = new EnumMap<Money, Integer>(Money.class);
	
	
	public KevenReg() {
		
	
		for (Money m : Money.values()) {	
			if(m.pretty().endsWith("coin"))
			{
				capacite.put(m, 40);
			}
			else if(m.pretty().endsWith("bill"))
			{
				capacite.put(m,25);
			}
			caisse.put(m, this.maxCapacityFor(m));
		}
		
		
		
	}
	
	
	@Override
	public int numberOfItemsFor(Money m) {
		if(caisse.containsKey(m))
		{
		return caisse.get(m);
		}
		return 0; 
	}

	@Override
	public void addItems(Money m, int number) throws TooManyItems {
		if((this.numberOfItemsFor(m) + number) >= this.maxCapacityFor(m)){
			throw new TooManyItems(m);
		}
		else{
			caisse.put(m, this.numberOfItemsFor(m) + number);
		}
		
	}

	@Override
	public double totalValue() {
		double total=0;
		for (Money m : Money.values() ) {
			total += (numberOfItemsFor(m) * m.value());
		}
		return total;
	}

	@Override
	public int totalNumberOfItems() {
		int total=0;
		for (int v : caisse.values()) {
			total += v;
		}
		return total;
	}

	@Override
	public int maxCapacityFor(Money m) {
		return capacite.get(m);
	}

	@Override
	public void useItems(Money m, int number) throws NoMoreItems{	
		
		if(this.numberOfItemsFor(m) > number){
		int NouveauMontant = caisse.get(m);		
		caisse.put(m, NouveauMontant - number);
		}
		else{
			throw new NoMoreItems(m);
		}
		
	}


}
