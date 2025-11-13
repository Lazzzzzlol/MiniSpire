package main.enemy.normalEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.positiveBuff.BuffRecovering;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffInvincible;
import main.buff.positiveBuff.BuffReflective;
import main.buff.positiveBuff.BuffTough;
import main.processor.DamageProcessor;
import main.processor.HealProcessor;

public class RidiculeClown extends Enemy {

	private Integer lastSurpriseDamage = null;
	private int dmg = 0;

	public RidiculeClown() {
		super("Ridicule Clown", 45 + Main.random.nextInt(11));
	}

	public void onMove() {

		super.onMove();

		dmg = 1 + Main.random.nextInt(10); // 1-10
		switch (movementCounter) {
			case 0:
				System.out.println("\n >> " + this.getName() + " uses Surprise Box!");
				surpriseBox();
				movementCounter++;
				break;
			case 1:
				System.out.println("\n >> " + this.getName() + " uses Smash Hit!");
				smashHit();
				movementCounter++;
				break;
			case 2:
				System.out.println("\n >> " + this.getName() + " uses Surprise Box!");
				surpriseBox();
				movementCounter++;
				break;
			case 3:
				System.out.println("\n >> " + this.getName() + " uses Halftime!");
				halftime();
				movementCounter++;
				break;
			case 4:
				System.out.println("\n >> " + this.getName() + " uses Surprise Box!");
				surpriseBox();
				movementCounter++;
				break;
			case 5:
				System.out.println("\n >> " + this.getName() + " uses Perfect Ending!");
				perfectEnding();
				movementCounter = 0;
				break;
			default:
				movementCounter = 0;
		}
	}

	private void surpriseBox() {
		
		if (lastSurpriseDamage != null && lastSurpriseDamage == dmg) {
			dmg *= 2;
		}
		DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
		lastSurpriseDamage = dmg ;
	}

	private void smashHit() {
		// random positive buff for 3 turn
		switch (Main.random.nextInt(4)) {
			case 0:
				addBuff(new BuffStrengthened(3), 3);
				break;
			case 1:
				addBuff(new BuffInvincible(3), 3);
				break;
			case 2:
				addBuff(new BuffReflective(3),  3);
				break;
			default:
				addBuff(new BuffTough(3), 3);
		}
	}

	private void halftime() {
		int dur = 1 + Main.random.nextInt(10);
		addBuff(new BuffRecovering(dur), dur);
	}

	private void perfectEnding() {
		int roll = 1 + Main.random.nextInt(20);
		if (roll == 1) {
			DamageProcessor.applyDamageToEnemy(500, null, this);
			return;
		}
		if (roll >= 2 && roll <= 10) {
			int dmg = 1 + Main.random.nextInt(15);
			DamageProcessor.applyDamageToEnemy(dmg, null, this);
			DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
			return;
		}
		if (roll >= 11 && roll <= 19) {
			int heal = 1 + Main.random.nextInt(15);
			HealProcessor.applyHeal(this, heal, null);
			HealProcessor.applyHeal(Player.getInstance(), heal, null);
			return;
		}
		// 20
		int dmg = 10 + Main.random.nextInt(41); // 10-50
		DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
	}
}


