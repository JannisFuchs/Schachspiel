package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.List;
import java.util.Map;

public class CheckHandler
{
    private final IBoard board;
    private final BoardHelper helper;
    private final PieceColor color;
    private final PieceColor enemyColor;
    private final Map<Field, IPiece> defenders;

    public CheckHandler(IBoard board, PieceColor color)
    {
        this.board = board;
        this.color = color;
        this.enemyColor = color.getOtherColor();
        helper = new BoardHelper(board);
        defenders = helper.getAllPiecesFromColor(color);
    }

    public boolean isCheck()
    {
        Field currentKingField = board.getKingField(color);
        PieceColor enemyColor = color.getOtherColor();
        return !helper.getAttacker(currentKingField, enemyColor).isEmpty();
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
            Move attack = new Move(defenderField, attackField, defenders.get(defenderField), true, false, false);
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
            board.makeMove(move);
        } catch (Move.IllegalMoveException e)
        {
            return false;
        }
        boolean isCheck = isCheck();
        board.undoMove();
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
        IPiece piece = board.getPiece(start);
        for (Field currentTarget : target.getSet())
        {

            Move move = new Move(start, currentTarget, piece, false, false, false);
            if (isMoveExecutable(move))
            {
                return true;
            }
        }
        return false;
    }

    public boolean pieceCanMove(PieceType type)
    {

        FieldSet fieldsOfPiece = board.getFieldsWithPiece(type, color);
        for (Field field : fieldsOfPiece.getSet())
        {
            IPiece currentPiece = board.getPiece(field);
            FieldSet candidateFields = currentPiece.getCandidateFields(field, board);
            FieldSet occupiedByOwnColor = candidateFields.getOccupiedByColor(board, currentPiece.getColor());
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

        Field currentKingField = board.getKingField(color);
        FieldSet attacker = helper.getAttacker(currentKingField, enemyColor);
        if (attacker.size() > 1)
        {
            return false;
        }
        Field attackField = attacker.getSingleItem();
        IPiece attackerPiece = board.getPiece(attackField);
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
        if (pieceCanMove(PieceType.KING))
        {
            return false;
        }

        return !canOtherPieceDefendKing();
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
        return colorHasSufficientMaterial(PieceColor.WHITE) || colorHasSufficientMaterial(PieceColor.BLACK);
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
            Move currentMove = moveList.get(lastIndexOfColor);
            if (!currentMove.equals(lastMove))
            {
                return false;
            }
        }
        return true;
    }

    private boolean isThreeFold(List<Move> moveList)
    {
        if (moveList.size() < 6)
        {
            return false;
        }
        return isThreeFoldColor(moveList, PieceColor.WHITE) && isThreeFoldColor(moveList, PieceColor.BLACK);
    }


    public boolean isDraw()
    {
        return isStalemate() || isInsufficientMaterial();
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
