package main.enemy.normalEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.debuff.BuffWeakened;
import main.buff.positiveBuff.BuffBloodLeeching;
import main.buff.positiveBuff.BuffStrengthened;

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
				addBuff(new BuffBloodLeeching(1), 1);
				System.out.println(buffList);
				break;
				
			case 1:
				addBuff(new BuffStrengthened(1), 1);
				System.out.println(buffList);
				break;
				
			case 2:
				Player.getInstance().addBuff(new BuffWeakened(1), 1);
				break;
		}
		
	}
	
	private void strongScratch() {
		Player.getInstance().deductHp(7 + Main.random.nextInt(3));
	}
}
