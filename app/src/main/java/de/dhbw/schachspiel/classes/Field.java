package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

public record Field(int row, int column) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Field p) {
            return p.row == row && p.column == column;
        }
        return false;
    }
    public boolean isInValid() {
        return row < 0 || row >=8 || column < 0 || column >= 8;
    }
    public boolean isOccupiedByColor(Color color, AbstractPiece[][] board) {
        AbstractPiece piece = board[row][column];
        if(piece instanceof None) return false;
		return piece.getColor() == color;
	}
    public boolean hasPiece(AbstractPiece piece, AbstractPiece[][] board) {
        return  board[row][column].equals(piece);
    }
}
