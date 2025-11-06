package main.card.effectCard;

import main.buff.oneFightBuff.BuffBlessed;
import main.enemy.Enemy;
import main.player.Player;

public class Card18PrayForFavor extends EffectCard{
	
	String name = "Pray for Favor";
	String info = "Apply Blessing to self, lasts for this fight. ";
	String rarity = "legendary";
	int cost = 4;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		player.addBuff(new BuffBlessed(1), 1);
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