package main.enemy.bossEnemy;

import main.enemy.Enemy;
import main.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import main.Main;
import main.buff.Buff;
import main.buff.oneFightBuff.BuffResurrection;
import main.buff.oneFightBuff.BuffIgnore;
import main.buff.oneFightBuff.BuffIndomitable;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffTough;
import main.buff.positiveBuff.BuffSteelsoul;
import main.buff.debuff.BuffWeakened;
import main.processor.DamageProcessor;
import main.buff.debuff.BuffMuted;
import main.buff.debuff.BuffVulnerable;

/**
 * 不屈意志 Boss：两阶段战斗，包含连招机制和常规循环
 */
public class IndomitableWill extends Enemy { 

	private boolean phase2 = false;
	private int deliriumDamageBonus = 0; // Delirium 的伤害加成（有 Weakened 时 +5）
	private boolean lastDeliriumHit = false; // 上次 Delirium 是否命中（用于 Reject 判断）
	
	// 第二阶段连招状态
	private boolean desperateRoarDone = false;
	private boolean scarletDeliriumDone =false;
	private boolean impalementDone = false;
	private boolean comboCompleted = false; // 连招完成后进入常规循环
	
	// 连招成功标志（用于 fallback 机制）
	private boolean desperateRoarSucceeded = false;
	private boolean scarletDeliriumSucceeded = false;
	private boolean impalementSucceeded = false;
	
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
		
