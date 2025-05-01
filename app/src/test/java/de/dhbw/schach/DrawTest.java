package de.dhbw.schach;

import de.dhbw.schachspiel.classes.Board;
import de.dhbw.schachspiel.classes.CheckHandler;
import de.dhbw.schachspiel.classes.PieceColor;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DrawTest
{
    @Test
    public void startPosition(){
        String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        IBoard board = new Board(PieceFactory.createBoardFromFEN(startPosition));
        CheckHandler handler = new CheckHandler(board, PieceColor.WHITE);
        Assertions.assertFalse(handler.isStalemate());
        Assertions.assertFalse(handler.isInsufficientMaterial());
        Assertions.assertFalse(handler.isThreeFold(List.of()));
        Assertions.assertFalse(handler.fiftyMoveRule(List.of()));
    }

}
