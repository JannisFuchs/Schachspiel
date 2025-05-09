package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.*;

public interface IBoard
{
    IPiece getPiece(Field currentField);

    int getRowLength();

    int getColumnLength();

    void makeMove(Move move) throws Move.IllegalMoveException;


    FieldSet getFieldsWithPiece(PieceType type, PieceColor color);

    Field getKingField(PieceColor color);

    /**
     *
     * @return deep copy of the current board
     */
    IBoard copy();

}
