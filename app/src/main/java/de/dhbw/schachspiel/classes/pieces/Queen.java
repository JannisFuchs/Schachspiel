package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

public record Queen (Color c) implements IPiece {

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
    public Field calculateStartField(Move move, IBoard board) throws Move.IllegalMoveException {
        Field target =  move.target;
        List<Field> candidateFields = new ArrayList<>();
        for (int i = 1 ; i < board.getRowLength() ; i++) {
            candidateFields.add(new Field(target.row(), target.column()+i));
            candidateFields.add(new Field(target.row(), target.column()-i));
            candidateFields.add(new Field(target.row()+i, target.column()));
            candidateFields.add(new Field(target.row()-i, target.column()));

            candidateFields.add(new Field(target.row()+i, target.column()+i));
            candidateFields.add(new Field(target.row()-i, target.column()+i));
            candidateFields.add(new Field(target.row()+i, target.column()-i));
            candidateFields.add(new Field(target.row()-i, target.column()-i));

        }
        List<Field> fieldsWithQueen = board.getFieldsWithPiece(candidateFields,move.piece);
        if (fieldsWithQueen.isEmpty()) throw new Move.IllegalMoveException("Wrong piece");
        List<Field> reachableFields = getReachableFields(candidateFields,target,board);
        if (reachableFields.isEmpty()) throw new Move.IllegalMoveException("Field not reachable");
        //with this notation it is not possible to get a second Queen so this non-ambiguous
        return reachableFields.get(0);
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
                continue;
            }
            if (startField.isReachableByDiagonal(startField,board)){
                reachableFields.add(startField);
            }
        }
        return reachableFields;
    }
}
