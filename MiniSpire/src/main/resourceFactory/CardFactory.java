package main.resourceFactory;

import java.util.ArrayList;

import main.Main;

import main.card.Card;
import main.card.attackCard.Card00Strike;
import main.card.attackCard.Card01Onslaught;
import main.card.attackCard.Card02Earthquake;
import main.card.attackCard.Card03Upheaval;
import main.card.attackCard.Card04AbdomenTear;
import main.card.attackCard.Card05DecesiveStrike;
import main.card.attackCard.Card06LifeDrain;
import main.card.attackCard.Card07Ruination;
import main.card.attackCard.Card23Ragnarok;
import main.card.attackCard.Card24FlurryOfBlows;
import main.card.attackCard.Card25TheSinisterBlade;
import main.card.attackCard.Card26Stratagem;
import main.card.attackCard.Card27InnerRelease;

import main.card.effectCard.Card08Bloodbath;
import main.card.effectCard.Card09Double;
import main.card.effectCard.Card10Comeuppance;
import main.card.effectCard.Card11Rage;
import main.card.effectCard.Card12Rampart;
import main.card.effectCard.Card13Equilibrium;
import main.card.effectCard.Card14Interject;
import main.card.effectCard.Card15Esuna;
import main.card.effectCard.Card16Holmgang;
import main.card.effectCard.Card17PrimalRend;
import main.card.effectCard.Card18PrayForFavor;
import main.card.effectCard.Card28Tremble;

import main.card.passiveCard.Card19RecoveryStone;
import main.card.passiveCard.Card20OldRadiantLifegem;
import main.card.passiveCard.Card21BlessingOfTheErdtree;
import main.card.passiveCard.Card22DeathBrand;

public class CardFactory implements ResourceFactory {
	
	private static CardFactory instance = null;
	public static CardFactory getInstance() {
		if (instance == null) {
			instance = new CardFactory();
		}
		return instance;
	}

	private ArrayList<Card> cardPool;

	private ArrayList<Card> normalCards;
	private ArrayList<Card> rareCards;
	private ArrayList<Card> epicCards;
	private ArrayList<Card> legendaryCards;

	private static final double NORMAL_RATE = 0.35;
	private static final double RARE_RATE = 0.3;
	private static final double EPIC_RATE = 0.225;
	private static final double LEGENDARY_RATE = 0.125;

