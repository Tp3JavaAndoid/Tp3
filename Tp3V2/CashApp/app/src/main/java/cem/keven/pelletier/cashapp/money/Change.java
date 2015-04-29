package cem.keven.pelletier.cashapp.money;



public interface Change {

	public int numberOfItemsFor(Money m);

	public  void addItems(Money m, int number) ;
	
	public double totalValue();
	
	public int totalNumberOfItems();

}
