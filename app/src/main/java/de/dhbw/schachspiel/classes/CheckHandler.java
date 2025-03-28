package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

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

    public boolean kingCanMove()
    {

        Field kingField = board.getKingField(color);
        IPiece king = board.getPiece(kingField);
        FieldSet candidateFields = king.getCandidateFields(kingField, board);
        FieldSet occupiedByOwnColor = candidateFields.getOccupiedByColor(board, king.getColor());
        FieldSet freeFields = candidateFields.difference(occupiedByOwnColor);

        return canMoveToFields(kingField, freeFields);
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
        if (kingCanMove())
        {
            return false;
        }

        return !canOtherPieceDefendKing();
    }

    public PieceColor getColor()
    {
        return color;
    }
}
