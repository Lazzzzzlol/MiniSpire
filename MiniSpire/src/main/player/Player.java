package main.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import main.Colors;
import main.Main;
import main.MessageQueue;
import main.TextDisplay;
import main.buff.Buff;
import main.buff.positiveBuff.BuffInvincible;
import main.processor.*;
import main.card.Card;
import main.enemy.Enemy;
import main.game.Game;
import main.resourceFactory.CardFactory;

public class Player {
	
	private static Player instance = null;
	public static Player getInstance() {
		if (instance == null) {
			instance = new Player();
		}
		return instance;
	}

	private int hp;
	private int maxHp;
	private int actionPoints;
	private int maxActionPoints;
	private int gold;
	private int totalGold;
	private int drawCardNumPerTurn;

	private boolean colorViews;
	
	private ArrayList<Card> handCardList;
	private ArrayList<Card> drawCardList;
	private ArrayList<Card> discardCardList;
	private ArrayList<Card> removeCardList;
	
	private ArrayList<Buff> buffList;
	//private ArrayList<Ability> abilityList;
	
	public Player() {
		
		this.hp = 70;
		this.maxHp = 70;
		this.actionPoints = 3;
		this.maxActionPoints = 3;
		this.gold = 0;
		this.totalGold = 0;
		this.drawCardNumPerTurn = 5;

		this.colorViews = true;
		
		this.handCardList = new ArrayList<Card>();
		this.drawCardList = new ArrayList<Card>();
		this.discardCardList = new ArrayList<Card>();
		this.removeCardList = new ArrayList<Card>();
		
		this.buffList = new ArrayList<Buff>();
		//this.abilityList = new ArrayList<Ability>();
	}
	
	public void drawCardListInit() {
		
		this.drawCardList.clear();
		this.drawCardList = CardFactory.getInstance().getInitialDrawCardList(this.drawCardList);
		Collections.shuffle(this.drawCardList);
	}
	
	public void onStartTurn() {

		boolean playerHasMuted = buffList.stream().anyMatch(buff -> "Muted".equals(buff.getName()));

		this.actionPoints = this.maxActionPoints;
		if (playerHasMuted){
			this.actionPoints = 0;
			System.out.println(" >> Action Point becomes 0 (" + Colors.colorOnAnyElse("Muted", Colors.BLUE) + ").");
		}

		Iterator<Buff> it = buffList.iterator();
	    
	    while (it.hasNext()) {
	        Buff buff = it.next();
	        buff.onEndTurn();
			if (buff.getDuration() <= 0)
	            it.remove();
		}
		
		drawHandCards(drawCardNumPerTurn, 2000);
	}
	
	public void drawHandCards(int num, Integer time) {
		if (Game.getInstance().getIsVictory() || hp <= 0)
			return;
		if (time == null) time = 1001;
		internalDrawHandCards(num, time);
	}

	public List<Card> drawHandCardsWithDetails(int num, Integer time) {
		if (time == null) time = 1001;
		return internalDrawHandCards(num, time);
	}

	private List<Card> internalDrawHandCards(int num, int time) {
		List<Card> drawnCards = new ArrayList<>();
		
		if (drawCardList.size() < num) {
			int needToDrawNum = num - drawCardList.size();
			
			drawnCards.addAll(drawCardList);
			handCardList.addAll(drawCardList);
			drawCardList.clear();
			
			Collections.shuffle(discardCardList);
			drawCardList.addAll(discardCardList);
			discardCardList.clear();
			
			for (int i = 0; i < needToDrawNum && !drawCardList.isEmpty(); i++) {
				Card card = drawCardList.remove(0);
				drawnCards.add(card);
				handCardList.add(card);
			}
		} else {
			for (int i = 0; i < num; i++) {
				Card card = drawCardList.remove(0);
				drawnCards.add(card);
				handCardList.add(card);
			}
		}

		if (!drawnCards.isEmpty()) {
			StringBuilder log = new StringBuilder(" >> Drawed card: ");
			for (int i = 0; i < drawnCards.size(); i++) {
				if (i > 0) {
					log.append(", ");
				}
				log.append(Colors.colorOnForCardName(drawnCards.get(i)));
			}
			Main.executor.schedule(() -> {
				System.out.println(log.toString());
			}, time, TimeUnit.MILLISECONDS);
		}
		
		return drawnCards;
	}
	
	public void playCard(int cardIndex, Enemy enemy) {
		
		Card cardToPlay = handCardList.get(cardIndex - 1);
		
		if (!cardToPlay.getCanPlay()) {
			System.out.println(" >> Unplayable card type.");
			return;
		}

		int cardCost = cardToPlay.getCost();

		if (cardCost <= actionPoints) {

			changeCurrentActionPoint(-cardCost);
			
			PlayCardProcessor.processCardPlay(this, cardToPlay, enemy);

			handCardList.remove(cardIndex - 1);

			if (cardToPlay.getNeedRemove())
				removeCardList.add(cardToPlay);
			else if (!cardToPlay.getDisposable() && !cardToPlay.getTemporary())
				discardCardList.add(cardToPlay);
			
		} else {
			System.out.println(" >> Not enough action points to play this card.");
		}
	}

