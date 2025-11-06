package main.game;

import main.Main;
import main.Util;
import main.node.Node;
import main.node.NodeDecider;
import main.player.Player;
import main.resourceFactory.CardFactory;
import main.resourceFactory.EnemyFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Game {
	
	private static Game INSTANCE = null;
	
	public static Game getInstance() {
		if (INSTANCE == null) 
			INSTANCE = new Game();
		return INSTANCE;
	}
	
	private Player player;
	private Boolean isGameOver;
	
	private int nodeIndex;
	private Node currentNode;
	private ArrayList<Node> nodeHistory;
	private NodeDecider nodeDecider;
	private int eliteEncounterCount;
	
	private Boolean isEndTurn;
	private Boolean hasDrawnNodeInfo;
	
	private Boolean isVictory;
	private int score;
	
	public Game() {
		
		this.player = Player.getInstance();
		this.isGameOver = false;
		
		this.nodeIndex = 1;
		this.currentNode = null;
		this.nodeHistory = new ArrayList<Node>();
		this.nodeDecider = NodeDecider.getInstance();
		
		this.eliteEncounterCount = 0;
		
		this.isEndTurn = false;
		this.hasDrawnNodeInfo = false;
		
		this.isVictory = false;
		this.score = 0;
	}
	
	public void init() {
		
		EnemyFactory.getInstance().init();
		CardFactory.getInstance().init();
		
		player.drawCardListInit();
	}
	
	public void onUpdate() {
		
		if (currentNode == null)
			currentNode = nodeDecider.decideNode(nodeIndex, nodeHistory, eliteEncounterCount);
		
		onStartTurn();
		currentNode.onUpdate();
		
		if (player.getHp() <= 0)
			isGameOver = true;
	}
	
	public void onStartTurn() {
		currentNode.onStartTurn();
	}
	
	public void onDraw() {
		// If currentNode is null we have advanced to the next node
		// but haven't decided/generated it yet (advanceToNextNode sets
		// currentNode = null). In that case skip drawing now to avoid
		// a NullPointerException; the next update() will set the node.
		if (currentNode == null)
			return;

		if (!hasDrawnNodeInfo)
			drawNodeInfo(currentNode);
		drawNodeContent(currentNode);
        
	}
	
	public void onInput(String input) {
		currentNode.onInput(input);
	}
	
	public Boolean isValidInput(String input) {
		return currentNode.isValidInput(input);
	}
	
	private void drawNodeInfo(Node node) {
		
		int spaceNum1 = Util.getCenterAlignSpaceNum((String)("Node " + nodeIndex), Main.longLine.length());
		int spaceNum2 = Util.getCenterAlignSpaceNum(node.getName(), Main.longLine.length());
		
		System.out.println(Main.longLine);
		
		for (int i = 0; i < spaceNum1 ; i++)
			System.out.print(" ");
		System.out.println((String)("Node " + nodeIndex));
		
		Main.executor.schedule(() -> {
			for (int i = 0; i < spaceNum2 ; i++)
				System.out.print(" ");
			System.out.println(node.getName());
			System.out.println(Main.longLine);
		}, 1, TimeUnit.SECONDS);
		
		hasDrawnNodeInfo = true;
	}
	
	private void drawNodeContent(Node node) {
		
		Main.executor.schedule(() -> {
			node.onDraw();
		}, 2, TimeUnit.SECONDS);
	}
	
	public void setIsGameOver(Boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public Boolean getIsGameOver() {
		return isGameOver;
	}
	
	public void setIsVictory(Boolean isVictory) {
		this.isVictory = isVictory;
	}
	
	public Boolean getIsVictory() {
		return isVictory;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setIsEndTurn(Boolean isEndTurn) {
		this.isEndTurn = isEndTurn;
	}
	
	public Boolean getIsEndTurn() {
		return isEndTurn;
	}
	
	public void advanceToNextNode() {
		nodeHistory.add(currentNode);
		nodeIndex += 1;
		currentNode = null;
		// Reset node-info-drawn flag so the next node's header will be shown
		// when it is decided and first drawn.
		hasDrawnNodeInfo = false;
	}
}
