package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.*;

public interface IBoard
{
	IPiece getPiece(Field currentField);

	Field getKingField(PieceColor pieceColor);

	int getRowLength();

	int getColumnLength();

	void makeMove(Move move) throws Move.IllegalMoveException;

	void simulateMove(Move move) throws Move.IllegalMoveException;

	IBoard copy();

}
