package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.*;

public interface IBoard
{
	IPiece getPiece(Field currentField);

	Field getKingField(PieceColor pieceColor);

	int getRowLength();

	int getColumnLength();

	void makeMove(Move move) throws Move.IllegalMoveException;

	Field simulateMove(Move move) throws Move.IllegalMoveException;

	FieldSet getFieldsWithPiece(IPiece piece);


	class UncommittedChangesException extends IllegalStateException
	{
		public UncommittedChangesException(String message)
		{
			super(message);
		}
	}
}
