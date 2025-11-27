package main;

import main.enemy.Enemy;
import main.game.Game;
import main.player.Player;

public class ScoreCalculator {
	
	private static ScoreCalculator instance = null;
	
	public static ScoreCalculator getInstance() {
		if (instance == null) {
			instance = new ScoreCalculator();
		}
		return instance;
	}
	
	private ScoreCalculator() {}
	
	public int calculateBattleScore(Enemy enemy, String enemyType) {
		
		int baseScore = 0;
		
		switch (enemyType) {
			case "normal":
				baseScore = 50;
				break;
			case "elite":
				baseScore = 150;
				break;
			case "boss":
				baseScore = 500;
				break;
			default:
				baseScore = 50;
				break;
		}
		Player player = Player.getInstance();
		int hpBonus = player.getHp(); 
		int totalScore = baseScore + hpBonus;	
		return totalScore;
	}
	public void addScore(int score) {
		Game.getInstance().addScore(score);
	}
}
