package de.dhbw.schach;

import de.dhbw.schachspiel.classes.Board;
import de.dhbw.schachspiel.classes.CheckHandler;
import de.dhbw.schachspiel.classes.PieceColor;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckHandlerTest
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
        String startPosition = "rnbqk1nr/pppp1ppp/4p3/8/1b6/3P3P/PPP1PPP1/RNBQKBNR";
        CheckHandler handler = setUp(startPosition, PieceColor.WHITE);
        Assertions.assertTrue(handler.isCheck());
        Assertions.assertTrue(handler.canOtherPieceDefendKing());
        Assertions.assertFalse(handler.kingCanMove());
        Assertions.assertFalse(handler.isMate());
    }
}
