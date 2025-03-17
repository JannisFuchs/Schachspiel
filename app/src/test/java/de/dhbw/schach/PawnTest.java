package de.dhbw.schach;
import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PawnTest {
    private  MockBoard board;
    @BeforeEach
    void setUp() {
        IPiece [][] basicBoard = new IPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                basicBoard[i][j] = new None(Color.RESET);
            }
        }
        board = new MockBoard(basicBoard);
    }
    @Test
    void capture() throws Move.IllegalMoveException {
        Move move = new Move("exd5", Color.WHITE);
        IPiece capturer= PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //e4 captures on d5
        board.setField(new Field(4, 4), capturer);
        IPiece caputred = PieceFactory.createPieceFromType(PieceType.PAWN, Color.BLACK); //d5
        board.setField(new Field(3, 3), caputred);
        IPiece neighbour= PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //c4 makes move ambiguous
        board.setField(new Field(4, 2), neighbour);
        List<Field> possibleFields = board.selectFields(move);
        Field f = move.piece.calculateStartField(possibleFields,move, board);
        Assertions.assertEquals(new Field(4, 4), f);
    }
    @Test
    void move2Squares() throws Move.IllegalMoveException {
        Move move = new Move("e4", Color.WHITE);
        IPiece mover = PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //e2
        board.setField(new Field(6, 4), mover);
        List<Field> possibleFields = board.selectFields(move);
        Field f = move.piece.calculateStartField(possibleFields,move, board);
        Assertions.assertEquals(new Field(6, 4), f);
    }
    @Test
    void moveBlocked() throws Move.IllegalMoveException {
        Move move = new Move("e4", Color.WHITE);
        IPiece mover = PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //e2
        board.setField(new Field(6, 4), mover);
        IPiece blocker = PieceFactory.createPieceFromType(PieceType.PAWN, Color.BLACK); //e4
        board.setField(new Field(4, 4), blocker);
        List<Field> possibleFields = board.selectFields(move);
        Assertions.assertThrows(Move.IllegalMoveException.class, () -> move.piece.calculateStartField(possibleFields,move, board));
    }


}
