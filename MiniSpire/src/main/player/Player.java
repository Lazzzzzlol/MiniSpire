package main.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import main.Main;
import main.buff.Buff;
import main.card.Card;
import main.card.attackCard.AttackCard;
import main.card.effectCard.EffectCard;
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
	
	private ArrayList<Card> handCardList;
	private ArrayList<Card> drawCardList;
	private ArrayList<Card> discardCardList;
	
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
		
		this.handCardList = new ArrayList<Card>();
		this.drawCardList = new ArrayList<Card>();
		this.discardCardList = new ArrayList<Card>();
		
		this.buffList = new ArrayList<Buff>();
		//this.abilityList = new ArrayList<Ability>();
	}
	
	public void drawCardListInit() {
		
		this.drawCardList.clear();
		this.drawCardList = CardFactory.getInstance().getInitialDrawCardList(this.drawCardList);
		Collections.shuffle(this.drawCardList);
	}
	
	public void onStartTurn() {
		this.actionPoints = this.maxActionPoints;
		drawHandCards(drawCardNumPerTurn);
	}
	
	public void drawHandCards(int num) {
		
		if (drawCardList.size() < num) {
			
			int needToDrawNum = num - drawCardList.size();
			handCardList.addAll(drawCardList);
			drawCardList.clear();
			
			Collections.shuffle(discardCardList);
			drawCardList.addAll(discardCardList);
			discardCardList.clear();
			
			for (int i = 0; i < needToDrawNum; i++) {
				handCardList.add(drawCardList.get(0));
				drawCardList.remove(0);
			}
		} else {
			for (int i = 0; i < num; i++) {
				handCardList.add(drawCardList.get(0));
				drawCardList.remove(0);
			}
		}
		
	}
	
	public void playCard(int cardIndex, Enemy enemy) {
		
		Card cardToPlay = handCardList.get(cardIndex - 1);
		
		if (!cardToPlay.getCanPlay()) {
			System.out.println(" >> Unplayable card type.");
			return;
		}
		
		if (cardToPlay.getCost() <= actionPoints) {
			actionPoints -= cardToPlay.getCost();
			
			switch (cardToPlay.getType()) {
			
				case "Attack":
					((AttackCard)cardToPlay).onPlay(this, enemy);
					System.out.println(" >> Played card: " + cardToPlay.getName());
					break;
			
				case "Effect":
					((EffectCard)cardToPlay).onUse(this, enemy);
					System.out.println(" >> Played card: " + cardToPlay.getName());
					break;
					
				default:
					System.out.println(" >> Unknown or unplayable card type.");
					break;
			}
			handCardList.remove(cardIndex - 1);
			discardCardList.add(cardToPlay);
		} else {
			System.out.println(" >> Not enough action points to play this card.");
		}
	}
	
	public void onEndTurn() {
		
		discardCardList.addAll(handCardList);
		handCardList.clear();
		
		if (buffList != null) {
			for (Buff buff : buffList) {
				buff.onEndTurn();
				
				// Handle Recovering - heal equal to remaining duration
				if (buff.getName().equals("Recovering")) {
					int duration = buff.getDuration();
					if (duration > 0) {
						addHp(duration);
					}
				}
				
				// Handle Enshroud - reduce duration when healing
				// (This is handled in addHp, but we check here too)
			}
	        buffList.removeIf(buff -> buff.getDuration() <= 0);
	    }
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
			System.out.println(" >> Max HP increased by " + delta + ".");
		} else {
			System.out.println(" >> Max HP decreased by " + (-delta) + ".");
		}
	}
	
	public void addHp(int heal) {
		
		// Check for Lost - prevent healing
		boolean hasLost = false;
		for (Buff buff : buffList) {
			if (buff.getName().equals("Lost")) {
				hasLost = true;
				break;
			}
		}
		
		if (hasLost) {
			System.out.println(" >> Lost: Cannot heal!");
			return;
		}
		
		// Reduce Enshroud duration when healing (1 turn per heal point, but at least 1)
		for (Buff buff : buffList) {
			if (buff.getName().equals("Enshroud") && heal > 0) {
				int reduction = Math.max(1, heal);
				buff.extendDuration(-reduction);
				if (buff.getDuration() <= 0) {
					System.out.println(" >> Enshroud removed by healing!");
				}
				break;
			}
		}
		
		this.hp += heal;
		if (this.hp > this.maxHp)
			this.hp = this.maxHp;
		
		//Main.executor.schedule(() -> {
			System.out.println(" >> Healed " + heal + " HP.");
		//}, 1, TimeUnit.SECONDS);
	}
	
	public void deductHp(int damage) {
		
		// Check for Blessed - prevent fatal damage once
		boolean hasBlessed = false;
		main.buff.oneFightBuff.BuffBlessed blessedBuff = null;
		for (Buff buff : buffList) {
			if (buff instanceof main.buff.oneFightBuff.BuffBlessed) {
				hasBlessed = true;
				blessedBuff = (main.buff.oneFightBuff.BuffBlessed) buff;
				break;
			}
		}
		
		if (hasBlessed && this.hp - damage <= 0 && blessedBuff != null) {
			// Prevent death, heal to half max HP, gain 1 turn Invincible
			this.hp = this.maxHp / 2;
			buffList.remove(blessedBuff);
			addBuff(new main.buff.positiveBuff.BuffInvincible(1), 1);
			System.out.println(" >> Blessed: Fatal damage prevented! Healed to " + this.hp + " HP and gained Invincible!");
			return;
		}
		
		this.hp -= damage;
		if (this.hp < 0)
			Game.getInstance().setIsGameOver(true);
		
		Main.executor.schedule(() -> {
			System.out.println(" >> Took " + damage + " damage.");
		}, 1, TimeUnit.SECONDS);
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
			System.out.println(" >> Max action points increased by " + delta + ".");
		} else {
			System.out.println(" >> Max action points decreased by " + (-delta) + ".");
		}
	}

	public void addGold(int gold) {
		this.gold += gold;
		this.totalGold += gold;
		System.out.println(" >> Gained " + gold + " gold.");
		System.out.println((" >> Current gold: " + this.gold));
	}
	
	public int getGold() {
		return gold;
	}
	
	public int getTotalGold() {
		return totalGold;
	}

	public void lostGold(int cost) {
		this.gold -= cost;
		System.out.println(" >> Lost " + gold + " gold.");
		System.out.println((" >> Current gold: " + this.gold));
	}
	
	public void changeDrawCardNumPerTurn(int delta) {
		this.drawCardNumPerTurn += delta;
		if (this.drawCardNumPerTurn < 1)
			this.drawCardNumPerTurn = 1;
		if (delta >= 0) {
			System.out.println(" >> Cards drawn per turn increased by " + delta + ".");
		} else {
			System.out.println(" >> Cards drawn per turn decreased by " + (-delta) + ".");
		}
	}
	
	public void addBuff(Buff buff, int duration) {
		
		for (Buff existBuff : buffList)
			if (existBuff.getName().equals(buff.getName())) {
				existBuff.extendDuration(duration);
				return;
			}
				
		buffList.add(buff);
		System.out.println(" >> Gained buff: " + buff.getName());
	}
	
	public ArrayList<Buff> getBuffList() {
		return buffList;
	}

	public ArrayList<Card> getHandCardList() {
		return handCardList;
	}
	
	public void clearHandCards(){
		discardCardList.addAll(handCardList);
		handCardList.clear();
	}
	
	public String getBuffListString() {
		
		if (buffList.size() == 0) 
			return "";
		
		String result = "";
		for (int i = 0; i < buffList.size() - 1; i++)
			result += (buffList.get(i).getName() + "(" + buffList.get(i).getDuration() + "),  ");
		result += buffList.get(buffList.size() - 1).getName() + "(" + buffList.get(buffList.size() - 1).getDuration() + ")";
		
		return result;
	}
	
	public String getHandCardListString() {
		
		if (handCardList.size() == 0) 
			return "";
		
		String result = "";
		for (int i = 0; i < handCardList.size() - 1; i++)
			result += ((i + 1) + "-" + handCardList.get(i).getName() + "(" + handCardList.get(i).getCost() + "),  ");
		result += (handCardList.size() + "-" + handCardList.get(handCardList.size() - 1).getName() + 
				"(" + handCardList.get(handCardList.size() - 1).getCost() + ")");
		
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
				
                System.out.println("   " + index++ + ") " + " [" + card.getRarity() + "] <" + card.getCost() + "> "+ card.getName() + "  -" + card.getInfo() );
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
					System.out.println("   " + index + ") " + " [" + card.getRarity() + "] <" + card.getCost() + "> "+ card.getName() + "  -" + card.getInfo());
					index++;
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
}
