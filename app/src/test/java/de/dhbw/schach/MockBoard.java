package de.dhbw.schach;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.HashMap;
import java.util.Map;

public class MockBoard implements IBoard
{
    private final Map<Field, IPiece> board;

    public MockBoard(Map<Field, IPiece> importantPieces)
    {
        this.board = importantPieces;
    }


    @Override
    public void makeMove(Move move) throws Move.IllegalMoveException
    {

    }



    @Override
    public FieldSet getFieldsWithPiece(PieceType type, PieceColor color)
    {
        FieldSet fields = new FieldSet();
        for (Field field : board.keySet())
        {
            IPiece piece = board.get(field);
            if (piece.getPieceType() == type && piece.getColor() == color)
            {
                fields.add(field);
            }
        }
        return fields;
    }

    @Override
    public Field getKingField(PieceColor color)
    {
        FieldSet set = getFieldsWithPiece(PieceType.KING, color);
        return set.getSingleItem();
    }

    @Override
    public IBoard copy()
    {
        HashMap<Field, IPiece> copy = new HashMap<>();
        for (Map.Entry<Field,IPiece> entries: board.entrySet()){
            IPiece copiedPiece = PieceFactory.createPieceFromType(entries.getValue().getPieceType(), entries.getValue().getColor());
            copy.put(entries.getKey(), copiedPiece);
        }
        return new MockBoard(copy);
    }


    @Override
    public IPiece getPiece(Field currentField)
    {
        IPiece piece = board.get(currentField);
        if (piece == null)
        {
            return new None(PieceColor.WHITE);
        }
        return piece;
    }


    @Override
    public int getRowLength()
    {
        return 8;
    }

    @Override
    public int getColumnLength()
    {
        return 8;
    }

}
