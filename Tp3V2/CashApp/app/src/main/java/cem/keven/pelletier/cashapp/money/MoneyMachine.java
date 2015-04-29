package cem.keven.pelletier.cashapp.money;

import cem.keven.pelletier.cashapp.exeption.CashException;

public interface MoneyMachine {

	public Change computeChange(double amount, CashRegister register) throws CashException;
	
}
