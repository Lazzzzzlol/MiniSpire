package main.buff.oneRoundBuff;

import main.buff.Buff;

public class BuffStratagem implements Buff{

	String name = "Stratagem";
	int duration = 0;
	
	public BuffStratagem(int duration) {
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