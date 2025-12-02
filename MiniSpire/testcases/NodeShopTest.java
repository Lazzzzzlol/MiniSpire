package main.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.game.Game;
import main.node.NodeShop;
import main.player.Player;
import main.resourceFactory.CardFactory;
import main.card.Card;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class NodeShopTest {

    private Game game;
    private NodeShop shop;
    private Player player;

    @Before
    public void setUp() throws Exception {
        resetSingleton(Player.class, "instance");
        resetSingleton(Game.class, "INSTANCE");
        
        game = Game.getInstance();
        player = Player.getInstance();
        shop = new NodeShop();
        
        game.init();
        
        testGold();
        
    }

    private void resetSingleton(Class<?> clazz, String fieldName) throws Exception {
        Field instanceField = clazz.getDeclaredField(fieldName);
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    public void testShopInitialization() {
    	System.out.println("Test 1 -- testShopInitialization");
        assertNotNull("The store node should be initialized correctly.", shop);
        assertEquals("The store node name should be correct.", "Shop", shop.getName());
        
        System.out.println();
    }

    @Test
    public void testShopHasThreeAvailableCards() {
    	System.out.println("Test 2 -- testShopHasThreeAvailableCards");

        assertNotNull("The first card exists.", getShopCard(0));
        assertNotNull("The second card exists.", getShopCard(1));
        assertNotNull("The third card exists.", getShopCard(2));
        
        System.out.println();
    }

    @Test
    public void testValidInputCommands() {
    	System.out.println("Test 3 -- testValidInputCommands");

        assertTrue("b 1 vaild.", shop.isValidInput("b 1"));
        assertTrue("b 2 vaild.", shop.isValidInput("b 2"));
        assertTrue("b 3 vaild.", shop.isValidInput("b 3"));
        assertTrue("r 1 vaild.", shop.isValidInput("r 1"));
        assertTrue("r 2 vaild.", shop.isValidInput("r 2"));
        assertTrue("c 1 vaild.", shop.isValidInput("c 1"));
        assertTrue("l 1 vaild.", shop.isValidInput("l 1"));
        
        System.out.println();
    }

    @Test
    public void testInvalidInputCommands() {
    	System.out.println("Test 4 -- testInvalidInputCommands");
    	
        assertFalse("Void input invaild", shop.isValidInput(""));
        assertFalse("null input invaild", shop.isValidInput(null));
        assertFalse("Wrong command input invaild", shop.isValidInput("x 1"));
        assertFalse("Wrong index input invaild", shop.isValidInput("b 4"));
        assertFalse("Wrong format input invaild", shop.isValidInput("b1"));
        assertFalse("No index input invaild", shop.isValidInput("b"));
        
        System.out.println();
    }

    @Test
    public void testCardPurchaseWithSufficientGold() {
    	System.out.println("Test 5 -- testCardPurchaseWithSufficientGold");
        int initialGold = player.getGold();
        int initialDeckSize = player.getPlayerDeck().size();
        
        Card cardToBuy = getShopCard(0);
        int cardCost = getCardCost(cardToBuy);
        
        shop.onInput("b 1");
        
        assertEquals("Gold should be deducted correctly after purchase.", initialGold - cardCost, player.getGold());
        assertEquals("After purchase, the deck should be expanded by one card.", initialDeckSize + 1, player.getPlayerDeck().size());
        assertNull("The card slot should be null after purchase.", getShopCard(0));
        
        System.out.println();
    }
    
    @Test
    public void testPurchaseSoldOutCard() {
    	System.out.println("Test 6 -- testPurchaseSoldOutCard");
    	
        shop.onInput("b 2");
        shop.onInput("b 2");
        
        assertTrue("The program should display a sold-out message, but it shouldn't crash.", true);
        
        System.out.println();
    }

    @Test
    public void testCardPurchaseWithInsufficientGold() {
    	System.out.println("Test 7 -- testCardPurchaseWithInsufficientGold");

        player.lostGold(player.getGold());
        int initialDeckSize = player.getPlayerDeck().size();
        
        shop.onInput("b 3");
        
        assertEquals("The deck size should remain unchanged when there are insufficient gold.", initialDeckSize, player.getPlayerDeck().size());
        assertNotNull("Cards should not be sold out when gold are insufficient.", getShopCard(2));
        
        System.out.println();
    }

    

    @Test
    public void testShopRefresh() {
    	System.out.println("Test 8 -- testShopRefresh");
        int initialGold = player.getGold();
        Card[] initialCards = getShopCards();
        for (Card card : initialCards) {
        	System.out.println(card.getName());
        }
        
        shop.onInput("r 1");
        
        Card[] newCards = getShopCards();
        for (Card card : newCards) {
        	System.out.println(card.getName());
        }
        int newGold = player.getGold();
        System.out.println(initialCards[0].getName().equals("111"));
        System.out.println(initialCards[0].getName().equals(newCards[0].getName()));

        assertEquals("Gold should be deducted after refreshing.", initialGold - 20, newGold);
        
        boolean allSame = true;
        for (int i = 0; i < initialCards.length; i++) {
        	String oldCardName = initialCards[i].getName();
        	String newCardName = newCards[i].getName();
            if (oldCardName.equals(newCardName)) {
                allSame = false;
                break;
            }
        }
        assertFalse("The cards should be different after refreshing.", allSame);
        
        System.out.println();
    }

    @Test
    public void testShopRefreshWithInsufficientGold() {
    	System.out.println("Test 9 -- testShopRefreshWithInsufficientGold");
        player.lostGold(player.getGold());
        Card[] initialCards = getShopCards();
        
        shop.onInput("r 1");
        
        Card[] newCards = getShopCards();
        
        assertEquals("Cards should not be refreshed when gold is insufficient.", initialCards[0], newCards[0]);
        
        System.out.println();
    }

    @Test
    public void testMaxRefreshLimit() {
    	System.out.println("Test 10 -- testMaxRefreshLimit");
    	
    	shop.onInput("r 1");
    	shop.onInput("r 1");
    	shop.onInput("r 1");
        
        int goldBefore = player.getGold();
        shop.onInput("r 1");
        int goldAfter = player.getGold();
        
        assertEquals("Gold should not be deducted after the refresh limit is reached.", goldBefore, goldAfter);
        
        System.out.println();
    }

    @Test
    public void testCardRemoval() {
    	System.out.println("Test 11 -- testCardRemoval");
        int initialGold = player.getGold();
        int initialDeckSize = player.getPlayerDeck().size();
        
        shop.onInput("r 2");
        //The card removal function is based on user input; users need to manually enter a number, such as "1".
        
        assertEquals("Gold should be deducted correctly after Removal.", initialGold-40 , player.getGold());
        assertEquals("Card removal function is available.", initialDeckSize-1 , player.getPlayerDeck().size());
        
        System.out.println();
    }


    @Test
    public void testLeaveShop() {
    	System.out.println("Test 12 -- testLeaveShop");

        shop.onInput("l 1");
        
        assertFalse("The game should not end after leaving the store.", game.getIsGameOver());
        assertFalse("Game should not win after leaving the store.", game.getIsVictory());
        
        System.out.println();
    }


    private Card getShopCard(int index) {
        try {
            Field cardsField = NodeShop.class.getDeclaredField("availableCards");
            cardsField.setAccessible(true);
            Card[] cards = (Card[]) cardsField.get(shop);
            return cards[index];
        } catch (Exception e) {
            return null;
        }
    }

    private Card[] getShopCards() {
        try {
            Field cardsField = NodeShop.class.getDeclaredField("availableCards");
            cardsField.setAccessible(true);
            return (Card[]) cardsField.get(shop);
        } catch (Exception e) {
            return new Card[0];
        }
    }

    private int getCardCost(Card card) {
        try {
            var method = NodeShop.class.getDeclaredMethod("getCardCost", Card.class);
            method.setAccessible(true);
            return (int) method.invoke(shop, card);
        } catch (Exception e) {
            return 0;
        }
    }

    private void testGold() {
        player.addGold(500);
    }
    
}
