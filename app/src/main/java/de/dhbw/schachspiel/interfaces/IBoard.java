package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.*;

import java.util.Map;

public interface IBoard
{
    IPiece getPiece(Field currentField);

    Field getKingField(PieceColor pieceColor);

    int getRowLength();

    int getColumnLength();

    void makeMove(Move move) throws Move.IllegalMoveException;

    Field simulateMove(Move move) throws Move.IllegalMoveException;

    FieldSet getFieldsWithPiece(IPiece piece);
    Map<Field, IPiece> getAllPiecesFromColor(PieceColor color);
    FieldSet getAttacker(Field target,PieceColor attacker);
    void undoMove();
    void commitMove();
}
