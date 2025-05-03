package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.logger.LogHandler;
import de.dhbw.schachspiel.classes.logger.LogType;
import de.dhbw.schachspiel.interfaces.IPlayer;

import java.util.Scanner;

public class Player implements IPlayer
{

    private final PieceColor pieceColor;
    private final LogHandler logger;

    public Player(PieceColor pieceColor, LogHandler logger)
    {
        this.logger = logger;
        this.pieceColor = pieceColor;
    }

    @Override
    public Move readMove(Scanner s) throws Move.IllegalMoveException
    {
        String move;
        logger.log(pieceColor.name() + " Enter your move : \n", LogType.GAME);
        move = s.nextLine();
        logger.log(move,LogType.TEST);
        return new Move(move, pieceColor);
    }

    @Override
    public PieceColor getColor()
    {
        return pieceColor;
    }
}
