package main.enemy.normalEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.debuff.BuffWeaken;
import main.buff.positiveBuff.BuffBloodbath;
import main.buff.positiveBuff.BuffStrength;

public class Zombie extends Enemy {
	
	public Zombie() {
		super("Zombie", 30 + Main.random.nextInt(11));
	}
	
	public void onMove() {
		
		switch (movementCounter) {
		
			case 0:
				System.out.println(" >> " + this.getName() + " uses Scratch!");
				scratch();
				movementCounter++;
				break;
				
			case 1:
				System.out.println(" >> " + this.getName() + " uses Corruption!");
				corruption();
				movementCounter++;
				break;
				
			case 2:
				System.out.println(" >> " + this.getName() + " uses Strong Scratch!");
				strongScratch();
				movementCounter = 0;
				break;
				
			default:
				System.out.println(" >> " + this.getName() + " is confused.");
				movementCounter = 0;
				break;
		}
	}
	
	private void scratch() {
		Player.getInstance().deductHp(5 + Main.random.nextInt(3));
	}

	private void corruption() {
		
		switch(Main.random.nextInt(3)) {
			case 0:
				addBuff(new BuffBloodbath(1), 1);
				System.out.println(buffList);
				break;
				
			case 1:
				addBuff(new BuffStrength(1), 1);
				System.out.println(buffList);
				break;
				
			case 2:
				Player.getInstance().addBuff(new BuffWeaken(1), 1);
				break;
		}
		
	}
	
	private void strongScratch() {
		Player.getInstance().deductHp(7 + Main.random.nextInt(3));
	}
}
