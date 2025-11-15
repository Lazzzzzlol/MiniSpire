package main.node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import main.Colors;
import main.Main;
import main.ScoreCalculator;
import main.Util;
import main.card.Card;
import main.card.passiveCard.PassiveCard;
import main.enemy.Enemy;
import main.game.Game;
import main.player.Player;
import main.resourceFactory.EnemyFactory;
import main.resourceFactory.CardFactory;

public class NodeBattle extends Node {
	
	protected String enemyType;
	protected Enemy enemy;

	protected boolean isWin;
	private Card rewardCard1;
	private Card rewardCard2;
	private Card rewardCard3;
	
	public NodeBattle(String enemyType) {
		super("Battle");
		this.enemyType = enemyType;
		enemy = setEnemy(enemyType);
		isWin = false;

		Set<String> excludedNames = new HashSet<>();
    
		rewardCard1 = CardFactory.getInstance().getRandomCard();
		excludedNames.add(rewardCard1.getName());
		
		Card tempCard2 = CardFactory.getInstance().getRandomCard();
		if (excludedNames.contains(tempCard2.getName())) {
			rewardCard2 = CardFactory.getInstance().getRandomCardWithRarityFallback(excludedNames, tempCard2);
		} else {
			rewardCard2 = tempCard2;
		}
		excludedNames.add(rewardCard2.getName());
		
		Card tempCard3 = CardFactory.getInstance().getRandomCard();
		if (excludedNames.contains(tempCard3.getName())) {
			rewardCard3 = CardFactory.getInstance().getRandomCardWithRarityFallback(excludedNames, tempCard3);
		} else {
			rewardCard3 = tempCard3;
		}

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

		Player player = Player.getInstance();
		player.onStartTurn();

		// 检查敌人是否有 Steelsoul buff，如果有则扣除玩家行动点
		if (enemy != null) {
			for (main.buff.Buff buff : enemy.getBuffList()) {
				if ("Steelsoul".equals(buff.getName()) && buff instanceof main.buff.positiveBuff.BuffSteelsoul) {
					main.buff.positiveBuff.BuffSteelsoul steelsoul = (main.buff.positiveBuff.BuffSteelsoul) buff;
					int actionPointsToDeduct = steelsoul.getActionPointsToDeduct();
					if (actionPointsToDeduct > 0) {
						player.changeCurrentActionPoint(-actionPointsToDeduct);
						Main.executor.schedule(() -> {
							System.out.println(" >> Steelsoul deducts " + actionPointsToDeduct + " action points!");
						}, 1, TimeUnit.SECONDS);
						// 扣除后重置，避免重复扣除
						steelsoul.setActionPointsToDeduct(0);
					}
					break; // 只处理第一个 Steelsoul buff
				}
			}
		}

		/*boolean playerHasRecovering = player.getBuffList().stream()
                .anyMatch(buff -> "Recovering".equals(buff.getName()));

		if (playerHasRecovering) {
			Buff recoveringBuff = null;
			for (Buff buff : player.getBuffList()) {
				if (buff.getName().equals("Recovering")) {
					recoveringBuff = buff;
					break;
				}
        	}
			HealProcessor.applyHeal(player, recoveringBuff.getDuration());
		}*/
	}
	
