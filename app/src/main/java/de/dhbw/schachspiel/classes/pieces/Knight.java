package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

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
    public Field calculateStartField(Move move, IBoard board) throws Move.IllegalMoveException {
        Field target =  move.target;
        List<Field> candidateFields = new ArrayList<>();

        candidateFields.add(new Field(target.row()+2, target.column()+1));
        candidateFields.add(new Field(target.row()+2, target.column()-1));
        candidateFields.add(new Field(target.row()-2, target.column()+1));
        candidateFields.add(new Field(target.row()-2, target.column()-1));

        candidateFields.add(new Field(target.row()+1, target.column()+2));
        candidateFields.add(new Field(target.row()-1, target.column()+2));
        candidateFields.add(new Field(target.row()+1, target.column()-2));
        candidateFields.add(new Field(target.row()-1, target.column()-2));


        List<Field> fieldsWithKnight = board.getFieldsWithPiece(candidateFields,move.piece);
        if (fieldsWithKnight.isEmpty()) throw new Move.IllegalMoveException("Wrong piece");
        if (fieldsWithKnight.size() == 1) return fieldsWithKnight.get(0);
        int row = move.start.row();
        int column = move.start.column();
        if (row == -1 && column == -1) throw new Move.IllegalMoveException("Ambiguous piece");
        if (row!=-1 &&column != -1){
            Field specifiedStartField = new Field(row,column);
            if(fieldsWithKnight.contains(specifiedStartField)){
                return specifiedStartField;
            }
            throw new Move.IllegalMoveException("Wrong start coordinate");
        }
        if (row!=-1){
            return Field.findColumn(row,fieldsWithKnight);
        }
        return Field.findRow(column,fieldsWithKnight);
    }
}
