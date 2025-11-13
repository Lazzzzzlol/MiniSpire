package main.card.attackCard;

import main.processor.DamageProcessor;
import main.buff.oneFightBuff.BuffFlurry;
import main.enemy.Enemy;
import main.player.Player;

public class Card24Flurry extends AttackCard {
    
    public Card24Flurry() {
        this.name = "Flurry of Blows";
        this.info = "Deal 4 damage, draw 1 card. During this fight, when Effect Card is used, gain a Flurry of Blows to hand.";
        this.cost = 0;
        this.rarity = "rare";
        this.baseDamage = 4;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, Player.getInstance(), enemy);
        player.drawHandCards(1, null);
        player.addBuff(new BuffFlurry(1), 1);
    }
}