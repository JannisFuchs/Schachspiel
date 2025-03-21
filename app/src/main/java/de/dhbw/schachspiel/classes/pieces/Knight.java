package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public record Knight (Color c) implements IPiece {

    @Override
    public char getSymbol() {
        return '♞';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public FieldSet getCandidateFields(Field target, IBoard board){
        FieldSet candidateFields = new FieldSet();
        candidateFields.add(new Field(target.row()+2, target.column()+1));
        candidateFields.add(new Field(target.row()+2, target.column()-1));
        candidateFields.add(new Field(target.row()-2, target.column()+1));
        candidateFields.add(new Field(target.row()-2, target.column()-1));

        candidateFields.add(new Field(target.row()+1, target.column()+2));
        candidateFields.add(new Field(target.row()-1, target.column()+2));
        candidateFields.add(new Field(target.row()+1, target.column()-2));
        candidateFields.add(new Field(target.row()-1, target.column()-2));
        return candidateFields;
    }

    @Override
    public Field calculateStartField(FieldSet fields, Move move, IBoard board) throws Move.IllegalMoveException {
        if (fields.size() == 1) return fields.getSingleItem();
        int row = move.start.row();
        int column = move.start.column();
        if (row == -1 && column == -1) throw new Move.IllegalMoveException("Ambiguous piece");
        if (row!=-1 &&column != -1){
            Field specifiedStartField = new Field(row,column);
            if(fields.contains(specifiedStartField)){
                return specifiedStartField;
            }
            throw new Move.IllegalMoveException("Wrong start coordinate");
        }
        if (row!=-1){
            return fields.findColumn(row);
        }
        return fields.findRow(column);
    }
}
