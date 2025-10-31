package main.enemy.eliteEnemy;

import main.Main;
import main.enemy.Enemy;

public class Watcher extends Enemy{
	
	public Watcher() {
		super("Watcher", 90 + Main.random.nextInt(30));
	}

}
