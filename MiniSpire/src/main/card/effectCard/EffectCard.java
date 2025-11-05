package main.card.effectCard;

import main.card.Card;
import main.enemy.Enemy;
import main.player.Player;

public class EffectCard implements Card{

	private Boolean canPlay = true;
	private String name = "Generic Effect Card";
	private int cost = 1;
	private String type = "Effect";
	private String rarity = "normal";
	
	public void onUse(Player player, Enemy enemy) {};
	
	@Override
	public Boolean getCanPlay() {
		return canPlay;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public String getType() {
		return type;
	}

	public String getRarity(){
		return rarity;
	}
}
