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

public class IndomitableWill extends Enemy { 

	private boolean phase2 = false;
	private int deliriumDamageBonus = 0;
	private boolean lastDeliriumHit = false;
	
	private boolean desperateRoarDone = false;
	private boolean scarletDeliriumDone =false;
	private boolean impalementDone = false;
	
	private List<String> scriptures = new ArrayList<>();

	public IndomitableWill() {
		super("Indomitable Will", 250,"boss");
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
		desperateRoarDone = false;
		scarletDeliriumDone = false;
		impalementDone = false;
		movementCounter = 0;
	}

	public void onDie(){

		super.onDie();

		addBuff(new BuffIndomitable(999), 999);
		desperateRoarDone = false;
		scarletDeliriumDone = false;
		impalementDone = false;
		movementCounter = 0;
		this.name = "IndOm?taB?e W?Ll";

		if (!phase2){
			Main.executor.schedule(() -> {
				System.out.println();
				System.out.println(" >> IndOm?taB?e W?Ll: Pre pa re  thy self  to  di e.");
			}, 10, TimeUnit.MILLISECONDS);
			phase2 = true;
		}
		return;
	}

	public boolean onMove() {
		
		if (!super.onMove())
			return false;

		if (!phase2) {
			phase1Move();
		} else {
			phase2Move();
		}
		return true;
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

		switch (movementCounter) {
			case 0:
				System.out.println("\n >> " + this.getName() + " uses Desperate Roar!");
				desperateRoar();
				desperateRoarDone = true;
				movementCounter++;
				break;
		
			case 1:
				if (desperateRoarDone){
					System.out.println("\n >> " + this.getName() + " uses Scarlet Delirium!");
					scarletDelirium();
					scarletDeliriumDone = true;
				} else {
					desperateRoarDone = false;
					darkMind();
				}
				movementCounter++;
				break;

			case 2:
				if (scarletDeliriumDone){
					System.out.println("\n >> " + this.getName() + " uses impalement!");
					impalement();
					impalementDone = true;
				} else {
					desperateRoarDone = false;
					scarletDeliriumDone = false;
					System.out.println(" >> : ... ..?");
				}
				movementCounter++;
				break;

			case 3:
				if (impalementDone){
					System.out.println("\n >> " + this.getName() + " uses disesteem!");
					disesteem();

					desperateRoarDone = false;
					scarletDeliriumDone = false;
					impalementDone = false;
				} else {
					depressed();
					desperateRoarDone = false;
					scarletDeliriumDone = false;
					impalementDone = false;
				}
				movementCounter++;
				break;

			case 4:
				depressed();
				desperateRoarDone = false;
				scarletDeliriumDone = false;
				impalementDone = false;
				movementCounter++;

			case 5:
				darkMissionary();
				desperateRoarDone = false;
				scarletDeliriumDone = false;
				impalementDone = false;
				movementCounter = 0;

			default:
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

		if (!lastDeliriumHit) {
			deliriumDamageBonus += 10;
			System.out.println(" >> Reject: Delirium absorbed more power... ");
			delirium();
			deliriumDamageBonus -= 10;
		} else {
			Player.getInstance().addBuff(new BuffMuted(1), 1);
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
		System.out.println(" >> Indomitable Will: " + msg);
	}

	private void desperateRoar() {
		
		int hpLoss = 10 + Main.random.nextInt(6);
		DamageProcessor.applyDamageToEnemy(hpLoss, null, this);
		
		addBuff(new BuffSteelsoul(2), 2);
	}

	private void scarletDelirium() {

		int damage = 16 + Main.random.nextInt(2);
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());
		addBuff(new BuffVulnerable(2), 2);	
	}

	private void darkMind(){
		addBuff(new BuffTough(5), 5);
	}

	private void impalement() {

		int damage = 9 + Main.random.nextInt(3);
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());
	}

	private void disesteem() {

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


