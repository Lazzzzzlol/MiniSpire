package main.resourceFactory;

import main.Main;
import main.enemy.Enemy;
import main.enemy.bossEnemy.IndomitableWill;
import main.enemy.bossEnemy.Thanalous;
import main.enemy.eliteEnemy.MakoFighter;
import main.enemy.eliteEnemy.PerplexedMonk;
import main.enemy.eliteEnemy.Watcher;
import main.enemy.normalEnemy.LostDancer;
import main.enemy.normalEnemy.PhantomBard;
import main.enemy.normalEnemy.RidiculeClown;
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
		return new MakoFighter();
		/*switch (enemyType) {
			case "normal":

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
						return new LostDancer();
				}

			case "elite":

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

			case "boss":

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
		}*/
	}
}
