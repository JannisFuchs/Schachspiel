package de.dhbw.schachspiel.Classes;

import de.dhbw.schachspiel.Classes.Pieces.Pawn;
import de.dhbw.schachspiel.Classes.Pieces.PieceFactory;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move {
  public Field start;
  public Field end;
  public AbstractPiece piece;
  public boolean isCapture;
  public boolean isCheck;
  public boolean isCheckmate;
  private enum columns {
    a, b, c, d, e, f, g, h
  }
  /**
   * @param stringRepresentation : Move in algebraic notation
   * String should match pattern : ([RBQKPN])?([a-h])?([1-8])?([x])?([a-h])([1-8])([+#]?)
   * ([RBQKN])? - Piece optional because pawn moves don't have a piece
   * ([a-h])? - Start column optional because most moves unambiguous
   * ([1-8])? - Start row optional because most moves unambiguous
   * ([x])? - Capture optional
   * ([a-h]) - End column
   * ([1-8]) - End row
   * ([+#]?) - Check or checkmate optional
   */
  public Move(String stringRepresentation, Color c) throws IllegalMoveException {

    Matcher matcher = Pattern.compile("([RBQKPN])?([a-h])?([1-8])?([x])?([a-h])([1-8])([+#]?)").matcher(stringRepresentation);
    if (!matcher.matches())
      throw new IllegalMoveException("Invalid Move");
    piece = (matcher.group(0) == null) ? PieceFactory.createPieceFromSymbol("P", c) : PieceFactory.createPieceFromSymbol(matcher.group(0), c);
    start = new Field(columns.valueOf(matcher.group(1).toLowerCase()).ordinal(), Integer.parseInt(matcher.group(2)) - 1);
    isCapture = matcher.group(3) != null;
    end = new Field(columns.valueOf(matcher.group(4).toLowerCase()).ordinal(), Integer.parseInt(matcher.group(5)) - 1);
    isCheck = matcher.group(6) != null && matcher.group(6).equals("+");
    isCheck = matcher.group(6) != null && matcher.group(6).equals("#");
  }


  public static class IllegalMoveException extends Exception {
    public IllegalMoveException(String message) {
      super(message);
    }
  }

}
