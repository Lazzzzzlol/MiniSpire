package main.card.effectCard;

import main.buff.oneFightBuff.BuffDouble;
import main.enemy.Enemy;
import main.player.Player;

public class Card09Double extends EffectCard{
    
    public Card09Double() {
        this.name = "Double";
        this.info = "Next attack card will affect twice (If that card leads to a kill already, then this effect will remain).";
        this.rarity = "normal";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addBuff(new BuffDouble(1), 1);
    }
}