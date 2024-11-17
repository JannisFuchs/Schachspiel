package de.dhbw.schachspiel.Interfaces;

public interface AbstractGame {

  boolean checkForLegalMove();

  void makeMove();

  boolean checkForWin();
}
