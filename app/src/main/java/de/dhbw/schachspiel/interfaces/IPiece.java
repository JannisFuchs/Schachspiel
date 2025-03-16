package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;

public interface IPiece {
  // gets the piece as a unicode character
  char getSymbol();
  Color getColor();
  PieceType getPieceType();

  /**
   *
   * @param move the move this piece is about to make
   * @param board the whole board before the piece moves
   * @return the field on which the piece currently stands
   */
  Field calculateStartField(Move move, IBoard board) throws Move.IllegalMoveException;
}
