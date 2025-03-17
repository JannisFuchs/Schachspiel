package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.List;

public record None (Color c) implements IPiece {


    @Override
    public char getSymbol() {
        return ' ';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.NONE;
    }

    @Override
    public List<Field> getCandidateFields(Field target, IBoard board) {
        return List.of();
    }

    @Override
    public Field calculateStartField(List<Field> fieldList, Move move, IBoard board) {
        throw new IllegalCallerException("This class is just a null wrapper and this method should not be called");
    }
}
