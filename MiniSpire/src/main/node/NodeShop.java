package main.node;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import main.Main;
import main.Util;
import main.card.Card;
import main.game.Game;
import main.player.Player;
import main.resourceFactory.CardFactory;

public class NodeShop extends Node {

	private String name = "Shop";
	private Card[] availableCards;
	private int refreshCount;
	private int currentRefreshCost;
	private boolean isTripleCards;    
	private boolean isRemoved; 
	private boolean Welcoming; 
	Player player = Player.getInstance();
	ArrayList<Card> deck = player.getPlayerDeck();
	CardFactory cf = CardFactory.getInstance();
	Set<String> validCommands = Set.of("b", "r", "c", "l");

	private static final int BASE_REFRESH_COST = 20;
	private static final int REFRESH_COST_INCREASE = 10;
	private static final int MAX_REFRESH_TIMES = 3;

	private static final int REMOVE_CARD_COST = 40;

	public NodeShop() {
		super("Shop");
		this.availableCards = new Card[3];
		this.refreshCount = 0;
		this.currentRefreshCost = BASE_REFRESH_COST;
		this.isTripleCards = false;
		this.isRemoved = false;
		this.Welcoming = false;

		generateRandomCards();
	}

	@Override
	public void onUpdate() {
	}
	@Override
	public void onStartTurn() {

	}

	@Override
	public void onDraw() {
		System.out.println(Main.longLine);
		Util.printBlankLines(1);
		if (!Welcoming){
			Welcoming = true;
			System.out.println(" [" + name + "] In this land of chaos, you suddenly see bright lights flickering in the distance...");
			System.out.println(" [Merchant] Ah, traveler! Care for some supplies? All this could be yours... for the right price.");
		}
		drawCommonView();
	}

	@Override
	public void onInput(String input) {
		if (input == null) return;
		String[] parts = input.split(" ");
		
		if (!validCommands.contains(parts[0])) {
			return;
		}
		
			if (parts[0].equals("r")){
				switch (parts[1]){
					case "1":
						System.out.println(" >> Chosen: Refesh merchant's stock.");
						refreshShop();
						break;
					case "2":
						System.out.println(" >> Chosen: Remove one card.");
						removeCardFromDeck();
						break;
				}
			}else if (parts[0].equals("b")){
				switch (parts[1]) {
					case "1":
						buyCard(1);
						break;
					case "2":
						buyCard(2);
						break;
					case "3":
						buyCard(3);
						break;
				}
			}else if (parts[0].equals("c")){
				switch (parts[1]) {
					case "1":
						System.out.println(" >> Chosen: Check your deck.");
						showDeck();
						break;
				}
			}else if (parts[0].equals("l")){
				switch (parts[1]) {
					case "1":
						System.out.println(" >> [Merchant] Bye for now, hope luck smiles on you.");
						Game.getInstance().advanceToNextNode();
				}
			}
	}

	@Override
	public boolean isValidInput(String input) {
		if (input == null) return false;
		String[] parts = input.split(" ");
		if (parts.length == 0) return false;
		
		String command = parts[0].toLowerCase();
		return command.equals("b") || command.equals("r") || command.equals("c") || command.equals("l");
	}


	private void generateRandomCards() {
		isTripleCards = false;
		for (int i = 0; i < availableCards.length; i++) {
			availableCards[i] = null;
		}
		for (int i = 0; i < availableCards.length; i++) {
			Card card = cf.getRandomCard();
			availableCards[i] = card;
		}
		
		checkForTripleCards();
	}

	private void checkForTripleCards() {
		if (availableCards.length == 3) {
			Card card1 = availableCards[0];
			Card card2 = availableCards[1];
			Card card3 = availableCards[2];
			
			if (card1.getName().equals(card2.getName()) && 
				card2.getName().equals(card3.getName())) {
				isTripleCards = true;
				System.out.println(" >> Special Offer! Triple cards found - all cards are FREE!");
			}
		}
	}

	private void displayAvailableCards() {
		System.out.println(" Cards on sale:");
		for (int i = 0; i < availableCards.length; i++) {
			if (availableCards[i] == null) {
				System.out.println("   " + (i + 1) + ") [SOLD OUT]");
			} else {
				Card card = availableCards[i];
				System.out.println("   " + (i + 1) + ") " + " [" + card.getRarity() + "] <" + card.getCost() + "> "+ card.getName() + "  -" + card.getInfo() );
				if (isTripleCards){
					System.out.println("   Cost: 0");
				}else{
					System.out.println("   Cost: " + getCardCost(card));
				}
			}
			System.out.println();
		}
	}

