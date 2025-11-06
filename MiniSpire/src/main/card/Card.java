package main.card;

public interface Card {

	Boolean getCanPlay();
	String getName();
	String getInfo();
	int getCost();
	String getType();
	String getRarity();
}
