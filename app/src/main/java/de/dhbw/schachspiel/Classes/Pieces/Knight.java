package de.dhbw.schachspiel.Classes.Pieces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Position;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

import java.util.List;

public class Knight implements AbstractPiece {
    private Color color;
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public List<Position> getStartingPosition() {
        return List.of(
                new Position(0, 1),
                new Position(0, 6),
                new Position(7, 1),
                new Position(7, 6)
        );
    }

    @Override
    public String getSymbol() {
        return "♞";
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
