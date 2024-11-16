package de.dhbw.schachspiel.Classes;

import de.dhbw.schachspiel.Interfaces.AbstractPiece;

public class Field {
  public AbstractPiece piece;

  public int X;
  public int Y;
  public color c;
  public enum color {
    BLACK, WHITE
  }
}
