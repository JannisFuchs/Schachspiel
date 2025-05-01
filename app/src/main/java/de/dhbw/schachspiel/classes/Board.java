package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public class Board implements IBoard
{
    private final IPiece[][] pieceArray;

    public Board(IPiece[][] board)
    {
        this.pieceArray = board;
    }

    @Override
    public Field getKingField(PieceColor color)
    {
        FieldSet kingFields = getFieldsWithPiece(PieceType.KING, color);
        return kingFields.getSingleItem();
    }


    @Override
    public IPiece getPiece(Field currentField)
    {
        return pieceArray[currentField.row()][currentField.column()];
    }


    @Override
    public int getRowLength()
    {
        return pieceArray.length;
    }

    @Override
    public int getColumnLength()
    {
        return pieceArray[0].length;
    }


    /**
     * checks if a field is occupied. This is sufficient for normal pieces but pawns need extra checking
     * It doesn't return a boolean because if the Field is invalid an exception is thrown
     *
     * @param move the move the player is about to make
     * @throws Move.IllegalMoveException thrown when the field is either occupied by your own piece
     *                                   or occupied by the enemy piece and no capture is specified
     *                                   or not occupied by the enemy piece but capture is specified
     */
    private void checksField(Move move) throws Move.IllegalMoveException
    {
        Field target = move.target;
        //these ifs check if the target field is free might move the code up
        if (target.isOccupiedByColor(move.piece.getColor(), this))
        {
            throw new Move.IllegalMoveException("Can't capture your own piece");
        }
        PieceColor enemyPieceColor = move.piece.getColor().getOtherColor();
        if (target.isOccupiedByColor(enemyPieceColor, this) && !move.isCapture)
        {
            throw new Move.NoCaptureException();
        }
        if (!target.isOccupiedByColor(enemyPieceColor, this) && move.isCapture)
        {
            throw new Move.CaptureException();
        }
    }

    public void makeMove(Move move) throws Move.IllegalMoveException
    {

        Field startField = prepareMove(move);
        IPiece piece = move.piece;
        int startRow = startField.row();
        int startCol = startField.column();
        IPiece currentPiece = pieceArray[startRow][startCol];
        if (!currentPiece.equals(piece))
        {
            throw new Move.IllegalMoveException("wrong piece");
        }

        pieceArray[startRow][startCol] = PieceFactory.createPieceFromType(PieceType.NONE, PieceColor.WHITE);
        int endRow = move.target.row();
        int endCol = move.target.column();
        pieceArray[endRow][endCol] = currentPiece;

    }


    private Field prepareMove(Move move) throws Move.IllegalMoveException
    {
        IPiece piece = move.piece;
        checksField(move);
        FieldSet fields = selectFields(move);
        if (fields.isEmpty())
        {
            throw new Move.IllegalMoveException("No fields found");
        }
        return piece.calculateStartField(fields, move, this);
    }





    @Override
    public FieldSet getFieldsWithPiece(PieceType type, PieceColor color)
    {
        FieldSet fieldsWithPiece = new FieldSet();
        for (int row = 0; row < getRowLength(); row++)
        {
            for (int column = 0; column < getColumnLength(); column++)
            {
                IPiece currentPiece = pieceArray[row][column];
                if (currentPiece.getPieceType() == type && currentPiece.getColor() == color)
                {
                    fieldsWithPiece.add(new Field(row, column));
                }
            }
        }
        return fieldsWithPiece;
    }

    public FieldSet selectFields(Move move)
    {
        IPiece movingPiece = move.piece;
        Field target = move.target;
        FieldSet candidateFields = movingPiece.getCandidateFields(target, this);
        FieldSet allFieldsWithPiece = getFieldsWithPiece(movingPiece.getPieceType(), movingPiece.getColor());
        return candidateFields.intersection(allFieldsWithPiece);
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Board other))
        {
            return false;
        }
        int otherRowLength = other.getRowLength();
        int otherColumnLength = other.getColumnLength();
        if (otherRowLength != getRowLength() || otherColumnLength != getColumnLength())
        {
            return false;
        }
        for (int row = 0; row < otherRowLength; row++)
        {
            for (int column = 0; column < otherColumnLength; column++)
            {
                if (pieceArray[row][column] != other.pieceArray[row][column])
                {
                    return false;
                }
            }
        }
        return true;
    }


    public Board copy()
    {
        IPiece[][] clone = new IPiece[this.pieceArray.length][this.pieceArray[0].length];
        for (int row = 0; row < this.pieceArray.length; row++){
            for (int column = 0; column < this.pieceArray[0].length; column++){
                IPiece piece = this.pieceArray[row][column];
                clone[row][column] = PieceFactory.createPieceFromType(piece.getPieceType(), piece.getColor());
            }
        }
        return new Board(clone);
    }
}