	public void changeCurrentActionPoint(int delta) {
		actionPoints += delta;
		Main.executor.schedule(() -> {
			if (delta > 0) {
				Main.executor.schedule(() -> {
					System.out.println(" >> Action point +" + delta);
				}, 1002, TimeUnit.MILLISECONDS);
			} else if (delta < 0) {
				System.out.println(" >> Action point " + delta);
			} else if (delta == 0) {
				System.out.println(" >> Action point -0");
			}
		}, 0, TimeUnit.MILLISECONDS);
	}
	
	public void onEndTurn() {

		boolean playerHasRecovering = buffList.stream()
                .anyMatch(buff -> "Recovering".equals(buff.getName()));

		if (playerHasRecovering) {
			Buff recoveringBuff = null;
			for (Buff buff : buffList) {
				if (buff.getName().equals("Recovering")) {
					recoveringBuff = buff;
					break;
				}
        	}
			HealProcessor.applyHeal(this, recoveringBuff.getDuration(), null);
		}
		
		clearHandCards();
		

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void onWin(){

		clearHandCards();
		drawCardList.addAll(discardCardList);
		discardCardList.clear();

		drawCardList.addAll(removeCardList);
		removeCardList.clear();

		buffList.clear();
	}

	public int getHp() {
		return hp;
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	
	public void changeMaxHp(int delta) {
		this.maxHp += delta;
		if (this.maxHp < 1)
			this.maxHp = 1;
		if (this.hp > this.maxHp)
			this.hp = this.maxHp;
		if (delta >= 0) {
			MessageQueue.scheduleMessage("Max HP increases by " + delta,250L);
		} else {
			MessageQueue.scheduleMessage("Max HP decreases by " + (-delta),250L);
		}
	}
	
	public void addHp(int heal, Integer time) {

		if (time == null) time = 1001;

		if (heal == 0){
			MessageQueue.scheduleMessage("Healed 0 HP (" + Colors.colorOnAnyElse("Lost", Colors.RED) + ")", 250L);
			return;
		}
		
		this.hp += heal;
		if (this.hp > this.maxHp)
			this.hp = this.maxHp;
		
		MessageQueue.scheduleMessage("Healed " + heal + " HP", 250L);
	}
	
	public void deductHp(int damage) {
		
		this.hp -= damage;
		if (this.hp < 0){

			boolean hasBlessed = buffList.stream()
            .anyMatch(buff -> "Blessed".equals(buff.getName()));

				if (hasBlessed) {

					Main.executor.schedule(() -> {
						System.out.println(" >> Blessed be thou, that thou shalt be delivered from death. ");
					}, 1, TimeUnit.MILLISECONDS);

					addHp((maxHp / 2), 1);
					buffList.removeIf(buff -> "Blessed".equals(buff.getName()));
					buffList.add(new BuffInvincible(3));

				} else {
					Game.getInstance().setIsGameOver(true);
				}
		}
	}
	
	public int getActionPoints() {
		return actionPoints;
	}
	
	public int getMaxActionPoints() {
		return maxActionPoints;
	}
	
	public void changeMaxActionPoints(int delta) {
		this.maxActionPoints += delta;
		if (this.maxActionPoints < 0)
			this.maxActionPoints = 0;
		if (this.actionPoints > this.maxActionPoints)
			this.actionPoints = this.maxActionPoints;
		if (delta >= 0) {
			MessageQueue.scheduleMessage("Max action points increases by " + delta,250L);
		} else {
			MessageQueue.scheduleMessage("Max action points decreases by " + (-delta),250L);
		}
	}

	public void addGold(int gold) {
		this.gold += gold;
		this.totalGold += gold;
		System.out.println(" >> Gained " + gold + " gold");
		System.out.println((" >> Current gold: " + this.gold + " G"));
	}
	
	public int getGold() {
		return gold;
	}
	
	public int getTotalGold() {
		return totalGold;
	}

	public void lostGold(int cost) {
		this.gold -= cost;
		System.out.println(" >> Lost " + cost + " gold");
		System.out.println((" >> Current gold: " + this.gold + " G"));
	}
	
	public void changeDrawCardNumPerTurn(int delta) {
		this.drawCardNumPerTurn += delta;
		if (this.drawCardNumPerTurn < 1)
			this.drawCardNumPerTurn = 1;
		if (delta >= 0) {
			MessageQueue.scheduleMessage("Cards drawn per turn increases by " + delta,250L);
		} else {
			MessageQueue.scheduleMessage("Cards drawn per turn decreases by " + (-delta),250L);
		}
	}
	
	public void addBuff(Buff buff, int duration) {
		
		for (Buff existBuff : buffList)
			if (existBuff.getName().equals(buff.getName())) {
				existBuff.extendDuration(duration);
				return;
			}
				
		buffList.add(buff);
		Main.executor.schedule(() -> {
			System.out.println(" >> Obtained buff: " + Colors.colorOnForBuff(buff.getName(), buff.getType()));
		}, 2, TimeUnit.MILLISECONDS);
	}
	
	public ArrayList<Buff> getBuffList() {
		return buffList;
	}

	public ArrayList<Card> getHandCardList() {
		return handCardList;
	}
	
	public void clearHandCards(){
		for (Card card : handCardList){
			if (!card.getTemporary()){
				discardCardList.add(card);
			}
		}
		handCardList.clear();
	}
	
	public String getBuffListString() {
		
		if (buffList.size() == 0) 
			return "";
		
		String result = "";
		for (int i = 0; i < buffList.size() - 1; i++)
			
			result += (Colors.colorOnForBuff(buffList.get(i).getName(), buffList.get(i).getType()) + "(" + buffList.get(i).getDuration() + "),  ");
		result += Colors.colorOnForBuff(buffList.get(buffList.size() - 1).getName(), buffList.get(buffList.size() - 1).getType()) + "(" + buffList.get(buffList.size() - 1).getDuration() + ")";
		
		return result;
	}
	
	public String getHandCardListString() {
		
		if (handCardList.size() == 0) 
			return "";
		
		String result = "";
		for (int i = 0; i < handCardList.size() - 1; i++)
			result += ((i + 1) + ") <" + Colors.colorOnForCardCost(handCardList.get(i)) + "> " + Colors.colorOnForCardName(handCardList.get(i)) + ",  ");
			result += (handCardList.size() + ") <" + Colors.colorOnForCardCost(handCardList.get(handCardList.size() - 1)) + "> " + Colors.colorOnForCardName(handCardList.get(handCardList.size() - 1)));
		
		return result;
	}

	public ArrayList<Card> getPlayerDeck() {
		return drawCardList;
	}

	public void showDeck() {
		int cardNum = drawCardList.size();

		//sort
		Map<Integer, List<Card>> costBuckets = new TreeMap<>();

		for (int cost = 0; cost <= 4; cost++) {
			costBuckets.put(cost, new ArrayList<>());
		}
		for (Card card : drawCardList) {
			int cost = card.getCost();
			if (costBuckets.containsKey(cost)) {
				costBuckets.get(cost).add(card);
			}else{
				costBuckets.get(4).add(card);
			}
		}

		for (List<Card> bucket : costBuckets.values()) {
			bucket.sort((card1, card2) -> card1.getName().compareTo(card2.getName()));
		}

		//layout
		System.out.println(Main.longLine);
		System.out.println(" Your Deck (" + cardNum + " cards) :");
		System.out.println(Main.longLine);
		
		int index = 1;
		for (Map.Entry<Integer, List<Card>> entry : costBuckets.entrySet()) {
			int cost = entry.getKey();
			List<Card> bucket = entry.getValue();
        
            System.out.println(" [" + cost + " Cost Cards]");
            
            for (Card card : bucket) {
				TextDisplay.printLineWithDelay("   " + index++ + ") " + " <" + Colors.colorOnForCardCost(card) + "> [" + Colors.colorOnForCardRarity(card) + "] " + Colors.colorOnForCardName(card) + "  -" + Colors.colorOnForCardInfo(card) , 50);
            }
            System.out.println();
        }

		System.out.println(Main.longLine);
	}

	public Map<Integer, Card> showDeckWithIndex() {
		int cardNum = drawCardList.size();
		Map<Integer, Card> indexToCardMap = new HashMap<>();
		
		//sort
		Map<Integer, List<Card>> costBuckets = new TreeMap<>();

		for (int cost = 0; cost <= 4; cost++) {
			costBuckets.put(cost, new ArrayList<>());
		}
		for (Card card : drawCardList) {
			int cost = card.getCost();
			if (costBuckets.containsKey(cost)) {
				costBuckets.get(cost).add(card);
			} else {
				costBuckets.get(4).add(card);
			}
		}

		for (List<Card> bucket : costBuckets.values()) {
			bucket.sort((card1, card2) -> card1.getName().compareTo(card2.getName()));
		}

		//layout
		System.out.println(Main.longLine);
		System.out.println(" Your Deck (" + cardNum + " cards) :");
		System.out.println(Main.longLine);

		int index = 1;
		for (Map.Entry<Integer, List<Card>> entry : costBuckets.entrySet()) {
			int cost = entry.getKey();
			List<Card> bucket = entry.getValue();
			
			if (!bucket.isEmpty()) {
				System.out.println(" [" + cost + " Cost Cards]");
				
				for (Card card : bucket) {
					indexToCardMap.put(index, card);
					TextDisplay.printLineWithDelay("   " + index++ + ") " + " <" + Colors.colorOnForCardCost(card) + "> [" + Colors.colorOnForCardRarity(card) + "] " + Colors.colorOnForCardName(card) + "  -" + Colors.colorOnForCardInfo(card) , 50);
				}
				System.out.println();
			}
		}

		System.out.println(Main.longLine);
		return indexToCardMap;
	}

	public void addCardToDeck(Card card) {
		this.drawCardList.add(card);
	}

	public void lostColors(){
		this.colorViews = false;
	}

	public boolean getColorViewStatus(){
		return this.colorViews;
	}
}




