package main.card.attackCard;

import main.buff.DamageCalculator;
import main.enemy.Enemy;
import main.player.Player;

public class Card04AbdomenTear extends AttackCard{
	
	String name = "Abdomen Tear";
	String info = "Deal 12 damage; Take 3 damage. ";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 12;

		DamageCalculator.DamageResult result = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());

		int baseSelfDamage = 3;
		DamageCalculator.DamageResult selfResult = DamageCalculator.calculateDamageToPlayer(baseSelfDamage, player);
		player.deductHp(selfResult.getFinalDamage());
	}
	
	@Override
	public String getName() {
		return name;
	}
}