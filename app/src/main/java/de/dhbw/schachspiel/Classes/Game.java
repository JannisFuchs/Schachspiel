package de.dhbw.schachspiel.Classes;

import de.dhbw.schachspiel.Interfaces.AbstractGame;

public class Game implements AbstractGame {
  int currentPlayer = 0;
  Player[] players = new Player[2];
  @Override
  public void init() {
    players[0] = new Player();
    players[1] = new Player();
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
