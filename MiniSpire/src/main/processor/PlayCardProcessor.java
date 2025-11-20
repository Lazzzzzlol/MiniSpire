package main.processor;

import main.player.Player;
import main.resourceFactory.CardFactory;
import main.Colors;
import main.card.Card;
import main.card.attackCard.AttackCard;
import main.card.effectCard.EffectCard;
import main.enemy.Enemy;

public class PlayCardProcessor {
    
    public static void processCardPlay(Player player, Card card, Enemy enemy) {

        if (!card.getCanPlay()) {
            schedulePlayCardMessage("Unplayable card type.", 250L);
            return;
        }

        switch (card.getType()) {

            case "Attack":
                processAttackCard(player, card, enemy);
                break;
            case "Effect":
                processEffectCard(player, card, enemy);
                break;
            default:
                schedulePlayCardMessage("Unknown or unplayable card type.", 250L);
                break;

        }
    }
    
    private static void processAttackCard(Player player, Card card, Enemy enemy) {

        AttackCard attackCard = (AttackCard) card;
        
        schedulePlayCardMessage("Played card: " + Colors.colorOnForCardName(card), 250L);
        attackCard.onPlay(player, enemy);
        
        if (hasBuff(player, "Double") && (enemy.getHp() > 0) ) {
            player.getBuffList().removeIf(buff -> "Double".equals(buff.getName()));
            schedulePlayCardMessage("Played card: " + Colors.colorOnForCardName(card) + " (" + Colors.colorOnForBuff("Double", "positive") + ")", 250L);
            attackCard.onPlay(player, enemy);
        }
    }
    
    private static void processEffectCard(Player player, Card card, Enemy enemy) {

        EffectCard effectCard = (EffectCard) card;

        schedulePlayCardMessage("Played card: " + Colors.colorOnForCardName(card), 250L);
        effectCard.onUse(player, enemy);

        if (hasBuff(player, "Flurry")) {
            addFlurryToHand(player);
        }

    }
    
    private static boolean hasBuff(Player player, String buffName) {

        return player.getBuffList().stream()
                .anyMatch(buff -> buffName.equals(buff.getName()));

    }
    
    private static void addFlurryToHand(Player player) {

        Card temporaryFlurryCard = CardFactory.getInstance().createCard(24);
        temporaryFlurryCard.setTemporary(true);
        player.getHandCardList().add(temporaryFlurryCard);
        schedulePlayCardMessage("Gained card: " + Colors.colorOnForCardName(temporaryFlurryCard),250L);

    }

    private static void schedulePlayCardMessage(String message, Long delaySeconds) {
        MessageQueue.scheduleMessage(message, delaySeconds);
    }
}
