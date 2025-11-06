package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card14Interject extends EffectCard{
	
	String name = "Interject";
	String info = "Makes enemy fail to apply any buff to any target in next round. ";
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