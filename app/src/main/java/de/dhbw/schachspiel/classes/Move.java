package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move
{
	public final Field start;
	public final Field target;
	public final IPiece piece;
	public final boolean isCheck;
	public final boolean isMate;
	public final boolean isCapture;

	public Move(Field start, Field target, IPiece piece, boolean isCapture, boolean isCheck, boolean isMate)
	{
		this.piece = piece;
		this.start = start;
		this.target = target;
		this.isCapture = isCapture;
		this.isCheck = isCheck;
		this.isMate = isMate;
	}

	/**
	 * @param stringRepresentation : Move in algebraic notation
	 *                             String should match pattern : ([RBQKN])?([a-h])?([1-8])?(x)?([a-h])([1-8]([+#])?)
	 *                             ([RBQKN])? - Piece optional because Pawns have no indicator
	 *                             ([a-h])? - Start column optional
	 *                             ([1-8])? - Start row
	 *                             (x)? -  capture
	 *                             ([a-h]) - End column
	 *                             ([1-8]) - End row
	 *                             ([+#])? - check or mate
	 * @param c                    the color the current pieces have
	 */

	public Move(String stringRepresentation, PieceColor c) throws IllegalMoveException
	{

		Matcher matcher = Pattern.compile("([RBQKN])?([a-h])?([1-8])?(x)?([a-h])([1-8])([+#])?").matcher(stringRepresentation);
		if (!matcher.matches())
		{
			throw new IllegalMoveException("incorrect Notation");
		}
		String pieceLabel = matcher.group(1);
		if (pieceLabel == null)
		{
			piece = PieceFactory.createPieceFromType(PieceType.PAWN, c);
		}
		else
		{
			piece = PieceFactory.createPieceFromType(PieceType.pieceTypeFromChar(pieceLabel.charAt(0)), c);
		}
		int startRow = -1;
		int startCol = -1;

		if (matcher.group(2) != null)
		{
			startCol = Columns.valueOf(matcher.group(2).toUpperCase()).ordinal();
		}
		if (matcher.group(3) != null)
		{
			startRow = 8 - Integer.parseInt(matcher.group(3));
		}
		start = new Field(startRow, startCol);

		isCapture = matcher.group(4) != null;
		if (isCapture && piece.getPieceType() == PieceType.PAWN && startCol == -1)
		{
			throw new IllegalMoveException("Illegal notation");
		}

		int endRow = 8 - Integer.parseInt(matcher.group(6));
		int endCol = Columns.valueOf(matcher.group(5).toUpperCase()).ordinal();
		target = new Field(endRow, endCol);

		if (Objects.equals(matcher.group(7), "+"))
		{
			isCheck = true;
			isMate = false;
		}
		else
		{
			if (Objects.equals(matcher.group(7), "#"))
			{
				isMate = true;
				isCheck = false;
			}
			else
			{
				isCheck = false;
				isMate = false;
			}
		}
	}

	private enum Columns
	{
		A, B, C, D, E, F, G, H
	}

	public static class IllegalMoveException extends Exception
	{
		public IllegalMoveException(String message)
		{

			super("Illegal move : " + message);
		}
	}

	/**
	 * more than one piece can execute this move
	 */
	public static class AmbiguousMoveException extends IllegalMoveException
	{

		public AmbiguousMoveException()
		{
			super("ambiguous");
		}
	}

	/**
	 * The current move is marked as a normal move but the target field contains an enemy piece
	 */
	public static class NoCaptureException extends IllegalMoveException
	{

		public NoCaptureException()
		{
			super("is marked as a normal move but captures a piece");
		}
	}

	/**
	 * The current move is marked as a capture but the target field does not have an enemy piece
	 */
	public static class CaptureException extends IllegalMoveException
	{

		public CaptureException()
		{
			super("is marked as a capture but does not capture a piece");
		}
	}

}
