package cem.keven.pelletier.cashapp.tests;

import cem.keven.pelletier.cashapp.money.*;
import cem.keven.pelletier.cashapp.pelletier.*;
import junit.framework.TestCase;
import  cem.keven.pelletier.cashapp.exeption.*;

public abstract class TestAbstract extends TestCase {


	
	public void test999(){
		try{
			String nom = getStudentName();
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
			Change result = m.computeChange((float) 999.00, cash);
			Change expected = new KevenChange();
			expected.addItems(Money.bill100, 9);
			expected.addItems(Money.bill50, 1);
			expected.addItems(Money.bill20, 2);
			expected.addItems(Money.bill5, 1);
			expected.addItems(Money.coin2, 2);
			assertEquals("Ouch for  " + nom, result.numberOfItemsFor(Money.bill100), 9);
			assertEquals("Ouch for  " + nom, result.numberOfItemsFor(Money.bill50), 1);
			assertEquals("Ouch for  " + nom, result.numberOfItemsFor(Money.bill20), 2);
			assertEquals("Ouch for  " + nom, result.numberOfItemsFor(Money.bill5), 1);
			assertEquals("Ouch for  " + nom, result.numberOfItemsFor(Money.coin2), 2);

		}catch(CashException e){
			fail();
		}
		
	}
	
