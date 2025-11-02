package main.resourceFactory;

import java.util.ArrayList;

import main.card.Card;
import main.card.attackCard.CardDecesiveStrike;
import main.card.attackCard.CardOnslaught;
import main.card.attackCard.CardStrike;
import main.card.effectCard.CardBloodbath;
import main.card.effectCard.CardDouble;
import main.card.effectCard.CardEquilibrium;
import main.card.passiveCard.CardRecoveryStone;

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

		cardPool.add(new CardStrike());
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Card> getInitialDrawCardList(ArrayList<Card> drawCardList) {
		
		drawCardList.add(new CardStrike());
		drawCardList.add(new CardStrike());
		drawCardList.add(new CardStrike());
		drawCardList.add(new CardStrike());
		
		drawCardList.add(new CardOnslaught());
		drawCardList.add(new CardOnslaught());
		
		drawCardList.add(new CardDecesiveStrike());
		
		drawCardList.add(new CardBloodbath());
		
		drawCardList.add(new CardDouble());
		
		drawCardList.add(new CardEquilibrium());
		
		drawCardList.add(new CardRecoveryStone());
		drawCardList.add(new CardRecoveryStone());
		
		return drawCardList;
	}

}
