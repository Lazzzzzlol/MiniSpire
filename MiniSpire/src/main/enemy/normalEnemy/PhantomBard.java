package main.enemy.normalEnemy;

import main.enemy.Enemy;
import main.player.Player;

import java.util.concurrent.TimeUnit;

import main.Main;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.debuff.BuffLost;
import main.buff.oneFightBuff.BuffMisty;
import main.buff.positiveBuff.BuffRecovering;
import main.buff.DamageProcessor;

public class PhantomBard extends Enemy {

	private int cycleDamage = 0;
	private boolean grantRegenNextBattleVoice = false;

	public PhantomBard() {
		super("Phantom Bard", 25 + Main.random.nextInt(11));
		Main.executor.schedule(() -> {
			addBuff(new BuffMisty(1), 1);
		}, 1, TimeUnit.SECONDS);
	}

	@Override
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
				movementCounter = 0;
				cycleDamage = 0;
				break;
			default:
				movementCounter = 0;
		}
	}

	private void battleVoice() {
		addBuff(new BuffStrengthened(3), 3);
		if (grantRegenNextBattleVoice) {
			addBuff(new BuffRecovering(3), 3);
			grantRegenNextBattleVoice = false;
		}
	}

	private void ladonsbite() {
		int dmg = 7 + Main.random.nextInt(3); // 7-9
		DamageProcessor.applyDamageToPlayer(dmg, Player.getInstance());
		cycleDamage += dmg;
	}

	private void heartbreakShot() {
		int dmg = 4;
		DamageProcessor.applyDamageToPlayer(dmg, Player.getInstance());
		Player.getInstance().addBuff(new BuffLost(1), 1);
		cycleDamage += dmg;
	}

	private void shadowbite() {
		int dmg = 6 + Main.random.nextInt(3); // 6-8
		DamageProcessor.applyDamageToPlayer(dmg, Player.getInstance());
		Player.getInstance().addBuff(new main.buff.debuff.BuffWeakened(2), 2);
		cycleDamage += dmg;
	}

	private void radiantFinale() {
		addBuff(new BuffRecovering(3), 3);
		if (cycleDamage > 22) {
			grantRegenNextBattleVoice = true;
		}
	}
}


