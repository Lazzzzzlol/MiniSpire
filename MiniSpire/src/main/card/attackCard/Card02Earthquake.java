package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card02Earthquake extends AttackCard{
	
	String name = "Earthquake";
	String info = "Deal 3 damage for 3 times. ";
	String rarity = "normal";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
		int baseDamage = 3;
		
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
	}
	
	@Override
	public String getName() {
		return name;
	}
}