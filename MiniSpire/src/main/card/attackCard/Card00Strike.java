package main.card.attackCard;

import main.buff.DamageCalculator;
import main.enemy.Enemy;
import main.player.Player;

public class Card00Strike extends AttackCard{
	
	String name = "Strike";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 8;

		DamageCalculator.DamageResult result = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());
	}
	
	@Override
	public String getName() {
		return name;
	}
}