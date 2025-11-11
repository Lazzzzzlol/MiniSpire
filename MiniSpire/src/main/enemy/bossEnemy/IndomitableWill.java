package main.enemy.bossEnemy;

import main.enemy.Enemy;
import main.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import main.Main;
import main.buff.Buff;
import main.buff.oneFightBuff.BuffResurrection;
import main.buff.oneFightBuff.BuffIndomitable;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffTough;
import main.buff.positiveBuff.BuffSteelsoul;
import main.buff.debuff.BuffWeakened;
import main.buff.DamageProcessor;
import main.buff.debuff.BuffMuted;
import main.buff.debuff.BuffVulnerable;

public class IndomitableWill extends Enemy { 

	private boolean phase2 = false;
	private int deliriumDamageBonus = 0;
	private boolean lastDeliriumHit = false;
	private boolean desperateRoarDone = false;
	private boolean scarletDeliriumDone =false;
	private boolean impalementDone = false;
	private List<String> scriptures = new ArrayList<>();

	public IndomitableWill() {
		super("Indomitable Will", 250);
		Main.executor.schedule(() -> {
			addBuff(new BuffResurrection(1), 1);
		}, 1, TimeUnit.SECONDS);

		scriptures.add("All that rebelleth against thee shall surely be destroyed. (1:1)");
		scriptures.add("Thou shalt surely die in battle, that thy faith may be proven. (1:2)");
		scriptures.add("Behold, I stand here to persevere; none shall pass through. (1:3)");
		scriptures.add("May thy heart be shattered, and thou returnest not again. (1:4)");

		scriptures.add("I will pray twice for the losts, that their path may be guided.. (2:1)");
		scriptures.add("May thou prevail wherever thou goest, as a wolf rendeth all things. (2:2)");
		scriptures.add("The way of redemption dwelleth therein. (2:3)");
	}

	public void onMove() {

		boolean hasResurrection = buffList.stream()
                .anyMatch(buff -> "Resurrection".equals(buff.getName()));

		// Check phase transition
		if (!phase2 && !hasResurrection) {
			phase2 = true;
			System.out.println(" >> " + this.getName() + " enters Phase 2!");
			addBuff(new BuffIndomitable(1), 1);
			return;
		}

		if (!phase2) {
			phase1Move();
		} else {
			phase2Move();
		}
	}

	private void phase1Move() {
		switch (movementCounter) {
			case 0:
				System.out.println(" >> " + this.getName() + " uses Unleash!");
				unleash();
				movementCounter++;
				break;

			case 1:
				System.out.println(" >> " + this.getName() + " uses Torcleaver!");
				torcleaver();
				movementCounter++;
				break;

			case 2:
				System.out.println(" >> " + this.getName() + " uses Delirium!");
				delirium();
				movementCounter++;
				break;

			case 3:
				System.out.println(" >> " + this.getName() + " uses Reject!");
				reject();
				movementCounter++;
				break;

			case 4:
				System.out.println(" >> " + this.getName() + " uses Stalwart Soul!");
				stalwartSoul();
				movementCounter++;
				break;

			case 5:
				System.out.println(" >> " + this.getName() + " uses Depressed!");
				depressed();
				movementCounter++;
				break;

			case 6:
				System.out.println(" >> " + this.getName() + " uses Dark Missionary!");
				darkMissionary();
				movementCounter = 0;
				break;

			default:
				movementCounter = 0;
				break;
		}
	}

	private void phase2Move() {
		// Phase 2 has conditional moves
		if (!desperateRoarDone) {
			System.out.println(" >> " + this.getName() + " uses Desperate Roar!");
			desperateRoar();
			desperateRoarDone = true;
			return;
		}

		if (desperateRoarDone && !scarletDeliriumDone) {
			System.out.println(" >> " + this.getName() + " uses Scarlet Delirium!");
			scarletDelirium();
			scarletDeliriumDone = true;
			return;
		}

		if (scarletDeliriumDone && !impalementDone) {
			System.out.println(" >> " + this.getName() + " uses Impalement!");
			impalement();
			impalementDone = true;
			return;
		}

		if (impalementDone) {
			System.out.println(" >> " + this.getName() + " uses Disesteem!");
			disesteem();
			// Reset chain
			desperateRoarDone = false;
			scarletDeliriumDone = false;
			impalementDone = false;
			return;
		}

		// Fallback
		System.out.println(" >> " + this.getName() + " uses Depressed!");
		depressed();
	}

