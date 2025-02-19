package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Move;

import java.util.Scanner;

public interface AbstractPlayer {
    Move readMove(Scanner s);
    void setMove(AbstractPiece move);
    AbstractPiece getMove();
}
