package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;

import java.util.List;

public interface IBoard {
    IPiece getPiece(Field currentField);

    int getRowLength();

    int getColumnLength();

    boolean isOccupiedByColor(Color color, Field target);

    void makeMove(Move move) throws Move.IllegalMoveException;

    List<Field> getFieldsWithPiece(List<Field> candidateFields, IPiece piece);
}
