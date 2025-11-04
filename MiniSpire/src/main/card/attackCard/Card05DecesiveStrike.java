package main.card.attackCard;

import main.buff.DamageCalculator;
import main.buff.debuff.BuffVulnerable;
import main.enemy.Enemy;
import main.player.Player;

public class Card05DecesiveStrike extends AttackCard{
	
	String name = "Decisive Strike";
	int cost = 2;

	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 15;

		DamageCalculator.DamageResult result = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());

		enemy.addBuff(new BuffVulnerable(1), 1);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getCost() {
		return cost;
	}
}