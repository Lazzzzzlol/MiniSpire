package main.card.effectCard;

import main.buff.positiveBuff.BuffStrengthened;
import main.enemy.Enemy;
import main.player.Player;

public class Card11Rage extends EffectCard{
	
	String name = "Rage";
	String info = "Apply 2 round Strengthened to self; Draw 1 card. ";
	String rarity = "rare";
	int cost = 1;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		player.addBuff(new BuffStrengthened(2), 2);

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