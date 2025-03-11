package de.dhbw.schach;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.classes.pieces.Pawn;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.classes.pieces.Rook;
import de.dhbw.schachspiel.interfaces.AbstractPiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnTest {
    private final AbstractPiece[][] board = new AbstractPiece[8][8];
    @BeforeEach
    void setUp() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new None(Color.RESET);
            }
        }
    }
    @Test
    void captureAmbiguous() throws Move.IllegalMoveException {
        Move move = new Move("exd5", Color.WHITE);
        board[4][4] = PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //e4 captures on d5
        board[3][3] = PieceFactory.createPieceFromType(PieceType.PAWN, Color.BLACK); //d5
        board[4][2] = PieceFactory.createPieceFromType(PieceType.PAWN, Color.WHITE); //e5 is neighbour
        Field f = move.piece.calculateStartField(move, board);
        Assertions.assertEquals(new Field(2, 3), f);
    }

}
