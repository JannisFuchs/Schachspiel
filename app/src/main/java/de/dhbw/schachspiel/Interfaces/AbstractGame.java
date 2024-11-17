package de.dhbw.schachspiel.Interfaces;

public interface AbstractGame {

  void init();

  boolean checkForLegalMove();

  void makeMove();

  boolean checkForWin();
}
