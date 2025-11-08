package main.card.passiveCard;

import main.*; //use to trigger warning, delete it after fill onUse()

public class Card21BlessingOfTheErdtree extends PassiveCard{
    
    public Card21BlessingOfTheErdtree() {
        this.name = "Blessing of the Erdtree";
        this.info = "When discarded, apply 3 round Strengthened, 3 round Tough to self.";
        this.rarity = "epic";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {
        //need to fill
    }
}