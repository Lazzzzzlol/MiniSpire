package main.card.attackCard;

import main.buff.DamageCalculator;
import main.enemy.Enemy;
import main.player.Player;

public class Card02Earthquake extends AttackCard{
	
	String name = "Earthquake";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
		int baseDamage = 3;
		
		DamageCalculator.DamageResult result1 = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		DamageCalculator.DamageResult result2 = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		DamageCalculator.DamageResult result3 = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result1.getFinalDamage());
		enemy.deductHp(result2.getFinalDamage());
		enemy.deductHp(result3.getFinalDamage());
	}
	
	@Override
	public String getName() {
		return name;
	}
}