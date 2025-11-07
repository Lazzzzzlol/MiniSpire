package main.node;

public class Node {
	
	private String name;
	
	public Node(String name) {
		this.name = name;
	};
	
	public void onUpdate() {};
	public void onStartTurn() {};
	public void onDraw() {};
	public void onInput(String input) {};
	
	public String getName() {
		return name;
	}

	public boolean isValidInput(String input) {
        if (input == null) return false;
		if (input.equals("e")) return true;
        String[] parts = input.split(" ");
        return (parts.length == 2);
    }
}
