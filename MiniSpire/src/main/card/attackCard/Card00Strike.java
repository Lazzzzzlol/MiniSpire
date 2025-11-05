package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card00Strike extends AttackCard{
	
	String name = "Strike";
	String info = "Deal 8 damage. ";
	String rarity = "normal";
	int cost = 1;
	int baseDamage = 8;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getBaseDamage(){
		return baseDamage;
	}
}