	public void testMonnayeur() {
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
		
			Change result = m.computeChange(35.45, cash);
			
			assertEquals(1, result.numberOfItemsFor(Money.bill20));
			assertEquals(1, result.numberOfItemsFor(Money.bill10));
			assertEquals(1, result.numberOfItemsFor(Money.bill5));
			assertEquals(1, result.numberOfItemsFor(Money.coin25s));
			assertEquals(2, result.numberOfItemsFor(Money.coin10s));
			
			assertEquals(35.45,result.totalValue());
			assertEquals(6, result.totalNumberOfItems());
		}
		catch(CashException e)
		{
			fail();
		}
			
	}
	
	public void testMonnayeurTropDeci() {
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
		
			Change result = m.computeChange(35.455, cash);
			
			assertEquals(1, result.numberOfItemsFor(Money.bill20));
			assertEquals(1, result.numberOfItemsFor(Money.bill10));
			assertEquals(1, result.numberOfItemsFor(Money.bill5));
			assertEquals(1, result.numberOfItemsFor(Money.coin25s));
			assertEquals(2, result.numberOfItemsFor(Money.coin10s));
			
		}
		catch(CashException e)
		{
			fail();
		}
			
	}
	
	public void testMonnayeurNegatif() {
		boolean passed = false;
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
		
			Change result = m.computeChange(-54.45, cash);	
			
		}
		catch(CashException e)
		{
			passed = true; 
		}
		assertTrue(passed);	
	}
	
	
	public void testMonnayeurTropElever() {
		boolean passed = false;
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
		
			Change result = m.computeChange(100000.45, cash);	
			
		}
		catch(CashException e)
		{
			passed = true; 
		}
		assertTrue(passed);	
	}
	
	public void testMonnayeurSansGrosMontant() {
		boolean passed = false;
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
			cash.useItems(Money.bill100, 25);
			cash.useItems(Money.bill50, 25);
			Change result = m.computeChange(200.45, cash);	
			
			assertEquals(10, result.numberOfItemsFor(Money.bill20));
			assertEquals(1, result.numberOfItemsFor(Money.coin25s));
			assertEquals(2, result.numberOfItemsFor(Money.coin10s));	
		}
		catch(CashException e)
		{
			
		}
		catch(TooManyItems e)
		{
			
		}
		catch(NoMoreItems e)
		{
			
		}
		
	}
	
	public void testMonnayeurSansGrosMontant600() {
		
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
			cash.useItems(Money.bill100, 25);
			cash.useItems(Money.bill50, 25);
			Change result = m.computeChange(600, cash);	
			
			assertEquals(25, result.numberOfItemsFor(Money.bill20));
			assertEquals(10, result.numberOfItemsFor(Money.bill10));	
		}
		catch(CashException e)
		{
			
		}
		catch(TooManyItems e)
		{
			
		}
		catch(NoMoreItems e)
		{
			
		}
		
	}
	
	public void testMonnayeurSansGrosMontant1255() {
		
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
			cash.useItems(Money.bill10, 25);
			cash.useItems(Money.bill50, 25);
			Change result = m.computeChange(12.55, cash);	
			
			assertEquals(2, result.numberOfItemsFor(Money.bill5));
			assertEquals(1, result.numberOfItemsFor(Money.coin2));
			assertEquals(2, result.numberOfItemsFor(Money.coin25s));
			assertEquals(1, result.numberOfItemsFor(Money.coin5s));
		}
		catch(CashException e)
		{
			
		}
		catch(TooManyItems e)
		{
			
		}
		catch(NoMoreItems e)
		{
			
		}
			
	}
	
	
	public void testDecimals(){
	 		
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
			
			Change result1 = m.computeChange(0.09, cash);
			Change result2 = m.computeChange(0.08, cash);
			Change result3 = m.computeChange(0.07, cash);
			Change result4 = m.computeChange(0.06, cash);
			Change result5 = m.computeChange(0.05, cash);
			Change result6 = m.computeChange(0.04, cash);
			Change result7 = m.computeChange(0.03, cash);
			Change result8 = m.computeChange(0.02, cash);
			Change result9 = m.computeChange(0.01, cash);
			
			assertEquals(1, result1.numberOfItemsFor(Money.coin10s));
			assertEquals(1, result2.numberOfItemsFor(Money.coin10s));
			assertEquals(1, result3.numberOfItemsFor(Money.coin5s));
			assertEquals(1, result4.numberOfItemsFor(Money.coin5s));
			assertEquals(1, result5.numberOfItemsFor(Money.coin5s));
			assertEquals(1, result6.numberOfItemsFor(Money.coin5s));
			assertEquals(1, result7.numberOfItemsFor(Money.coin5s));
			assertEquals(0, result8.numberOfItemsFor(Money.coin5s));
			assertEquals(0, result9.numberOfItemsFor(Money.coin5s));
		}
		catch(CashException e)
		{
			fail();
		}
	}
		
	
	public void testCapacity() {
		CashRegister cash = new KevenReg();					
		assertEquals(25, cash.numberOfItemsFor(Money.bill10));
		assertEquals(25, cash.numberOfItemsFor(Money.bill100));
		assertEquals(25, cash.numberOfItemsFor(Money.bill20));
		assertEquals(25, cash.numberOfItemsFor(Money.bill5));
		assertEquals(25, cash.numberOfItemsFor(Money.bill50));
		assertEquals(40, cash.numberOfItemsFor(Money.coin1));
		assertEquals(40, cash.numberOfItemsFor(Money.coin10s));
		assertEquals(40, cash.numberOfItemsFor(Money.coin2));
		assertEquals(40, cash.numberOfItemsFor(Money.coin25s));
		assertEquals(40, cash.numberOfItemsFor(Money.coin5s));
	}
	
	public void testInsertOutOfRange() {
		boolean passed = false;
		try{
		CashRegister cash = new KevenReg();
		cash.addItems(Money.bill10, 300);						
		}
		catch(TooManyItems e){
			passed = true;
		}	
		
		assertTrue(passed);
	}
	
	public void testInsert(){
		try{
			CashRegister cash = new KevenReg();	
			cash.useItems(Money.bill100, 10);
			cash.addItems(Money.bill100, 5);
			assertEquals(20, cash.numberOfItemsFor(Money.bill100));
		}
		catch(TooManyItems e)
		{
			fail();
		}
		
	}
	
	public void testRemoveItems(){
		try{
		CashRegister cash = new KevenReg();
		
		cash.useItems(Money.bill10, 10);
		assertEquals(15, cash.numberOfItemsFor(Money.bill10));
		}
		catch(TooManyItems e){
			fail();
		}
		
		
	}
	
	public void testRemoveMore(){
		boolean passed = false;
			try{		
	    CashRegister cash = new KevenReg();	
		cash.useItems(Money.bill10, 60);
			}
			catch(NoMoreItems e){
				passed = true;
			}
			assertTrue(passed);
	}
	
	
	public void testMoneyeurAvantApresItems()
	{
		try{
		MoneyMachine m = getMonnayeur();
		CashRegister cash = new KevenReg();
	
		assertEquals(405, cash.totalNumberOfItems());
		
		Change result = m.computeChange(35.45, cash);
		
		assertEquals(399, cash.totalNumberOfItems());
		
		}
		catch(CashException e)
		{
			
		}
	}
	
	public void testMoneyeurAvantApresMontant()
	{
		try{
			MoneyMachine m = getMonnayeur();
			CashRegister cash = new KevenReg();
			
			assertEquals(4769.4, cash.totalValue());
			
			Change result = m.computeChange(335.45, cash);
			
			assertEquals(4433.95, cash.totalValue());
			
			}
			catch(CashException e)
			{
				
			}
	}

	
	public abstract CashRegister fullCashReg();
	public abstract MoneyMachine getMonnayeur();
	public abstract String getStudentName() ;
}
