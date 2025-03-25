package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

public class FieldPieceMap
{
	private final List<Field> fields = new ArrayList<>();
	private final List<IPiece> pieces = new ArrayList<>();
	public final int maxSize;

	public FieldPieceMap(int maxSize)
	{
		this.maxSize = maxSize;
	}

	public void clear()
	{
		fields.clear();
		pieces.clear();
	}

	public void addPair(Field field, IPiece piece)
	{
		if (fields.contains(field))
		{
			throw new IllegalArgumentException("Field is already in the map");
		}
		if (fields.size() < maxSize)
		{
			fields.add(field);
			pieces.add(piece);
		}
		throw new IndexOutOfBoundsException();

	}

	public IPiece getPiece(Field field)
	{
		if (!fields.contains(field))
		{
			return PieceFactory.createPieceFromType(PieceType.NONE, PieceColor.RESET);
		}
		int index = fields.indexOf(field);
		return pieces.get(index);
	}

	public boolean isEmpty()
	{
		return fields.isEmpty();
	}


}
