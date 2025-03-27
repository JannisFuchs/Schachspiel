package de.dhbw.schachspiel.classes;

public enum PieceColor
{
	BLACK,
	WHITE;

	

	public PieceColor getOtherColor()
	{
		if (this == BLACK)
		{
			return WHITE;
		}
		return BLACK;
	}
}
