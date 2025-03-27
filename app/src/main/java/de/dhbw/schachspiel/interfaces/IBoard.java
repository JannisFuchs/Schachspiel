package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.FieldSet;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceColor;

public interface IBoard
{
    IPiece getPiece(Field currentField);

    Field getKingField(PieceColor pieceColor);

    int getRowLength();

    int getColumnLength();

    void makeMove(Move move) throws Move.IllegalMoveException;

    Field simulateMove(Move move) throws Move.IllegalMoveException;

    FieldSet getFieldsWithPiece(IPiece piece);

    void undoMove();

    void commitMove();
}
