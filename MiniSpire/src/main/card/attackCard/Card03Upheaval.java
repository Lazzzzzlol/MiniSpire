package main.card.attackCard;

import main.buff.DamageCalculator;
import main.enemy.Enemy;
import main.player.Player;

public class Card03Upheaval extends AttackCard{
    
	String name = "Upheaval";
	String info = "Deal increasing damage from 6 each time played. ";
	int cost = 1;

	private int timesUsed = 0;

	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = calculateDamage(6);

		DamageCalculator.DamageResult result = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());

		timesUsed++;
	}

	private int calculateDamage(int base) {

		if (timesUsed == 0) return base;

		int m = timesUsed + 1;
		int sum = (m * (m + 1)) / 2 - 1;
		return base + sum;
	}

	public void resetBattle() {
		timesUsed = 0;
	}

	@Override
	public String getName() {
		return name;
	}
}