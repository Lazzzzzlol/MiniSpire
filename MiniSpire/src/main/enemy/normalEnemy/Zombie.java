package main.enemy.normalEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.debuff.BuffWeakened;
import main.buff.positiveBuff.BuffBloodLeeching;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.oneFightBuff.BuffResurrection;
import main.buff.DamageProcessor;

public class Zombie extends Enemy {
	
	public Zombie() {
		super("Zombie", 25 + Main.random.nextInt(11));
		// Initial Resurrection
		addBuff(new BuffResurrection(1), 1);
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
		DamageProcessor.applyDamageToPlayer(5 + Main.random.nextInt(3), Player.getInstance());
	}

	private void corruption() {
		
		switch(Main.random.nextInt(3)) {
			case 0:
				addBuff(new BuffBloodLeeching(1), 2);
				break;
				
			case 1:
				addBuff(new BuffStrengthened(1), 1);
				break;
				
			case 2:
				Player.getInstance().addBuff(new BuffWeakened(1), 2);
				break;
		}
		
	}
	
	private void strongScratch() {
		DamageProcessor.applyDamageToPlayer(7 + Main.random.nextInt(3), Player.getInstance());
	}
}
