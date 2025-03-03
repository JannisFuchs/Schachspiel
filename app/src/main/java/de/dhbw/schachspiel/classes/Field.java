package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.List;

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
    public static Field findRow(int column, List<Field> fields) throws Move.IllegalMoveException {
        Field target = null;

        for (Field field : fields) {
            if (field.column == column && target == null) {
                target = field;
                continue;
            }
            throw new Move.IllegalMoveException("Move is ambiguous");
        }
        if (target == null){
            throw new Move.IllegalMoveException("No piece found ");
        }
        return target;
    }

    /**
     * target and start field are on the same diagonal
     * @param start starting square from piece
     * @param target square the piece is trying to reach
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public static boolean isReachableByDiagonal(Field start,Field target, AbstractPiece[][] board) {
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

    /**
     * target and start field are on the same row
     * @param start starting square from piece
     * @param target square the piece is trying to reach
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public static boolean isReachableByRow(Field start,Field target, AbstractPiece[][] board) {
        int diffColumn = Math.abs(target.column() - start.column());

        for(int i = 1; i < diffColumn; i++){
            int nextColumn = (start.column()<target.column()) ?start.column() + i : start.column()-i;
            if(!(board[start.row()][nextColumn] instanceof None)) return false;
        }
        return true;
    }
    /**
     * target and start field are on the same column
     * @param start starting square from piece
     * @param target square the piece is trying to reach
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public static boolean isReachableByColumn(Field start,Field target, AbstractPiece[][] board) {
        int diffRow = Math.abs(target.row() - start.row());

        for(int i = 1; i < diffRow; i++){
            int nextRow = (start.row()<target.row()) ?start.row() + i : start.row()-i;
            if(!(board[nextRow][start.column()] instanceof None)) return false;
        }

        return true;
    }
    public static Field findColumn(int row, List<Field> fields) throws Move.IllegalMoveException {
        Field target = null;

        for (Field field : fields) {
            if (field.row == row && target == null) {
                target = field;
                continue;
            }
            throw new Move.IllegalMoveException("Move is ambiguous");
        }
        if (target == null){
            throw new Move.IllegalMoveException("No piece found ");
        }
        return target;
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
