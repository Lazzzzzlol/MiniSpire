package main.enemy.bossEnemy;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.DamageProcessor;
import main.buff.debuff.BuffEnshroud;
import main.buff.debuff.BuffLost;
import main.buff.debuff.BuffWeakened;
import main.buff.positiveBuff.BuffRecovering;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffBloodLeeching;
import main.buff.positiveBuff.BuffInvincible;

public class Thanalous extends Enemy {
	
	private boolean phase2 = false;
	private boolean initiateUsed = false;
	private int declarationCount = 0;
	
	private String[] phase1Declarations = {
		"\"The darkness shall consume all...\"",
		"\"Your light is but a flicker in the void.\"",
		"\"I am the eternal shadow, the unending night.\"",
		"\"Your struggle is meaningless.\""
	};
	
	private String[] phase2Declarations = {
		"\"The feast begins...\"",
		"\"Your essence shall sustain me.\"",
		"\"The hunger never ends...\"",
		"\"I devour all that stands before me.\""
	};
	
	public Thanalous() {
		super("Thanalous the Dark Lily", 450);
	}
	
	public void onMove() {
		
		// Check phase transition
		if (!phase2 && getHp() < 150) {
			phase2 = true;
			System.out.println(" >> " + this.getName() + " enters Phase 2!");
			addBuff(new BuffBloodLeeching(1), 10);
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
				if (!initiateUsed) {
					System.out.println(" >> " + this.getName() + " uses Initiate!");
					initiate();
					initiateUsed = true;
				} else {
					// Skip to next move
					movementCounter++;
					phase1Move();
				}
				break;
				
			case 1:
				System.out.println(" >> " + this.getName() + " uses Spread!");
				spread();
				movementCounter++;
				break;
				
			case 2:
				System.out.println(" >> " + this.getName() + " uses Growth!");
				growth();
				movementCounter++;
				break;
				
			case 3:
				System.out.println(" >> " + this.getName() + " uses Gibbet!");
				gibbet();
				movementCounter++;
				break;
				
			case 4:
				System.out.println(" >> " + this.getName() + " uses Gallows!");
				gallows();
				movementCounter++;
				break;
				
			case 5:
				System.out.println(" >> " + this.getName() + " uses Guillotine!");
				guillotine();
				movementCounter++;
				break;
				
			case 6:
				System.out.println(" >> " + this.getName() + " uses Filled!");
				filled();
				movementCounter++;
				break;
				
			case 7:
				System.out.println(" >> " + this.getName() + " uses Declaration!");
				declaration();
				movementCounter = 1; // Loop back to Spread
				break;
				
			default:
				movementCounter = 1;
				break;
		}
	}
	
	private void phase2Move() {
		switch (movementCounter) {
			case 0:
				System.out.println(" >> " + this.getName() + " uses Declaration!");
				declaration();
				movementCounter++;
				break;
				
			case 1:
				System.out.println(" >> " + this.getName() + " uses Devour!");
				devour();
				movementCounter++;
				break;
				
			case 2:
				System.out.println(" >> " + this.getName() + " uses Feast!");
				feast();
				movementCounter++;
				break;
				
			case 3:
				System.out.println(" >> " + this.getName() + " uses Gluttony!");
				gluttony();
				movementCounter++;
				break;
				
			case 4:
				System.out.println(" >> " + this.getName() + " uses Confusion!");
				confusion();
				movementCounter++;
				break;
				
			case 5:
				System.out.println(" >> " + this.getName() + " uses Declaration!");
				declaration();
				movementCounter = 0;
				break;
				
			default:
				movementCounter = 0;
				break;
		}
	}
	
	private void initiate() {
		System.out.println(" >> \"Go home. This is not a place for you.\"");
	}
	
	private void spread() {
		Player.getInstance().addBuff(new BuffEnshroud(1), 5);
		addBuff(new BuffRecovering(1), 5);
	}
	
	private void growth() {
		Player.getInstance().addBuff(new BuffStrengthened(1), 1);
		addBuff(new BuffStrengthened(1), 3);
	}
	
	private void gibbet() {
		int damage = 13 + Main.random.nextInt(5);
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		
		// Check if player has Enshroud and extend it
		for (main.buff.Buff buff : Player.getInstance().getBuffList()) {
			if (buff.getName().equals("Enshroud")) {
				buff.extendDuration(1);
				break;
			}
		}
	}
	
	private void gallows() {
		addBuff(new BuffBloodLeeching(1), 2);
	}
	
	private void guillotine() {
		int baseDamage = 35;
		int actualDamage = DamageProcessor.calculateDamageToPlayer(baseDamage, Player.getInstance());
		DamageProcessor.applyDamageToPlayer(baseDamage, Player.getInstance());
		
		if (actualDamage > 35) {
			Player.getInstance().addBuff(new BuffWeakened(1), 2);
		}
	}
	
	private void filled() {
		Player.getInstance().addBuff(new BuffLost(1), 2);
	}
	
	private void declaration() {
		String[] declarations = phase2 ? phase2Declarations : phase1Declarations;
		String msg = declarations[Main.random.nextInt(declarations.length)];
		System.out.println(" >> " + msg);
		declarationCount++;
	}
	
	private void devour() {
		int damage = 20 + Main.random.nextInt(4);
		damage += declarationCount; // +1 or +2 per declaration
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
	}
	
	private void feast() {
		Player.getInstance().addBuff(new BuffLost(1), 2);
	}
	
	private void gluttony() {
		boolean hasBloodLeeching = false;
		for (main.buff.Buff buff : buffList) {
			if (buff.getName().equals("BloodLeeching")) {
				hasBloodLeeching = true;
				break;
			}
		}
		
		if (hasBloodLeeching) {
			Player.getInstance().addBuff(new BuffEnshroud(1), 2);
		} else {
			addBuff(new BuffStrengthened(1), 4);
		}
	}
	
	private void confusion() {
		addBuff(new BuffInvincible(1), 2);
		
		// Random positive buff
		switch (Main.random.nextInt(3)) {
			case 0:
				addBuff(new BuffStrengthened(1), 2);
				break;
			case 1:
				addBuff(new BuffBloodLeeching(1), 2);
				break;
			case 2:
				addBuff(new BuffRecovering(1), 2);
				break;
		}
		
		// Random negative buff (but we don't have enemy debuffs, so skip)
	}
}

