package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card09Double extends EffectCard{
	
	String name = "Double";
	String info = "Next attack card will effect twice. ";
	String rarity = "normal";
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
