package main.node;

import java.util.concurrent.TimeUnit;

import main.Colors;
import main.Main;
import main.TextDisplay;
import main.Util;
import main.card.Card;
import main.processor.HealProcessor;
import main.resourceFactory.CardFactory;
import main.game.Game;
import main.player.Player;

public class NodeSanctuary extends Node {
	
	private String name = "Sanctuary";
	private boolean hasHealed;
	private boolean hasChosen;
	
	public NodeSanctuary() {
		super("Sanctuary");
		hasHealed = false;
		hasChosen = false;
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void onStartTurn() {
		Player player = Player.getInstance();
		
		if (!hasHealed) {
			int heal = (int)Math.floor(player.getMaxHp() * 0.3);
			Main.executor.schedule(() -> {
			HealProcessor.applyHeal(player, heal, null);
			hasHealed = true;
			}, 1500, TimeUnit.MILLISECONDS);
		}

		int getCard30RandNum = Main.random.nextInt(10);
		switch (getCard30RandNum) {
			case 0:
				Card card30 = CardFactory.getInstance().createCard(30);
				player.addCardToDeck(card30);
				TextDisplay.printCharWithDelay("A flicker of light from the campfire's ashes catches your eye. There's something there.",10);
				TextDisplay.printCharWithDelay("It's a... vibrantly colorful card. Yet when you look directly at it, a sharp pain stings your eyes. It seems as though everything around the card is losing its color.", 5);
				TextDisplay.printLineWithDelay(" >> Added " + Colors.colorOnForCardName(card30) + " to your deck!", 100);
				break;
			default:
				break;
		}
	}

	@Override
	public void onDraw() {
		System.out.println(Main.longLine);
		Util.printBlankLines(1);
		TextDisplay.printCharWithDelay(" [Sanctuary] You feel restored. Choose a path:", 10);
		TextDisplay.printLineWithDelay("   c 1) Play Safe     : +20 Max HP, heal 35 HP", 150);
		TextDisplay.printLineWithDelay("   c 2) Play Strategy : +1 draw per turn, +1 max action point", 150);
		TextDisplay.printLineWithDelay("   c 3) Play Risk     : -15 Max HP, +3 max action points", 150);
		Util.printBlankLines(1);
		System.out.println(Main.longLine);
		System.out.print("Action >> ");
	}

	@Override
	public void onInput(String input) {
		if (input == null) return;
		String[] parts = input.split(" ");
		if (parts.length != 2) return;
		
		if (!parts[0].equals("c")) return;
		
		Player player = Player.getInstance();
		switch (parts[1]) {
			case "1":
				TextDisplay.printCharWithDelay(" >> Chosen: Play Safe.", 30);
				player.changeMaxHp(20);
				HealProcessor.applyHeal(player, 35, 0);
				break;
			case "2":
				TextDisplay.printCharWithDelay(" >> Chosen: Play Strategy.", 30);
				player.changeDrawCardNumPerTurn(1);
				player.changeMaxActionPoints(1);
				break;
			case "3":
				TextDisplay.printCharWithDelay(" >> Chosen: Play Risk.", 30);
				player.changeMaxHp(-15);
				player.changeMaxActionPoints(3);
				break;
			default:
				return;
		}
		hasChosen = true;
		Game.getInstance().advanceToNextNode();
	}

	@Override
	public boolean isValidInput(String input) {

		if (input == null) return false;
		
		String[] parts = input.split(" ");
		if (parts.length != 2) return false;

		if (!parts[0].equals("c")) return false;

		return parts[1].equals("1") || parts[1].equals("2") || parts[1].equals("3");

	}

	public String getName(){
		return name;
	}

	public Boolean getHasChosen(){
		return hasChosen;
	}
}