	@Override
	public void onDraw() {

		if (isWin)
			return;
		
		Player player = Player.getInstance();
		
		System.out.println(Main.longLine);
		Util.printBlankLines(1);

		String enemyLeftPattern = getEnemyLeftPattern();
		String enemyRightPattern = getEnemyRightPattern();
		
		String enemyInfo = enemyLeftPattern + enemy.getName() + " HP: " + enemy.getHp() + enemyRightPattern;
		String enemyInfoWithColor = enemyLeftPattern + Colors.colorOnForEnemyName(enemy.getName(), enemy.getType())
			+ " HP: " + Colors.colorOnForHP(enemy.getHp(), enemy.getInitialHp()) + enemyRightPattern;

		String enemyBuffList = enemy.getBuffListString();
		String enemyBuffListWithColor = enemy.getBuffListStringWithColor();

		String enemySpecialContainer = "";
		if (enemy.getHasSpecialContainer())
			enemySpecialContainer = enemy.getSpecialContainerString();
		
		for (int i = 0; i < Util.getCenterAlignSpaceNum(enemyInfo, Main.longLine.length()); i++) 
			System.out.print(" ");
		System.out.println(enemyInfoWithColor);
		for (int i = 0; i < Util.getCenterAlignSpaceNum(enemyBuffList, Main.longLine.length()); i++) 
			System.out.print(" ");
		System.out.println(enemyBuffListWithColor);
		if (!enemySpecialContainer.equals("")){
			for (int i = 0; i < Util.getCenterAlignSpaceNum(enemySpecialContainer, Main.longLine.length()); i++) 
				System.out.print(" ");
			System.out.println(enemySpecialContainer);
		}
		
		Util.printBlankLines(1);
		
		System.out.println(" [Status:   HP: " + Colors.colorOnForHP(player.getHp(), player.getMaxHp()) + "/" + player.getMaxHp() + 
				"   Action points: " + player.getActionPoints() + "/" + player.getMaxActionPoints() + "]");
		System.out.println(" [Buff: " + player.getBuffListString() + "]");
		System.out.println();
		System.out.println(" [Card: " + player.getHandCardListString() + "]\n");
		
		System.out.println(Main.longLine);
		
		System.out.print("Action >> ");
	}

	private String getEnemyLeftPattern(){
		switch (enemyType) {
			case "normal":
				return "- ";

			case "elite":
				return "-= ";

			case "boss":
				return "--=[ ";

			default:
				return "- ";
		}
	}

	private String getEnemyRightPattern(){
		switch (enemyType) {
			case "normal":
				return " -";

			case "elite":
				return " =-";

			case "boss":
				return " ]=--";

			default:
				return " -";
		}
	}
	
	@Override
	public void onInput(String input) {

		if (isWin) {
			onWinInput(input);
			return;
		}
		
		Player player = Player.getInstance();
		String[] parts = input.split(" ");
		
		switch (parts[0]) {
		
			case "p":
				playcard(Integer.parseInt(parts[1]));

				int delay = 3000;
				Main.executor.schedule(() -> {
					if (enemy.getIsDied())
						onWin();
				}, delay, TimeUnit.MILLISECONDS);

				break;
				
			case "i":
				Card card = player.getHandCardList().get(Integer.parseInt(parts[1]) - 1);
				System.out.println(" >> " + card.getInfo());

			default:
				break;
		}
		
		if (input.equals("e")) {
			Game.getInstance().setIsEndTurn(true);
			onEndTurn();
		}
	}

	@Override
	public boolean isValidInput(String input) {
		if (isWin) 
			if (Game.getInstance().getIsVictory())
				return isValidVictoryInput(input);
			else
				return isValidWinInput(input);
		else return isValidBattleInput(input);
	}
	
	public boolean isValidBattleInput(String input) {
		
		if (input == null) return false;
		if (input.equals("e")) return true;

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
		
		return false;

	}
	
