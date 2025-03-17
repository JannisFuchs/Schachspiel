package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.interfaces.IBoard;

import java.util.ArrayList;
import java.util.List;

public record Field(int row, int column) {

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
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public boolean isReachableByDiagonal(Field start, IBoard board) {
        int diffRow = Math.abs(this.row() - start.row());
        int diffColumn = Math.abs(this.column() - start.column());
        if (diffColumn != diffRow) {
            return false;
        }
        for (int i = 1 ; i < diffRow; i++) {
            int nextx = (start.row()<this.row()) ?start.row() + i : start.row()-i;
            int nexty = (start.column()<this.column()) ?start.column() + i : start.column()-i;
            Field nextField = new Field(nextx,nexty);
            if (! (board.getPiece(nextField) instanceof None)) return false;
        }
        return true;
    }

    /**
     * target and start field are on the same row
     * @param start starting square from piece
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public boolean isReachableByRow(Field start, IBoard board) {
        int diffColumn = Math.abs(this.column() - start.column());
        if(this.row() != start.row()){
            return false;
        }
        for(int i = 1; i < diffColumn; i++){
            int nextColumn = (start.column()<this.column()) ?start.column() + i : start.column()-i;
            Field posssibleField = new Field(start.row,nextColumn);
            if(!(board.getPiece(posssibleField) instanceof None)) return false;
        }
        return true;
    }
    /**
     * target and start field are on the same column
     * @param start starting square from piece
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public boolean isReachableByColumn(Field start, IBoard board) {
        int diffRow = Math.abs(this.row() - start.row());
        if (this.column != start.column()){
            return false;
        }
        for(int i = 1; i < diffRow; i++){
            int nextRow = (start.row()<this.row()) ?start.row() + i : start.row()-i;
            Field posssibleField = new Field(nextRow,start.column);
            if(!(board.getPiece(posssibleField) instanceof None)) return false;
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
    public static List<Field> intersectionOfFieldList(List<Field> fieldList, List<Field> otherList){
        List<Field> interceptFields = new ArrayList<>();
        for (Field field:fieldList){
            if (otherList.contains(field)){
                interceptFields.add(field);
            }
        }
        return interceptFields;
    }
}
