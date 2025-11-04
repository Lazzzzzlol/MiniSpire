package main.resourceFactory;

import java.util.ArrayList;

import main.card.Card;
import main.card.attackCard.Card00Strike;
import main.card.attackCard.Card01Onslaught;
import main.card.attackCard.Card02Earthquake;
import main.card.attackCard.Card03Upheaval;
import main.card.attackCard.Card04AbdomenTear;
import main.card.attackCard.Card05DecesiveStrike;
import main.card.attackCard.Card06LifeDrain;
import main.card.attackCard.Card07Ruination;

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

	public CardFactory(){

		cardPool = new ArrayList<>();

		cardPool.add(new Card00Strike());					//00
		cardPool.add(new Card01Onslaught());				//01
		cardPool.add(new Card02Earthquake());				//02
		cardPool.add(new Card03Upheaval());					//03
		cardPool.add(new Card04AbdomenTear());				//04
		cardPool.add(new Card05DecesiveStrike());			//05
		cardPool.add(new Card06LifeDrain());				//06
		cardPool.add(new Card07Ruination());				//07

		cardPool.add(new Card08Bloodbath());				//08
		cardPool.add(new Card09Double());					//09
		cardPool.add(new Card10Comeuppance());				//10
		cardPool.add(new Card11Rage());						//11
		cardPool.add(new Card12Rampart());					//12
		cardPool.add(new Card13Equilibrium());				//13
		cardPool.add(new Card14Interject());				//14
		cardPool.add(new Card15Esuna());					//15
		cardPool.add(new Card16Holmgang());					//16
		cardPool.add(new Card17PrimalRend());				//17
		cardPool.add(new Card18PrayForFavor());				//18

		cardPool.add(new Card19RecoveryStone());			//19
		cardPool.add(new Card20OldRadiantLifegem());		//20
		cardPool.add(new Card21BlessingOfTheErdtree());		//21
		cardPool.add(new Card22DeathBrand());				//22
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
}
