package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.*;

public interface IBoard {
    IPiece getPiece(Field currentField);

    int getRowLength();

    int getColumnLength();

    void makeMove(Move move) throws Move.IllegalMoveException;


}
