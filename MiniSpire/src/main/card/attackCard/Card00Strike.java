package main.card.attackCard;

import main.processor.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card00Strike extends AttackCard {
    
    public Card00Strike() {
        this.name = "Strike";
        this.info = "Deal 8 damage.";
        this.cost = 1;
        this.rarity = "normal";
        this.baseDamage = 800;
    }
    
    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
    }
    
}