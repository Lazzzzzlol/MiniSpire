package main.resourceFactory;

import main.Main;
import main.enemy.Enemy;
import main.enemy.normalEnemy.LostDancer;
import main.enemy.normalEnemy.Zombie;
import main.enemy.normalEnemy.PhantomBard;
import main.enemy.normalEnemy.RidiculeClown;
import main.enemy.eliteEnemy.Watcher;
import main.enemy.eliteEnemy.MakoFighter;
import main.enemy.eliteEnemy.PerplexedMonk;
import main.enemy.bossEnemy.Thanalous;
import main.enemy.bossEnemy.IndomitableWill;

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
			// Normal enemies: Zombie, LostDancer, PhantomBard, RidiculeClown
			switch (Main.random.nextInt(4)) {
				case 0:
					return new Zombie();
				
				case 1:
					return new LostDancer();
					
				case 2:
					return new PhantomBard();
					
				case 3:
					return new RidiculeClown();
			
				default:
					return new Zombie();
			}
			
		case "elite":
			// Elite enemies: Watcher, MakoFighter, PerplexedMonk
			switch (Main.random.nextInt(3)) {
				case 0:
					return new Watcher();
					
				case 1:
					return new MakoFighter();
					
				case 2:
					return new PerplexedMonk();
					
				default:
					return new Watcher();
			}
			
		case "Boss":
			// Boss enemies: Thanalous, IndomitableWill
			switch (Main.random.nextInt(2)) {
				case 0:
					return new Thanalous();
					
				case 1:
					return new IndomitableWill();
					
				default:
					return new Thanalous();
			}
			
		default:
			return new Zombie();
		}
	}
}
