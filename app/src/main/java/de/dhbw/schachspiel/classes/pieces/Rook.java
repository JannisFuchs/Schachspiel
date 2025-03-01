package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

public record Rook (Color c) implements AbstractPiece {


    @Override
    public char getSymbol() {
        return '♜';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public Field calculateStartField(Move move, AbstractPiece[][] board) {
        return null;
    }
}
