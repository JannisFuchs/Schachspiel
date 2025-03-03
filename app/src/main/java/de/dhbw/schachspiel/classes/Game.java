package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.Scanner;

public class Game {
  private static final Scanner SCANNER = new Scanner(System.in);
  int currentPlayer ;
  Player[] players = new Player[2];

	public Game() {
    players[0] = new Player(Color.WHITE);
    players[1] = new Player(Color.BLACK);
    currentPlayer = 0;
    AbstractPiece[][] board = createBoard();
    Visualisation.drawBoard(board);
    for(int i=0;i<50;i++){
      //this is a duplication see Move class
      boolean isValid = false;
      while(!isValid){
        try{
          Move m = players[currentPlayer].readMove(SCANNER);
          makeMove(m, board);
          isValid = true;
        }
        catch(Move.IllegalMoveException e){
          System.out.println(e.getMessage());
        }
      }


      Visualisation.drawBoard(board);
      currentPlayer = (currentPlayer + 1) % 2;
    }


  }
  public AbstractPiece[][] createBoard() {
    AbstractPiece[][] board = new AbstractPiece[8][8];
    for (int i = 0; i < 8; i++) {
      Color pieceColor = i < 2 ? Color.BLACK : Color.WHITE;
      for (int j = 0; j < 8; j++) {
        board[i][j] = PieceFactory.createPieceFromField(new Field(i, j),pieceColor);
      }
    }
    return board;
  }


  public boolean checkForLegalMove() {
    return false;
  }


  public void makeMove(Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
    AbstractPiece piece = move.piece;
    checksField(move, board);
    Field startField = piece.calculateStartField(move,board);
    int startRow = startField.row();
    int startCol = startField.column();
    AbstractPiece currentPiece = board[startRow][startCol];
    if(!currentPiece.equals(piece)) throw new Move.IllegalMoveException("wrong piece");
    //0 because it defaults to None anyway Color also doesn't matter
    board[startRow][startCol] = PieceFactory.createPieceFromLetter('0',Color.RESET);
    int endRow = move.target.row();
    int endCol = move.target.column();
    board[endRow][endCol] = currentPiece;

  }

  /**
   * checks if a field is occupied. This is sufficient for normal pieces but pawns need extra checking
   * It doesn't return a boolean because if the Field is invalid an exception is thrown
   * @param move the move the player is about to make
   * @param board the current state of the board
   * @throws Move.IllegalMoveException thrown when the field is either occupied by your own piece or occupied by the enemy piece and no capture is specified
   */
  private void checksField(Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
    Field target = move.target;
    //these ifs check if the target field is free might move the code up
    if (target.isOccupiedByColor(move.piece.getColor(), board))
      throw new Move.IllegalMoveException("Can't capture your own piece");
    Color enemyColor = Color.getOtherColor(move.piece.getColor());
    if (target.isOccupiedByColor(enemyColor, board)&&!move.isCapture){
      throw new Move.IllegalMoveException("This move is not a capture");
    }
  }
  public boolean checkForWin() {
      return false;
  }

}
