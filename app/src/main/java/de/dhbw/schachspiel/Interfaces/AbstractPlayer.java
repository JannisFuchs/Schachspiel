package de.dhbw.schachspiel.Interfaces;

import de.dhbw.schachspiel.Classes.Move;

public interface AbstractPlayer {
    Move readMove();
    void setMove(AbstractPiece move);
    AbstractPiece getMove();
}
