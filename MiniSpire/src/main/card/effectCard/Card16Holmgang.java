package main.card.effectCard;

import main.buff.positiveBuff.BuffInvincible;
import main.enemy.Enemy;
import main.player.Player;

public class Card16Holmgang extends EffectCard{
	
	String name = "Holmgang";
	String info = "Apply 1 round Invincible to self. ";
	String rarity = "epic";
	int cost = 2;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		player.addBuff(new BuffInvincible(1), 1);
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