	private int getCardCost(Card card) {
		String rarity = card.getRarity();
		switch (rarity) {
			case "normal":
				return 30;
			case "rare":
				return 60;
			case "epic":
				return 90;
			case "legendary":
				return 120;
			default:
				return 30;
		}
	}

	private void buyCard(int indexStr) {
		try {
			int index = indexStr - 1;
			if (index < 0 || index >= availableCards.length) {
				System.out.println(" >> Invalid card index. Choose 1, 2, or 3.");
				return;
			}
			
			if (availableCards[index] == null){
				System.out.println(" >> Sold out item!");
				return;
			}

			Card card = availableCards[index];
			int cost = isTripleCards ? 0 : getCardCost(card);
			
			if (player.getGold() >= cost) {
				player.lostGold(cost);
				player.addCardToDeck(card);
				
				availableCards[index] = null;
				
			} else {
				System.out.println(" >> Not enough gold! Need " + cost + " gold, but only have " + player.getGold() + ".");
			}
		} catch (NumberFormatException e) {
			System.out.println(" >> Invalid card index. Use numbers 1, 2, or 3.");
		}
	}

	private void showDeck() {
		player.showDeck();
	}

	private void removeCardFromDeck() {
		if (player.getGold() < REMOVE_CARD_COST) {
			System.out.println(" >> Not enough gold! Need " + REMOVE_CARD_COST + " gold, but only have " + player.getGold() + ".");
			return;
		}
		if (isRemoved){
			System.out.println(" >> You've already removed one card, don't be so greedy...");
			return;
		}
		
		Map<Integer, Card> indexToCardMap = player.showDeckWithIndex();
		System.out.println("Enter the number of the card you want to remove, or 'cancel':");
    	System.out.print("Remove >> ");
		
		String input = Main.scanner.nextLine().trim();
		
		if (input.equalsIgnoreCase("cancel")) {
			System.out.println(" >> Removal cancelled.");
			return;
		}
		
		try {
			int selectedIndex = Integer.parseInt(input);
			
			if (indexToCardMap.containsKey(selectedIndex)) {
				Card cardToRemove = indexToCardMap.get(selectedIndex);
				
				boolean removed = deck.remove(cardToRemove);
				
				if (removed) {
					player.lostGold(REMOVE_CARD_COST);
					System.out.println(" >> Removed: " + cardToRemove.getName() + " for " + REMOVE_CARD_COST + " gold.");
					this.isRemoved = true;
				} else {
					System.out.println(" >> Failed to remove the card. Please try again.");
				}
			} else {
				System.out.println(" >> Invalid card number. Please select a number from the list.");
			}
		} catch (NumberFormatException e) {
			System.out.println(" >> Invalid input. Please enter a number or 'cancel'.");
		}
	}

	private void refreshShop() {
		if (refreshCount == MAX_REFRESH_TIMES) {
			System.out.println(" >> No more refreshes left in this shop!");
			return;
		}
		
		Player player = Player.getInstance();
		if (player.getGold() < currentRefreshCost) {
			System.out.println(" >> Not enough gold! Need " + currentRefreshCost + " gold, but only have " + player.getGold() + ".");
			return;
		}
		
		player.lostGold(currentRefreshCost);
		refreshCount++;
		
		generateRandomCards();
		System.out.println(" >> Shop refreshed! Cost: " + currentRefreshCost + " gold.");
		displayAvailableCards();
		
		currentRefreshCost += REFRESH_COST_INCREASE;
		
		if (refreshCount < MAX_REFRESH_TIMES) {
			System.out.println(" >> You have " + (MAX_REFRESH_TIMES-refreshCount) + " more chance to refresh cards...");
		}else{
			System.out.println(" >> You have no more chance to refresh cards in this shop!");
		}
	}

	private void drawCommonView() {
		displayAvailableCards();
		System.out.println("   b 1) Buy card 1    : The cards lay quietly on the merchant's wooden table, bathed in a tempting luster...");
		System.out.println("   b 2) Buy card 2    : The cards lay quietly on the merchant's wooden table, bathed in a tempting luster...");
		System.out.println("   b 3) Buy card 3    : The cards lay quietly on the merchant's wooden table, bathed in a tempting luster...");
		System.out.println("   r 1)   Refresh!    : Give the slot machine a spin! It can refresh the merchant's stock. If you're lucky, you might win it all for free!");
		System.out.println("   r 2)    Remove!    : Feeling overloaded? Here's your one-time chance to destroy a card from your deck.");
		System.out.println("   c 1)     Check!    : Check your deck. Think carefully before you decide.");
		System.out.println("   l 1)      Leave    : Gook luck on you...");
		System.out.println(Main.longLine);
		System.out.println("Your gold sack: " + player.getGold());
		System.out.print("Action >> ");
	}
}
