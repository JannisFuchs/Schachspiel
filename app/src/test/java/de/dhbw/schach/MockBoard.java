package de.dhbw.schach;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.Map;

public class MockBoard implements IBoard
{
	private final Map<Field, IPiece> board;

	public MockBoard(Map<Field, IPiece> importantPieces)
	{
		this.board = importantPieces;
	}


	@Override
	public void makeMove(Move move) throws Move.IllegalMoveException
	{

	}

	@Override
	public void simulateMove(Move move) throws Move.IllegalMoveException
	{

	}


	@Override
	public IPiece getPiece(Field currentField)
	{
		IPiece piece = board.get(currentField);
		if (piece == null)
		{
			return new None(PieceColor.RESET);
		}
		return piece;
	}

	@Override
	public Field getKingField(PieceColor boardColor)
	{
		Field kingField = new Field(-1, -1);
		for (Field field : board.keySet())
		{
			if (board.get(field).getPieceType() == PieceType.KING && board.get(field).getColor() == boardColor)
			{
				kingField = field;
			}
		}
		return kingField;

	}

	@Override
	public int getRowLength()
	{
		return 8;
	}

	@Override
	public int getColumnLength()
	{
		return 8;
	}

	@Override
	public IBoard copy()
	{
		return new MockBoard(board);
	}


}
