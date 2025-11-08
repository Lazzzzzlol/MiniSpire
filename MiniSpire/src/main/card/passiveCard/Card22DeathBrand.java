package main.card.passiveCard;

import main.*; //use to trigger warning, delete it after fill onUse()

public class Card22DeathBrand extends PassiveCard{
    
    public Card22DeathBrand() {
        this.name = "Death Brand";
        this.info = "When discarded, take 4 damage.";
        this.rarity = "special";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {
        //need to fill
    }
}