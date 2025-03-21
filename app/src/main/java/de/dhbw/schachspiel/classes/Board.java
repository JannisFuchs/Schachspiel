package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.King;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public class Board implements IBoard, Cloneable
{
    private final IPiece[][] board;

    public Board(int rows, int columns)
    {
        board = PieceFactory.createBoard(rows, columns);
    }

    @Override
    public IPiece getPiece(Field currentField)
    {
        return board[currentField.row()][currentField.column()];
    }

    @Override
    public Field getKingField(Color color)
    {
        IPiece king = new King(color);
        FieldSet fieldsWithKing = getFieldsWithPiece(king);
        return fieldsWithKing.getSingleItem();
    }

    @Override
    public int getRowLength()
    {
        return board.length;
    }

    @Override
    public int getColumnLength()
    {
        return board[0].length;
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
        Color enemyColor = move.piece.getColor().getOtherColor();
        if (target.isOccupiedByColor(enemyColor, this) && !move.isCapture)
        {
            throw new Move.NoCaptureException();
        }
        if (!target.isOccupiedByColor(enemyColor, this) && move.isCapture)
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
        IPiece currentPiece = board[startRow][startCol];
        if (!currentPiece.equals(piece))
        {
            throw new Move.IllegalMoveException("wrong piece");
        }
        board[startRow][startCol] = PieceFactory.createPieceFromType(PieceType.NONE, Color.RESET);
        int endRow = move.target.row();
        int endCol = move.target.column();
        board[endRow][endCol] = currentPiece;

    }

    private Field prepareMove(Move move) throws Move.IllegalMoveException
    {
        IPiece piece = move.piece;
        checksField(move);
        FieldSet fields = selectFields(move);
        return piece.calculateStartField(fields, move, this);
    }

    @Override
    public void simulateMove(Move move) throws Move.IllegalMoveException
    {
        prepareMove(move);
    }

    private FieldSet getFieldsWithPiece(IPiece piece)
    {
        FieldSet fieldsWithPiece = new FieldSet();
        for (int i = 0; i < getRowLength(); i++)
        {
            for (int j = 0; j < getColumnLength(); j++)
            {
                if (board[i][j].equals(piece))
                {
                    fieldsWithPiece.add(new Field(i, j));
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
        FieldSet allFieldsWithPiece = getFieldsWithPiece(move.piece);
        return candidateFields.intersectionOfFieldList(allFieldsWithPiece);
    }

    //this is one idea to allow reverting to the board from the last move
    @Override
    public Board clone()
    {
        try
        {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Board) super.clone();
        } catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }
}
