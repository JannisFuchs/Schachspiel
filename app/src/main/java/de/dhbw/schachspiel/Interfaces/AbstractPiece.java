package de.dhbw.schachspiel.Interfaces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Position;

import java.util.List;

public interface AbstractPiece {
  boolean isValid();
  List<Position> getStartingPosition();
  String getSymbol();
  Color getColor();
  void setColor(Color color);
}
