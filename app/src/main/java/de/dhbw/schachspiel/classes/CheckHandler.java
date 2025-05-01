package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckHandler
{
    private final IBoard boardCopy;
    private final IBoard originalBoard;
    private final BoardHelper helper;
    private final PieceColor color;
    private final PieceColor enemyColor;
    private final Map<Field, IPiece> defenders;

    public CheckHandler(IBoard board, PieceColor color)
    {
        this.originalBoard = board;
        this.boardCopy = board.copy();
        this.color = color;
        this.enemyColor = color.getOtherColor();
        helper = new BoardHelper(board);
        defenders = helper.getAllPiecesFromColor(color);
    }
    private List<Move> generateOptions(){
        return new ArrayList<Move>();
    }

    public boolean isCheck()
    {
        Field currentKingField = boardCopy.getKingField(color);
        PieceColor otherColor = color.getOtherColor();
        return !helper.getAttacker(currentKingField, otherColor).isEmpty();
    }

    public boolean canCaptureAttacker(Field attackField, PieceColor attackerColor)
    {
        FieldSet defenderFields = helper.getAttacker(attackField, attackerColor);
        for (Field defenderField : defenderFields.getSet())
        {
            if (!defenders.containsKey(defenderField))
            {
                continue;
            }
            Move attack = new Move(defenderField, attackField, defenders.get(defenderField), true, false, false, new None(PieceColor.WHITE));
            if (isMoveExecutable(attack))
            {
                return true;
            }
        }
        return false;

    }

    public boolean isMoveExecutable(Move move)
    {
        try
        {
            boardCopy.makeMove(move);
        } catch (Move.IllegalMoveException e)
        {
            return false;
        }
        boolean isCheck = isCheck();

        return !isCheck;
    }

    public boolean canBlockAttacker(Field kingField, Field fieldToBlock, IPiece attacker)
    {
        if (attacker.getPieceType() == PieceType.KNIGHT)
        {
            return false;
        }
        FieldSet blockingFields = kingField.getFieldsInBetween(fieldToBlock);
        for (Map.Entry<Field, IPiece> defender : defenders.entrySet())
        {
            if (canMoveToFields(defender.getKey(), blockingFields))
            {
                return true;
            }
        }
        return false;
    }

    public boolean canMoveToFields(Field start, FieldSet target)
    {
        IPiece piece = boardCopy.getPiece(start);
        for (Field currentTarget : target.getSet())
        {

            Move move = new Move(start, currentTarget, piece, false, false, false, new None(PieceColor.WHITE));
            if (isMoveExecutable(move))
            {
                return true;
            }
        }
        return false;
    }

    public boolean pieceCanMove(PieceType type)
    {

        FieldSet fieldsOfPiece = boardCopy.getFieldsWithPiece(type, color);
        for (Field field : fieldsOfPiece.getSet())
        {
            IPiece currentPiece = boardCopy.getPiece(field);
            FieldSet candidateFields = currentPiece.getCandidateFields(field, boardCopy);
            FieldSet occupiedByOwnColor = candidateFields.getOccupiedByColor(boardCopy, currentPiece.getColor());
            FieldSet freeFields = candidateFields.difference(occupiedByOwnColor);
            if (canMoveToFields(field, freeFields))
            {
                return true;
            }

        }

        return false;
    }


    public boolean canOtherPieceDefendKing()
    {

        Field currentKingField = boardCopy.getKingField(color);
        FieldSet attacker = helper.getAttacker(currentKingField, enemyColor);
        if (attacker.size() > 1)
        {
            return false;
        }
        Field attackField = attacker.getSingleItem();
        IPiece attackerPiece = boardCopy.getPiece(attackField);
        if (canBlockAttacker(currentKingField, attackField, attackerPiece))
        {
            return true;
        }

        return canCaptureAttacker(attackField, color);
    }

    public boolean isMate()
    {
        if (!isCheck())
        {
            return false;
        }
        List<Move> possibleMoves = generateOptions();
        for (Move move : possibleMoves){
            boardCopy.makeMove(move);
            CheckHandler handler = new CheckHandler(boardCopy, color);
            if (!handler.isCheck()){
                return false;
            }
        }

        return true;
    }

    //insufficient material : King has only Bishop or Knight or Bishop and Bishop and Knight and Knight
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
        for (PieceType type : PieceType.values())
        {
            if (pieceCanMove(type))
            {
                return false;
            }
        }
        return true;

    }

    public PieceColor getColor()
    {
        return color;
    }
}
