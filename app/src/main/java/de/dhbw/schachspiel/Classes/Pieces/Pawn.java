package de.dhbw.schachspiel.Classes.Pieces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Field;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public class Pawn implements AbstractPiece {
    private Color color;
    @Override
    public List<Field> getStartingPosition() {
        List<Field> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            result.add(new Field(1, i));
            result.add(new Field(6, i));
        }
        return result;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String getSymbol() {
        return "♟";
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
