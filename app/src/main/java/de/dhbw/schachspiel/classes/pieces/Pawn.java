package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.Iterator;

public record Pawn(PieceColor c) implements IPiece
{

	@Override
	public char getSymbol()
	{
		return '♟';
	}

	@Override
	public PieceColor getColor()
	{
		return c;
	}

	@Override
	public PieceType getPieceType()
	{
		return PieceType.PAWN;
	}

	@Override
	public FieldSet getCandidateFields(Field target, IBoard board)
	{
		FieldSet candidateFields = new FieldSet();

		candidateFields.add(new Field(target.row() + 1, target.column()));
		candidateFields.add(new Field(target.row() + 2, target.column()));
		candidateFields.add(new Field(target.row() + 1, target.column() + 1));
		candidateFields.add(new Field(target.row() + 1, target.column() - 1));
		candidateFields.add(new Field(target.row() - 1, target.column()));
		candidateFields.add(new Field(target.row() - 2, target.column()));
		candidateFields.add(new Field(target.row() - 1, target.column() + 1));
		candidateFields.add(new Field(target.row() - 1, target.column() - 1));
		return candidateFields;
	}

	@Override
	public Field calculateStartField(FieldSet fields, Move move, IBoard board) throws Move.IllegalMoveException
	{
		/*
		 * 4 possible start squares from each color
		 * 2 from capture
		 * 2 from normal moving (pawn can move two square at a time)
		 */
		FieldSet candidateFields = filterBehindSquares(fields, move.target, move.piece.getColor());
		if (candidateFields.isEmpty())
		{
			throw new Move.IllegalMoveException("No pieces found");
		}
		if (move.isCapture)
		{
			return calculateCapture(move, board, candidateFields);
		}
		return calculateNormalMove(move, board, candidateFields);
	}

	private FieldSet filterBehindSquares(FieldSet candidateFields, Field target, PieceColor piecePieceColor)
	{
		Iterator<Field> allFields = candidateFields.getIterator();
		FieldSet reachableFields = new FieldSet();
		while (allFields.hasNext())
		{
			Field field = allFields.next();
			if (piecePieceColor == PieceColor.WHITE && field.row() < target.row())
			{
				reachableFields.add(field);
			}
			else
			{
				reachableFields.add(field);
			}
		}
		return reachableFields;
	}

	private Field calculateCapture(Move move, IBoard board, FieldSet candidateFields) throws Move.IllegalMoveException
	{
		Field target = move.target;
		PieceColor enemyPieceColor = PieceColor.WHITE;
		if (move.piece.getColor() == PieceColor.WHITE)
		{
			enemyPieceColor = PieceColor.BLACK;
		}
		if (!target.isOccupiedByColor(enemyPieceColor, board))
		{
			throw new Move.NoCaptureException();
		}
		candidateFields = candidateFields.filterReachableByDiagonal(target, board);
		if (candidateFields.isEmpty())
		{
			throw new Move.IllegalMoveException("No pieces found");
		}
		if (candidateFields.size() == 1)
		{
			return candidateFields.getSingleItem();
		}
		if (candidateFields.size() > 2)
		{
			throw new Move.IllegalMoveException("This should not happen");
		}
		int column = move.start.column();
		if (column == -1)
		{
			throw new Move.AmbiguousMoveException();
		}
		return candidateFields.findRow(column);

	}


	private Field calculateNormalMove(Move move, IBoard board, FieldSet candidateFields) throws Move.IllegalMoveException
	{
		Field target = move.target;
		if (target.isOccupiedByColor(PieceColor.BLACK, board) || target.isOccupiedByColor(PieceColor.WHITE, board))
		{
			throw new Move.IllegalMoveException("Field is occupied");
		}

		candidateFields = candidateFields.filterReachableByColumn(target, board);
		if (candidateFields.isEmpty())
		{
			throw new Move.IllegalMoveException("No pieces found");
		}
		if (candidateFields.size() > 1)
		{
			throw new Move.IllegalMoveException("This should not happen");
		}
		return validateMove(candidateFields.getSingleItem(), move, board);

	}

	private Field validateMove(Field start, Move move, IBoard board) throws Move.IllegalMoveException
	{
		Field target = move.target;
		int diffRow = Math.abs(start.row() - target.row());
		if (diffRow == 1)
		{
			return start;
		}
		if (diffRow > 2)
		{
			throw new Move.IllegalMoveException("This should not happen");
		}
		if (move.piece.getColor() == PieceColor.WHITE && start.row() == board.getColumnLength() - 2)
		{
			return start;
		}
		if (move.piece.getColor() == PieceColor.BLACK && start.row() == 1)
		{
			return start;
		}
		throw new Move.IllegalMoveException("was not the start field");

	}
}
