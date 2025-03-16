package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

public record Rook (Color c) implements IPiece {


    @Override
    public char getSymbol() {
        return '♜';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public Field calculateStartField(Move move, IBoard board) throws Move.IllegalMoveException {
        Field target =  move.target;
        List<Field> candidateFields = new ArrayList<>();
        for (int i = 1 ; i < board.getRowLength(); i++) {
            candidateFields.add(new Field(target.row(), target.column()+i));
            candidateFields.add(new Field(target.row(), target.column()-i));
            candidateFields.add(new Field(target.row()+i, target.column()));
            candidateFields.add(new Field(target.row()-i, target.column()));


        }

        List<Field> fieldsWithRook = board.getFieldsWithPiece(candidateFields,move.piece);
        if (fieldsWithRook.isEmpty()) throw new Move.IllegalMoveException("Wrong piece");
        List<Field> reachableFields = getReachableFields(fieldsWithRook,target,board);
        if (reachableFields.isEmpty()) throw new Move.IllegalMoveException("Field not reachable");
        if (reachableFields.size() == 1) return reachableFields.get(0);
        int row = move.start.row();
        int column = move.start.column();
        if (row == -1 && column == -1) throw new Move.IllegalMoveException("Ambiguous piece");
        if (row!=-1 &&column != -1){
            Field specifiedStartField = new Field(row,column);
            if(reachableFields.contains(specifiedStartField)){
                return specifiedStartField;
            }

            throw new Move.IllegalMoveException("Wrong start coordinate");
        }
        if (row!=-1){
            return Field.findColumn(row,reachableFields);
        }
        return Field.findRow(column,reachableFields);
    }
    private List<Field> getReachableFields(List<Field> candidateFields, Field target, IBoard board) {
        List<Field> reachableFields = new ArrayList<>(candidateFields.size());
        for (Field startField:candidateFields){
            if (startField.isReachableByColumn(startField,board)){
                reachableFields.add(startField);
                continue;
            }

            if (startField.isReachableByRow(startField,board)){
                reachableFields.add(startField);
            }
        }
        return reachableFields;
    }
}
