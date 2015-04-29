package cem.keven.pelletier.cashapp.money;

import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import cem.keven.pelletier.cashapp.exeption.*;
import cem.keven.pelletier.cashapp.pelletier.*;



public class ScannerApp 
{

	public static void main( String[] args )
	{
		Locale.setDefault(Locale.CANADA);
		Random rand = new Random();
		Scanner s = new Scanner(System.in);
		
		
		/**
		 * Changez ici pour tester vos propres classes
		 */
		CashRegister r = new KevenReg(); // changez la caisse par votre caisse
		MoneyMachine m = new KevenMachine();  // changez le monnayeur par le vôtre
		
		while(true){
			double amount = 0.0;
			System.out.println("Entrez un montant : (CTRL+C pour arreter, x POUR UN MONTANT AU HASARD)");
			if(!s.hasNextDouble()){
				s.next();
				amount = rand.nextInt(10000)*1.0 / 100;
			} 
			else{
				amount = s.nextDouble();
			}
			System.out.println("################################## Calcul en cours pour "+amount);
			try{
				Change c = m.computeChange(amount, r);
				System.out.println("Change total is " + c.totalValue());
				System.out.println(StringUtils.toString(c));

				System.out.println(StringUtils.toString(r));
			}catch(CashException e){
				e.printStackTrace();
			}
			System.out.println("##################################");
		}
	}
}
