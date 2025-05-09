package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public record None(PieceColor c) implements IPiece
{


	@Override
	public char getSymbol()
	{
		return ' ';
	}

	@Override
	public PieceColor getColor()
	{
		return c;
	}

	@Override
	public PieceType getPieceType()
	{
		return PieceType.NONE;
	}

	@Override
	public FieldSet getCandidateFields(Field target, IBoard board)
	{
		return new FieldSet();
	}

	@Override
	public Field calculateStartField(FieldSet set, Move move, IBoard board)
	{
		throw new IllegalCallerException("This class is just a null wrapper and this method should not be called");
	}

	@Override
	public boolean isAbleToAttack(Field start, Field target, PieceColor color, IBoard board)
	{
		return false;
	}
}
