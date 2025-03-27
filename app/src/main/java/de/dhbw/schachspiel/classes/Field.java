package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public record Field(int row, int column)
{


    /**
     * target and start field are on the same diagonal
     *
     * @param start starting square from piece
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public boolean isReachableByDiagonal(Field start, IBoard board)
    {
        int diffRow = Math.abs(this.row() - start.row());
        int diffColumn = Math.abs(this.column() - start.column());
        if (diffColumn != diffRow)
        {
            return false;
        }
        for (int i = 1; i < diffRow; i++)
        {
            int nextx = (start.row() < this.row()) ? start.row() + i : start.row() - i;
            int nexty = (start.column() < this.column()) ? start.column() + i : start.column() - i;
            Field nextField = new Field(nextx, nexty);
            if (!(board.getPiece(nextField) instanceof None))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * target and start field are on the same row
     *
     * @param start starting square from piece
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public boolean isReachableByRow(Field start, IBoard board)
    {
        int diffColumn = Math.abs(this.column() - start.column());
        if (this.row() != start.row())
        {
            return false;
        }
        for (int i = 1; i < diffColumn; i++)
        {
            int nextColumn = (start.column() < this.column()) ? start.column() + i : start.column() - i;
            Field posssibleField = new Field(start.row, nextColumn);
            if (!(board.getPiece(posssibleField) instanceof None))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * target and start field are on the same column
     *
     * @param start starting square from piece
     * @param board the given position
     * @return checks if fields between target and start are free
     */
    public boolean isReachableByColumn(Field start, IBoard board)
    {
        int diffRow = Math.abs(this.row() - start.row());
        if (this.column != start.column())
        {
            return false;
        }
        for (int i = 1; i < diffRow; i++)
        {
            int nextRow = (start.row() < this.row()) ? start.row() + i : start.row() - i;
            Field posssibleField = new Field(nextRow, start.column);
            if (!(board.getPiece(posssibleField) instanceof None))
            {
                return false;
            }
        }

        return true;
    }

    public boolean isOccupiedByColor(PieceColor pieceColor, IBoard board)
    {
        IPiece piece = board.getPiece(this);
        if (piece.getPieceType() == PieceType.NONE)
        {
            return false;
        }
        return piece.getColor() == pieceColor;
    }

    public FieldSet getFieldsInBetween(Field target)
    {
        FieldSet fieldsInBetween = new FieldSet();
        int rowDiff = Math.abs(target.row() - this.row());
        int columnDiff = Math.abs(target.column() - this.column());
        if (rowDiff == 0)
        {
            fieldsInBetween = getFieldsInBetweenRow(target);
        }
        else
        {
            if (columnDiff == 0)
            {
                fieldsInBetween = getFieldsInBetweenColumn(target);
            }
            else
            {
                if (rowDiff == columnDiff)
                {
                    fieldsInBetween = getFieldsInBetweenDiagonal(target);
                }
            }
        }
        return fieldsInBetween;
    }

    private FieldSet getFieldsInBetweenRow(Field target)
    {
        FieldSet fieldsInBetween = new FieldSet();
        int columnDiff = Math.abs(target.column() - this.column());
        for (int i = 1; i < columnDiff; i++)
        {
            int nextColumn;
            if (this.column() < target.column())
            {
                nextColumn = this.column() + i;
            }
            else
            {
                nextColumn = this.column() - i;
            }
            Field possibleField = new Field(this.row(), nextColumn);
            fieldsInBetween.add(possibleField);
        }
        return fieldsInBetween;
    }

    private FieldSet getFieldsInBetweenColumn(Field target)
    {
        FieldSet fieldsInBetween = new FieldSet();
        int rowDiff = Math.abs(target.row() - this.row());
        for (int i = 1; i < rowDiff; i++)
        {
            int nextRow;
            if (this.row() < target.row())
            {
                nextRow = this.row() + i;
            }
            else
            {
                nextRow = this.row() - i;
            }
            Field possibleField = new Field(this.row(), nextRow);
            fieldsInBetween.add(possibleField);
        }
        return fieldsInBetween;
    }

    private FieldSet getFieldsInBetweenDiagonal(Field target)
    {
        FieldSet fieldsInBetween = new FieldSet();
        int columnDiff = Math.abs(target.column() - this.column());
        for (int i = 1; i < columnDiff; i++)
        {

            int nextRow;
            if (this.row() < target.row())
            {
                nextRow = this.row() + i;
            }
            else
            {
                nextRow = this.row() - i;
            }
            int nextColumn;
            if (this.column() < target.column())
            {
                nextColumn = this.column() + i;
            }
            else
            {
                nextColumn = this.column() - i;
            }
            Field possibleField = new Field(nextRow, nextColumn);
            fieldsInBetween.add(possibleField);
        }
        return fieldsInBetween;
    }
}
