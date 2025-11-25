package main.node;

import java.util.concurrent.TimeUnit;

import main.Main;
import main.ScoreCalculator;
import main.Util;
import main.game.Game;

public class NodeBoss extends NodeBattle {

	public NodeBoss() {
		super("boss");
	}

	public void onStartTurn(){
		super.onStartTurn();
		Game.getInstance().setFinalBoss(enemy);
	}

	@Override
	public void onWin(){

		isWin = true;

		Game game = Game.getInstance();
		game.setIsEndTurn(true);
		game.setIsGameOver(true);
		game.setIsVictory(true);

		Main.executor.schedule(() -> {
			onWinDraw();
		}, 500, TimeUnit.MILLISECONDS);

		ScoreCalculator scoreCalculator = ScoreCalculator.getInstance();
		int gainedScore = scoreCalculator.calculateBattleScore(enemy, enemyType);
		scoreCalculator.addScore(gainedScore);

	}

	private void onWinDraw(){

		switch (enemy.getName()) {
			case "Thanalous":
				printThanalousSpeech();
				break;

			case "IndOm?taB?e W?Ll":
				printIndomitableWillSpeech();
		
			default:
				break;
		}
	}

	@Override
	public void onInput(String input){

		if (!isWin)
			super.onInput(input);
		else
			while (!input.equals("end"))
				input = Main.scanner.nextLine();
	}

	private void printThanalousSpeech() {

		Util.printBlankLines(10);

		Main.executor.schedule(() -> {
			System.out.println(" >> Thanalous: ... ");
		}, 1, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.println(" >> Thanalous: Well... you defeated me... ");
		}, 3, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.print(" >> Thanalous: As you wish, you may pass now... ");
		}, 5, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.println("for your dog I guess..?");
		}, 7, TimeUnit.SECONDS);

		Main.executor.schedule(() -> {
			System.out.println("[Type 'end' to end game.]");
		}, 9, TimeUnit.SECONDS);
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

		for (int i = 1; i <= 70; i++){
			Main.executor.schedule(() -> {
			System.out.println(" >> : Unacceptable.");
			}, 7000 + 25 * i, TimeUnit.MILLISECONDS);
		}
		Main.executor.schedule(() -> {
			System.out.println("[Type 'end' to end game.]");
		}, 9, TimeUnit.SECONDS);
	}
}
