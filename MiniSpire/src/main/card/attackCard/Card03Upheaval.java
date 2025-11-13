package main.card.attackCard;

import main.processor.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card03Upheaval extends AttackCard {
    
    public Card03Upheaval() {
        this.name = "Upheaval";
        this.info = "Deal increasing damage from 6 each time played in this fight.";
        this.cost = 1;
        this.rarity = "rare";
        this.baseDamage = 6;
    }
    
    private int timesUsed = 0;

    @Override
    public void onPlay(Player player, Enemy enemy) {
        int baseDamage = calculateDamage(6);

		DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);

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
}