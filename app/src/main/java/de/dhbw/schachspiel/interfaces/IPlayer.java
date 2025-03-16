package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Move;

import java.util.Scanner;

public interface IPlayer {
    Move readMove(Scanner s);
    IPiece getMove();
}
