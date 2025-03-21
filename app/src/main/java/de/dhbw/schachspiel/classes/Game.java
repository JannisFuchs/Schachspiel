package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;
import de.dhbw.schachspiel.interfaces.IPlayer;

import java.util.Scanner;

public class Game
{
    private static final Scanner SCANNER = new Scanner(System.in);
    private final int MAX_PLAYER_COUNT = 2;
    private final IBoard board;
    int currentPlayer;
    Player[] players = new Player[MAX_PLAYER_COUNT];

    public Game()
    {
        players[0] = new Player(Color.WHITE);
        players[1] = new Player(Color.BLACK);
        currentPlayer = 0;
        board = new Board(8, 8);
        Visualisation.drawBoard(board);
        for (int i = 0; i < 50; i++)
        {
            //this is a duplication see Move class
            boolean isValid = false;
            while (!isValid)
            {
                try
                {
                    Move m = players[currentPlayer].readMove(SCANNER);

                    isValid = executeMove(m);
                    if (!isValid)
                    {
                        System.out.println("This puts the king in check");
                    }
                } catch (Move.IllegalMoveException e)
                {
                    System.out.println(e.getMessage());
                }
            }


            Visualisation.drawBoard(board);
            currentPlayer = (currentPlayer + 1) % MAX_PLAYER_COUNT;
        }
    }

    /**
     * This tries to execute the entered move
     *
     * @param m the current move
     * @return true if the move is valid false if this move puts the king in check
     * @throws Move.IllegalMoveException if the move is illegal
     */
    boolean executeMove(Move m) throws Move.IllegalMoveException
    {
        if (isInCheck())
        {
            return false;
        }
        board.makeMove(m);
        return true;
    }

    boolean isInCheck()
    {
        IPlayer player = players[currentPlayer];
        Color currentColor = player.getColor();
        Color enemyColor = currentColor.getOtherColor();
        Field currentKingField = board.getKingField(currentColor);
        for (PieceType type : PieceType.values())
        {
            if (type.equals(PieceType.NONE))
            {
                continue;
            }

            IPiece piece = PieceFactory.createPieceFromType(type, enemyColor);
            if (canReachField(currentKingField, piece))
            {
                return true;
            }
        }
        return false;
    }

    boolean canReachField(Field target, IPiece piece)
    {
        Move necessaryMove = createMove(target, piece);
        try
        {
            board.simulateMove(necessaryMove);
        } catch (Move.AmbiguousMoveException e)
        {
            return true;
        } catch (Move.IllegalMoveException e)
        {
            return false;
        }
        return true;
    }

    Move createMove(Field target, IPiece piece)
    {
        Field start = new Field(-1, -1);
        return new Move(start, target, piece, true, false, false);
    }
}
