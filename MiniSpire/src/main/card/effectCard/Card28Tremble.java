package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card28Tremble extends EffectCard{
	
	String name = "Tremble";
	String info = "Draw 1 card. ";
	String rarity = "normal";
	int cost = 0;
	
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