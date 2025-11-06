package main.card.attackCard;

import main.card.Card;
import main.enemy.Enemy;
import main.player.Player;

public class AttackCard implements Card{

	private Boolean canPlay = true;
	private String name = "Generic Attack Card";
	private String info = "info";
	private int cost = 1;
	private String type = "Attack";
	private String rarity = "normal";
	private int baseDamage = 1;
	
	public void onPlay(Player player, Enemy enemy) {};

	@Override
	public Boolean getCanPlay() {
		return canPlay;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getInfo(){
		return info;
	}

	@Override
	public int getCost() {
		return cost;
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getRarity(){
		return rarity;
	}

	public int getBaseDamage(){
		return baseDamage;
	}
}