package main.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.Colors;
import main.Main;
import main.TextDisplay;
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
			TextDisplay.printCharWithDelay(" [" + name + "] In this land of chaos, you suddenly see bright lights flickering in the distance...", 10);
			TextDisplay.printCharWithDelay(" [Merchant] Ah, traveller! Care for some supplies? All this could be yours... for the right price.", 30);
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
						TextDisplay.printLineWithDelay(" >> Chosen: Refesh merchant's stock.",150);
						refreshShop();
						break;
					case "2":
						TextDisplay.printLineWithDelay(" >> Chosen: Remove one card.",150);
						removeCardFromDeck();
						break;
				}
			}else if (parts[0].equals("b")){
				switch (parts[1]) {
					case "1":
						TextDisplay.printLineWithDelay(" >> Chosen: Buy the leftmost card.",150);
						buyCard(1);
						break;
					case "2":
						TextDisplay.printLineWithDelay(" >> Chosen: Buy the middle card.",150);
						buyCard(2);
						break;
					case "3":
						TextDisplay.printLineWithDelay(" >> Chosen: Buy the rightmost card.",150);
						buyCard(3);
						break;
				}
			}else if (parts[0].equals("c")){
				switch (parts[1]) {
					case "1":
						TextDisplay.printLineWithDelay(" >> Chosen: Check your deck.",150);
						showDeck();
						break;
				}
			}else if (parts[0].equals("l")){
				switch (parts[1]) {
					case "1":
						TextDisplay.printCharWithDelay(" >> [Merchant] Bye for now, hope luck smiles on you.", 30);
						Game.getInstance().advanceToNextNode();
				}
			}
	}

	@Override
	public boolean isValidInput(String input) {

		if (input == null) return false;
		
		String[] parts = input.split(" ");
		if (parts.length != 2) return false;
		
		String command = parts[0].toLowerCase();
		String option = parts[1];
		
		if (!validCommands.contains(command)) {
			return false;
		}
		
		switch (command) {
			case "b":
				return option.equals("1") || option.equals("2") || option.equals("3");
			case "r":
				return option.equals("1") || option.equals("2");
			case "c":
				return option.equals("1");
			case "l":
				return option.equals("1");
			default:
				return false;
		}

	}

	private void generateRandomCards() {
		isTripleCards = false;

		for (int i = 0; i < availableCards.length; i++) {
			availableCards[i] = null;
		}
		
		Set<String> excludedNames = new HashSet<>();
		
		availableCards[0] = cf.getRandomCard();
		excludedNames.add(availableCards[0].getName());
		
		Card tempCard2 = cf.getRandomCard();
		if (excludedNames.contains(tempCard2.getName())) {
			availableCards[1] = cf.getRandomCardWithRarityFallback(excludedNames, tempCard2);
		} else {
			availableCards[1] = tempCard2;
		}
		excludedNames.add(availableCards[1].getName());
		
		Card tempCard3 = cf.getRandomCard();
		if (excludedNames.contains(tempCard3.getName())) {
			availableCards[2] = cf.getRandomCardWithRarityFallback(excludedNames, tempCard3);
		} else {
			availableCards[2] = tempCard3;
		}
		
		checkForTripleCards();
	}

	private void checkForTripleCards() {
		if (availableCards.length == 3) {
			Card card1 = availableCards[0];
			Card card2 = availableCards[1];
			Card card3 = availableCards[2];			
			
			if (card1.getRarity().equals(card2.getRarity()) && 
				card2.getRarity().equals(card3.getRarity())) {
				isTripleCards = true;
			}
		}
	}

	private void displayAvailableCards() {
		if (isTripleCards){
			Util.printBlankLines(1);
			TextDisplay.printLineWithDelay(Colors.getColorfulText(" >> Special Offer! Triple cards found - all cards are FREE!","rainbow"), 500);
		}
		
		Util.printBlankLines(1);
		TextDisplay.printLineWithDelay(" Cards on sale:", 200);

		Util.printBlankLines(1);

		for (int i = 0; i < availableCards.length; i++) {
			if (availableCards[i] == null) {

				TextDisplay.printLineWithDelay("   " + (i + 1) + ") [" + Colors.colorOnForSOLDOUT("SOLD OUT") + "]", 200);

			} else {

				Card card = availableCards[i];

				TextDisplay.printLineWithDelay("   " + (i + 1) + ") <" + Colors.colorOnForCardCost(card) + "> [" + Colors.colorOnForCardRarity(card) + "] " + Colors.colorOnForCardName(card),0);
				TextDisplay.printLineWithDelay("      " + Colors.colorOnForCardInfo(card),0);
				if (isTripleCards){
					TextDisplay.printLineWithDelay("      Cost: 0 G",0);
				}else{
					TextDisplay.printLineWithDelay("      Cost: " + getCardCost(card) + " G",0);
				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
				return 50;
			case "epic":
				return 80;
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
				TextDisplay.printLineWithDelay(" >> Invalid card index. Choose 1, 2, or 3.",150);
				return;
			}
			
			if (availableCards[index] == null){
				TextDisplay.printLineWithDelay(" >> Sold out item!",150);
				return;
			}

			Card card = availableCards[index];
			int cost = isTripleCards ? 0 : getCardCost(card);
			
			if (player.getGold() >= cost) {
				player.lostGold(cost);
				player.addCardToDeck(card);
				
				availableCards[index] = null;
				
			} else {
				TextDisplay.printLineWithDelay(" >> Not enough gold! Need " + cost + " gold, but only have " + player.getGold() + ".",150);
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
			TextDisplay.printLineWithDelay(" >> Not enough gold! Need " + REMOVE_CARD_COST + " gold, but only have " + player.getGold() + ".",150);
			return;
		}
		if (isRemoved){
			TextDisplay.printLineWithDelay(" >> You've already removed one card, don't be so greedy...",150);
			return;
		}
		
		Map<Integer, Card> indexToCardMap = player.showDeckWithIndex();
		TextDisplay.printLineWithDelay("Enter the number of the card you want to remove, or 'cancel':",150);
    	System.out.print("Remove >> ");
		
		String input = Main.scanner.nextLine().trim();
		
		if (input.equalsIgnoreCase("cancel")) {
			TextDisplay.printLineWithDelay(" >> Removal cancelled.",150);
			return;
		}
		
		try {
			int selectedIndex = Integer.parseInt(input);
			
			if (indexToCardMap.containsKey(selectedIndex)) {
				Card cardToRemove = indexToCardMap.get(selectedIndex);
				
				boolean removed = deck.remove(cardToRemove);
				
				if (removed) {
					player.lostGold(REMOVE_CARD_COST);
					TextDisplay.printLineWithDelay(" >> Removed: " + Colors.colorOnForCardName(cardToRemove) + " for " + REMOVE_CARD_COST + " gold.",150);
					this.isRemoved = true;
				} else {
					TextDisplay.printLineWithDelay(" >> Failed to remove the card. Please try again.",150);
				}
			} else {
				TextDisplay.printLineWithDelay(" >> Invalid card number. Please select a number from the list.",150);
			}
		} catch (NumberFormatException e) {
			TextDisplay.printLineWithDelay(" >> Invalid input. Please enter a number or 'cancel'.",150);
		}
	}

	private void refreshShop() {
		if (refreshCount == MAX_REFRESH_TIMES) {
			TextDisplay.printLineWithDelay(" >> No more refreshes left in this shop!",150);
			return;
		}
		
		Player player = Player.getInstance();
		if (player.getGold() < currentRefreshCost) {
			TextDisplay.printLineWithDelay(" >> Not enough gold! Need " + currentRefreshCost + " gold, but only have " + player.getGold() + " G.",150);
			return;
		}
		
		player.lostGold(currentRefreshCost);
		refreshCount++;
		
		generateRandomCards();
		TextDisplay.printLineWithDelay(" >> Shop refreshed! Cost: " + currentRefreshCost + " gold.", 150);
		
		currentRefreshCost += REFRESH_COST_INCREASE;
		
		if (refreshCount < MAX_REFRESH_TIMES) {
			TextDisplay.printLineWithDelay(" >> You have " + (MAX_REFRESH_TIMES-refreshCount) + " more chance to refresh cards...", 150);
		}else{
			TextDisplay.printLineWithDelay(" >> You have no more chance to refresh cards in this shop!", 150);
		}
	}

	private void drawCommonView() {
		displayAvailableCards();
		
		TextDisplay.printLineWithDelay("   b 1) Buy card 1    : The cards lay quietly on the merchant's wooden table, bathed in a tempting luster...", 150);
		TextDisplay.printLineWithDelay("   b 2) Buy card 2    : The cards lay quietly on the merchant's wooden table, bathed in a tempting luster...", 150);
		TextDisplay.printLineWithDelay("   b 3) Buy card 3    : The cards lay quietly on the merchant's wooden table, bathed in a tempting luster...", 150);
		TextDisplay.printLineWithDelay("   r 1)   Refresh!    : Give the slot machine a spin! It can refresh the merchant's stock. If you're lucky, you might win it all for free!", 150);
		TextDisplay.printLineWithDelay("   r 2)    Remove!    : Feeling overloaded? Here's your one-time chance to destroy a card from your deck.", 150);
		TextDisplay.printLineWithDelay("   c 1)     Check!    : Check your deck. Think carefully before you decide.", 150);
		TextDisplay.printLineWithDelay("   l 1)      Leave    : Good luck on you...", 150);
		Util.printBlankLines(1);

		System.out.println(Main.longLine);
		System.out.println("Your gold sack: " + player.getGold() + " G");
		System.out.print("Action >> ");
	}
}
