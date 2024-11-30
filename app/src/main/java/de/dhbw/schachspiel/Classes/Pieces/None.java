package de.dhbw.schachspiel.Classes.Pieces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Field;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

import java.util.List;

public class None implements AbstractPiece {

    @Override
    public List<Field> getStartingPosition() {
        return List.of(new Field(-1, -1));
    }
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public String getSymbol() {
        return " ";
    }

    @Override
    public Color getColor() {
        return Color.RESET;
    }

    @Override
    public void setColor(Color color) {

    }
}
