package main.enemy.bossEnemy;

import main.enemy.Enemy;
import main.player.Player;

import java.util.ArrayList;
import java.util.List;

import main.Main;
import main.buff.debuff.BuffEnshroud;
import main.buff.positiveBuff.BuffRecovering;
import main.buff.positiveBuff.BuffStrengthened;
import main.card.Card;
import main.buff.positiveBuff.BuffBloodLeeching;
import main.buff.debuff.BuffWeakened;
import main.buff.debuff.BuffLost;
import main.processor.DamageProcessor;
import main.resourceFactory.CardFactory;

public class Thanalous extends Enemy {

	private int phrase = 1;
	private boolean initiated = false;
	private int declarationCount = 0;
	private boolean forceNextBloodLeeching = false;
	private List<String> declarations = new ArrayList<>(); 

	public Thanalous() {
		super("Thanalous", 450,"boss");

		declarations.add("The lotus shall bloom again, no deny accepted...");
		declarations.add("Connecting the cycle of life, death, and rebirth...");
		declarations.add("Beauty, spirituality, and eternity...");
		declarations.add("Beholding the lush beauty of a hundred flowers...");
		declarations.add("Emerging from the mud yet unsoiled...");
		declarations.add("Washed by clear ripples yet unadorned...");
	}

	@Override
	public boolean onMove() {
		
		if (!super.onMove())
			return false;
		
		if (handlePhaseTransition()) {
			return true;
		}

		if (phrase == 1) 
			executePhaseOne();
		else
			executePhaseTwo();
		return true;
	}

	private void executePhaseOne() {
		switch (movementCounter) {
			case 0:
				openingSequence();
				break;
			case 1:
				spread();
				break;
			case 2:
				growth();
				break;
			case 3:
				gibbet();
				break;
			case 4:
				gallows();
				break;
			case 5:
				guillotine();
				break;
			case 6:
				filled();
				break;
			case 7:
				loopingDeclarationPhaseOne();
				break;
			default:
				movementCounter = 1;
		}
	}

	private void executePhaseTwo() {
		switch (movementCounter) {
			case 0:
				devour();
				break;
			case 1:
				feast();
				break;
			case 2:
				gluttony();
				break;
			case 3:
				confusion();
				break;
			case 4:
				declaration();
				movementCounter++;
				break;
			default:
				movementCounter = 0;
		}
	}

	private boolean handlePhaseTransition() {
		if (this.getHp() <= 0 || isDied) {
			return false;
		}
		
		if (phrase == 1 && this.getHp() < 150 && !forceNextBloodLeeching) {
			System.out.println(" >> Thanalous: You will regret this...");
			forceNextBloodLeeching = true;
		}

		if (forceNextBloodLeeching) {
			gainOverwhelmingLeech();
			return true;
		}
		return false;
	}

	private void openingSequence() {
		if (!initiated) {
			System.out.println(" >> Thanalous: Go home, this is not your place.");
			initiated = true;
		}
		movementCounter++;
	}

	private void spread() {
		System.out.println(" >> Spread!");
		Player.getInstance().addBuff(new BuffEnshroud(5), 5);
		addBuff(new BuffRecovering(5), 5);
		movementCounter++;
	}

	private void growth() {
		System.out.println(" >> Growth!");
		Player.getInstance().addBuff(new main.buff.positiveBuff.BuffStrengthened(2), 2);
		addBuff(new BuffStrengthened(3), 3);
		movementCounter++;
	}

	private void gibbet() {

		System.out.println(" >> Gibbet!");
		int damage = 13 + Main.random.nextInt(5);
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());

		boolean playerHasEnshroud = buffList.stream()
                .anyMatch(buff -> "Enshroud".equals(buff.getName()));
		if (playerHasEnshroud)
			Player.getInstance().addBuff(new BuffEnshroud(2), 2);

		movementCounter++;
	}

	private void gallows() {
		System.out.println(" >> Gallows!");

		boolean playerHasEnshroud = buffList.stream()
                .anyMatch(buff -> "Enshroud".equals(buff.getName()));
		
		if (playerHasEnshroud)
			Player.getInstance().addBuff(new BuffEnshroud(3), 3);
		else
			addBuff(new BuffBloodLeeching(2), 2);

		movementCounter++;
	}

	private void guillotine() {
		System.out.println(" >> Guillotine!");
		int damage = 35;
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());
		int finalDamage = DamageProcessor.calculateDamageToPlayer(damage, this, Player.getInstance());
		if (finalDamage >= damage)
			Player.getInstance().addBuff(new BuffWeakened(2), 2);
		movementCounter++;
	}

	private void filled() {
		System.out.println(" >> Filled!");
		Player.getInstance().addBuff(new BuffLost(2), 2);
		movementCounter++;
	}

	private void loopingDeclarationPhaseOne() {
		declaration();
		movementCounter = 1;
	}

	private void devour() {
		System.out.println(" >> Devour!");
		int bonus = 1 + Main.random.nextInt(2);
		int damage = 25 + Main.random.nextInt(4) + declarationCount * bonus;
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());
		movementCounter++;
	}

	private void feast() {
		System.out.println(" >> Feast!");

		int damage = 15 + Main.random.nextInt(5);
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());

		Card deathBrandCard = CardFactory.getInstance().createCard(22);
		Player.getInstance().addCardToDeck(deathBrandCard);
		Player.getInstance().addCardToDeck(deathBrandCard);

		Player.getInstance().addBuff(new BuffLost(2), 2);

		movementCounter++;
	}

	private void gluttony() {
		System.out.println(" >> Gluttony!");

		int damage = 11 + Main.random.nextInt(7);
		DamageProcessor.applyDamageToPlayer(damage, this, Player.getInstance());

		boolean hasBloodLeeching = buffList.stream()
            .anyMatch(buff -> "BloodLeeching".equals(buff.getName()));

		if (hasBloodLeeching)
			addBuff(new BuffEnshroud(2), 2);
		else
			addBuff(new BuffStrengthened(4), 4);

		movementCounter++;
	}

	private void confusion() {
		System.out.println(" >> Confusion!");
		addBuff(new main.buff.positiveBuff.BuffInvincible(2), 2);
		addBuff(new BuffStrengthened(2), 2);
		Player.getInstance().addBuff(new BuffWeakened(2), 2);
		movementCounter++;
	}

	private void gainOverwhelmingLeech() {
		System.out.println(" >> " + this.getName() + " gains overwhelming leech!");
		addBuff(new BuffBloodLeeching(10), 10);
		forceNextBloodLeeching = false;
		phrase = 2;
		movementCounter = 0;
	}

	private void declaration() {
		System.out.println(" >> Thanalous: " + declarations.get(Main.random.nextInt(declarations.size())));
		declarationCount++;
	}
}
