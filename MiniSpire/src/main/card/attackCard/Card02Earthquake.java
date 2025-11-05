package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card02Earthquake extends AttackCard{
	
	String name = "Earthquake";
	String info = "Deal 3 damage for 3 times. ";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
		int baseDamage = 3;
		
		DamageProcessor.DamageResult result1 = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		DamageProcessor.DamageResult result2 = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		DamageProcessor.DamageResult result3 = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result1.getFinalDamage());
		enemy.deductHp(result2.getFinalDamage());
		enemy.deductHp(result3.getFinalDamage());
	}
	
	@Override
	public String getName() {
		return name;
	}
}