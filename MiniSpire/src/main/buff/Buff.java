package main.buff;

public interface Buff {
	
	int duration = 0;
	String name = "";

	//void onUpdate();
	void onEndTurn();

	void setDuration(int duration);
	void extendDuration(int duration);
	int getDuration();
	String getName();
}
