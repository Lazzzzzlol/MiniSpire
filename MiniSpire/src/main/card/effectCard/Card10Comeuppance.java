package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card10Comeuppance extends EffectCard{
	
	String name = "Comeuppance";
	String info = "Apply 2 round Strengthened to self. ";
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