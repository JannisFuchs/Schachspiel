package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public record Bishop (Color c) implements AbstractPiece {

    @Override
    public char getSymbol() {
        return '♝';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public Field calculateStartField(Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
        Field target = move.end;
        //these ifs check if the target field is free might move the code up
        if (target.isOccupiedByColor(move.piece.getColor(), board))
            throw new Move.IllegalMoveException("Can't capture your own piece");
        Color enemyColor = Color.getOtherColor(move.piece.getColor());
        if (target.isOccupiedByColor(enemyColor, board)&&!move.isCapture){
            throw new Move.IllegalMoveException("This move is not a capture");
        }

        List<Field> candidateFields = new ArrayList<>();
        for (int i = 1 ; i < board.length ; i++) {
            candidateFields.add(new Field(target.row()+i, target.column()+i));
            candidateFields.add(new Field(target.row()-i, target.column()+i));
            candidateFields.add(new Field(target.row()+i, target.column()-i));
            candidateFields.add(new Field(target.row()-i, target.column()-i));
        }
        candidateFields.removeIf(Field::isInValid);

        List<Field> fieldsWithBishop = candidateFields.stream().filter(field ->field.hasPiece(move.piece, board)).toList();
        if (fieldsWithBishop.isEmpty()) throw new Move.IllegalMoveException("Wrong piece");
        List<Field> reachableFields = fieldsWithBishop.stream().filter(field -> isReachable(target, field, board)).toList();
        if (reachableFields.isEmpty()) throw new Move.IllegalMoveException("Field not reachable");
        //with this notation it is not possible to get a second bishop for a color so this non-ambiguous
        return reachableFields.get(0);
    }
    private boolean isReachable(Field target,Field start, AbstractPiece[][] board) {
        int diffRow = Math.abs(target.row() - start.row());
        int diffColumn = Math.abs(target.column() - start.column());
        for (int i = 1 ; i < diffRow; i++) {
            for (int j = 1 ; j < diffColumn; j++) {
                int nextx = (start.row()<target.row()) ?start.row() + i : start.row()-i;
                int nexty = (start.column()<target.column()) ?start.column() + i : start.column()-i;
                if (! (board[nextx][nexty] instanceof None)) return false;
            }
        }
        return true;
    }


}
