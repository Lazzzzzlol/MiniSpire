package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card13Equilibrium extends EffectCard {
	
	String name = "Equilibrium";
	String info = "Heal 5 hp, and apply 4 round Recovery to self. ";
	String rarity = "rare"; 
	int cost = 1;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getInfo(){
		return info;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
