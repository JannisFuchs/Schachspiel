package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public record CheckHandler(IBoard board, PieceColor color)
{
    public boolean isCheck()
    {
        Field currentKingField = board.getKingField(color);

        return !getAttackingFields(currentKingField, color.getOtherColor()).isEmpty();
    }

    /**
     * @param attack It is important that the move is an attack because of pawns
     * @return if this field can be reached by an attack
     * @throws IllegalArgumentException normal Moves are an illegal Argument
     */
    FieldSet canAttackField(Move attack) throws IllegalArgumentException
    {
        FieldSet attackingFields = new FieldSet();
        if (!attack.isCapture)
        {
            throw new IllegalArgumentException("This method expects an attack");
        }
        try
        {
            attackingFields.add(board.simulateMove(attack));
        } catch (Move.AmbiguousMoveException e)
        {
            return getAmbiguousAttackFields(attack);
        } catch (Move.IllegalMoveException e)
        {
            return attackingFields;
        }
        return attackingFields;
    }

    FieldSet getAmbiguousAttackFields(Move move)
    {
        IPiece currentPiece = move.piece;
        FieldSet allPositionsWithPiece = board.getFieldsWithPiece(currentPiece);
        FieldSet union = new FieldSet();
        for (Field currentField : allPositionsWithPiece.getSet())
        {
            Move attack = new Move(currentField, move.target, currentPiece, true, false, false);
            FieldSet attackingFields = canAttackField(attack);
            union.addAll(attackingFields);
        }
        return union;
    }

    Move createAttack(Field target, IPiece piece)
    {
        Field start = new Field(-1, -1);
        return new Move(start, target, piece, true, false, false);
    }

    FieldSet getAttacker()
    {

        return new FieldSet();
    }
    boolean canDoMove(Field start,Field target, boolean isAttack){
        IBoard clone = board.copy();
        IPiece defender = clone.getPiece(start);
        Move defenseCapture = new Move(start, target, defender, isAttack, false, false);
        try{
            clone.makeMove(defenseCapture);
        } catch (Move.IllegalMoveException e)
        {
            throw new RuntimeException(e);
        }
        CheckHandler handler = new CheckHandler(clone, color);
        return handler.isCheck();

    }
    boolean canCaptureAttacker(Field attackField)
    {
        FieldSet defenderFields = getAttackingFields(attackField, color);
        for (Field defenderField : defenderFields.getSet())
        {
            if (canDoMove(defenderField, attackField, true))
            {
                return true;
            }

        }
        return false;

    }

    boolean canBlockAttacker(Field fieldToBlock, IPiece attacker)
    {

    }

    boolean kingCanMove()
    {

        Field kingField = board.getKingField(color);
        IPiece king = board.getPiece(kingField);
        FieldSet candidateFields = king.getCandidateFields(kingField, board);
        FieldSet occupiedByOwnColor = candidateFields.getOccupiedByColor(board, king.getColor());
        FieldSet freeFields = candidateFields.difference(occupiedByOwnColor);

        return !isAnyFieldAttacked(freeFields);
    }

    boolean isAnyFieldAttacked(FieldSet set)
    {

        for (Field currentField : set.getSet())
        {

            if (!getAttackingFields(currentField,color.getOtherColor()).isEmpty())
            {
                return true;
            }
        }
        return false;
    }


    FieldSet getAttackingFields(Field field, PieceColor enemyColor)
    {
        FieldSet attackingFields = new FieldSet();
        for (PieceType type : PieceType.values())
        {
            IPiece attackingPiece = PieceFactory.createPieceFromType(type, enemyColor);
            Move attack = createAttack(field, attackingPiece);
            attackingFields.addAll(canAttackField(attack));

        }
        return attackingFields;
    }
    boolean canOtherPieceDefendKing()
    {
        FieldSet attacker = getAttacker();
        if (attacker.size() > 1)
        {
            return false;
        }
        Field attackField = attacker.getSingleItem();
        IPiece attackerPiece = board.getPiece(attackField);
        if (canCaptureAttacker(attackField)
        {
            return true;
        }
        return canBlockAttacker(attackField, attackerPiece);
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

        return canOtherPieceDefendKing();
    }
}
