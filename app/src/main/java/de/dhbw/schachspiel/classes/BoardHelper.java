package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.HashMap;
import java.util.Map;

public class BoardHelper
{
    private final IBoard board;

    public BoardHelper(IBoard board)
    {
        this.board = board;
    }


    public Map<Field, IPiece> getAllPiecesFromColor(PieceColor color)
    {
        Map<Field, IPiece> pieces = new HashMap<>();
        for (int row = 0; row < board.getRowLength(); row++)
        {
            for (int column = 0; column < board.getColumnLength(); column++)
            {
                Field field = new Field(row, column);
                IPiece piece = board.getPiece(field);
                if (piece.getColor() == color)
                {
                    pieces.put(field, piece);
                }
            }
        }
        return pieces;
    }

    public FieldSet getAttacker(Field attackedField, PieceColor attacker)
    {
        FieldSet attackers = new FieldSet();
        for (int row = 0; row < board.getRowLength(); row++)
        {
            for (int column = 0; column < board.getColumnLength(); column++)
            {
                Field currentField = new Field(row, column);
                IPiece piece = board.getPiece(currentField);
                if (piece.getColor() != attacker)
                {
                    continue;
                }
                if (piece.isAbleToAttack(currentField, attackedField, piece.getColor(), board))
                {
                    attackers.add(currentField);
                }
            }
        }
        return attackers;
    }
}
