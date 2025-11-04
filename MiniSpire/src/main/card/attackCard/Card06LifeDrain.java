package main.card.attackCard;

import main.buff.DamageCalculator;
import main.enemy.Enemy;
import main.player.Player;

public class Card06LifeDrain extends AttackCard{
	
	String name = "Life Drain";
	int cost = 2;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 6;

		DamageCalculator.DamageResult damageResult = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		int finalDamage = damageResult.getFinalDamage();

		enemy.deductHp(finalDamage);

		if (finalDamage > 0) {
			player.addHp(finalDamage);
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
}