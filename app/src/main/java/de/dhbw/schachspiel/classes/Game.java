package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;
import de.dhbw.schachspiel.interfaces.IPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game
{
    private final Scanner scanner;
    private final IBoard board;
    private final List<Move> moveList = new ArrayList<>();
    private PieceColor winner = null;
    int currentPlayer;
    IPlayer[] players = {new Player(PieceColor.WHITE), new Player(PieceColor.BLACK)};

    public Game(String startPosition, Scanner scanner)
    {
        this.scanner = scanner;
        String[] split = startPosition.split(" ");
        IPiece[][] positionAsPieces = PieceFactory.createBoardFromFEN(split[0]);
        board = new Board(positionAsPieces);
        initializePlayer(split[1]);

    }


    void initializePlayer(String playerString)
    {
        if (playerString.equals("w"))
        {
            currentPlayer = 0;
        }
        else if (playerString.equals("b"))
        {
            currentPlayer = 1;
        }
        else
        {
            throw new IllegalArgumentException("Invalid FEN");
        }
    }

    /**
     *
     * @return true if the game is over
     */
    public boolean play()
    {
        final int MAX_PLAYER_COUNT = 2;
        Visualisation output = createVisualisation();
        PieceColor currentPieceColor = players[currentPlayer].getColor();
        CheckHandler handler = new CheckHandler(board, currentPieceColor);
        handleDrawing(output, handler);

        if (handler.isMate())
        {

            winner = players[currentPlayer].getColor().getOtherColor();
            return true;
        }
        if (handler.isDraw(moveList))
        {

            return true;
        }
        handleMove();

        currentPlayer = (currentPlayer + 1) % MAX_PLAYER_COUNT;
        return false;
    }
    public PieceColor getWinner()
    {
        return winner;
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

                Move m = players[currentPlayer].readMove(scanner);

                isValid = testMove(m);
                if (!isValid)
                {
                    System.out.println("This puts the king in check");
                }
                else
                {
                    moveList.add(m);
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
        CheckHandler notationHandler = new CheckHandler(board, currentPieceColor.getOtherColor());
        if (notationHandler.isMate() && !m.isMate)
        {
            throw new Move.IllegalMoveException("this is a mate but not declared");
        }
        if (notationHandler.isCheck() && !m.isCheck)
        {
            throw new Move.IllegalMoveException("this is a check but not declared");
        }
        if (!notationHandler.isMate() && m.isMate)
        {
            throw new Move.IllegalMoveException("this is not a mate but it's declared");
        }
        if (!notationHandler.isCheck() && m.isCheck)
        {
            throw new Move.IllegalMoveException("this is not a check but it's declared");
        }
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
