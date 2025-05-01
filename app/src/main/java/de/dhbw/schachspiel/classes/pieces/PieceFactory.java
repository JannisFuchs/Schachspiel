package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.PieceColor;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IPiece;

public class PieceFactory
{

    private PieceFactory()
    {
    }


    public static IPiece[][] createBoardFromFEN(String position) throws IndexOutOfBoundsException
    {
        IPiece[][] board = new IPiece[8][8];
        String[] rows = position.split("/");
        for (int row = 0; row < 8; row++)
        {
            char[] pieceChars = rows[row].toCharArray();
            IPiece[] rowPieces = new IPiece[8];
            for (char piece : pieceChars)
            {
                IPiece[] pieces = generatePiecesFromChar(piece);
                mergePieceArrayIntoArray(rowPieces, pieces);
            }
            board[row] = rowPieces;
        }
        return board;
    }

    private static void mergePieceArrayIntoArray(IPiece[] base, IPiece[] insert) throws IndexOutOfBoundsException
    {
        int firstEmptyIndex = -1;
        for (int index = 0; index < base.length; index++)
        {
            if (base[index] == null)
            {
                firstEmptyIndex = index;
                break;
            }
        }
        System.arraycopy(insert, 0, base, firstEmptyIndex, insert.length);

    }

    static IPiece[] generatePiecesFromChar(char piece)
    {
        IPiece[] pieces;
        if (Character.isDigit(piece))
        {
            int pieceCount = Character.getNumericValue(piece);
            pieces = new IPiece[pieceCount];
            for (int i = 0; i < pieceCount; i++)
            {
                pieces[i] = new None(PieceColor.WHITE);
            }
        }
        else
        {
            pieces = new IPiece[1];
            PieceColor color;
            if (Character.isUpperCase(piece))
            {
                color = PieceColor.WHITE;
            }
            else
            {
                color = PieceColor.BLACK;
            }
            PieceType type = PieceType.pieceTypeFromChar(Character.toUpperCase(piece));
            pieces[0] = createPieceFromType(type, color);
        }
        return pieces;
    }

    public static IPiece createPieceFromType(PieceType type, PieceColor c)
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
