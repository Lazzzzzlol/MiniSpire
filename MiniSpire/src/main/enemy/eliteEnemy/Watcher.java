package main.enemy.eliteEnemy;

import main.Main;
import main.enemy.Enemy;
import main.player.Player;
import main.buff.DamageProcessor;
import main.buff.debuff.BuffWeakened;
import main.buff.debuff.BuffVulnerable;
import main.buff.debuff.BuffLost;
import main.buff.positiveBuff.BuffSteadfast;

public class Watcher extends Enemy{
	
	public Watcher() {
		super("Watcher", 90 + Main.random.nextInt(31));
		// Initial Steadfast
		addBuff(new BuffSteadfast(1), 1);
	}
	
	public void onMove() {
		
		switch (movementCounter) {
		
			case 0:
				System.out.println(" >> " + this.getName() + " uses Blunt Hit!");
				bluntHit();
				movementCounter++;
				break;
				
			case 1:
				System.out.println(" >> " + this.getName() + " uses Stab Hit!");
				stabHit();
				movementCounter++;
				break;
				
			case 2:
				System.out.println(" >> " + this.getName() + " uses Slash Hit!");
				slashHit();
				movementCounter++;
				break;
				
			case 3:
				System.out.println(" >> " + this.getName() + " uses Ruminate I!");
				ruminateI();
				movementCounter++;
				break;
				
			case 4:
				System.out.println(" >> " + this.getName() + " uses Ruminate II!");
				ruminateII();
				movementCounter = 0;
				break;
				
			default:
				System.out.println(" >> " + this.getName() + " is confused.");
				movementCounter = 0;
				break;
		}
	}
	
	private void bluntHit() {
		DamageProcessor.applyDamageToPlayer(6, Player.getInstance());
		Player.getInstance().addBuff(new BuffWeakened(1), 2);
	}
	
	private void stabHit() {
		DamageProcessor.applyDamageToPlayer(6, Player.getInstance());
		Player.getInstance().addBuff(new BuffVulnerable(1), 2);
	}
	
	private void slashHit() {
		int damage = 12 + Main.random.nextInt(4);
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		Player.getInstance().addBuff(new BuffLost(1), 2);
	}
	
	private void ruminateI() {
		int heal = 4 + Main.random.nextInt(3);
		addHp(heal);
	}
	
	private void ruminateII() {
		int heal = 6 + Main.random.nextInt(3);
		addHp(heal);
	}
}
