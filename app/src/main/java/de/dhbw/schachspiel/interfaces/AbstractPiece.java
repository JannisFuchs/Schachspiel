package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Color;

public interface AbstractPiece {
  boolean isValid();
  char getSymbol();
  Color getColor();
  void setColor(Color color);
}
