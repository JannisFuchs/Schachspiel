package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.King;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.Stack;

public class Board implements IBoard
{
    private final IPiece[][] board;
    private final Stack<FieldPiecePair> previousMove = new Stack<>();

    public Board(IPiece[][] board)
    {
        this.board = board;
    }


    @Override
    public IPiece getPiece(Field currentField)
    {
        return board[currentField.row()][currentField.column()];
    }

    @Override
    public Field getKingField(PieceColor pieceColor)
    {
        IPiece king = new King(pieceColor);
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
        if (!previousMove.isEmpty())
        {
            throw new IllegalStateException("can't make more than one move at once.");
        }
        Field startField = prepareMove(move);
        IPiece piece = move.piece;
        int startRow = startField.row();
        int startCol = startField.column();
        IPiece currentPiece = board[startRow][startCol];
        if (!currentPiece.equals(piece))
        {
            throw new Move.IllegalMoveException("wrong piece");
        }
        FieldPiecePair start = new FieldPiecePair(startField, piece);
        previousMove.push(start);
        board[startRow][startCol] = PieceFactory.createPieceFromType(PieceType.NONE, PieceColor.WHITE);
        int endRow = move.target.row();
        int endCol = move.target.column();
        IPiece endPiece = board[endRow][endCol];
        FieldPiecePair target = new FieldPiecePair(move.target, endPiece);
        previousMove.push(target);
        board[endRow][endCol] = currentPiece;

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

    public void commitMove()
    {
        previousMove.clear();
    }


    public void undoMove()
    {
        FieldPiecePair target = previousMove.pop();
        Field targetField = target.getField();
        FieldPiecePair start = previousMove.pop();
        Field startField = start.getField();
        board[targetField.row()][targetField.column()] = target.getPiece();
        board[startField.row()][startField.column()] = target.getPiece();
    }

    @Override
    public Field simulateMove(Move move) throws Move.IllegalMoveException
    {
        return prepareMove(move);
    }


    @Override
    public FieldSet getFieldsWithPiece(IPiece piece)
    {
        FieldSet fieldsWithPiece = new FieldSet();
        for (int row = 0; row < getRowLength(); row++)
        {
            for (int column = 0; column < getColumnLength(); column++)
            {
                if (board[row][column].equals(piece))
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
        FieldSet allFieldsWithPiece = getFieldsWithPiece(move.piece);
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
                if (board[row][column] != other.board[row][column])
                {
                    return false;
                }
            }
        }
        return true;
    }
}
