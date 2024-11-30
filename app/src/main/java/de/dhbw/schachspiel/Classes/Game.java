package de.dhbw.schachspiel.Classes;

import de.dhbw.schachspiel.Classes.Pieces.PieceFactory;
import de.dhbw.schachspiel.Interfaces.AbstractGame;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

public class Game implements AbstractGame {
  int currentPlayer = 0;
  Player[] players = new Player[2];
  private final AbstractPiece  [][] board;
  public Game() {
    players[0] = new Player();
    players[1] = new Player();
    currentPlayer = 0;
    board = createBoard();
    Visualisation.drawBoard(board);
  }
  private void switchPlayer() {
    currentPlayer = (currentPlayer + 1) % players.length;
  }
  public AbstractPiece[][] createBoard() {
    AbstractPiece[][] board = new AbstractPiece[8][8];
    for (int i = 0; i < 8; i++) {
      Color pieceColor = i < 2 ? Color.WHITE : Color.BLACK;
      for (int j = 0; j < 8; j++) {
        board[i][j] = PieceFactory.createPieceFromField(new Field(i, j),pieceColor);
      }
    }
    return board;
  }

  @Override
  public boolean checkForLegalMove() {
    return false;
  }

  @Override
  public void makeMove() {

  }

  @Override
  public boolean checkForWin() {
    return false;
  }

}
