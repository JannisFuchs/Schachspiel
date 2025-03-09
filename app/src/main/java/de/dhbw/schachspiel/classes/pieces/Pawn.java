package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public record Pawn (Color c) implements AbstractPiece {

    @Override
    public char getSymbol() {
        return '♟';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }


    @Override
    public Field calculateStartField(Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
        /*
         * 4 possible start squares from each color
         * 2 from capture
         * 2 from normal moving (pawn can move two square at a time)
         */
        Field endField = move.target;
        List<Field> candidateFields = new ArrayList<>();
        if (move.piece.getColor() == Color.WHITE) {

            candidateFields.add(new Field(endField.row()+1,endField.column()) ); //Pawn moves one square forward
            candidateFields.add(new Field(endField.row()+2,endField.column())); //Pawn starts with two squares forward
            candidateFields.add(new Field(endField.row()+1,endField.column()+1)); //Pawn captures right
            candidateFields.add(new Field(endField.row()+1,endField.column()-1)); //Pawn captures left
        }
        else { //Pawn is black
            candidateFields.add(new Field(endField.row()-1,endField.column())); //Pawn moves one square forward
            candidateFields.add(new Field(endField.row()-2,endField.column())); //Pawn starts with two squares forward
            candidateFields.add(new Field(endField.row()-1,endField.column()+1)); //Pawn captures left
            candidateFields.add(new Field(endField.row()-1,endField.column()-1));//Pawn captures right
        }
        candidateFields.removeIf(Field::isInValid);
        candidateFields.removeIf(field -> !field.hasPiece(move.piece, board));
        if (candidateFields.isEmpty()) {
            throw new Move.IllegalMoveException("No pieces found");
        }
        if (move.isCapture){
            return calculateCapture(move,board, candidateFields);
        }
        return calculateNormalMove(move,board,candidateFields);
}
private Field calculateCapture(Move move,AbstractPiece[][] board, List<Field> candidateFields) throws Move.IllegalMoveException {
    Field target = move.target;
    if (!target.isOccupiedByColor(Color.BLACK,board)){
        throw new Move.IllegalMoveException("Target is no capture");
    }
    candidateFields.removeIf(field -> !target.isReachableByDiagonal(field,board));
    if (candidateFields.isEmpty()) {
        throw new Move.IllegalMoveException("No pieces found");
    }
    if (candidateFields.size() == 1) {
        return candidateFields.get(0);
    }
    if (candidateFields.size() > 2) {
        throw new Move.IllegalMoveException("This should not happen");
    }
    int column = target.column();
    if(column == -1){
        throw new Move.IllegalMoveException("move is ambiguous");
    }
    for (Field f: candidateFields) {
        if (f.column() == column) {
            return f;
        }
    }
    throw new Move.IllegalMoveException("Field not found");

}


private Field calculateNormalMove(Move move, AbstractPiece[][] board, List<Field> candidateFields) throws Move.IllegalMoveException {
    Field target = move.target;
    if (target.isOccupiedByColor(Color.BLACK, board)||target.isOccupiedByColor(Color.WHITE, board)) {
        throw new Move.IllegalMoveException("Field is occupied");
    }

	candidateFields.removeIf(f -> !target.isReachableByColumn(f, board));
    if (candidateFields.isEmpty()) {
        throw new Move.IllegalMoveException("No pieces found");
    }
    if (candidateFields.size() > 1) {
        throw new Move.IllegalMoveException("This should not happen");
    }
    return calculateMove(candidateFields.get(0),move,board);

}
private Field calculateMove(Field start, Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
    Field target = move.target;
    int diffRow =  Math.abs(start.row() - target.row());
    if (diffRow == 1) {
        return start;
    }
    if (diffRow > 2) {
        throw new Move.IllegalMoveException("This should not happen");
    }
    if (move.piece.getColor() == Color.WHITE && start.row() == board.length - 2) {
        return start;
    }
    if (move.piece.getColor() == Color.BLACK && start.row() == 1) {
        return start;
    }
    throw new Move.IllegalMoveException("was not the start field");

}
}
