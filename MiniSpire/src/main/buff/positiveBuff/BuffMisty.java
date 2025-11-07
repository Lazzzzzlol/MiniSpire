package main.buff.positiveBuff;

import main.buff.Buff;

public class BuffMisty implements Buff {

	String name = "Misty";
	int duration = 0;
	
	public BuffMisty(int duration) {
		this.duration = duration;
	}
	
	@Override
	public void onEndTurn() {
		this.duration -= 0; // Permanent until removed
	}
	
	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public int getDuration() {
		return this.duration;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void extendDuration(int duration) {
		this.duration += duration;
	}
}

