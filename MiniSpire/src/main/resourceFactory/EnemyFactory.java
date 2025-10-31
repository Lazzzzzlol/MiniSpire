package main.resourceFactory;

import java.util.HashMap;
import java.util.Map;

import main.enemy.Enemy;
import main.enemy.normalEnemy.Zombie;

public class EnemyFactory implements ResourceFactory {
	
	private static EnemyFactory instance = null;
	public static EnemyFactory getInstance() {
		if (instance == null) {
			instance = new EnemyFactory();
		}
		return instance;
	}
	
	static Map<String, Enemy> pool = new HashMap<String, Enemy>();
	
	@Override
	public void init() {
		
	}
	
	public static Enemy getRandomEnemy(String enemyType) {
		
		switch (enemyType) {
		case "normal":
			return new Zombie();
			
		default:
			return new Zombie();
		}
	}
}
