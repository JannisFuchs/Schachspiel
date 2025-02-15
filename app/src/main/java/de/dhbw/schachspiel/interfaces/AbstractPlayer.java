package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Move;

public interface AbstractPlayer {
    Move readMove();
    void setMove(AbstractPiece move);
    AbstractPiece getMove();
}
