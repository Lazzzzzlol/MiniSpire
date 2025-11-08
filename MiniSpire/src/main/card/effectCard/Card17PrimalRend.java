package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;
import main.*; //use to trigger warning, delete it after fill onUse()

public class Card17PrimalRend extends EffectCard{
    
    public Card17PrimalRend() {
        this.name = "Primal Rend";
        this.info = "Deal damage that same as the total damage of Attack Cards in hand cards.";
        this.rarity = "epic";
        this.cost = 3;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        //need to fill
    }
}