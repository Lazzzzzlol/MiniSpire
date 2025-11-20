package main.enemy.eliteEnemy;

import java.util.concurrent.TimeUnit;

import main.Main;
import main.enemy.Enemy;
import main.player.Player;
import main.buff.debuff.BuffWeakened;
import main.buff.debuff.BuffVulnerable;
import main.buff.debuff.BuffLost;
import main.buff.oneFightBuff.BuffSteadfast;
import main.buff.positiveBuff.BuffInvincible;
import main.processor.DamageProcessor;
import main.processor.HealProcessor;

public class Watcher extends Enemy{

	private int gettedDamagerCounter;
	
	public Watcher() {
		super("Watcher", 90 + Main.random.nextInt(31),"elite");
		gettedDamagerCounter = 0;
		Main.executor.schedule(() -> {
			addBuff(new BuffSteadfast(1), 1);
		}, 1, TimeUnit.SECONDS);
	}

	@Override
	public boolean onMove() {
		
		if (!super.onMove())
			return false;

		gettedDamagerCounter = 0;
		
		switch (movementCounter) {
			case 0:
				System.out.println("\n >> " + this.getColoredName() + " uses Blunt Hit!");
				bluntHit();
				movementCounter++;
				break;
			case 1:
				System.out.println("\n >> " + this.getColoredName() + " uses Stab Hit!");
				stabHit();
				movementCounter++;
				break;
			case 2:
				System.out.println("\n >> " + this.getColoredName() + " uses Slash Hit!");
				slashHit();
				movementCounter++;
				break;
			case 3:
				System.out.println("\n >> " + this.getColoredName() + " uses Ruminate I!");
				ruminateI();
				movementCounter++;
				break;
			case 4:
				System.out.println("\n >> " + this.getColoredName() + " uses Ruminate II!");
				ruminateII();
				movementCounter = 0;
				break;
			default:
				movementCounter = 0;
		}
		return true;
	}

	private void bluntHit() {
		DamageProcessor.applyDamageToPlayer(6, this, Player.getInstance());
		Player.getInstance().addBuff(new BuffWeakened(2), 2);
	}

	private void stabHit() {
		DamageProcessor.applyDamageToPlayer(6, this, Player.getInstance());
		Player.getInstance().addBuff(new BuffVulnerable(2), 2);
	}

	private void slashHit() {
		DamageProcessor.applyDamageToPlayer(12 + Main.random.nextInt(4), this, Player.getInstance()); // 12-15
		Player.getInstance().addBuff(new BuffLost(2), 2);
	}

	private void ruminateI() {
		HealProcessor.applyHeal(this, 4 + Main.random.nextInt(3), null); // 4-6
	}

	private void ruminateII() {
		HealProcessor.applyHeal(this, 6 + Main.random.nextInt(3), null); // 6-8
	}

	public void addGettedDamageCounter(int damage){
		gettedDamagerCounter += damage;

		if (gettedDamagerCounter >= 15)
			if (!isDied)
				addBuff(new BuffInvincible(1), 1);
	}
}
