package main.enemy.bossEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.debuff.BuffEnshroud;
import main.buff.positiveBuff.BuffRecovering;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffBloodLeeching;
import main.buff.debuff.BuffWeakened;
import main.buff.debuff.BuffLost;
import main.buff.DamageProcessor;

public class Thanalous extends Enemy {

	private int phase = 1;
	private boolean initiated = false;
	private int declarationCount = 0;
	private boolean forceNextBloodLeeching = false;

	public Thanalous() {
		super("Thanalous", 450);
	}

	@Override
	public void onMove() {
		// phase transition check
		if (phase == 1 && this.getHp() < 150 && !forceNextBloodLeeching) {
			System.out.println(" >> " + this.getName() + " enrages and prepares to leech!");
			forceNextBloodLeeching = true;
		}

		if (forceNextBloodLeeching) {
			System.out.println(" >> " + this.getName() + " gains overwhelming leech!");
			addBuff(new BuffBloodLeeching(10), 10);
			forceNextBloodLeeching = false;
			phase = 2;
			movementCounter = 0;
			return;
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
				if (!initiated) {
					System.out.println(" >> Initiate: Go home, this is not your place.");
					initiated = true;
					movementCounter++;
					break;
				}
				// fallthrough if already initiated
				movementCounter++;
				break;
			case 1:
				System.out.println(" >> Spread!");
				Player.getInstance().addBuff(new BuffEnshroud(5), 5);
				addBuff(new BuffRecovering(5), 5);
				movementCounter++;
				break;
			case 2:
				System.out.println(" >> Growth!");
				Player.getInstance().addBuff(new main.buff.positiveBuff.BuffStrengthened(1), 1);
				addBuff(new BuffStrengthened(3), 3);
				movementCounter++;
				break;
			case 3:
				System.out.println(" >> Gibbet!");
				int dmgG = 13 + Main.random.nextInt(5); // 13-17
				DamageProcessor.applyDamageToPlayer(dmgG, Player.getInstance());
				// extend Shrouded by 1 if player has it
				Player.getInstance().addBuff(new BuffEnshroud(1), 1);
				movementCounter++;
				break;
			case 4:
				System.out.println(" >> Gallows!");
				addBuff(new BuffBloodLeeching(2), 2);
				movementCounter++;
				break;
			case 5:
				System.out.println(" >> Guillotine!");
				int dmgQ = 35;
				DamageProcessor.applyDamageToPlayer(dmgQ, Player.getInstance());
				// if actually >35, apply Weakened 2 (we treat as fixed 35 here)
				movementCounter++;
				break;
			case 6:
				System.out.println(" >> Filled!");
				Player.getInstance().addBuff(new BuffLost(2), 2);
				movementCounter++;
				break;
			case 7:
				declaration();
				movementCounter = 1; // loop back to spread
				break;
			default:
				movementCounter = 1;
		}
	}

	private void executePhaseTwo() {
		switch (movementCounter) {
			case 0:
				declaration();
				movementCounter++;
				break;
			case 1:
				System.out.println(" >> Devour!");
				int bonus = 1 + Main.random.nextInt(2); // +1 or +2 per declaration
				int dmg = 20 + Main.random.nextInt(4) + declarationCount * bonus; // 20-23 base + scaling
				DamageProcessor.applyDamageToPlayer(dmg, Player.getInstance());
				movementCounter++;
				break;
			case 2:
				System.out.println(" >> Feast!");
				Player.getInstance().addBuff(new BuffLost(2), 2);
				movementCounter++;
				break;
			case 3:
				System.out.println(" >> Gluttony!");
				addBuff(new BuffEnshroud(2), 2);
				movementCounter++;
				break;
			case 4:
				System.out.println(" >> Confusion!");
				addBuff(new main.buff.positiveBuff.BuffInvincible(2), 2);
				// 2 turns random positive buff and 2 turns negative buff (simplified as one each)
				addBuff(new BuffStrengthened(2), 2);
				Player.getInstance().addBuff(new BuffWeakened(2), 2);
				movementCounter++;
				break;
			case 5:
				declaration();
				movementCounter = 0;
				break;
			default:
				movementCounter = 0;
		}
	}

	private void declaration() {
		System.out.println(" >> Declaration: ...");
		declarationCount++;
	}
}