	public CardFactory(){

		cardPool = new ArrayList<>();

		normalCards = new ArrayList<>();
		rareCards = new ArrayList<>();
		epicCards = new ArrayList<>();
		legendaryCards = new ArrayList<>();

		cardPool.add(new Card00Strike());					//00	normal
		cardPool.add(new Card01Onslaught());				//01	normal
		cardPool.add(new Card02Earthquake());				//02	normal
		cardPool.add(new Card03Upheaval());					//03	rare
		cardPool.add(new Card04AbdomenTear());				//04	normal
		cardPool.add(new Card05DecesiveStrike());			//05	normal
		cardPool.add(new Card06LifeDrain());				//06	normal
		cardPool.add(new Card07Ruination());				//07	legendary

		cardPool.add(new Card08Bloodbath());				//08	normal
		cardPool.add(new Card09Double());					//09	normal
		cardPool.add(new Card10Comeuppance());				//10	rare
		cardPool.add(new Card11Rage());						//11	rare
		cardPool.add(new Card12Rampart());					//12	rare
		cardPool.add(new Card13Equilibrium());				//13	rare
		cardPool.add(new Card14Interject());				//14	rare
		cardPool.add(new Card15Esuna());					//15	rare
		cardPool.add(new Card16Holmgang());					//16	epic
		cardPool.add(new Card17PrimalRend());				//17	epic
		cardPool.add(new Card18PrayForFavor());				//18	legendary

		cardPool.add(new Card19RecoveryStone());			//19	normal
		cardPool.add(new Card20OldRadiantLifegem());		//20	rare
		cardPool.add(new Card21BlessingOfTheErdtree());		//21	epic
		cardPool.add(new Card22DeathBrand());				//22	special

		//v1.1 new card
		cardPool.add(new Card23Ragnarok()); 				//23	epic
		cardPool.add(new Card24FlurryOfBlows());			//24	rare
		cardPool.add(new Card25TheSinisterBlade());			//25	epic
		cardPool.add(new Card26Stratagem());				//26	epic
		cardPool.add(new Card27InnerRelease());				//27	rare

		cardPool.add(new Card28Tremble());					//28 	normal

		for (Card card : cardPool) {
			String rarity = card.getRarity().toLowerCase().replace("\u001B[0m", "");
			if (rarity.contains("normal")) {
				normalCards.add(card);
			} else if (rarity.contains("rare")) {
				rareCards.add(card);
			} else if (rarity.contains("epic")) {
				epicCards.add(card);
			} else if (rarity.contains("legendary")) {
				legendaryCards.add(card);
			}
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Card> getInitialDrawCardList(ArrayList<Card> drawCardList) {
		
		drawCardList.add(cardPool.get(0));
		drawCardList.add(cardPool.get(0));
		drawCardList.add(cardPool.get(0));
		drawCardList.add(cardPool.get(0));
		
		drawCardList.add(cardPool.get(1));
		drawCardList.add(cardPool.get(1));
		
		drawCardList.add(cardPool.get(5));
		
		drawCardList.add(cardPool.get(8));
		
		drawCardList.add(cardPool.get(9));
		
		drawCardList.add(cardPool.get(13));
		
		drawCardList.add(cardPool.get(19));
		drawCardList.add(cardPool.get(19));

		// Test: 
		/* drawCardList.add(cardPool.get(24));
		drawCardList.add(cardPool.get(24));
		drawCardList.add(cardPool.get(24));
		drawCardList.add(cardPool.get(24));
		drawCardList.add(cardPool.get(24));
		drawCardList.add(cardPool.get(9));
		drawCardList.add(cardPool.get(9));
		drawCardList.add(cardPool.get(9)); */
		
		return drawCardList;
	}

	public Card createCard(int code){
		return cardPool.get(code);
	}

	// Reset any per-battle state on cards that need it. Called when a new battle/node starts.
	public void resetCardsForNewBattle() {
		for (Card c : cardPool) {
			if (c instanceof main.card.attackCard.Card03Upheaval) {
				((main.card.attackCard.Card03Upheaval) c).resetBattle();
			}
		}
	}

	public Card getRandomCard(){

		/* int randNum = Main.random.nextInt(10) + 1;
		switch (randNum) {
			case 1:
			case 2:
			case 3:
			case 4:
				return getRandomNormalCard();

			case 5:
			case 6:
			case 7:
				return getRandomRareCard();
			
			case 8:
			case 9:
				return getRandomEpicCard();

			case 10:
				return getRandomLegendaryCard();
		
			default:
				return getRandomNormalCard();
		} */

		double randNum = Main.random.nextDouble();
	
		if (randNum < NORMAL_RATE) {
			return getRandomNormalCard();
		} else if (randNum < NORMAL_RATE + RARE_RATE) {
			return getRandomRareCard();
		} else if (randNum < NORMAL_RATE + RARE_RATE + EPIC_RATE) {
			return getRandomEpicCard();
		} else {
			return getRandomLegendaryCard();
		}
	}

	private Card getRandomLegendaryCard() {

		/* int randNum = Main.random.nextInt(legendaryCardNo) + 1;
		switch (randNum) {
			case 1:
				return cardPool.get(7);

			case 2:
				return cardPool.get(24);

			case 3:
				return cardPool.get(18);
		
			default:
				return cardPool.get(7);
		} */

		if (legendaryCards.isEmpty()) {
			return getRandomNormalCard();
		}
		int randNum = Main.random.nextInt(legendaryCards.size());
		return legendaryCards.get(randNum);
	}

	private Card getRandomEpicCard() {
		
		/* int randNum = Main.random.nextInt(epicCardNo) + 1;
		switch (randNum) {
			case 1:
				return cardPool.get(16);
				
			case 2:
				return cardPool.get(17);

			case 3:
				return cardPool.get(21);

			case 4:
				return cardPool.get(23);

			case 5:
				return cardPool.get(25);

			case 6:
				return cardPool.get(26);

			case 7:
				return cardPool.get(27);
		
			default:
				return cardPool.get(16);
		} */

		if (epicCards.isEmpty()) {
			return getRandomNormalCard();
		}
		int randNum = Main.random.nextInt(epicCards.size());
		return epicCards.get(randNum);
	}

	private Card getRandomRareCard() {

		/* int randNum = Main.random.nextInt(rareCardNo) + 1;
		switch (randNum) {
			case 1:
				return cardPool.get(3);

			case 2:
				return cardPool.get(5);

			case 3:
				return cardPool.get(6);
			
			case 4:
				return cardPool.get(10);

			case 5:
				return cardPool.get(11);

			case 6:
				return cardPool.get(12);

			case 7:
				return cardPool.get(13);

			case 8:
				return cardPool.get(14);

			case 9:
				return cardPool.get(15);

			case 10:
				return cardPool.get(20);
		
			default:
				return cardPool.get(3);
		} */

		if (rareCards.isEmpty()) {
			return getRandomNormalCard();
		}
		int randNum = Main.random.nextInt(rareCards.size());
		return rareCards.get(randNum);
	}

	private Card getRandomNormalCard() {
		
		/* int randNum = Main.random.nextInt(normalCardNo) + 1;
		switch (randNum) {
			case 1:
				return cardPool.get(0);
		
			case 2:
				return cardPool.get(1);

			case 3:
				return cardPool.get(2);

			case 4:
				return cardPool.get(4);

			case 5:
				return cardPool.get(8);

			case 6:
				return cardPool.get(9);

			case 7:
				return cardPool.get(19);

			case 8:
				return cardPool.get(28);

			default :
			return cardPool.get(0);
		} */

		if (normalCards.isEmpty()) {
			return cardPool.get(0);
		}
		int randNum = Main.random.nextInt(normalCards.size());
		return normalCards.get(randNum);
	}
}