	private void unleash() {
		if (Main.random.nextBoolean()) {
			addBuff(new BuffTough(3), 3);
		} else {
			addBuff(new BuffStrengthened(3), 3);
		}
	}

	private void torcleaver() {
		int actualDamage = DamageProcessor.calculateDamageToPlayer(20, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(20, Player.getInstance());

		if (actualDamage > 0) {
			Player.getInstance().addBuff(new BuffWeakened(2), 2);
		}
	}

	private void delirium() {
		int damage = 10 + deliriumDamageBonus;
		int actualDamage = DamageProcessor.calculateDamageToPlayer(damage, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());

		lastDeliriumHit = (actualDamage > 0);

		// Check if player has Weakened
		boolean hasWeakened = false;
		for (main.buff.Buff buff : Player.getInstance().getBuffList()) {
			if (buff.getName().equals("Weakened") || buff.getName().equals("Weaken")) {
				hasWeakened = true;
				break;
			}
		}

		if (hasWeakened) {
			deliriumDamageBonus += 5;
		}
	}

	private void reject() {
		if (!lastDeliriumHit) {
			// Delirium didn't hit, use it again with +10 bonus
			deliriumDamageBonus += 10;
			System.out.println(" >> Reject: Delirium failed! Using again with +10 bonus!");
			delirium();
			deliriumDamageBonus -= 10; // Reset bonus after use
		} else {
			// Delirium hit, apply Muted
			Player.getInstance().addBuff(new BuffMuted(2), 2);
		}
	}

	private void stalwartSoul() {
		addBuff(new BuffSteelsoul(2), 2);
	}

	private void depressed() {
		int heal = 1 + Main.random.nextInt(2);
		addHp(heal);
	}

	private void darkMissionary() {
		String msg = scriptures.get(Main.random.nextInt(scriptures.size()));
		System.out.println(" >> Indomitable" + msg);
	}

	private void desperateRoar() {
		int hpLoss = 15 + Main.random.nextInt(6);
		deductHp(hpLoss);
		addBuff(new BuffSteelsoul(2), 2);
	}

	private void scarletDelirium() {
		if (!desperateRoarDone) {
			// Use Dark Mind instead
			System.out.println(" >> " + this.getName() + " uses Dark Mind!");
			addBuff(new BuffTough(5), 5);
			return;
		}

		int damage = 16 + Main.random.nextInt(2);
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		Player.getInstance().addBuff(new BuffVulnerable(1), 2);
	}

	private void impalement() {
		if (!scarletDeliriumDone) {
			System.out.println(" >> :... ..?");
			return;
		}

		int damage = 9 + Main.random.nextInt(3);
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		Player.getInstance().addBuff(new BuffVulnerable(1), 1);
	}

	private void disesteem() {
		if (!impalementDone) {
			// Use Depressed instead
			depressed();
			return;
		}

		// Deal 23 damage, ignoring Tough and Reflective
		// We need to bypass these buffs
		int damage = 23;

		// Temporarily remove Tough and Reflective from player
		ArrayList<Buff> tempBuffs = new ArrayList<>();
		ArrayList<Buff> playerBuffs = Player.getInstance().getBuffList();

		for (main.buff.Buff buff : playerBuffs) {
			if (buff.getName().equals("Tough") || buff.getName().equals("Reflective")) {
				tempBuffs.add(buff);
			}
		}

		playerBuffs.removeAll(tempBuffs);

		// Deal damage
		Player.getInstance().deductHp(damage);

		// Restore buffs
		playerBuffs.addAll(tempBuffs);
	}
}


