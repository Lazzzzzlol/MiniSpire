package main.node;

import main.Main;
import main.Util;
import main.enemy.Enemy;
import main.game.Game;
import main.player.Player;
import main.resourceFactory.EnemyFactory;

public class NodeBattle extends Node {
	
	private String enemyType;
	private Enemy enemy;
	
	public NodeBattle(String enemyType) {
		super("Battle");
		this.enemyType = enemyType;
		enemy = setEnemy(enemyType);
	}

	@Override
	public void onUpdate() {
		
		Game game = Game.getInstance();
		
		if (game.getIsEndTurn())
			onEndTurn();
	}
	
	@Override
	public void onStartTurn() {
		Player.getInstance().onStartTurn();
	}
	
	@Override
	public void onDraw() {
		
		Player player = Player.getInstance();
		
		System.out.println(Main.longLine);
		Util.printBlankLines(1);
		
		String enemyInfo = "-  " + enemy.getName() + " HP: " + enemy.getHp() + "  -";
		String enemyBuffList = enemy.getBuffListString();
		
		for (int i = 0; i < Util.getCenterAlignSpaceNum(enemyInfo, Main.longLine.length()); i++) 
			System.out.print(" ");
		System.out.println(enemyInfo);
		for (int i = 0; i < Util.getCenterAlignSpaceNum(enemyBuffList, Main.longLine.length()); i++) 
			System.out.print(" ");
		System.out.println(enemyBuffList);
		
		Util.printBlankLines(3);
		
		System.out.println(" [Your status :   HP: " + player.getHp() + "/" + player.getMaxHp() + 
				"   Action points: " + player.getActionPoints() + "/" + player.getMaxActionPoints() + "]");
		System.out.println(" [Your buff: " + player.getBuffListString() + "]");
		System.out.println();
		System.out.println(" [Your card: " + player.getHandCardListString() + "]");
		
		System.out.println(Main.longLine);
		
		System.out.print("Action >> ");
	}
	
	@Override
	public void onInput(String input) {
		
		String[] parts = input.split(" ");
		
		switch (parts[0]) {
		
			case "play":
				playcard(Integer.parseInt(parts[1]));
				break;
				
			default:
				break;
		}
		
		if (input.equals("end turn")) {
			Game.getInstance().setIsEndTurn(true);
			onEndTurn();
		}
	}
	
	@Override
	public boolean isValidInput(String input) {
		
		if (input == null) return false;
		String[] parts = input.split(" ");
		if (parts.length != 2) return false;
		
		if (parts[0].equals("play")) {
			try {
				int cardIndex = Integer.parseInt(parts[1]);
				Player player = Player.getInstance();
				
				if (cardIndex < 1 || cardIndex > player.getHandCardList().size())
					return false;
				
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		
		if (input.equals("end turn"))
			return true;
		
		return false;
	}
	
	private void playcard(int cardIndex) {
		
		Player player = Player.getInstance();
		
		player.playCard(cardIndex, enemy);
	}
	
	public void onEndTurn() {
		
		Player player = Player.getInstance();
		
		player.onEndTurn();
		enemy.onMove();
		enemy.onEndTurn();
	}
	
	private Enemy setEnemy(String enemyType) {
		return EnemyFactory.getRandomEnemy(enemyType);
	}
	
	public String getEnemyType() {
		return enemyType;
	}
}
