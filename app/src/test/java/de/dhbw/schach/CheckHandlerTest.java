package de.dhbw.schach;

import de.dhbw.schachspiel.classes.Board;
import de.dhbw.schachspiel.classes.CheckHandler;
import de.dhbw.schachspiel.classes.PieceColor;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckHandlerTest
{


    CheckHandler setUp(String startPosition, PieceColor color)
    {

        IPiece[][] pieces = PieceFactory.createBoardFromFEN(startPosition);
        IBoard board = new Board(pieces);
        return new CheckHandler(board, color);
    }

    @Test
    void canOtherPieceBlock()
    {
        String startPosition = "rnbqkb1r/pp3ppp/5n2/1B1pp1B1/8/3P4/PPP2PPP/RN1QK1NR";
        CheckHandler handler = setUp(startPosition, PieceColor.BLACK);
        Assertions.assertTrue(handler.isCheck());
        Assertions.assertTrue(handler.canOtherPieceDefendKing());
        Assertions.assertTrue(handler.pieceCanMove(PieceType.KING));
        Assertions.assertFalse(handler.isMate());
    }

    @Test
    void canKingMove()
    {
        String startPosition = "rnbqkb1r/pppB1pp1/4p3/4N2p/4n3/8/PPPP1PPP/RNBQK2R";
        CheckHandler handler = setUp(startPosition, PieceColor.BLACK);
        Assertions.assertTrue(handler.canOtherPieceDefendKing());
        Assertions.assertTrue(handler.pieceCanMove(PieceType.KING));
        Assertions.assertFalse(handler.isMate());
    }
    @Test
    void isCheck(){
        String startPosition = "rnbqkb1r/pppB1pp1/4p3/4N2p/4n3/8/PPPP1PPP/RNBQK2R";
        CheckHandler handler = setUp(startPosition, PieceColor.BLACK);
        Assertions.assertTrue(handler.isCheck());

    }

    @Test
    void isMate()
    {
        String startPosition = "1R4k1/5ppp/8/8/8/8/8/3K4";
        CheckHandler handler = setUp(startPosition, PieceColor.BLACK);
        Assertions.assertTrue(handler.isCheck());
        Assertions.assertFalse(handler.canOtherPieceDefendKing());
        Assertions.assertFalse(handler.pieceCanMove(PieceType.KING));
        Assertions.assertTrue(handler.isMate());
    }
}