	private boolean isValidWinInput(String input) {

		if (input == null) return false;
		
		String[] parts = input.split(" ");
		if (parts.length != 2) return false;
		
		if (!parts[0].equals("c")) {
			return false;
		}
		
		try {
			int choice = Integer.parseInt(parts[1]);
			if (parts[0].equals("c")) {
				return choice >= 1 && choice <= 4;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return false;

	}

	private boolean isValidVictoryInput(String input){
		if (!input.equals("e"))
			return false;
		return true;
	}

	private void playcard(int cardIndex) {
		
		Player player = Player.getInstance();
		
		player.playCard(cardIndex, enemy);
	}
	
	public void onEndTurn() {
		
		Player player = Player.getInstance();
		List<Card> handCardList = player.getHandCardList();
		
		for (Card card : handCardList)
			if (!card.getCanPlay())
				((PassiveCard)card).onDiscard();
		
		if (enemy.getHp() <= 0 || enemy.getIsDied()) {
			onWin();
			return;
		}
				
		enemy.onMove();
		enemy.onEndTurn();
		player.onEndTurn();
	}

	public void onWin(){

		isWin = true;

		Main.executor.schedule(() -> {
			onWinDraw();
		}, 500, TimeUnit.MILLISECONDS);

		if (Game.getInstance().getCurrentNode().getClass() != NodeBattle.class)
			Player.getInstance().onWin();
	}

	private void onWinDraw(){

		int gainedGold;
		gainedGold = 70 + Main.random.nextInt(16);
		if (enemyType.equals("elite"))
			gainedGold += (35 + Main.random.nextInt(8));
		
		ScoreCalculator scoreCalculator = ScoreCalculator.getInstance();
		int gainedScore = scoreCalculator.calculateBattleScore(enemy, enemyType);

		// Util.printBlankLines(3);
		System.out.println(Main.longLine);

		System.out.println("\n >> You defeated " + Colors.colorOnForEnemyName(enemy.getName(), enemy.getType()) + "!");
		Player.getInstance().addGold(gainedGold);
		scoreCalculator.addScore(gainedScore);

		System.out.println();
		System.out.println(" >> Choose a reward card:");
		System.out.println();

		Card[] rewardCards = {rewardCard1, rewardCard2, rewardCard3};
		for (int i = 0; i < rewardCards.length; i++) {
			Card card = rewardCards[i];
			System.out.println("   c " + (i + 1) + ") <" + Colors.colorOnForCardCost(card) + "> [" + Colors.colorOnForCardRarity(card) + "] " + Colors.colorOnForCardName(card));
			System.out.println("        " + card.getInfo());
		}

		System.out.println("   c 4) Move on");
		Util.printBlankLines(1);
		System.out.println(Main.longLine);

		System.out.print("Action >> ");
	}

	private void onWinInput(String input) {

		String invalidInput = " Invalid input. | Choose card or action - c 1 |\nAction >> ";

		if (!isValidWinInput(input)) {
			System.out.println(invalidInput);
        	return;
		}

		String[] parts = input.split(" ");
		
		try {
			int choice = Integer.parseInt(parts[1]);
			if (choice < 1 || choice > 4) {
				System.out.println(" Invalid choice.\nAction >> ");
				return;
			}
			
			/* if (parts[0].equals("i")) {
				Card cardToShow = null;
				switch (choice) {
					case 1:
						cardToShow = rewardCard1;
						break;
					case 2:
						cardToShow = rewardCard2;
						break;
					case 3:
						cardToShow = rewardCard3;
						break;
				}
				if (cardToShow != null) {
					System.out.println(" >> " + cardToShow.getInfo() + "\nAction >> ");
				}
				return;
			} */
			
			if (parts[0].equals("c")) {
				Card chosenCard = null;
				switch (choice) {
					case 1:
						chosenCard = rewardCard1;
						break;
					case 2:
						chosenCard = rewardCard2;
						break;
					case 3:
						chosenCard = rewardCard3;
						break;
					case 4:
						chosenCard = null;
						break;
				}
				if (chosenCard != null) {
					Player.getInstance().addCardToDeck(chosenCard);
					System.out.println(" >> Added " + Colors.colorOnForCardName(chosenCard) + " to your deck!");
				}
				completeBattleNode();
			}
		} catch (NumberFormatException e) {
			System.out.println(" Please enter a valid number. ");
		}
	}

	private void completeBattleNode() {
		isWin = false;
		Player.getInstance().clearHandCards();
		Game.getInstance().advanceToNextNode();
	}
	
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
}
