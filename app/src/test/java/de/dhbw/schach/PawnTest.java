package de.dhbw.schach;
import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.classes.pieces.Pawn;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

class PawnTest {

    @Test
    void capture() throws Move.IllegalMoveException {
        Move move = new Move("exd5", Color.WHITE);
        Map<Field, IPiece> importantPieces = Map.of(
                new Field(4, 4), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE), //e4
                new Field(3, 3), PieceFactory.createPieceFromType(PieceType.PAWN, Color.BLACK), //d5
                new Field(4, 2), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE), //c4
                new Field(4, 3), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE) //d4
        );
        IBoard board = new MockBoard(importantPieces);
        FieldSet possibleFields = new FieldSet();
        possibleFields.add(new Field(4, 2));
        possibleFields.add(new Field(4, 4));
        Field f = move.piece.calculateStartField(possibleFields,move, board);
        Assertions.assertEquals(new Field(4, 4), f);
    }
    @Test
    void move2Squares() throws Move.IllegalMoveException {
        Move move = new Move("e4", Color.WHITE);
        Map<Field, IPiece> importantPieces = Map.of(
                new Field(6, 4), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE), //e2
                new Field(6, 3), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE), //d2
                new Field(6, 2), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE) //f2
        );
        IBoard board = new MockBoard(importantPieces);
        FieldSet possibleFields = new FieldSet();
        possibleFields.add(new Field(6, 4));
        possibleFields.add(new Field(6, 3));
        possibleFields.add(new Field(6, 2));
        Field f = move.piece.calculateStartField(possibleFields,move, board);
        Assertions.assertEquals(new Field(6, 4), f);
    }
    @Test
    void moveBlocked() throws Move.IllegalMoveException {
        Move move = new Move("e4", Color.WHITE);
        Map<Field, IPiece> importantPieces = Map.of(
                new Field(6, 4), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE), //e2
                new Field(5, 4), PieceFactory.createPieceFromType(PieceType.ROOK, Color.WHITE), //e3
                new Field(6, 2), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE) //f4
        );
        IBoard board = new MockBoard(importantPieces);
        FieldSet possibleFields = new FieldSet();
        possibleFields.add(new Field(6, 4));
        possibleFields.add(new Field(6, 3));
        possibleFields.add(new Field(6, 2));
        Assertions.assertThrows(Move.IllegalMoveException.class, () -> move.piece.calculateStartField(possibleFields,move, board));
    }
    @Test
    void moveOneSquare() throws Move.IllegalMoveException {
        Move move = new Move("e4", Color.WHITE);
        Map<Field, IPiece> importantPieces = Map.of(
                new Field(6, 4), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE), //e2
                new Field(5, 4), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE), //e3
                new Field(6, 2), PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE) //f4
        );
        IBoard board = new MockBoard(importantPieces);
        FieldSet possibleFields = new FieldSet();
        possibleFields.add(new Field(6, 4));

        possibleFields.add(new Field(5, 4));
        possibleFields.add(new Field(6, 3));
        possibleFields.add(new Field(6, 2));
        Field f = move.piece.calculateStartField(possibleFields,move, board);
        Assertions.assertEquals(new Field(5, 4), f);
    }

}
