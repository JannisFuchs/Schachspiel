package de.dhbw.schach;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

public class MockBoard implements IBoard {
    private final IPiece [][] board;


    public MockBoard(IPiece[][] board) {
        this.board = board;
    }
    public void setField(Field field, IPiece piece) {
        board[field.row()][field.column()] = piece;
    }
    public List<Field> selectFields(Move move) {
        IPiece movingPiece = move.piece;
        Field target = move.target;
        List<Field> candidateFields = movingPiece.getCandidateFields(target, this);
        List<Field> allFieldsWithPiece = getFieldsWithPiece(move.piece);
        return Field.intersectionOfFieldList(candidateFields, allFieldsWithPiece);
    }
    @Override
    public void makeMove(Move move) throws Move.IllegalMoveException {

    }

    @Override
    public List<Field> getFieldsWithPiece(IPiece piece) {
        List<Field> fieldsWithPiece = new ArrayList<>();
        for (int i = 0; i < getRowLength(); i++) {
            for (int j = 0; j < getColumnLength(); j++) {
                if (board[i][j].equals(piece)) {
                    fieldsWithPiece.add(new Field(i, j));
                }
            }
        }
        return fieldsWithPiece;

    }

    @Override
    public IPiece getPiece(Field currentField) {
        return board[currentField.row()][currentField.column()];
    }

    @Override
    public int getRowLength() {
        return board.length;
    }

    @Override
    public int getColumnLength() {
        return board[0].length;
    }
    public boolean isOccupiedByColor(Color color, Field target) {
        IPiece piece = board[target.row()][target.column()];
        if(piece.getPieceType() == PieceType.NONE) return false;
		return piece.getColor() == color;
	}
}
