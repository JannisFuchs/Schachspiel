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
    public List<Field> getCandidateFields(Field target,IBoard board){
        List<Field> candidateFields = new ArrayList<>();
        for (int rows = 1 ; rows < board.getRowLength() ; rows++) {
            candidateFields.add(new Field(target.row()+rows, target.column()));
            candidateFields.add(new Field(target.row()-rows, target.column()));
        }
        for (int columns = 1; columns < board.getColumnLength(); columns++) {
            candidateFields.add(new Field(target.row(), target.column()+columns));
            candidateFields.add(new Field(target.row(), target.column()-columns));
        }
        int min = Math.min(board.getRowLength(),board.getColumnLength());
        for (int diagonal = 1 ; diagonal < min ; diagonal++) {
            candidateFields.add(new Field(target.row()+diagonal, target.column()+diagonal));
            candidateFields.add(new Field(target.row()-diagonal, target.column()+diagonal));
            candidateFields.add(new Field(target.row()+diagonal, target.column()-diagonal));
            candidateFields.add(new Field(target.row()-diagonal, target.column()-diagonal));
        }
        return candidateFields;
    }

    @Override
    public Field calculateStartField(List<Field> fields,Move move, IBoard board) throws Move.IllegalMoveException {
        Field target =  move.target;
        List<Field> reachableFields = getReachableFields(fields,target,board);
        if (reachableFields.isEmpty()) throw new Move.IllegalMoveException("Field not reachable");
        //with this notation it is not possible to get a second Queen so this non-ambiguous
        return reachableFields.get(0);
    }

    private List<Field> getReachableFields(List<Field> candidateFields, Field target, IBoard board) {
        List<Field> reachableFields = new ArrayList<>(candidateFields.size());
        for (Field startField:candidateFields){
            if (target.isReachableByColumn(startField,board)){
                reachableFields.add(startField);
                continue;
            }

            if (target.isReachableByRow(startField,board)){
                reachableFields.add(startField);
                continue;
            }
            if (target.isReachableByDiagonal(startField,board)){
                reachableFields.add(startField);
            }
        }
        return reachableFields;
    }
}
