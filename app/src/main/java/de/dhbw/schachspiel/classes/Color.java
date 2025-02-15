package de.dhbw.schachspiel.classes;

public enum Color {
	BLACK("\u001B[38;2;0;0;0m", "\u001B[48;2;151;106;79m"),
	WHITE("\u001B[38;2;255;255;255m", "\u001B[48;2;210;187;151m"),
	//reset is necessary, so the rest of the screen is black and only the board has color
	RESET("\u001B[0m", "\u001B[0m");
	public final String foreGround;
	public final String backGround;

	Color(String foreGround, String backGround) {
		this.backGround = backGround;
		this.foreGround = foreGround;
	}
}
