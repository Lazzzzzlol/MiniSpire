package main.enemy.bossEnemy;

import main.enemy.Enemy;
import main.player.Player;

import java.util.concurrent.TimeUnit;

import main.Main;
import main.buff.oneFightBuff.BuffResurrection;
import main.buff.oneFightBuff.BuffIndomitable;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffTough;
import main.buff.positiveBuff.BuffSteelsoul;
import main.buff.debuff.BuffWeakened;
import main.buff.Buff;
import main.buff.debuff.BuffMuted;

public class IndomitableWill extends Enemy {

	private int phase = 1;
	private int deliriumBonus = 0;
	private boolean desperationRoarDone = false;

	public IndomitableWill() {
		super("Indomitable Will", 250);
		Main.executor.schedule(() -> {
			addBuff(new BuffResurrection(1), 1);
		}, 1, TimeUnit.SECONDS);
	}

	@Override
	public void onMove() {
		// transition when Resurrection is gone
		boolean hasRes = false;
		for (Buff b : this.getBuffList()) 
			if (b.getName().equals("Resurrection")) { 
				hasRes = true; 
				break; 
			}

		if (phase == 1 && !hasRes) {
			phase = 2;
			addBuff(new BuffIndomitable(999), 999);
			movementCounter = 0;
		}

		if (phase == 1) {
			executePhaseOne();
			return;
		}
		executePhaseTwo();
	}

	private void executePhaseOne() {
		switch (movementCounter) {
			case 0:
				System.out.println(" >> Unleash!");
				if (Main.random.nextBoolean()) addBuff(new BuffTough(3), 3); else addBuff(new BuffStrengthened(3), 3);
				movementCounter++;
				break;
			case 1:
				System.out.println(" >> Torcleaver!");
				Player.getInstance().deductHp(20);
				Player.getInstance().addBuff(new BuffWeakened(2), 2);
				movementCounter++;
				break;
			case 2:
				System.out.println(" >> Delirium!");
				int dmg = 10 + deliriumBonus;
				Player.getInstance().deductHp(dmg);
				// if player has Weakened, next Delirium +5
				deliriumBonus += 5;
				movementCounter++;
				break;
			case 3:
				System.out.println(" >> Reject!");
				// simplified: apply Muted 2
				Player.getInstance().addBuff(new BuffMuted(2), 2);
				movementCounter++;
				break;
			case 4:
				System.out.println(" >> Stalwart Soul!");
				addBuff(new BuffSteelsoul(1), 1);
				movementCounter++;
				break;
			case 5:
				System.out.println(" >> Depressed!");
				this.addHp(1 + Main.random.nextInt(2)); // 1-2
				movementCounter++;
				break;
			case 6:
				System.out.println(" >> Dark Missionary: ...");
				movementCounter = 0;
				break;
			default:
				movementCounter = 0;
		}
	}

	private void executePhaseTwo() {
		switch (movementCounter) {
			case 0:
				System.out.println(" >> Desperate Roar!");
				this.deductHp(15 + Main.random.nextInt(6)); // 15-20 self damage
				addBuff(new BuffSteelsoul(1), 1);
				desperationRoarDone = true;
				movementCounter++;
				break;
			case 1:
				if (desperationRoarDone) {
					System.out.println(" >> Scarlet Delirium!");
					Player.getInstance().deductHp(16 + Main.random.nextInt(2)); // 16-17
					addBuff(new main.buff.debuff.BuffVulnerable(2), 2);
					movementCounter++;
					break;
				}
				System.out.println(" >> Dark Mind!");
				addBuff(new BuffTough(5), 5);
				movementCounter++;
				break;
			case 2:
				if (desperationRoarDone) {
					System.out.println(" >> Impalement!");
					Player.getInstance().deductHp(9 + Main.random.nextInt(3)); // 9-11
					Player.getInstance().addBuff(new main.buff.debuff.BuffVulnerable(1), 1);
					movementCounter++;
					break;
				}
				// if not, no action
				movementCounter++;
				break;
			case 3:
				System.out.println(" >> Disesteem!");
				// else Depressed
				movementCounter++;
				break;
			case 4:
				System.out.println(" >> Depressed!");
				this.addHp(1 + Main.random.nextInt(2));
				movementCounter++;
				break;
			case 5:
				System.out.println(" >> Dark Missionary: ...");
				movementCounter = 0;
				desperationRoarDone = false;
				break;
			default:
				movementCounter = 0;
		}
	}
}


