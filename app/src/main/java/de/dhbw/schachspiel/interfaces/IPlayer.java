package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Move;

import java.util.Scanner;

//maybe use this as a mock
public interface IPlayer {
    Move readMove(Scanner s);
    IPiece getMove();
}
