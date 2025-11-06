package main.card.effectCard;

import main.buff.positiveBuff.BuffRecovering;
import main.enemy.Enemy;
import main.player.Player;

public class Card13Equilibrium extends EffectCard {
	
	String name = "Equilibrium";
	String info = "Heal 10 hp; Apply 5 round Recovering to self. ";
	String rarity = "rare"; 
	int cost = 1;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		player.addHp(10);
		player.addBuff(new BuffRecovering(5), 5);
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
