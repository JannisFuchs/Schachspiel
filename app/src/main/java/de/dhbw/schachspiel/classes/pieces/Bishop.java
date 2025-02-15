package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

public class Bishop implements AbstractPiece {
    private Color color;
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public char getSymbol() {
        return '♝';
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
