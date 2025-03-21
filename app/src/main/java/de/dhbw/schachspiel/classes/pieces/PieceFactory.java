package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IPiece;

public class PieceFactory
{

    private PieceFactory()
    {
    }

    public static IPiece createPieceFromField(Field p, Color c)
    {
        IPiece piece;
        if (p.row() == 1 || p.row() == 6)
        {
            piece = new Pawn(c);
        }
        else
        {
            if (p.row() < 6 && p.row() > 1)
            {
                piece = new None(c);
            }
            else
            {
                if (p.column() == 0 || p.column() == 7)
                {
                    piece = new Rook(c);
                }
                else
                {
                    if (p.column() == 1 || p.column() == 6)
                    {
                        piece = new Knight(c);
                    }
                    else
                    {
                        if (p.column() == 2 || p.column() == 5)
                        {
                            piece = new Bishop(c);
                        }
                        else
                        {
                            if (p.column() == 3)
                            {
                                piece = new Queen(c);
                            }
                            else
                            {
                                piece = new King(c);
                            }
                        }
                    }
                }
            }
        }

        return piece;

    }

    public static IPiece[][] createBoard(int rows, int columns)
    {
        IPiece[][] board = new IPiece[8][8];
        for (int row = 0; row < rows; row++)
        {
            Color pieceColor = row < 2 ? Color.BLACK : Color.WHITE;
            for (int column = 0; column < columns; column++)
            {
                board[row][column] = PieceFactory.createPieceFromField(new Field(row, column), pieceColor);
            }
        }
        return board;
    }

    public static IPiece createPieceFromType(PieceType type, Color c)
    {
        IPiece piece;
        piece = switch (type)
        {
            case ROOK -> new Rook(c);
            case KNIGHT -> new Knight(c);
            case BISHOP -> new Bishop(c);
            case QUEEN -> new Queen(c);
            case KING -> new King(c);
            case PAWN -> new Pawn(c);
            case NONE -> new None(c);
        };
        return piece;

    }

}
