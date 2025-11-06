package main.card.effectCard;

import main.buff.oneFightBuff.BuffDouble;
import main.enemy.Enemy;
import main.player.Player;

public class Card09Double extends EffectCard{
	
	String name = "Double";
	String info = "Next attack card will effect twice. ";
	String rarity = "normal";
	int cost = 1;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		player.addBuff(new BuffDouble(1), 1);
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
