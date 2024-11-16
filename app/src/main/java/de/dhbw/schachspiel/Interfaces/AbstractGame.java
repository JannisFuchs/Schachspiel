package de.dhbw.schachspiel.Interfaces;

public interface AbstractGame {
  void addPlayer(AbstractPlayer player);

  void init(int numberOfPlayers);

  boolean checkForLegalMove();

  void makeMove();

  boolean checkForWin();
}
