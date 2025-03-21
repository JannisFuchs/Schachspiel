package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public record King (Color c) implements IPiece {


    @Override
    public char getSymbol() {
        return '♚';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public FieldSet getCandidateFields(Field target, IBoard board) {
        FieldSet candidateFields = new FieldSet();
        candidateFields.add(new Field(target.row(), target.column()+1));
        candidateFields.add(new Field(target.row(), target.column()-1));
        candidateFields.add(new Field(target.row()+1, target.column()));
        candidateFields.add(new Field(target.row()-1, target.column()));

        candidateFields.add(new Field(target.row()+1, target.column()+1));
        candidateFields.add(new Field(target.row()-1, target.column()+1));
        candidateFields.add(new Field(target.row()+1, target.column()-1));
        candidateFields.add(new Field(target.row()-1, target.column()-1));
        return candidateFields;
    }

    @Override
    public Field calculateStartField(FieldSet fields, Move move, IBoard board) throws Move.IllegalMoveException {
       if (fields.size()>1){
           throw new Move.IllegalMoveException("King exists somehow two times");
       }
       return fields.getSingleItem();
    }
}
