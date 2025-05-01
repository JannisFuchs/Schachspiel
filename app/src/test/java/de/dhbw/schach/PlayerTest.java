package de.dhbw.schach;

import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceColor;
import de.dhbw.schachspiel.classes.Player;
import de.dhbw.schachspiel.interfaces.IPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class PlayerTest
{
    @Test
    void readMoveSuccess() throws Move.IllegalMoveException
    {
        IPlayer player = new Player(PieceColor.WHITE);
        Scanner scanner = new Scanner("e4");
        Move move = player.readMove(scanner);
        Move correctMove = new Move("e4", PieceColor.WHITE);
        Assertions.assertEquals(correctMove, move);
    }

    @Test
    void readMoveFailure()
    {
        IPlayer player = new Player(PieceColor.WHITE);
        Scanner scanner = new Scanner("e9");
        Assertions.assertThrows(Move.IllegalMoveException.class, () -> player.readMove(scanner));
    }
}
