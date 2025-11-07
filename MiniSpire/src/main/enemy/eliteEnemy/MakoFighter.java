package main.enemy.eliteEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.DamageProcessor;
import main.buff.debuff.BuffWeakened;
import main.buff.debuff.BuffVulnerable;
import main.buff.positiveBuff.BuffInvincible;
import main.buff.positiveBuff.BuffRecovering;

public class MakoFighter extends Enemy {
	
	public MakoFighter() {
		super("Mako Fighter", 120 + Main.random.nextInt(21));
	}
	
	public void onMove() {
		
		switch (movementCounter) {
		
			case 0:
				System.out.println(" >> " + this.getName() + " uses Mako SharkO!");
				makoSharkO();
				movementCounter++;
				break;
				
			case 1:
				System.out.println(" >> " + this.getName() + " uses Killer Wail 5.1!");
				killerWail();
				movementCounter++;
				break;
				
			case 2:
				System.out.println(" >> " + this.getName() + " uses Triple Tornado!");
				tripleTornado();
				movementCounter++;
				break;
				
			case 3:
				System.out.println(" >> " + this.getName() + " uses Dive!");
				dive();
				movementCounter = 0;
				break;
				
			default:
				System.out.println(" >> " + this.getName() + " is confused.");
				movementCounter = 0;
				break;
		}
	}
	
	private void makoSharkO() {
		DamageProcessor.applyDamageToPlayer(15, Player.getInstance());
	}
	
	private void killerWail() {
		Player.getInstance().addBuff(new BuffWeakened(1), 2);
		Player.getInstance().addBuff(new BuffVulnerable(1), 2);
	}
	
	private void tripleTornado() {
		DamageProcessor.applyDamageToPlayer(4, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(5, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(6, Player.getInstance());
	}
	
	private void dive() {
		addBuff(new BuffInvincible(1), 2);
		addBuff(new BuffRecovering(1), 4);
	}
}

