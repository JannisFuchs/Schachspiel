package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

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
    public FieldSet getCandidateFields(Field target, IBoard board){
        FieldSet candidateFields = new FieldSet();
        for (int rows = 1 ; rows < board.getRowLength() ; rows++) {
            candidateFields.add(new Field(target.row()+rows, target.column()));
            candidateFields.add(new Field(target.row()-rows, target.column()));
        }
        for (int columns = 1; columns < board.getColumnLength(); columns++) {
            candidateFields.add(new Field(target.row(), target.column()+columns));
            candidateFields.add(new Field(target.row(), target.column()-columns));
        }
        return candidateFields;
    }
    @Override
    public Field calculateStartField(FieldSet fields, Move move, IBoard board) throws Move.IllegalMoveException {
        Field target =  move.target;
        FieldSet reachableFields = getReachableFields(fields,target,board);
        if (reachableFields.isEmpty()) throw new Move.IllegalMoveException("Field not reachable");
        if (reachableFields.size() == 1) return reachableFields.getSingleItem();
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
            return reachableFields.findColumn(row);
        }
        return reachableFields.findColumn(row);
    }
    private FieldSet getReachableFields(FieldSet candidateFields, Field target, IBoard board) {
        FieldSet reachableFields = new FieldSet();
        FieldSet row = candidateFields.filterReachableByRow(target,board);
        FieldSet column = candidateFields.filterReachableByColumn(target,board);
        reachableFields.addAll(row);
        reachableFields.addAll(column);
        return reachableFields;
    }
}
