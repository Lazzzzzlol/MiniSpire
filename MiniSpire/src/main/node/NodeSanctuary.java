package main.node;

import main.Main;
import main.Util;
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
			player.addHp(heal);
			hasHealed = true;
		}
	}

	@Override
	public void onDraw() {
		System.out.println(Main.longLine);
		Util.printBlankLines(1);
		System.out.println(" [Sanctuary] You feel restored. Choose a path:");
		System.out.println("   c 1) Play Safe     : +20 Max HP, heal 20 HP");
		System.out.println("   c 2) Play Strategy : +1 draw per turn, +1 max action point");
		System.out.println("   c 3) Play Risk     : -15 Max HP, +2 max action points");
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
				player.changeMaxHp(20);
				player.addHp(20);
				System.out.println(" >> Chosen: Play Safe.");
				break;
			case "2":
				player.changeDrawCardNumPerTurn(1);
				player.changeMaxActionPoints(1);
				System.out.println(" >> Chosen: Play Strategy.");
				break;
			case "3":
				player.changeMaxHp(-15);
				player.changeMaxActionPoints(2);
				System.out.println(" >> Chosen: Play Risk.");
				break;
			default:
				return;
		}
		hasChosen = true;
		Game.getInstance().setIsEndTurn(true);
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
