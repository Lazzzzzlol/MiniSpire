package main.card.attackCard;

import main.processor.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card23Ragnarok extends AttackCard {
    
    public Card23Ragnarok() {
        this.name = "Ragnarok";
        this.info = "Deal 2 damage; Then deal double of damage actually dealt last step; Repeat last step; Repeat last step.";
        this.cost = 3;
        this.rarity = "epic";
        this.baseDamage = 2;
    }

    private int currentDamage = 2;
    private int hits = 4;

    @Override
    public void onPlay(Player player, Enemy enemy) {
        for (int i = 0; i < hits; i++) {
			int finalDamage = DamageProcessor.calculateDamageToEnemy(currentDamage, Player.getInstance(), enemy);
			DamageProcessor.applyDamageToEnemy(currentDamage, Player.getInstance(), enemy);
			
			if (i < hits - 1) {
				currentDamage = 2 * finalDamage;
			}
		}
        currentDamage = baseDamage;
    }

    @Override
	public int getBaseDamage(){
		return currentDamage;
	}
}