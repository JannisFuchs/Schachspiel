package de.dhbw.schachspiel.Classes.Pieces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Field;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

import java.util.List;

public class Rook implements AbstractPiece {
    private Color color;
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public List<Field> getStartingPosition() {
        return List.of(
                new Field(0, 0),
                new Field(0, 7),
                new Field(7, 0),
                new Field(7, 7)
        );
    }

    @Override
    public String getSymbol() {
        return "♜";
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
