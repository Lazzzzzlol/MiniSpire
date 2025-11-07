package main.buff.oneFightBuff;

import main.buff.Buff;

public class BuffResurrection implements Buff {

	String name = "Resurrection";
	int duration = 0;
	boolean used = false;
	
	public BuffResurrection(int duration) {
		this.duration = duration;
	}
	
	@Override
	public void onEndTurn() {
		this.duration -= 0; // Permanent until used
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
	
	public boolean isUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}
}

