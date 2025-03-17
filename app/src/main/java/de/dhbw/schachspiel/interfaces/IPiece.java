package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;

import java.util.List;

public interface IPiece {
  // gets the piece as a unicode character
  char getSymbol();
  Color getColor();
  PieceType getPieceType();
  List<Field> getCandidateFields(Field target, IBoard board);

  /**
   *
   * @param move the move this piece is about to make
   * @param board the whole board before the piece moves
   * @return the field on which the piece currently stands
   */
  Field calculateStartField(List<Field>candidateFields,Move move, IBoard board) throws Move.IllegalMoveException;
}
