import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Solution {

	private static int chessboardSide;
	private static int startRow;
	private static int startColumn;
	private static int goalRow;
	private static int goalColumn;
	private static Position start;
	private static Position goal;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		chessboardSide = scanner.nextInt();
		startRow = scanner.nextInt();
		startColumn = scanner.nextInt();
		goalRow = scanner.nextInt();
		goalColumn = scanner.nextInt();
		scanner.close();

		printResults();
	}

	/**
	 * If the goal can be reached, the method prints the minimum number of moves on
	 * one line. On another line, it prints the type of every move (UL, UR, R, LR,
	 * LL, L) to reach each position on the path.
	 * 
	 * If the goal is unreachable, the method prints "Impossible".
	 */
	private static void printResults() {
		Map<Position, Position> tracePathMap = new HashMap<Position, Position>();
		boolean goalHasBeenReached = searchPath_minimumNumberOfMoves(tracePathMap);

		if (goalHasBeenReached == false) {
			System.out.println("Impossible");
			return;
		}
		List<Position> pathWithMinimumMoves = traceBack_PathWithMinimumMoves(tracePathMap);
		int numberOfMoves = pathWithMinimumMoves.size() - 1;

		System.out.println(numberOfMoves);
		for (int i = pathWithMinimumMoves.size() - 2; i >= 0; i--) {
			System.out.print(pathWithMinimumMoves.get(i).typeOfMove_fromPreviousToThisPosition + " ");
		}
	}

	/**
	 * The method traces back the path with minimum number ofo moves.
	 * 
	 * @return List that contains the positions on this path, from start to goal.
	 */
	private static List<Position> traceBack_PathWithMinimumMoves(Map<Position, Position> tracePathMap) {
		List<Position> pathWithMinimumMoves = new ArrayList<Position>();
		Position current = tracePathMap.get(goal);
		pathWithMinimumMoves.add(goal);

		while (current != start) {
			pathWithMinimumMoves.add(current);
			current = tracePathMap.get(current);
		}
		pathWithMinimumMoves.add(start);

		return pathWithMinimumMoves;
	}

	/**
	 * The method applies breadth first search to seek the path with least number of
	 * moves from start to goal.
	 * 
	 * There might be multiple paths for the minimum number of moves. The result
	 * that is printed, is the path that prioritizes the moves in the following
	 * order: UpperLeft, UpperRight, Right, LowerRight, LowerLeft, Left.
	 * 
	 * The method also fills HashMap "tracePathMap", which is applied to trace 
	 * the path with minimum number of moves.
	 * 
	 * @return true, if the goal can be reached. Otherwise, false.
	 */
	private static boolean searchPath_minimumNumberOfMoves(Map<Position, Position> tracePathMap) {
		LinkedList<Position> chessboardPositions = new LinkedList<Position>();
		start = new Position(startRow, startColumn);
		Position current = start;
		chessboardPositions.add(current);

		boolean[][] visited = new boolean[chessboardSide][chessboardSide];
		visited[0][0] = true;
		int row;
		int column;

		while (!chessboardPositions.isEmpty()) {
			current = chessboardPositions.removeFirst();

			if (current.row == goalRow && current.column == goalColumn) {
				goal = current;
				return true;
			}

			/**
			 * Move: UpperLeft (UL).
			 */
			row = current.row - 2;
			column = current.column - 1;
			if (isOnChessboard(row, column) && !visited[row][column]) {
				Position next = new Position(row, column);
				next.typeOfMove_fromPreviousToThisPosition = "UL";
				chessboardPositions.add(next);
				visited[row][column] = true;
				tracePathMap.put(next, current);
			}

			/**
			 * Move: UpperRight (UR).
			 */
			row = current.row - 2;
			column = current.column + 1;
			if (isOnChessboard(row, column) && !visited[row][column]) {
				Position next = new Position(row, column);
				next.typeOfMove_fromPreviousToThisPosition = "UR";
				chessboardPositions.add(next);
				visited[row][column] = true;
				tracePathMap.put(next, current);
			}

			/**
			 * Move: Right (R).
			 */
			row = current.row;
			column = current.column + 2;
			if (isOnChessboard(row, column) && !visited[row][column]) {
				Position next = new Position(row, column);
				next.typeOfMove_fromPreviousToThisPosition = "R";
				chessboardPositions.add(next);
				visited[row][column] = true;
				tracePathMap.put(next, current);
			}

			/**
			 * Move: LowerRight (LR).
			 */
			row = current.row + 2;
			column = current.column + 1;
			if (isOnChessboard(row, column) && !visited[row][column]) {
				Position next = new Position(row, column);
				next.typeOfMove_fromPreviousToThisPosition = "LR";
				chessboardPositions.add(next);
				visited[row][column] = true;
				tracePathMap.put(next, current);
			}

			/**
			 * Move: LowerLeft (LL).
			 */
			row = current.row + 2;
			column = current.column - 1;
			if (isOnChessboard(row, column) && !visited[row][column]) {
				Position next = new Position(row, column);
				next.typeOfMove_fromPreviousToThisPosition = "LL";
				chessboardPositions.add(next);
				visited[row][column] = true;
				tracePathMap.put(next, current);
			}

			/**
			 * Move: Left (L).
			 */
			row = current.row;
			column = current.column - 2;
			if (isOnChessboard(row, column) && !visited[row][column]) {
				Position next = new Position(row, column);
				next.typeOfMove_fromPreviousToThisPosition = "L";
				chessboardPositions.add(next);
				visited[row][column] = true;
				tracePathMap.put(next, current);
			}
		}
		return false;
	}

	/**
	 * The method checks whether the neighboring row and column are within 
	 * the boundaries of the chess board.
	 * 
	 * @return true, if they are on the chess board. Otherwise, false.
	 */
	private static boolean isOnChessboard(int row, int column) {
		if (row < 0 || row >= chessboardSide || column < 0 || column >= chessboardSide) {
			return false;
		}
		return true;
	}
}

/**
 * Class "Position" stores the coordinates of a point on the chess board and 
 * the type of the move (UL, UR, R, LR, LL, L) from the previous position to this
 * position.
 */
class Position {
	int row;
	int column;
	String typeOfMove_fromPreviousToThisPosition;

	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