		phase2 = false;
		comboCompleted = false;
		desperateRoarDone = false;
		scarletDeliriumDone = false;
		impalementDone = false;
		desperateRoarSucceeded = false;
		scarletDeliriumSucceeded = false;
		impalementSucceeded = false;
		movementCounter = 0;
	}

	public void onDie(){

		super.onDie();

		phase2 = true;
		System.out.println(" >> In dom?tab?e W?ll : Pr epare thy self to d ie.");
		System.out.println(" >> " + this.getName() + " enters Phase 2!");
		addBuff(new BuffIndomitable(999), 999);
		desperateRoarDone = false;
		scarletDeliriumDone = false;
		impalementDone = false;
		comboCompleted = false;
		desperateRoarSucceeded = false;
		scarletDeliriumSucceeded = false;
		impalementSucceeded = false;
		movementCounter = 0;
	}

	public void onMove() {

		super.onMove();

		if (!phase2) {
			phase1Move();
		} else {
			phase2Move();
		}
	}

	private void phase1Move() {
		switch (movementCounter) {
			case 0:
				System.out.println("\n >> " + this.getName() + " uses Unleash!");
				unleash();
				movementCounter++;
				break;

			case 1:
				System.out.println("\n >> " + this.getName() + " uses Torcleaver!");
				torcleaver();
				movementCounter++;
				break;

			case 2:
				System.out.println("\n >> " + this.getName() + " uses Delirium!");
				delirium();
				movementCounter++;
				break;

			case 3:
				System.out.println("\n >> " + this.getName() + " uses Reject!");
				reject();
				movementCounter++;
				break;

			case 4:
				System.out.println("\n >> " + this.getName() + " uses Stalwart Soul!");
				stalwartSoul();
				movementCounter++;
				break;

			case 5:
				System.out.println("\n >> " + this.getName() + " uses Depressed!");
				depressed();
				movementCounter++;
				break;

			case 6:
				System.out.println("\n >> " + this.getName() + " uses Dark Missionary!");
				darkMissionary();
				movementCounter = 0;
				break;

			default:
				movementCounter = 0;
				break;
		}
	}

	private void phase2Move() {
		if (isDied || this.getHp() <= 0) {
			return;
		}
		
		// 连招完成后进入常规循环（Depressed ↔ Dark Missionary）
		if (comboCompleted) {
			switch (movementCounter % 2) {
				case 0:
					System.out.println("\n >> " + this.getName() + " uses Depressed!");
					depressed();
					movementCounter++;
					break;
				case 1:
					System.out.println("\n >> " + this.getName() + " uses Dark Missionary!");
					darkMissionary();
					movementCounter++;
					break;
			}
			return;
		}
		
		if (!desperateRoarDone) {
			System.out.println("\n >> " + this.getName() + " uses Desperate Roar!");
			desperateRoar();
			desperateRoarDone = true;
			return;
		}

		if (desperateRoarDone && !scarletDeliriumDone) {
			System.out.println("\n >> " + this.getName() + " uses Scarlet Delirium!");
			scarletDelirium();
			scarletDeliriumDone = true;
			return;
		}

		if (scarletDeliriumDone && !impalementDone) {
			System.out.println("\n >> " + this.getName() + " uses Impalement!");
			impalement();
			impalementDone = true;
			return;
		}

		if (impalementDone) {
			System.out.println("\n >> " + this.getName() + " uses Disesteem!");
			disesteem();
			comboCompleted = true; // 连招完成，进入常规循环
			desperateRoarDone = false;
			scarletDeliriumDone = false;
			impalementDone = false;
			desperateRoarSucceeded = false;
			scarletDeliriumSucceeded = false;
			impalementSucceeded = false;
			movementCounter = 0;
			return;
		}

		// Fallback: 安全机制，使用常规循环
		switch (movementCounter % 2) {
			case 0:
				System.out.println("\n >> " + this.getName() + " uses Depressed!");
				depressed();
				movementCounter++;
				break;
			case 1:
				System.out.println("\n >> " + this.getName() + " uses Dark Missionary!");
				darkMissionary();
				movementCounter++;
				break;
		}
	}

	private void unleash() {
		if (Main.random.nextBoolean()) {
			addBuff(new BuffTough(3), 3);
		} else {
			addBuff(new BuffStrengthened(3), 3);
		}
	}

	private void torcleaver() {
		// Gives Weakened only when damage is dealt successfully
		int damage = 20;
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());

		int actualDamage = DamageProcessor.calculateDamageToPlayer(damage, this, Player.getInstance());
		if (actualDamage > 0) {
			Player.getInstance().addBuff(new BuffWeakened(2), 2);
		}
	}

	private void delirium() {
		int damage = 10 + deliriumDamageBonus;
		int actualDamage = DamageProcessor.calculateDamageToPlayer(damage, this, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());

		lastDeliriumHit = (actualDamage > 0);

		boolean hasWeakened = false;
		for (main.buff.Buff buff : Player.getInstance().getBuffList()) {
			if (buff.getName().equals("Weakened")) {
				hasWeakened = true;
				break;
			}
		}

		if (hasWeakened) {
			deliriumDamageBonus += 5;
		}
	}

	private void reject() {
		// 如果 Delirium 未命中，重新使用并 +10 伤害；否则施加 Muted
		if (!lastDeliriumHit) {
			deliriumDamageBonus += 10;
			System.out.println(" >> Reject: Delirium failed!? Using again with +10 bonus!");
			delirium();
			deliriumDamageBonus -= 10;
		} else {
			Player.getInstance().addBuff(new BuffMuted(2), 2);
		}
	}

	private void stalwartSoul() {
		int playerActionPoints = Player.getInstance().getActionPoints();
		
		BuffSteelsoul existingSteelsoul = null;
		for (Buff buff : buffList) {
			if (buff instanceof BuffSteelsoul) {
				existingSteelsoul = (BuffSteelsoul) buff;
				break;
			}
		}
		
		if (existingSteelsoul != null) {
			existingSteelsoul.setActionPointsToDeduct(playerActionPoints);
			existingSteelsoul.extendDuration(2);
		} else {
			BuffSteelsoul steelsoul = new BuffSteelsoul(2, playerActionPoints);
			addBuff(steelsoul, 2);
		}
	}

	private void depressed() {
		int heal = 1 + Main.random.nextInt(2);
		addHp(heal);
	}

	private void darkMissionary() {
		String msg = scriptures.get(Main.random.nextInt(scriptures.size()));
		System.out.println(" >> Indomitable Will" + msg);
	}

	private void desperateRoar() {
		desperateRoarSucceeded = false;
		scarletDeliriumSucceeded = false;
		impalementSucceeded = false;
		
		int hpBefore = this.getHp();
		int hpLoss = 15 + Main.random.nextInt(6);
		DamageProcessor.applyDamageToEnemy(hpLoss, null, this);
		int hpAfter = this.getHp();
		
		desperateRoarSucceeded = (hpBefore > hpAfter);
		
		if (desperateRoarSucceeded) {
			addBuff(new BuffSteelsoul(2), 2);
		}
	}

	private void scarletDelirium() {
		// Fallback: 如果 Desperate Roar 失败，使用 Dark Mind
		if (!desperateRoarSucceeded) {
			System.out.println("\n >> " + this.getName() + " uses Dark Mind!");
			addBuff(new BuffTough(5), 5);
			return;
		}

		int playerHpBefore = Player.getInstance().getHp();
		int damage = 16 + Main.random.nextInt(2);
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());
		int playerHpAfter = Player.getInstance().getHp();
		
		scarletDeliriumSucceeded = (playerHpBefore > playerHpAfter);
		
		if (scarletDeliriumSucceeded) {
			addBuff(new BuffVulnerable(2), 2);
		}
	}

	private void impalement() {
		if (isDied || this.getHp() <= 0) {
			return;
		}
		
		if (!scarletDeliriumSucceeded) {
			System.out.println(" >> : ... ..?");
			return;
		}

		int playerHpBefore = Player.getInstance().getHp();
		int damage = 9 + Main.random.nextInt(3);
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());
		int playerHpAfter = Player.getInstance().getHp();
		
		impalementSucceeded = (playerHpBefore > playerHpAfter);
		
		if (impalementSucceeded) {
			Player.getInstance().addBuff(new BuffVulnerable(1), 1);
		}
	}

	private void disesteem() {

		if (!impalementSucceeded) {
			depressed();
			return;
		}

		addBuff(new BuffIgnore(1), 1);
		DamageProcessor.applyDamageToPlayer(23, this, Player.getInstance());

		Iterator<Buff> iterator = buffList.iterator();
		while (iterator.hasNext()) {
			Buff buff = iterator.next();
			if ("ignore".equals(buff.getName())) {
				iterator.remove();
				break;
			}
		}
	}
}


