package de.dhbw.schachspiel.Classes;

import de.dhbw.schachspiel.Interfaces.AbstractGame;
import de.dhbw.schachspiel.Interfaces.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public class Game implements AbstractGame {
  List<AbstractPlayer> players;
  int currentPlayer = 0;

  @Override
  public void init(int numberOfPlayers) {
    players = new ArrayList<>();
    for (int i = 0; i < numberOfPlayers; i++) {
      addPlayer(new Player());
    }

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

  @Override
  public void addPlayer(AbstractPlayer player) {
    players.add(player);
  }
}
