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
import main.buff.DamageProcessor;

public class Watcher extends Enemy{

	private int gettedDamagerCounter;
	
	public Watcher() {
		gettedDamagerCounter = 0;
		super("Watcher", 90 + Main.random.nextInt(31));
		Main.executor.schedule(() -> {
			addBuff(new BuffSteadfast(1), 1);
		}, 1, TimeUnit.SECONDS);
	}

	@Override
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
				movementCounter = 0;
		}
	}

	private void bluntHit() {
		DamageProcessor.applyDamageToPlayer(6, Player.getInstance());
		Player.getInstance().addBuff(new BuffWeakened(2), 2);
	}

	private void stabHit() {
		DamageProcessor.applyDamageToPlayer(6, Player.getInstance());
		Player.getInstance().addBuff(new BuffVulnerable(2), 2);
	}

	private void slashHit() {
		DamageProcessor.applyDamageToPlayer(12 + Main.random.nextInt(4), Player.getInstance()); // 12-15
		Player.getInstance().addBuff(new BuffLost(2), 2);
	}

	private void ruminateI() {
		this.addHp(4 + Main.random.nextInt(3)); // 4-6
	}

	private void ruminateII() {
		this.addHp(6 + Main.random.nextInt(3)); // 6-8
	}

	public void addGettedDamageCounter(int damage){
		gettedDamagerCounter += damage;

		if (gettedDamagerCounter >= 15){
			gettedDamagerCounter = 0;
			addBuff(new BuffInvincible(1), 1);
		}
	}
}
