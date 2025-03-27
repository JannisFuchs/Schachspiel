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
    IPlayer[] players = {new Player(PieceColor.WHITE), new Player(PieceColor.BLACK)};

    public Game(String startPosition)
    {
        IPiece[][] positionAsPieces = PieceFactory.createBoardFromFEN(startPosition);
        board = new Board(positionAsPieces);
        initializePlayer(startPosition);
        play();
    }

    void initializePlayer(String startPosition)
    {
        String[] split = startPosition.split(" ");
        if (split[1].equals("w"))
        {
            currentPlayer = 0;
        }
        else if (split[1].equals("b"))
        {
            currentPlayer = 1;
        }
        else
        {
            throw new IllegalArgumentException("Invalid FEN");
        }
    }


    public void play()
    {
        Visualisation output = createVisualisation();
        for (int i = 0; i < 50; i++)
        {
            //this is a duplication see Move class
            PieceColor currentPieceColor = players[currentPlayer].getColor();
            CheckHandler handler = new CheckHandler(board, currentPieceColor);
            handleDrawing(output, handler);
            if (handler.isMate())
            {
                System.out.println("Checkmate : " + players[currentPlayer].getColor() + " wins");
                break;
            }
            handleMove();
            currentPlayer = (currentPlayer + 1) % MAX_PLAYER_COUNT;
        }
    }

    public void handleDrawing(Visualisation output, CheckHandler handler)
    {
        if (handler.isCheck())
        {
            Field fieldWithKing = board.getKingField(handler.getColor());
            output.setCheck(fieldWithKing);
        }
        output.drawBoard(board);

    }

    public void handleMove()
    {
        boolean isValid = false;
        while (!isValid)
        {
            try
            {

                Move m = players[currentPlayer].readMove(SCANNER);

                isValid = testMove(m);
                if (!isValid)
                {
                    System.out.println("This puts the king in check");
                    board.undoMove();
                }
                else
                {
                    board.commitMove();
                }

            } catch (Move.IllegalMoveException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This tries to execute the entered move
     *
     * @param m the current move
     * @return true if the move is valid false if this move puts the king in check
     * @throws Move.IllegalMoveException if the move is otherwise illegal
     */
    boolean testMove(Move m) throws Move.IllegalMoveException
    {
        PieceColor currentPieceColor = players[currentPlayer].getColor();
        board.makeMove(m);
        CheckHandler handler = new CheckHandler(board, currentPieceColor);
        return !handler.isCheck();
    }


    Visualisation createVisualisation()
    {
        Color blackPieces = new Color(0, 0, 0, false);
        Color whitePieces = new Color(255, 255, 255, false);
        Color whiteSquares = new Color(210, 187, 151, true);
        Color blackSquares = new Color(151, 106, 79, true);
        Color redSquares = new Color(255, 0, 0, true);
        return new Visualisation(blackPieces, whitePieces, blackSquares, whiteSquares, redSquares);
    }
}
