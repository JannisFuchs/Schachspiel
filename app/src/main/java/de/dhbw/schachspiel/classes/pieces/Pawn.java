package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

public record Pawn (Color c) implements IPiece {

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
    public List<Field> getCandidateFields(Field target,IBoard board){
        List<Field> candidateFields = new ArrayList<>();

        candidateFields.add(new Field(target.row()+1, target.column()));
        candidateFields.add(new Field(target.row()+2, target.column()));
        candidateFields.add(new Field(target.row()+1, target.column()+1));
        candidateFields.add(new Field(target.row()+1, target.column()-1));
        candidateFields.add(new Field(target.row()-1, target.column()));
        candidateFields.add(new Field(target.row()-2, target.column()));
        candidateFields.add(new Field(target.row()-1, target.column()+1));
        candidateFields.add(new Field(target.row()-1, target.column()-1));
        return candidateFields;
    }
    @Override
    public Field calculateStartField(List<Field> fields,Move move, IBoard board) throws Move.IllegalMoveException {
        /*
         * 4 possible start squares from each color
         * 2 from capture
         * 2 from normal moving (pawn can move two square at a time)
         */
        if (fields.isEmpty()) {
            throw new Move.IllegalMoveException("No pieces found");
        }
        if (move.isCapture){
            return calculateCapture(move,board, fields);
        }
        return calculateNormalMove(move,board,fields);
}
private Field calculateCapture(Move move, IBoard board, List<Field> candidateFields) throws Move.IllegalMoveException {
    Field target = move.target;
    Color enemyColor = Color.WHITE;
    if (move.piece.getColor() == Color.WHITE){
        enemyColor = Color.BLACK;
    }
    if (!board.isOccupiedByColor(enemyColor,target)){
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
    int column = move.start.column();
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


private Field calculateNormalMove(Move move, IBoard board, List<Field> candidateFields) throws Move.IllegalMoveException {
    Field target = move.target;
    if (board.isOccupiedByColor(Color.BLACK,target)||board.isOccupiedByColor(Color.WHITE, target)) {
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
private Field calculateMove(Field start, Move move, IBoard board) throws Move.IllegalMoveException {
    Field target = move.target;
    int diffRow =  Math.abs(start.row() - target.row());
    if (diffRow == 1) {
        return start;
    }
    if (diffRow > 2) {
        throw new Move.IllegalMoveException("This should not happen");
    }
    if (move.piece.getColor() == Color.WHITE && start.row() == board.getColumnLength()- 2) {
        return start;
    }
    if (move.piece.getColor() == Color.BLACK && start.row() == 1) {
        return start;
    }
    throw new Move.IllegalMoveException("was not the start field");

}
}
