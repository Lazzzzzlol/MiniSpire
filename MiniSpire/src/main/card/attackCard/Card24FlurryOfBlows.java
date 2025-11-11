package main.card.attackCard;

import main.buff.DamageProcessor;
import main.buff.oneFightBuff.BuffGainFlurryOfBlows;
import main.enemy.Enemy;
import main.player.Player;

public class Card24FlurryOfBlows extends AttackCard {
    
    public Card24FlurryOfBlows() {
        this.name = "Flurry of Blows";
        this.info = "Deal 4 damage, draw 1 card. During this fight, when Effect Card is used, gain a Flurry of Blows to hand.";
        this.cost = 0;
        this.rarity = "rare";
        this.baseDamage = 4;
    }

    @Override
    public void onPlay(Player player, Enemy enemy) {
        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
        player.drawHandCards(1);
        player.addBuff(new BuffGainFlurryOfBlows(1), 1);
    }
}