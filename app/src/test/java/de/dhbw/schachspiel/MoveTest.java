package de.dhbw.schachspiel;

import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceColor;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.pieces.Pawn;
import de.dhbw.schachspiel.classes.pieces.Rook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoveTest
{
	@Test
	void PawnMove() throws Move.IllegalMoveException
	{
		String move = "e4";
		Move m = new Move(move, PieceColor.WHITE);
		Assertions.assertInstanceOf(Pawn.class, m.piece);
	}

	@Test
	void IllegalMove()
	{
		String move = "e50";
		Assertions.assertThrows(Move.IllegalMoveException.class, () -> new Move(move, PieceColor.WHITE));
	}

	@Test
	void normalMove() throws Move.IllegalMoveException
	{
		String move = "Rxd1#";
		Move m = new Move(move, PieceColor.WHITE);
		Assertions.assertInstanceOf(Rook.class, m.piece);
		Field f = new Field(7, 3);
		Assertions.assertEquals(f, m.target);
		Assertions.assertTrue(m.isMate);
	}
}
