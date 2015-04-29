package cem.keven.pelletier.cashapp.tests;

import cem.keven.pelletier.cashapp.money.*;
import cem.keven.pelletier.cashapp.pelletier.*;
import cem.keven.pelletier.cashapp.pelletier.KevenReg;


public class TestDeguet extends TestAbstract {

	@Override
	public CashRegister fullCashReg() {
		return new KevenReg();
	}

	@Override
	public MoneyMachine getMonnayeur() {
		return new KevenMachine();
	}

	@Override
	public String getStudentName() {
		return "Keven Pelletier";
	}
	
}
