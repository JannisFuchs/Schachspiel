package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.logger.LogHandler;
import de.dhbw.schachspiel.classes.logger.LogType;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.Map;

public class Visualisation
{
	private final Map<PieceColor, Color> pieceColorMap;
	private final Map<BoardColor, Color> boardColorColorMap;
	private final LogHandler logger;
	private Field inCheck;

	public Visualisation(LogHandler logger,Color blackPieces, Color whitePieces, Color blackSquare, Color whiteSquare, Color check)
	{
		this.logger = logger;
		pieceColorMap = Map.of(
				PieceColor.BLACK, blackPieces,
				PieceColor.WHITE, whitePieces

		);
		boardColorColorMap = Map.of(
				BoardColor.BLACK, blackSquare,
				BoardColor.WHITE, whiteSquare,
				BoardColor.RED, check
		);

	}

	public void drawBoard(IBoard board)
	{

		for (int row = 0; row < board.getRowLength(); row++)
		{

			for (int column = 0; column < board.getColumnLength(); column++)
			{

				//if the piece is None the color has to be the background
				Field currentField = new Field(row, column);
				BoardColor currentFieldBoardColor = getBoardColor(currentField);
				IPiece currentPiece = board.getPiece(currentField);
				PieceColor currentPieceColor = currentPiece.getColor();
				Color boardColor = boardColorColorMap.get(currentFieldBoardColor);
				if (currentPiece.getPieceType() == PieceType.NONE)
				{

					logger.log(boardColor.toString() + currentPiece.getSymbol(), LogType.GAME);
				}
				else
				{
					Color pieceColor = pieceColorMap.get(currentPieceColor);
					logger.log(boardColor.toString() + pieceColor + currentPiece.getSymbol(), LogType.GAME);
				}
			}
			//rest of the line is blank
			String reset = "\u001B[0m";
			logger.log(reset, LogType.GAME);
			logger.log((board.getRowLength() - row)+"\n", LogType.GAME);
		}
		logger.log("ABCDEFGH\n", LogType.GAME);
		resetCheck();
	}

	private BoardColor getBoardColor(Field currentField)
	{
		if (currentField.equals(inCheck))
		{
			return BoardColor.RED;
		}
		int sum = currentField.row() + currentField.column();
		if (sum % 2 == 0)
		{
			return BoardColor.WHITE;
		}
		return BoardColor.BLACK;
	}

	public void setCheck(Field inCheck)
	{
		this.inCheck = inCheck;
	}

	private void resetCheck()
	{
		this.inCheck = new Field(-1, -1);
	}

}
