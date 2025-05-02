package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IPlayer;

import java.util.Scanner;

public class Player implements IPlayer
{

    private final PieceColor pieceColor;

    public Player(PieceColor pieceColor)
    {
        this.pieceColor = pieceColor;
    }

    @Override
    public Move readMove(Scanner s) throws Move.IllegalMoveException
    {
        String move;
        System.out.print (pieceColor.name() + " Enter your move : ");
        move = s.nextLine();
        System.out.println(move);
        return new Move(move, pieceColor);
    }

    @Override
    public PieceColor getColor()
    {
        return pieceColor;
    }
}
