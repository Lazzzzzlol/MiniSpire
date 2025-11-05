package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card07Ruination extends AttackCard{
	
	String name = "Ruination";
	String info = "Deal 25 damage; If it finally deals more than 25 damage, deal 25 damage again, only once. ";
	int cost = 3;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
		int baseDamage = 25;

		DamageProcessor.DamageResult result = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());

		if (result.getFinalDamage() > 25) {
			DamageProcessor.DamageResult result2 = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
			enemy.deductHp(result2.getFinalDamage());
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
}