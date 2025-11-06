package main.card.effectCard;

import main.buff.positiveBuff.BuffBloodLeeching;
import main.enemy.Enemy;
import main.player.Player;

public class Card08Bloodbath extends EffectCard{
	
	String name = "Bloodbath";
	String info = "Apply 1 round BloodLeeching to self. ";
	String rarity = "normal";
	int cost = 0;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		player.addBuff(new BuffBloodLeeching(1), 1);
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
