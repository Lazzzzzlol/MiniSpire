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
		
		int currentDamage = 2;
		int hits = 4;

		for (int i = 0; i < hits; i++) {
			int finalDamage = DamageProcessor.calculateDamageToEnemy(currentDamage, enemy);
			DamageProcessor.applyDamageToEnemy(currentDamage, enemy);
			
			if (i < hits - 1) {
				currentDamage = 2 * finalDamage;
			}
		}
	}
		
	@Override
	public String getName() {
		return name;
	}
}