package main.enemy.bossEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.DamageProcessor;
import main.buff.debuff.BuffWeakened;
import main.buff.debuff.BuffVulnerable;
import main.buff.debuff.BuffMuted;
import main.buff.positiveBuff.BuffTough;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffSteelsoul;
import main.buff.positiveBuff.BuffIndomitable;
import main.buff.oneFightBuff.BuffResurrection;

public class IndomitableWill extends Enemy {
	
	private boolean phase2 = false;
	private boolean resurrectionUsed = false;
	private int deliriumDamageBonus = 0;
	private boolean lastDeliriumHit = false;
	private boolean desperateRoarUsed = false;
	private boolean scarletDeliriumUsed = false;
	private boolean impalementUsed = false;
	
	private String[] darkMissionary = {
		"\"In darkness, we find strength.\"",
		"\"The void calls to those who listen.\"",
		"\"Pain is but a gateway to power.\"",
		"\"Through suffering, we transcend.\"",
		"\"The abyss gazes back into you.\""
	};
	
	public IndomitableWill() {
		super("Indomitable Will", 250);
		// Initial Resurrection
		addBuff(new BuffResurrection(1), 1);
	}
	
	public void onMove() {
		
		// Check for resurrection
		if (!resurrectionUsed && getHp() <= 0) {
			resurrect();
			return;
		}
		
		// Check phase transition
		if (!phase2 && resurrectionUsed) {
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
		if (!desperateRoarUsed) {
			System.out.println(" >> " + this.getName() + " uses Desperate Roar!");
			desperateRoar();
			desperateRoarUsed = true;
			return;
		}
		
		if (desperateRoarUsed && !scarletDeliriumUsed) {
			System.out.println(" >> " + this.getName() + " uses Scarlet Delirium!");
			scarletDelirium();
			scarletDeliriumUsed = true;
			return;
		}
		
		if (scarletDeliriumUsed && !impalementUsed) {
			System.out.println(" >> " + this.getName() + " uses Impalement!");
			impalement();
			impalementUsed = true;
			return;
		}
		
		if (impalementUsed) {
			System.out.println(" >> " + this.getName() + " uses Disesteem!");
			disesteem();
			// Reset chain
			desperateRoarUsed = false;
			scarletDeliriumUsed = false;
			impalementUsed = false;
			return;
		}
		
		// Fallback
		System.out.println(" >> " + this.getName() + " uses Depressed!");
		depressed();
	}
	
	private void resurrect() {
		resurrectionUsed = true;
		// Remove resurrection buff
		for (int i = buffList.size() - 1; i >= 0; i--) {
			if (buffList.get(i).getName().equals("Resurrection")) {
				buffList.remove(i);
				break;
			}
		}
		// Revive with full HP
		int currentHp = getHp();
		int maxHp = getInitialHp();
		addHp(maxHp - currentHp);
		System.out.println(" >> " + this.getName() + " resurrects with full HP!");
	}
	
	private void unleash() {
		if (Main.random.nextBoolean()) {
			addBuff(new BuffTough(1), 3);
		} else {
			addBuff(new BuffStrengthened(1), 3);
		}
	}
	
	private void torcleaver() {
		int actualDamage = DamageProcessor.calculateDamageToPlayer(20, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(20, Player.getInstance());
		
		if (actualDamage > 0) {
			Player.getInstance().addBuff(new BuffWeakened(1), 2);
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
			Player.getInstance().addBuff(new BuffMuted(1), 2);
		}
	}
	
	private void stalwartSoul() {
		addBuff(new BuffSteelsoul(1), 1);
	}
	
	private void depressed() {
		int heal = 1 + Main.random.nextInt(2);
		addHp(heal);
	}
	
	private void darkMissionary() {
		String msg = darkMissionary[Main.random.nextInt(darkMissionary.length)];
		System.out.println(" >> " + msg);
	}
	
	private void desperateRoar() {
		int hpLoss = 15 + Main.random.nextInt(6);
		deductHp(hpLoss);
		addBuff(new BuffSteelsoul(1), 1);
	}
	
	private void scarletDelirium() {
		if (!desperateRoarUsed) {
			// Use Dark Mind instead
			System.out.println(" >> " + this.getName() + " uses Dark Mind!");
			addBuff(new BuffTough(1), 5);
			return;
		}
		
		int damage = 16 + Main.random.nextInt(2);
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		Player.getInstance().addBuff(new BuffVulnerable(1), 2);
	}
	
	private void impalement() {
		if (!scarletDeliriumUsed) {
			// Skip, don't act
			return;
		}
		
		int damage = 9 + Main.random.nextInt(3);
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		Player.getInstance().addBuff(new BuffVulnerable(1), 1);
	}
	
	private void disesteem() {
		if (!impalementUsed) {
			// Use Depressed instead
			depressed();
			return;
		}
		
		// Deal 23 damage, ignoring Tough and Reflective
		// We need to bypass these buffs
		int damage = 23;
		
		// Temporarily remove Tough and Reflective from player
		java.util.ArrayList<main.buff.Buff> tempBuffs = new java.util.ArrayList<>();
		java.util.ArrayList<main.buff.Buff> playerBuffs = Player.getInstance().getBuffList();
		
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

