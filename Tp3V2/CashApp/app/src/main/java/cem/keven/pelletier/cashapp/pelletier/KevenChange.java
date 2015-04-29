package cem.keven.pelletier.cashapp.pelletier;

import java.util.EnumMap;
import cem.keven.pelletier.cashapp.money.Change;
import cem.keven.pelletier.cashapp.money.Money;

public class KevenChange implements  Change {

	public EnumMap<Money, Integer> change = new EnumMap<Money, Integer>(Money.class);
	
	@Override
	public int numberOfItemsFor(Money m) {	
	
		if(!change.containsKey(m))
		{
			return 0;
		}	
		return change.get(m);
	}

	@Override
	public void addItems(Money m, int number) {
		change.put(m, this.numberOfItemsFor(m)+ number);
		
	}


    @Override
	public double totalValue() {
		double total = 0;
		
		for(Money m: Money.values()){
			if(change.containsKey(m)){
				total += (change.get(m) * m.value());
			}
		}		
		return total;
	}

	@Override
	public int totalNumberOfItems() {
		int total = 0;
		
		for(Money m: Money.values()){
			if(change.containsKey(m)){
				total += change.get(m);
			}
		}		
		return total;
	}

}
