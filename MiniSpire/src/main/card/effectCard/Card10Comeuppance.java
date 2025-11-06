package main.card.effectCard;

import main.buff.positiveBuff.BuffReflective;
import main.enemy.Enemy;
import main.player.Player;

public class Card10Comeuppance extends EffectCard{
	
	String name = "Comeuppance";
	String info = "Apply 1 round Reflective to self. ";
	String rarity = "normal";
	int cost = 1;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		player.addBuff(new BuffReflective(1), 1);
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