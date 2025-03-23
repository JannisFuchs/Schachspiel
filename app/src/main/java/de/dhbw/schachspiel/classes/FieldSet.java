package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IBoard;

import java.util.HashSet;
import java.util.Iterator;

public class FieldSet
{
	private final HashSet<Field> set = new HashSet<>();

	public void add(Field field)
	{
		set.add(field);
	}

	public void addAll(FieldSet fieldSet)
	{
		set.addAll(fieldSet.set);
	}

	public Iterator<Field> getIterator()
	{
		return set.iterator();
	}

	public Field getSingleItem()
	{
		if (set.size() != 1)
		{
			throw new IllegalStateException("Set has invalid size");
		}
		Field[] field = set.toArray(new Field[1]);
		return field[0];
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

	public FieldSet intersectionOfFieldList(FieldSet otherList)
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

}
