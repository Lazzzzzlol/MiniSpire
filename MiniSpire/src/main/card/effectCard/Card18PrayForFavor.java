package main.card.effectCard;

import main.buff.oneFightBuff.BuffBlessed;
import main.enemy.Enemy;
import main.player.Player;


public class Card18PrayForFavor extends EffectCard{
    
    public Card18PrayForFavor() {
        this.name = "Pray for Favor";
        this.info = "Apply Blessing to self, lasts for this fight.";
        this.rarity = "legendary";
        this.cost = 4;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addBuff(new BuffBlessed(1), 1);
    }
}