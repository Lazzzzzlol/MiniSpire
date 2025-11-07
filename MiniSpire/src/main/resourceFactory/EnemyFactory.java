package main.resourceFactory;

import java.util.HashMap;
import java.util.Map;

import main.Main;
import main.enemy.Enemy;
import main.enemy.normalEnemy.LostDancer;
import main.enemy.normalEnemy.Zombie;

public class EnemyFactory implements ResourceFactory {
	
	private static EnemyFactory instance = null;
	public static EnemyFactory getInstance() {
		if (instance == null) {
			instance = new EnemyFactory();
		}
		return instance;
	}
	
	
	
	@Override
	public void init() {

	}
	
	public static Enemy getRandomEnemy(String enemyType) {
		
		switch (enemyType) {
		case "normal":

			switch (Main.random.nextInt(2)) {
				case 0:
					return new Zombie();
				
				case 1:
					return new LostDancer();
			
				default:
					break;
			}
			
		default:
			return new Zombie();
		}
	}
}
