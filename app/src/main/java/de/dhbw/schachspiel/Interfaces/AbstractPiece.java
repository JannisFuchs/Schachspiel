package de.dhbw.schachspiel.Interfaces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Field;

import java.util.List;

public interface AbstractPiece {
  boolean isValid();
  List<Field> getStartingPosition();
  String getSymbol();
  Color getColor();
  void setColor(Color color);
}
