package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public record Queen (Color c) implements AbstractPiece {

    @Override
    public char getSymbol() {
        return '♛';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
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

            candidateFields.add(new Field(target.row()+i, target.column()+i));
            candidateFields.add(new Field(target.row()-i, target.column()+i));
            candidateFields.add(new Field(target.row()+i, target.column()-i));
            candidateFields.add(new Field(target.row()-i, target.column()-i));

        }
        candidateFields.removeIf(Field::isInValid);

        List<Field> fieldsWithQueen = candidateFields.stream().filter(field ->field.hasPiece(move.piece, board)).toList();
        if (fieldsWithQueen.isEmpty()) throw new Move.IllegalMoveException("Wrong piece");
        List<Field> reachableFields = fieldsWithQueen.stream().filter(field -> isReachable(field, target, board)).toList();
        if (reachableFields.isEmpty()) throw new Move.IllegalMoveException("Field not reachable");
        //with this notation it is not possible to get a second Queen so this non-ambiguous
        return reachableFields.get(0);
    }

    private boolean isReachable(Field start, Field target, AbstractPiece[][] board) {
        if (start.row() == target.row()) {
            return Field.isReachableByRow(start, target, board);
        }
        if (start.column() == target.column()) {
            return Field.isReachableByColumn(start, target, board);
        }
        return Field.isReachableByDiagonal(start, target, board);
    }
}
