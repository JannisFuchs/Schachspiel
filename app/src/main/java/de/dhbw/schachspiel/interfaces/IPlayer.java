package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Move;

import java.util.Scanner;

//maybe use this as a mock
public interface IPlayer
{
    Move readMove(Scanner s);

    Color getColor();

    IPiece getMove();
}
