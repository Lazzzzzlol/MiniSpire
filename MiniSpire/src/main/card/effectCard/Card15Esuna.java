package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card15Esuna extends EffectCard{
	
	String name = "Esuna";
	String info = "Remove all buff of self, then apply 1 round Strengthened to self. ";
	String rarity = "rare";
	int cost = 2;
	
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