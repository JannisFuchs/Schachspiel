package de.dhbw.schachspiel.classes;

public enum PieceColor
{
	BLACK,
	WHITE,
	RESET;
	

	public PieceColor getOtherColor()
	{
		if (this == BLACK)
		{
			return WHITE;
		}
		if (this == WHITE)
		{
			return BLACK;
		}
		return RESET;
	}
}
