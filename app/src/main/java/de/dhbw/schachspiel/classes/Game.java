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
	IPlayer[] players = new IPlayer[MAX_PLAYER_COUNT];

	public Game()
	{
		players[0] = new Player(PieceColor.WHITE);
		players[1] = new Player(PieceColor.BLACK);
		currentPlayer = 0;

		board = new Board(8, 8);
		Visualisation output = createVisualisation();
		output.setCheck(new Field(0, 0));
		output.drawBoard(board);
		for (int i = 0; i < 50; i++)
		{
			//this is a duplication see Move class
			boolean isValid = false;
			while (!isValid)
			{
				try
				{
					Move m = players[currentPlayer].readMove(SCANNER);

					isValid = testMove(m);
					output.drawBoard(board);
					if (!isValid)
					{
						System.out.println("This puts the king in check");
					}
					else
					{
						board.makeMove(m);
					}
				} catch (Move.IllegalMoveException e)
				{
					System.out.println(e.getMessage());
				}
			}


			currentPlayer = (currentPlayer + 1) % MAX_PLAYER_COUNT;
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
		IBoard clone = board.copy();
		clone.makeMove(m);
		return !isInCheck(clone);
	}

	boolean isInCheck(IBoard board)
	{
		IPlayer player = players[currentPlayer];
		PieceColor currentPieceColor = player.getColor();
		PieceColor enemyPieceColor = currentPieceColor.getOtherColor();
		Field currentKingField = board.getKingField(currentPieceColor);
		for (PieceType type : PieceType.values())
		{
			if (type.equals(PieceType.NONE))
			{
				continue;
			}

			IPiece piece = PieceFactory.createPieceFromType(type, enemyPieceColor);
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
