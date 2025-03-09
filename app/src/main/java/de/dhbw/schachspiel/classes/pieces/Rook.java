package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public record Rook (Color c) implements AbstractPiece {


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
    public Field calculateStartField(Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
        Field target =  move.target;
        List<Field> candidateFields = new ArrayList<>();
        for (int i = 1 ; i < board.length ; i++) {
            candidateFields.add(new Field(target.row(), target.column()+i));
            candidateFields.add(new Field(target.row(), target.column()-i));
            candidateFields.add(new Field(target.row()+i, target.column()));
            candidateFields.add(new Field(target.row()-i, target.column()));


        }
        candidateFields.removeIf(Field::isInValid);

        List<Field> fieldsWithRook = candidateFields.stream().filter(field ->field.hasPiece(move.piece, board)).toList();
        if (fieldsWithRook.isEmpty()) throw new Move.IllegalMoveException("Wrong piece");
        List<Field> reachableFields = fieldsWithRook.stream().filter(field -> isReachable(field, target, board)).toList();
        if (reachableFields.isEmpty()) throw new Move.IllegalMoveException("Field not reachable");
        if (reachableFields.size() == 1) return reachableFields.get(0);
        int row = move.start.row();
        int column = move.start.column();
        if (row == -1 && column == -1) throw new Move.IllegalMoveException("Ambiguous piece");
        if (row!=-1 &&column != -1){
            if (board[row][column].equals(move.piece)) {
                return new Field(row, column);
            }
            throw new Move.IllegalMoveException("Wrong start coordinate");
        }
        if (row!=-1){
            return Field.findColumn(row,reachableFields);
        }
        return Field.findRow(column,reachableFields);
    }
    private boolean isReachable(Field start, Field target, AbstractPiece[][] board) {
        if (start.row() == target.row()) {
            return target.isReachableByRow(start, board);
        }
        return target.isReachableByColumn(start, board);
    }
}
