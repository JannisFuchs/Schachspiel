package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public record Queen(Color c) implements IPiece
{

    @Override
    public char getSymbol()
    {
        return '♛';
    }

    @Override
    public Color getColor()
    {
        return c;
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.QUEEN;
    }

    @Override
    public FieldSet getCandidateFields(Field target, IBoard board)
    {
        FieldSet candidateFields = new FieldSet();
        for (int rows = 1; rows < board.getRowLength(); rows++)
        {
            candidateFields.add(new Field(target.row() + rows, target.column()));
            candidateFields.add(new Field(target.row() - rows, target.column()));
        }
        for (int columns = 1; columns < board.getColumnLength(); columns++)
        {
            candidateFields.add(new Field(target.row(), target.column() + columns));
            candidateFields.add(new Field(target.row(), target.column() - columns));
        }
        int min = Math.min(board.getRowLength(), board.getColumnLength());
        for (int diagonal = 1; diagonal < min; diagonal++)
        {
            candidateFields.add(new Field(target.row() + diagonal, target.column() + diagonal));
            candidateFields.add(new Field(target.row() - diagonal, target.column() + diagonal));
            candidateFields.add(new Field(target.row() + diagonal, target.column() - diagonal));
            candidateFields.add(new Field(target.row() - diagonal, target.column() - diagonal));
        }
        return candidateFields;
    }

    @Override
    public Field calculateStartField(FieldSet fields, Move move, IBoard board) throws Move.IllegalMoveException
    {
        Field target = move.target;
        FieldSet reachableFields = getReachableFields(fields, target, board);
        if (reachableFields.isEmpty())
        {
            throw new Move.IllegalMoveException("Field not reachable");
        }
        //with this notation it is not possible to get a second Queen so this non-ambiguous
        return reachableFields.getSingleItem();
    }

    private FieldSet getReachableFields(FieldSet candidateFields, Field target, IBoard board)
    {
        FieldSet reachableFields = new FieldSet();
        FieldSet diag = candidateFields.filterReachableByDiagonal(target, board);
        FieldSet row = candidateFields.filterReachableByRow(target, board);
        FieldSet column = candidateFields.filterReachableByColumn(target, board);
        reachableFields.addAll(diag);
        reachableFields.addAll(row);
        reachableFields.addAll(column);
        return reachableFields;
    }
}
