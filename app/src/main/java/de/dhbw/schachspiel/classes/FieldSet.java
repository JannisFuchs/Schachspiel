package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IBoard;

import java.util.HashSet;

public class FieldSet
{
    private final HashSet<Field> set = new HashSet<>();

    public boolean add(Field field)
    {
        if (field.isValid())
        {
            set.add(field);
            return true;
        }
        return false;
    }

    public void addAll(FieldSet fieldSet)
    {
        for (Field field : fieldSet.getSet())
        {
            add(field);
        }
    }

    public HashSet<Field> getSet()
    {
        return set;
    }

    public Field getSingleItem()
    {
        if (set.size() > 1)
        {
            throw new IllegalStateException("Set has invalid size");
        }
        for (Field field : set)
        {
            return field;
        }
        throw new IllegalStateException("Set is empty");
    }

    public int size()
    {
        return set.size();
    }

    public boolean isEmpty()
    {
        return set.isEmpty();
    }

    public Field findRow(int column) throws Move.IllegalMoveException
    {
        Field target = null;

        for (Field field : set)
        {
            if (field.column() != column)
            {
                continue;
            }
            if (target == null)
            {
                target = field;
                continue;
            }
            throw new Move.AmbiguousMoveException();
        }
        if (target == null)
        {
            throw new Move.IllegalMoveException("row not found");
        }
        return target;
    }

    public Field findColumn(int row) throws Move.IllegalMoveException
    {
        Field target = null;

        for (Field field : set)
        {
            if (field.row() != row)
            {
                continue;
            }
            if (target == null)
            {
                target = field;
                continue;
            }
            throw new Move.AmbiguousMoveException();
        }
        if (target == null)
        {
            throw new Move.IllegalMoveException("No piece found ");
        }
        return target;
    }

    public boolean contains(Field field)
    {
        return set.contains(field);
    }

    public FieldSet difference(FieldSet otherSet)
    {
        FieldSet difference = new FieldSet();
        for (Field field : set)
        {
            if (!otherSet.contains(field))
            {
                difference.add(field);
            }
        }
        return difference;
    }

    public FieldSet intersection(FieldSet otherList)
    {
        FieldSet interceptFields = new FieldSet();
        for (Field field : set)
        {
            if (otherList.contains(field))
            {
                interceptFields.add(field);
            }
        }
        return interceptFields;
    }


    public FieldSet filterReachableByRow(Field target, IBoard board)
    {
        FieldSet reachableFields = new FieldSet();
        for (Field startField : set)
        {

            boolean isReachable = target.isReachableByRow(startField, board);
            if (isReachable)
            {
                reachableFields.add(startField);
            }

        }
        return reachableFields;
    }

    public FieldSet filterReachableByDiagonal(Field target, IBoard board)
    {
        FieldSet reachableFields = new FieldSet();
        for (Field startField : set)
        {

            boolean isReachable = target.isReachableByDiagonal(startField, board);
            if (isReachable)
            {
                reachableFields.add(startField);
            }

        }
        return reachableFields;
    }

    public FieldSet filterReachableByColumn(Field target, IBoard board)
    {
        FieldSet reachableFields = new FieldSet();
        for (Field startField : set)
        {

            boolean isReachable = target.isReachableByColumn(startField, board);
            if (isReachable)
            {
                reachableFields.add(startField);
            }

        }
        return reachableFields;
    }

    public FieldSet getOccupiedByColor(IBoard board, PieceColor color)
    {
        FieldSet filteredSet = new FieldSet();
        for (Field currentField : set)
        {
            if (currentField.isOccupiedByColor(color, board))
            {
                filteredSet.add(currentField);
            }
        }
        return filteredSet;
    }


}
