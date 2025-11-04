package main.card.attackCard;

import main.buff.DamageCalculator;
import main.enemy.Enemy;
import main.player.Player;

public class Card03Upheaval extends AttackCard{
    
	String name = "Upheaval";
	int cost = 1;

	private int timesUsed = 0;

	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = calculateDamage();

		DamageCalculator.DamageResult result = DamageCalculator.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());
		
		timesUsed++;
	}

	private int calculateDamage() {

		if (timesUsed == 0) return 6;

		int m = timesUsed + 1;
		int sum = (m * (m + 1)) / 2 - 1;
		return 6 + sum;
	}

	public void resetBattle() {
		timesUsed = 0;
	}

	@Override
	public String getName() {
		return name;
	}
}