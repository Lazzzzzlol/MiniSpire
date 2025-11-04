package main.card.attackCard;

import main.buff.DamageCalculator;
import main.buff.debuff.BuffWeakened;
import main.enemy.Enemy;
import main.player.Player;

public class Card01Onslaught extends AttackCard{

	String name = "Onslaught";
	int cost = 1;

	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 6;

		DamageCalculator.DamageResult result = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());
		
		enemy.addBuff(new BuffWeakened(1), 1);
	}
	
	@Override
	public String getName() {
		return name;
	}
}