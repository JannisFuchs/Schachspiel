package de.dhbw.schach;
import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PawnTest {
    private  IBoard board;
    @BeforeEach
    void setUp() {
        IPiece [][] basicBoard = new IPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                basicBoard[i][j] = new None(Color.RESET);
            }
        }
        board = new Board(basicBoard);
    }
    @Test
    void capture() throws Move.IllegalMoveException {
        Move move = new Move("exd5", Color.WHITE);
        board[4][4] = PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //e4 captures on d5
        board[3][3] = PieceFactory.createPieceFromType(PieceType.PAWN, Color.BLACK); //d5
        board[4][2] = PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //c4 makes move ambiguous
        Field f = move.piece.calculateStartField(move, board);
        Assertions.assertEquals(new Field(4, 4), f);
    }
    @Test
    void move2Squares() throws Move.IllegalMoveException {
        Move move = new Move("e4", Color.WHITE);
        board[6][4] = PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //e2
        Field f = move.piece.calculateStartField(move, board);
        Assertions.assertEquals(new Field(6, 4), f);
    }


}
