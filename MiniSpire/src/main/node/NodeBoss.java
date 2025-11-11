package main.node;

import java.util.concurrent.TimeUnit;

import main.Main;
import main.ScoreCalculator;
import main.Util;
import main.game.Game;

public class NodeBoss extends NodeBattle {
	
	//private String name = "Boss";

	public NodeBoss() {
		super("boss");
	}

	public void onStartTurn(){

		if (enemy.getIsDied()){
			setIsWin(true);
			Game game = Game.getInstance();
			game.setIsGameOver(true);
			game.setIsVictory(true);
			onWin();
		}
		else
			super.onStartTurn();
	}

	@Override
	public void onWin(){

		ScoreCalculator scoreCalculator = ScoreCalculator.getInstance();
		int gainedScore = scoreCalculator.calculateBattleScore(enemy, enemyType);
		scoreCalculator.addScore(gainedScore);

		switch (enemy.getName()) {
			case "Thanalous":
				printThanalousSpeech();
				break;

			case "Indomitable Will":
				printIndomitableWillSpeech();
		
			default:
				break;
		}
		
		//Main.executor.schedule(() -> {
			Game game = Game.getInstance();
			game.setIsGameOver(true);
			game.setIsVictory(true);
		//}, 10, TimeUnit.SECONDS);
	}

	private void printThanalousSpeech() {

		Util.printBlankLines(10);

		Main.executor.schedule(() -> {
			System.out.println(" >> Thanalous: ...");
		}, 1, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.println(" >> Thanalous: Well...you defeated me...");
		}, 3, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.print(" >> Thanalous: As you wish, you may pass now...");
		}, 6, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.println("for your dog I guess..?");
		}, 8, TimeUnit.SECONDS);
	}

	private void printIndomitableWillSpeech() {

		Util.printBlankLines(10);

		Main.executor.schedule(() -> {
			System.out.print(" >> : .");
		}, 1, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.print(".");
		}, 2, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.println("?");
		}, 3, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.println(" >> : Unacceptable.");
		}, 5, TimeUnit.SECONDS);

		for (int i = 1; i <= 50; i++){
			Main.executor.schedule(() -> {
			System.out.println(" >> : Unacceptable.");
			}, 7000 + 50 * i, TimeUnit.MILLISECONDS);
		}
	}
}
