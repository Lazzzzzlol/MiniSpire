package main.player;

import java.util.ArrayList;
import java.util.Collections;
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
	//ArrayList<Ability> abilityList;
	
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
	
	private void drawHandCards(int num) {
		
		if (drawCardList.size() < num) {
			
			int needToDrawNum = num - drawCardList.size();
			handCardList.addAll(drawCardList);
			drawCardList.clear();
			
			Collections.shuffle(discardCardList);
			drawCardList.addAll(discardCardList);
			
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
			System.out.println("Not enough action points to play this card.");
		}
	}
	
	public void onEndTurn() {
		
		discardCardList.addAll(handCardList);
		handCardList.clear();
		
		if (buffList != null) {
			for (Buff buff : buffList)
				buff.onEndTurn();
	        buffList.removeIf(buff -> buff.getDuration() <= 0);
	    }
	}

	public int getHp() {
		return hp;
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	
	public void addHp(int heal) {
		
		this.hp += heal;
		if (this.hp > this.maxHp)
			this.hp = this.maxHp;
		
		Main.executor.schedule(() -> {
			System.out.println(" >> Healed " + heal + " HP.");
		}, 1, TimeUnit.SECONDS);
	}
	
	public void deductHp(int damage) {
		
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

	public void addGold(int gold) {
		this.gold += gold;
		this.totalGold += gold;
		System.out.println(" >> Gained " + gold + " gold.");
	}
	
	public int getGold() {
		return gold;
	}
	
	public int getTotalGold() {
		return totalGold;
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

	public  ArrayList<Card> getHandCardList() {
		return handCardList;
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
			result += ((i + 1) + ":" + handCardList.get(i).getName() + "(" + handCardList.get(i).getCost() + "),  ");
		result += (handCardList.size() + ":" + handCardList.get(handCardList.size() - 1).getName() + 
				"(" + handCardList.get(handCardList.size() - 1).getCost() + ")");
		
		return result;
	}
}
