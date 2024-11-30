package de.dhbw.schachspiel.Classes.Pieces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Field;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

import java.util.List;

public class Bishop implements AbstractPiece {
    private Color color;
    @Override
    public List<Field> getStartingPosition() {
        return List.of(
                new Field(0, 2),
                new Field(0, 5),
                new Field(7, 2),
                new Field(7, 5)
        );
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String getSymbol() {
        return "♝";
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
