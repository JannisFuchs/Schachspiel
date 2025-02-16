package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

public class Rook implements AbstractPiece {
    private Color color;

    @Override
    public char getSymbol() {
        return '♜';
    }



    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
this.color = color;
    }

    @Override
    public Field calculateStartField(Move move, AbstractPiece[][] board) {
        return null;
    }
}
