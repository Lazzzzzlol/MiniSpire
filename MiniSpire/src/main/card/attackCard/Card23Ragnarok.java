package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card23Ragnarok extends AttackCard{
	
	String name = "Ragnarok";
	String info = "Deal 2 damage; Then deal double of damage actually dealt last step; Repeat last step; Repeat last step. ";
	int cost = 3;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
		int baseDamage = 2;

		DamageProcessor.DamageResult result1 = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		int finalDamage1 = (int) result1.getFinalDamage();
        enemy.deductHp(finalDamage1);

        DamageProcessor.DamageResult result2 = DamageProcessor.calculateDamageToEnemy(2 * finalDamage1, enemy);
		int finalDamage2 = (int) result2.getFinalDamage();
        enemy.deductHp(finalDamage2);

        DamageProcessor.DamageResult result3 = DamageProcessor.calculateDamageToEnemy(2 * finalDamage2, enemy);
		int finalDamage3 = (int) result3.getFinalDamage();
        enemy.deductHp(finalDamage3);

        DamageProcessor.DamageResult result4 = DamageProcessor.calculateDamageToEnemy(2 * finalDamage3, enemy);
		int finalDamage4 = (int) result4.getFinalDamage();
        enemy.deductHp(finalDamage4);
	}
	
	@Override
	public String getName() {
		return name;
	}
}