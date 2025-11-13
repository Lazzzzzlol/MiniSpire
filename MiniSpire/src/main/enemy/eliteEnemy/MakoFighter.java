package main.enemy.eliteEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.debuff.BuffWeakened;
import main.buff.debuff.BuffVulnerable;
import main.buff.positiveBuff.BuffInvincible;
import main.buff.positiveBuff.BuffRecovering;
import main.processor.DamageProcessor;

public class MakoFighter extends Enemy {

	public MakoFighter() {
		super("Mako Fighter", 120 + Main.random.nextInt(21),"elite");
	}

	@Override
	public boolean onMove() {

		if (!super.onMove())
			return false;

		switch (movementCounter) {
			case 0:
				System.out.println("\n >> " + this.getName() + " uses Mako SharkO!");
				makoSharko();
				movementCounter++;
				break;
			case 1:
				System.out.println("\n >> " + this.getName() + " uses Killer Wail 5.1!");
				killerWail();
				movementCounter++;
				break;
			case 2:
				System.out.println("\n >> " + this.getName() + " uses Triple Tornado!");
				tripleTornado();
				movementCounter++;
				break;
			case 3:
				System.out.println("\n >> " + this.getName() + " uses Dive!");
				dive();
				movementCounter = 0;
				break;
			default:
				movementCounter = 0;
		}
		return true;
	}

	private void makoSharko() {
		DamageProcessor.applyDamageToPlayer(15, this, Player.getInstance());
	}

	private void killerWail() {
		Player.getInstance().addBuff(new BuffWeakened(2), 2);
		Player.getInstance().addBuff(new BuffVulnerable(2), 2);
	}

	private void tripleTornado() {
		DamageProcessor.applyDamageToPlayer(4, this, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(5, this, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(6, this, Player.getInstance());
	}

	private void dive() {
		addBuff(new BuffInvincible(2), 2);
		addBuff(new BuffRecovering(4), 4);
	}
}


