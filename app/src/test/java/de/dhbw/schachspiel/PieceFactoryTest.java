package de.dhbw.schachspiel;

import de.dhbw.schachspiel.classes.PieceColor;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.classes.pieces.Rook;
import de.dhbw.schachspiel.interfaces.IPiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceFactoryTest
{
    @Test
    void startPosition()
    {
        String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        IPiece[][] pieces = PieceFactory.createBoardFromFEN(startPosition);
        Assertions.assertEquals(new Rook(PieceColor.WHITE), pieces[7][0]);
    }
}
