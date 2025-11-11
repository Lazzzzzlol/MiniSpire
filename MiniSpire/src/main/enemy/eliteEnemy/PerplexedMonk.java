package main.enemy.eliteEnemy;

import java.util.HashSet;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.positiveBuff.BuffTough;
import main.processor.DamageProcessor;

public class PerplexedMonk extends Enemy {

	private HashSet<String> chakras = new HashSet<>(); // Opo, Raptor, Coeurl
	private boolean haveOpoStance = false; // 魔猿身形

	public PerplexedMonk() {
		super("Perplexed Monk", 130 + Main.random.nextInt(21));
	}

	@Override
	public void onMove() {
		switch (movementCounter) {
			case 0:
			case 1:
			case 2:
				System.out.println("\n >> " + this.getName() + " uses Form Shift!");
				formShiftAttack();
				movementCounter++;
				break;
			case 3:
				System.out.println(" >> " + this.getName() + " prepares Masterful Blitz!");
				movementCounter++;
				break;
			case 4:
				System.out.println("\n >> " + this.getName() + " uses Masterful Blitz!");
				masterfulBlitz();
				movementCounter = 0;
				chakras.clear();
				haveOpoStance = false;
				break;
			default:
				movementCounter = 0;
		}
	}

	private void formShiftAttack() {
		int roll = Main.random.nextInt(100); // 0-99
		if (roll < 30) { // 30%
			leapingOpo();
			return;
		}
		if (roll < 50) { // next 20%
			risingRaptor();
			return;
		}
		pouncingCoeurl(); // remaining 50%
	}

	private void leapingOpo() {
		System.out.println(" >> Leaping Opo!");
		int dmg = 9 + Main.random.nextInt(3); // 9-11
		if (haveOpoStance) dmg += 5;
		DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
		chakras.add("Opo");
	}

	private void risingRaptor() {
		System.out.println(" >> Rising Raptor!");
		int dmg = 12 + Main.random.nextInt(3); // 12-14
		DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
		chakras.add("Raptor");
		// if damage dealt successfully, gain Endure 2 (忍耐)
		addBuff(new BuffTough(2), 2);
	}

	private void pouncingCoeurl() {
		System.out.println(" >> Poucing Coeurl!");
		int dmg = 11 + Main.random.nextInt(3); // 11-13
		DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
		chakras.add("Coeurl");
		// if damage dealt successfully, gain 魔猿身形 标记
		haveOpoStance = true;
	}

	private void masterfulBlitz() {
		int types = chakras.size();
		if (types <= 0) return;
		if (types == 1) {
			System.out.println(" >> Tornado Kick!");
		DamageProcessor.applyDamageToPlayer(20, this, Player.getInstance());
			return;
		}
		if (types == 2) {
			System.out.println(" >> Phantom Rush!");
		DamageProcessor.applyDamageToPlayer(30, this, Player.getInstance());
			return;
		}
		System.out.println(" >> Fires Reply!");
		DamageProcessor.applyDamageToPlayer(50, this, Player.getInstance());
	}
}


