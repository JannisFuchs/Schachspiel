package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

public record Bishop (Color c) implements IPiece {

    @Override
    public char getSymbol() {
        return '♝';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public Field calculateStartField(Move move, IBoard board) throws Move.IllegalMoveException {
        Field target = move.target;
        List<Field> candidateFields = new ArrayList<>();
        for (int i = 1 ; i < board.getRowLength(); i++) {
            candidateFields.add(new Field(target.row()+i, target.column()+i));
            candidateFields.add(new Field(target.row()-i, target.column()+i));
            candidateFields.add(new Field(target.row()+i, target.column()-i));
            candidateFields.add(new Field(target.row()-i, target.column()-i));
        }
        List<Field> fieldsWithBishop = board.getFieldsWithPiece(candidateFields, move.piece);
        if (fieldsWithBishop.isEmpty()) throw new Move.IllegalMoveException("Wrong piece");
        List<Field> reachableFields = findReachableFields(fieldsWithBishop,target,board);
        if (reachableFields.isEmpty()) throw new Move.IllegalMoveException("Field not reachable");
        //with this notation it is not possible to get a second bishop for a color so this non-ambiguous
        return reachableFields.get(0);
    }
    private List<Field> findReachableFields(List<Field> fields,Field target, IBoard board){
        List<Field> reachableFields = new ArrayList<>(fields.size());
        for (Field startField:fields){
            if (target.isReachableByDiagonal(startField,board)){
                reachableFields.add(startField);
            }
        }
        return reachableFields;
    }
}
