package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card25TheSinisterBlade extends AttackCard {
    
    public Card25TheSinisterBlade() {
        this.name = "The Sinister Blade";
        this.info = "Deal 6 damage, draw 1 card. If Attack Card is drawn, gain 2 cost.";
        this.cost = 1;
        this.rarity = "epic";
        this.baseDamage = 6;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
    }
}