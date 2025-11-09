package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;
import main.*; //use to trigger warning, delete it after fill onUse()

public class Card28Tremble extends EffectCard{
    
    public Card28Tremble() {
        this.name = "Tremble";
        this.info = "Draw 1 card.";
        this.rarity = "normal";
        this.cost = 0;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.drawHandCards(1);
    }
}