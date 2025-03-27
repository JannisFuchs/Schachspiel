package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public record Bishop(PieceColor c) implements IPiece
{

	@Override
	public char getSymbol()
	{
		return '♝';
	}

	@Override
	public PieceColor getColor()
	{
		return c;
	}

	@Override
	public PieceType getPieceType()
	{
		return PieceType.BISHOP;
	}

	@Override
	public FieldSet getCandidateFields(Field target, IBoard board)
	{
		FieldSet candidateFields = new FieldSet();
		int min = Math.min(board.getRowLength(), board.getColumnLength());
		for (int diagonal = 1; diagonal < min; diagonal++)
		{
			candidateFields.add(new Field(target.row() + diagonal, target.column() + diagonal));
			candidateFields.add(new Field(target.row() - diagonal, target.column() + diagonal));
			candidateFields.add(new Field(target.row() + diagonal, target.column() - diagonal));
			candidateFields.add(new Field(target.row() - diagonal, target.column() - diagonal));
		}
		return candidateFields;
	}

	@Override
	public Field calculateStartField(FieldSet candidateFields, Move move, IBoard board) throws Move.IllegalMoveException
	{
		Field target = move.target;
		FieldSet reachableFields = candidateFields.filterReachableByDiagonal(target, board);
		if (reachableFields.isEmpty())
		{
			throw new Move.IllegalMoveException("Field not reachable");
		}
		//with this notation it is not possible to get a second bishop for a color so this non-ambiguous
		return reachableFields.getSingleItem();
	}

	@Override
	public boolean isAbleToAttack(Field start, Field target, PieceColor color, IBoard board)
	{
		FieldSet candidateFields = new FieldSet();
		candidateFields.add(start);
		FieldSet reachableFields = candidateFields.filterReachableByDiagonal(target, board);
		return !reachableFields.isEmpty();
	}
}
