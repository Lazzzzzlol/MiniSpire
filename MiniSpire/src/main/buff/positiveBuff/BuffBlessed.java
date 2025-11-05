package main.buff.positiveBuff;

import main.buff.Buff;

public class BuffBlessed implements Buff{

	String name = "Blessed";
	int duration = 0;
	
	public BuffBlessed(int duration) {
		this.duration = duration;
	}
	
	@Override
	public void onEndTurn() {
		this.duration -= 1;
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