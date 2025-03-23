package de.dhbw.schachspiel.classes;

public record Color(int red, int green, int blue, boolean isBackground)
{
	@Override
	public String toString()
	{
		if (isBackground)
		{
			return String.format("\u001B[48;2;%d;%d;%dm", red, green, blue);
		}
		return String.format("\u001B[38;2;%d;%d;%dm", red, green, blue);
	}
}
