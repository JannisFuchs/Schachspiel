package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IPiece;

public class FieldPiecePair
{
	private final Field field;
	private final IPiece piece;

	public FieldPiecePair(Field field, IPiece piece)
	{
		this.field = field;
		this.piece = piece;
	}

	public IPiece getPiece()
	{
		return piece;
	}

	public Field getField()
	{
		return field;
	}
}
