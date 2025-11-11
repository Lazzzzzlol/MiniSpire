package main.card.attackCard;

import main.processor.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card02Earthquake extends AttackCard {
    
    public Card02Earthquake() {
        this.name = "Earthquake";
        this.info = "Deal 3 damage for 3 times.";
        this.cost = 1;
        this.rarity = "normal";
        this.baseDamage = 3;
    }
    
    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
    }
    
}