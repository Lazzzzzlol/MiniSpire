package main.node;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import main.Main;
import main.ScoreCalculator;
import main.Util;
import main.card.Card;
import main.enemy.Enemy;
import main.game.Game;
import main.player.Player;
import main.resourceFactory.EnemyFactory;
import main.resourceFactory.CardFactory;

public class NodeBattle extends Node {
	
	private String enemyType;
	private Enemy enemy;

	private boolean isWin;
	private ArrayList<Card> rewardCardList;
	
	public NodeBattle(String enemyType) {
		super("Battle");
		this.enemyType = enemyType;
		enemy = setEnemy(enemyType);
		isWin = false;
		rewardCardList = new ArrayList<>();
		// reset per-battle state on all cards (Upheaval and any future cards that need it)
		CardFactory.getInstance().resetCardsForNewBattle();
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

		if (isWin)
			return;
		
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
		
		System.out.println(" [Status:   HP: " + player.getHp() + "/" + player.getMaxHp() + 
				"   Action points: " + player.getActionPoints() + "/" + player.getMaxActionPoints() + "   ]");
		System.out.println(" [Buff: " + player.getBuffListString() + "]");
		System.out.println();
		System.out.println(" [Card: " + player.getHandCardListString() + "]");
		
		System.out.println(Main.longLine);
		
		System.out.print("Action >> ");
	}
	
	@Override
	public void onInput(String input) {
		
		Player player = Player.getInstance();
		String[] parts = input.split(" ");
		
		switch (parts[0]) {
		
			case "p":
				playcard(Integer.parseInt(parts[1]));

				if (enemy.getHp() <= 0)
					Main.executor.schedule(() -> {
						onWin();
					}, 2, TimeUnit.SECONDS);
				break;
				
			case "i":
				Card card = player.getHandCardList().get(Integer.parseInt(parts[1]) - 1);
				System.out.println(" >> " + card.getInfo());

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
		
		if (parts[0].equals("p") || parts[0].equals("i")) {
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
		
		enemy.onMove();
		enemy.onEndTurn();
		player.onEndTurn();
	}

	public void onWin(){

		isWin = true;

		int gainedGold;
		gainedGold = 70 + Main.random.nextInt(16);
		if (enemyType.equals("elite"))
			gainedGold += (20 + Main.random.nextInt(6));
		
		// 计算并添加得分
		ScoreCalculator scoreCalculator = ScoreCalculator.getInstance();
		int gainedScore = scoreCalculator.calculateBattleScore(enemy, enemyType);
		Card rewardCard1 = CardFactory.getInstance().getRandomCard();
		Card rewardCard2 = CardFactory.getInstance().getRandomCard();
		Card rewardCard3 = CardFactory.getInstance().getRandomCard();

		Util.printBlankLines(3);
		System.out.println(Main.longLine);

		System.out.println(" >> You defeated " + enemy.getName() + "!");
		Player.getInstance().addGold(gainedGold);
		scoreCalculator.addScore(gainedScore);

		System.out.println();
		System.out.println(" >> Choose a reward card:");
		System.out.println( " >> 1-" + rewardCard1.getName() + "(" + rewardCard1.getCost() + "), " + 
								"2-" + rewardCard2.getName() + "(" + rewardCard2.getCost() + "), " + 
								"3-" + rewardCard3.getName() + "(" + rewardCard3.getCost() + ")");
		System.out.println(Main.longLine);

		System.out.print(" Action >>");

	}
	
	//这边set可能打错了，可能是get
	private Enemy setEnemy(String enemyType) {
		return EnemyFactory.getRandomEnemy(enemyType);
	}
	
	public String getEnemyType() {
		return enemyType;
	}

	public void setIsWin(boolean isWin){
		this.isWin = isWin;
	}

	public boolean getIsWin(){
		return isWin;
	}

	public ArrayList<Card> getRewardCardList(){
		return rewardCardList;
	}
}
