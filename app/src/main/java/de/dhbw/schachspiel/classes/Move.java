package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move
{
    public final Field start;
    public final Field target;
    public final IPiece piece;
    public final boolean isCheck;
    public final boolean isMate;
    public final boolean isCapture;
    public final IPiece promotedPiece;


    public Move(Field start, Field target, IPiece piece, boolean isCapture, boolean isCheck, boolean isMate, IPiece promotion)
    {
        this.piece = piece;
        this.start = start;
        this.target = target;
        this.isCapture = isCapture;
        this.isCheck = isCheck;
        this.isMate = isMate;
        this.promotedPiece = promotion;
    }
    public Move(Field start, Field target, IPiece piece, boolean isCapture)
    {
        this(start, target, piece, isCapture, false, false, PieceFactory.createPieceFromType(PieceType.NONE, piece.getColor()));
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(start, target, piece, isCheck, isMate, isCapture, promotedPiece);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Move move = (Move) o;
        return isCheck == move.isCheck
                && isMate == move.isMate
                && isCapture == move.isCapture
                && Objects.equals(start, move.start)
                && Objects.equals(target, move.target)
                && Objects.equals(piece, move.piece)
                && Objects.equals(promotedPiece, move.promotedPiece);
    }

    /**
     * @param stringRepresentation : Move in algebraic notation
     *                             String should match pattern : ([RBQKN])?([a-h])?([1-8])?(x)?([a-h])([1-8]([+#])?)
     *                             ([RBQKN])? - Piece optional because Pawns have no indicator
     *                             ([a-h])? - Start column optional
     *                             ([1-8])? - Start row
     *                             (x)? -  capture
     *                             ([a-h]) - End column
     *                             ([1-8]) - End row
     *                             (=[RBQKN]) - promotion
     *                             ([+#])? - check or mate
     * @param c                    the color the current pieces have
     */

    public Move(String stringRepresentation, PieceColor c) throws IllegalMoveException
    {

        Matcher matcher = Pattern.compile("([RBQKN])?([a-h])?([1-8])?(x)?([a-h])([1-8])(=[RBQKN])?([+#])?").matcher(stringRepresentation);
        if (!matcher.matches())
        {
            throw new IllegalMoveException("incorrect Notation");
        }
        piece = getPiece(matcher.group(1),c);
        start = getField(matcher.group(3), matcher.group(2));
        isCapture = isCapture(matcher.group(4));
        target = getField(matcher.group(6), matcher.group(5));
        promotedPiece = getPromotion(matcher.group(7));
        isCheck = isCheck(matcher.group(8));
        isMate = isMate(matcher.group(8));


    }
    private IPiece getPiece(String representation, PieceColor c)
    {
        if (representation == null)
        {
            return PieceFactory.createPieceFromType(PieceType.PAWN, c);
        }
        return PieceFactory.createPieceFromType(PieceType.pieceTypeFromChar(representation.charAt(0)), c);
    }
    private boolean isCapture(String stringRepresentation) throws IllegalMoveException
    {
        if (stringRepresentation == null)
        {
            return false;
        }
        boolean capture = stringRepresentation.contains("x");
        int startCol = start.column();
        if (capture && piece.getPieceType() == PieceType.PAWN && startCol == -1)
        {

            throw new IllegalMoveException("Illegal notation");
        }
        return capture;
    }
    private Field  getField(String rowRepresentation, String columnRepresentation){
        int row = -1;
        int col = -1;
        if (rowRepresentation != null)
        {
            row = 8 - Integer.parseInt(rowRepresentation);
        }
        if (columnRepresentation != null)
        {
            col = Columns.valueOf(columnRepresentation.toUpperCase()).ordinal();
        }
        return new Field(row, col);
    }
    private IPiece getPromotion(String representation) throws IllegalMoveException
    {
        IPiece promotion = PieceFactory.createPieceFromType(PieceType.NONE, piece.getColor());
        if (representation != null){
            promotion = PieceFactory.createPieceFromType(PieceType.pieceTypeFromChar(representation.charAt(1)), piece.getColor());
        }



        if (piece.getPieceType() != PieceType.PAWN && promotion.getPieceType() != PieceType.NONE)
        {
            throw new IllegalMoveException("Illegal promotion");
        }
        return promotion;
    }
    private boolean isCheck(String stringRepresentation)
    {
        if (stringRepresentation == null)
        {
            return false;
        }
        return stringRepresentation.contains("+") || stringRepresentation.contains("#");

    }
    private boolean isMate(String stringRepresentation)
    {
        if (stringRepresentation == null)
        {
            return false;
        }
        return stringRepresentation.contains("#");
    }

    private enum Columns
    {
        A, B, C, D, E, F, G, H
    }

    public static class IllegalMoveException extends RuntimeException
    {
        public IllegalMoveException(String message)
        {

            super("Illegal move : " + message);
        }
    }

    /**
     * more than one piece can execute this move
     */
    public static class AmbiguousMoveException extends IllegalMoveException
    {

        public AmbiguousMoveException()
        {
            super("ambiguous");
        }
    }

    /**
     * The current move is marked as a normal move but the target field contains an enemy piece
     */
    public static class NoCaptureException extends IllegalMoveException
    {

        public NoCaptureException()
        {
            super("is marked as a normal move but captures a piece");
        }
    }

    /**
     * The current move is marked as a capture but the target field does not have an enemy piece
     */
    public static class CaptureException extends IllegalMoveException
    {

        public CaptureException()
        {
            super("is marked as a capture but does not capture a piece");
        }
    }

}
