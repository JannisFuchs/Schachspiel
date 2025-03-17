package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard {
    private final IPiece[][] board;
    public Board(int rows, int columns) {
        board = PieceFactory.createBoard(rows, columns);
    }
    public Board(IPiece [][] testBoard) {
        this.board = testBoard;
    }
    @Override
    public IPiece getPiece(Field currentField) {
        return board[currentField.row()][currentField.column()];
    }

    @Override
    public int getRowLength() {
        return board.length;
    }

    @Override
    public int getColumnLength() {
        return board[0].length;
    }
    public boolean isOccupiedByColor(Color color, Field target) {
        IPiece piece = board[target.row()][target.column()];
        if(piece.getPieceType() == PieceType.NONE) return false;
		return piece.getColor() == color;
	}

    /**
   * checks if a field is occupied. This is sufficient for normal pieces but pawns need extra checking
   * It doesn't return a boolean because if the Field is invalid an exception is thrown
   * @param move the move the player is about to make
   * @throws Move.IllegalMoveException thrown when the field is either occupied by your own piece or occupied by the enemy piece and no capture is specified
   */
    private void checksField(Move move) throws Move.IllegalMoveException {
        Field target = move.target;
        //these ifs check if the target field is free might move the code up
        if (isOccupiedByColor(move.piece.getColor(), target)) {
            throw new Move.IllegalMoveException("Can't capture your own piece");
        }
        Color enemyColor = Color.getOtherColor(move.piece.getColor());
        if (isOccupiedByColor(enemyColor, target)&&!move.isCapture){
            throw new Move.IllegalMoveException("This move is not a capture");
        }
    }
public void makeMove(Move move) throws Move.IllegalMoveException {
    IPiece piece = move.piece;
    checksField(move);
    List<Field> fields = selectFields(move);
    Field startField = piece.calculateStartField(fields,move,this);
    int startRow = startField.row();
    int startCol = startField.column();
    IPiece currentPiece = board[startRow][startCol];
    if(!currentPiece.equals(piece)) {
        throw new Move.IllegalMoveException("wrong piece");
    }
    board[startRow][startCol] = PieceFactory.createPieceFromType(PieceType.NONE,Color.RESET);
    int endRow = move.target.row();
    int endCol = move.target.column();
    board[endRow][endCol] = currentPiece;

  }
  @Override
  public List<Field> getFieldsWithPiece(IPiece piece) {
      List<Field> fieldsWithPiece = new ArrayList<>();
      for (int i = 0; i < getRowLength(); i++) {
          for (int j = 0; j < getColumnLength(); j++) {
              if (board[i][j].equals(piece)) {
                  fieldsWithPiece.add(new Field(i, j));
              }
          }
      }
      return fieldsWithPiece;
  }
  public List<Field> selectFields(Move move){
        IPiece movingPiece = move.piece;
        Field target = move.target;
        List<Field> candidateFields = movingPiece.getCandidateFields(target,this);
        List<Field> allFieldsWithPiece = getFieldsWithPiece( move.piece);
      return Field.intersectionOfFieldList(candidateFields,allFieldsWithPiece);
    }

}
