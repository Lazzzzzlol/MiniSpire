package main.card.attackCard;

import main.processor.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card32InstantFlurry extends AttackCard {
    
    public Card32InstantFlurry() {
        this.name = "Instant Flurry";
        this.info = "Deal 4 damage, draw 1 card.[Temporary] --Make haste, the wind waits for no one.";
        this.cost = 0;
        this.rarity = "special";
        this.baseDamage = 4;
        this.temporary = true;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);
        player.drawHandCards(1, null);
    }
}