package de.dhbw.schachspiel;

import de.dhbw.schachspiel.classes.PieceColor;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.FieldSet;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.pieces.Bishop;
import de.dhbw.schachspiel.classes.pieces.Pawn;
import de.dhbw.schachspiel.classes.pieces.Rook;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class RookTest
{
	@Test
	void row() throws Move.IllegalMoveException
	{
		Move move = new Move("Raxd3", PieceColor.WHITE);
		Map<Field, IPiece> importantPieces = Map.of(
				new Field(5, 0), new Rook(PieceColor.WHITE), //a3
				new Field(5, 3), new Pawn(PieceColor.BLACK), //d3
				new Field(5, 6), new Pawn(PieceColor.WHITE) //g3
		);
		IBoard board = new MockBoard(importantPieces);
		FieldSet possibleFields = new FieldSet();
		possibleFields.add(new Field(5, 0));
		possibleFields.add(new Field(5, 6));
		Field f = move.piece.calculateStartField(possibleFields, move, board);
		Assertions.assertEquals(new Field(5, 0), f);
	}

	@Test
	void ambiguousMove() throws Move.IllegalMoveException
	{
		Move move = new Move("Ra8", PieceColor.WHITE);
		Map<Field, IPiece> importantPieces = Map.of(
				new Field(5, 0), new Rook(PieceColor.WHITE), //a3
				new Field(0, 3), new Rook(PieceColor.WHITE) //d8
		);
		IBoard board = new MockBoard(importantPieces);
		FieldSet possibleFields = new FieldSet();
		possibleFields.add(new Field(5, 0));
		possibleFields.add(new Field(0, 3));
		Assertions.assertThrows(Move.IllegalMoveException.class, () -> move.piece.calculateStartField(possibleFields, move, board));

	}

	@Test
	void column() throws Move.IllegalMoveException
	{
		Move move = new Move("Ra8", PieceColor.WHITE);
		Map<Field, IPiece> importantPieces = Map.of(
				new Field(5, 0), new Rook(PieceColor.WHITE), //a3
				new Field(0, 3), new Rook(PieceColor.WHITE), //d8
				new Field(0, 2), new Bishop(PieceColor.BLACK) //c8
		);
		IBoard board = new MockBoard(importantPieces);
		FieldSet possibleFields = new FieldSet();
		possibleFields.add(new Field(5, 0));
		possibleFields.add(new Field(0, 3));
		Field f = move.piece.calculateStartField(possibleFields, move, board);
		Assertions.assertEquals(new Field(5, 0), f);

	}
}
