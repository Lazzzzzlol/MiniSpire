package main.buff.positiveBuff;

import main.buff.Buff;

public class BuffSteadfast implements Buff {

	String name = "Steadfast";
	int duration = 0;
	
	public BuffSteadfast(int duration) {
		this.duration = duration;
	}
	
	@Override
	public void onEndTurn() {
		this.duration -= 0; // Permanent until triggered
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

