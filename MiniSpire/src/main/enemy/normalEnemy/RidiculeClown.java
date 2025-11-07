package main.enemy.normalEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.DamageProcessor;
import main.buff.positiveBuff.BuffRecovering;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffInvincible;
import main.buff.positiveBuff.BuffBloodLeeching;

public class RidiculeClown extends Enemy {
	
	private int lastSurpriseBoxDamage = -1;
	
	public RidiculeClown() {
		super("Ridicule Clown", 45 + Main.random.nextInt(11));
	}
	
	public void onMove() {
		
		switch (movementCounter) {
		
			case 0:
				System.out.println(" >> " + this.getName() + " uses Surprise Box!");
				surpriseBox();
				movementCounter++;
				break;
				
			case 1:
				System.out.println(" >> " + this.getName() + " uses Smash Hit!");
				smashHit();
				movementCounter++;
				break;
				
			case 2:
				System.out.println(" >> " + this.getName() + " uses Surprise Box!");
				surpriseBox();
				movementCounter++;
				break;
				
			case 3:
				System.out.println(" >> " + this.getName() + " uses Halftime!");
				halftime();
				movementCounter++;
				break;
				
			case 4:
				System.out.println(" >> " + this.getName() + " uses Surprise Box!");
				surpriseBox();
				movementCounter++;
				break;
				
			case 5:
				System.out.println(" >> " + this.getName() + " uses Perfect Ending!");
				perfectEnding();
				movementCounter = 0;
				break;
				
			default:
				System.out.println(" >> " + this.getName() + " is confused.");
				movementCounter = 0;
				break;
		}
	}
	
	private void surpriseBox() {
		int damage = 1 + Main.random.nextInt(10);
		if (lastSurpriseBoxDamage == damage) {
			damage *= 2;
			System.out.println(" >> Surprise Box: Same damage as last time! Double damage!");
		}
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		lastSurpriseBoxDamage = damage;
	}
	
	private void smashHit() {
		// Random positive buff
		switch (Main.random.nextInt(4)) {
			case 0:
				addBuff(new BuffStrengthened(1), 1);
				break;
			case 1:
				addBuff(new BuffInvincible(1), 1);
				break;
			case 2:
				addBuff(new BuffBloodLeeching(1), 1);
				break;
			case 3:
				addBuff(new BuffRecovering(1), 1);
				break;
		}
	}
	
	private void halftime() {
		int duration = 1 + Main.random.nextInt(10);
		addBuff(new BuffRecovering(1), duration);
	}
	
	private void perfectEnding() {
		int roll = 1 + Main.random.nextInt(20);
		System.out.println(" >> Perfect Ending: Rolled " + roll + "!");
		
		if (roll == 1) {
			// Critical failure
			System.out.println(" >> Critical Failure! Massive self-damage!");
			deductHp(500);
		} else if (roll >= 2 && roll <= 10) {
			// Damage both
			int damage = 1 + Main.random.nextInt(15);
			DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
			deductHp(damage);
		} else if (roll >= 11 && roll <= 19) {
			// Heal both
			int heal = 1 + Main.random.nextInt(15);
			addHp(heal);
			Player.getInstance().addHp(heal);
		} else if (roll == 20) {
			// Critical success
			System.out.println(" >> Critical Success! Massive damage!");
			int damage = 10 + Main.random.nextInt(41);
			DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		}
	}
}

