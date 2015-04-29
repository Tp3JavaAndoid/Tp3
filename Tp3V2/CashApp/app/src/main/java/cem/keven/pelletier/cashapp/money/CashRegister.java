package cem.keven.pelletier.cashapp.money;


public interface CashRegister extends Change{

	public int maxCapacityFor(Money m );
	
	public void useItems(Money m , int number)  ;
	
}
