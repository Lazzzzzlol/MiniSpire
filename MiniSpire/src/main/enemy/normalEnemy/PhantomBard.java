package main.enemy.normalEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.DamageProcessor;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.debuff.BuffLost;
import main.buff.debuff.BuffWeakened;
import main.buff.positiveBuff.BuffMisty;
import main.buff.positiveBuff.BuffRecovering;

public class PhantomBard extends Enemy {
	
	private int totalDamageThisCycle = 0;
	private boolean battleVoiceBonus = false;
	
	public PhantomBard() {
		super("Phantom Bard", 25 + Main.random.nextInt(11));
		// Initial Misty
		addBuff(new BuffMisty(1), 1);
	}
	
	public void onMove() {
		
		switch (movementCounter) {
		
			case 0:
				System.out.println(" >> " + this.getName() + " uses Battle Voice!");
				battleVoice();
				movementCounter++;
				break;
				
			case 1:
				System.out.println(" >> " + this.getName() + " uses Ladonsbite!");
				ladonsbite();
				movementCounter++;
				break;
				
			case 2:
				System.out.println(" >> " + this.getName() + " uses Heartbreak Shot!");
				heartbreakShot();
				movementCounter++;
				break;
				
			case 3:
				System.out.println(" >> " + this.getName() + " uses Shadowbite!");
				shadowbite();
				movementCounter++;
				break;
				
			case 4:
				System.out.println(" >> " + this.getName() + " uses Radiant Finale!");
				radiantFinale();
				totalDamageThisCycle = 0; // Reset for next cycle
				movementCounter = 0;
				break;
				
			default:
				System.out.println(" >> " + this.getName() + " is confused.");
				movementCounter = 0;
				break;
		}
	}
	
	private void battleVoice() {
		if (battleVoiceBonus) {
			addBuff(new BuffStrengthened(1), 3);
			addBuff(new BuffRecovering(1), 3);
			battleVoiceBonus = false;
		} else {
			addBuff(new BuffStrengthened(1), 3);
		}
	}
	
	private void ladonsbite() {
		int damage = 7 + Main.random.nextInt(3);
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		totalDamageThisCycle += damage;
	}
	
	private void heartbreakShot() {
		DamageProcessor.applyDamageToPlayer(4, Player.getInstance());
		Player.getInstance().addBuff(new BuffLost(1), 1);
		totalDamageThisCycle += 4;
	}
	
	private void shadowbite() {
		int damage = 6 + Main.random.nextInt(3);
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		Player.getInstance().addBuff(new BuffWeakened(1), 2);
		totalDamageThisCycle += damage;
	}
	
	private void radiantFinale() {
		if (totalDamageThisCycle > 25) {
			battleVoiceBonus = true;
			System.out.println(" >> Radiant Finale: Next Battle Voice will grant Recovering!");
		}
	}
}

