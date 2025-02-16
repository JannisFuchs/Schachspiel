package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.Pawn;
import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

public class Game {
  int currentPlayer ;
  Player[] players = new Player[2];

	public Game() {
    players[0] = new Player(Color.WHITE);
    players[1] = new Player(Color.BLACK);
    currentPlayer = 0;
    AbstractPiece[][] board = createBoard();
    Visualisation.drawBoard(board);
    for(int i=0;i<8;i++){
      //this is a duplication see Move class
      boolean isValid = false;
      while(!isValid){
        try{
          Move m = players[currentPlayer].readMove();
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
    Field startField = piece.calculateStartField(move,board);
    int startRow = startField.row();
    int startCol = startField.column();
    AbstractPiece currentPiece = board[startRow][startCol];
    if(!checkPieceEquality(currentPiece,piece)) throw new Move.IllegalMoveException("Illegal move wrong piece");
    //0 because it defaults to None anyway Color also doesn't matter
    board[startRow][startCol] = PieceFactory.createPieceFromLetter('0',Color.RESET);
    int endRow = move.end.row();
    int endCol = move.end.column();
    board[endRow][endCol] = currentPiece;

  }

  private boolean checkPieceEquality(AbstractPiece p1, AbstractPiece p2) {
      return p1.getSymbol() == p2.getSymbol();
  }
  public boolean checkForWin() {
    return false;
  }

}
