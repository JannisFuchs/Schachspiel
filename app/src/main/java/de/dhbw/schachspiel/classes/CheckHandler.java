package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckHandler
{
    private IBoard boardCopy;
    private final IBoard originalBoard;
    private final BoardHelper helper;
    private final PieceColor color;
    private final Map<Field, IPiece> defenders;

    public CheckHandler(IBoard board, PieceColor color)
    {
        this.originalBoard = board;
        this.boardCopy = board.copy();
        this.color = color;
        helper = new BoardHelper(board);
        defenders = helper.getAllPiecesFromColor(color);
    }
    private List<Move> generateOptions(){
        List<Move> possibleMoves = new ArrayList<>();
        for (Map.Entry<Field, IPiece> entry : defenders.entrySet()){
            Field field = entry.getKey();
            IPiece piece = entry.getValue();
            FieldSet possibleTargets = piece.getCandidateFields(field, originalBoard);
            possibleMoves.addAll(generateMovesForFieldSet(possibleTargets, field, piece));
        }

        return possibleMoves;
    }

    private List<Move> generateMovesForFieldSet(FieldSet set, Field startField, IPiece piece)
    {
        List<Move> possibleMoves = new ArrayList<>();
        for (Field target : set.getSet())
        {
            if (target.isOccupiedByColor(color, originalBoard) && !(piece instanceof None)){
                continue;
            }
            boolean isCapture = target.isOccupiedByColor(color.getOtherColor(), originalBoard) && !(piece instanceof None);
            Move move = new Move(startField,target, piece, isCapture);
            possibleMoves.add(move);
        }
        return possibleMoves;
    }

    public boolean isCheck()
    {
        Field currentKingField = boardCopy.getKingField(color);
        PieceColor otherColor = color.getOtherColor();
        return !helper.getAttacker(currentKingField, otherColor).isEmpty();
    }

    public boolean isMate()
    {
        if (!isCheck())
        {
            return false;
        }
        return testMoves();
    }
    private boolean testMoves(){
        List<Move> possibleMoves = generateOptions();
        for (Move move : possibleMoves){
            try{
                boardCopy.makeMove(move);
            } catch (Move.IllegalMoveException e)
            {
                continue;
            }
            CheckHandler handler = new CheckHandler(boardCopy, color);
            if (!handler.isCheck()){
                boardCopy = originalBoard.copy();
                return false;
            }
            boardCopy = originalBoard.copy();
        }

        return true;
    }

    //insufficient material : King has only Bishop or Knight or Bishop and Bishop and Knight and Knight or neither
    private boolean colorHasSufficientMaterial(PieceColor color)
    {
        boolean hasBishop = false;
        boolean hasKnight = false;
        for (Map.Entry<Field, IPiece> entry : helper.getAllPiecesFromColor(color).entrySet())
        {
            IPiece piece = entry.getValue();
            switch (piece.getPieceType())
            {
                case BISHOP:
                    hasBishop = true;
                    break;
                case KNIGHT:
                    hasKnight = true;
                    break;
                case KING:
                    break;
                default:
                    return true;
            }
            if (hasBishop && hasKnight)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isInsufficientMaterial()
    {
        return !(colorHasSufficientMaterial(PieceColor.WHITE) || colorHasSufficientMaterial(PieceColor.BLACK));
    }

    private int getIndexOfLastMoveOfColor(PieceColor currentColor, List<Move> moveList)
    {
        int lastIndex = moveList.size() - 1;
        Move lastElement = moveList.get(lastIndex);
        if (lastElement.piece.getColor() == currentColor)
        {
            return lastIndex;
        }
        return lastIndex - 1;
    }

    private boolean isThreeFoldColor(List<Move> moveList, PieceColor currentColor)
    {
        int lastIndexOfColor = getIndexOfLastMoveOfColor(currentColor, moveList);
        Move lastMove = moveList.get(lastIndexOfColor);
        for (int moveIndex = lastIndexOfColor - 2; moveIndex > lastIndexOfColor - 7; moveIndex -= 2)
        {
            Move currentMove = moveList.get(moveIndex);
            if (!currentMove.equals(lastMove))
            {
                return false;
            }
        }
        return true;
    }

    public boolean isThreeFold(List<Move> moveList)
    {
        if (moveList.size() < 6)
        {
            return false;
        }
        return isThreeFoldColor(moveList, PieceColor.WHITE) && isThreeFoldColor(moveList, PieceColor.BLACK);
    }

    public boolean fiftyMoveRule(List<Move> moveList)
    {
        if (moveList.size() < 100)
        {
            return false;
        }
        int indexLastMove = moveList.size() - 1;
        for (int indexMove = indexLastMove; indexMove > indexLastMove - 100; indexMove--)
        {
            Move currentMove = moveList.get(indexMove);
            if (currentMove.isCapture || currentMove.piece.getPieceType() == PieceType.PAWN)
            {
                return false;
            }
        }
        return true;
    }


    public boolean isDraw(List<Move> moveList)
    {
        return isStalemate()
                || isInsufficientMaterial()
                || fiftyMoveRule(moveList)
                || isThreeFold(moveList);
    }

    public boolean isStalemate()
    {
        if (isCheck())
        {
            return false;
        }
        return testMoves();

    }

    public PieceColor getColor()
    {
        return color;
    }
}
