import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Solution {

	private static int chessboardSide;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		chessboardSide = scanner.nextInt();
		int startRow = scanner.nextInt();
		int startColumn = scanner.nextInt();
		int goalRow = scanner.nextInt();
		int goalColumn = scanner.nextInt();
		scanner.close();

		getResults(startRow, startColumn, goalRow, goalColumn);
	}

	/**
	 * If the goal can be reached, the method traces back the path with minimum
	 * number of moves. This path is stored in List "pathWithMinimumMoves".
	 * 
	 * This list contains all positions on the path, except the start. 
   * Thus, the size of the list is printed for the value of minimum number of moves.
	 * 
	 * Then, on the next line, the type of move to reach each position on the path
	 * is printed.
	 * 
	 * If the goal is unreachable, i.e. "searchPath_minimumNumberOfMoves(...)"
	 * returns null, the method prints "Impossible".
	 */
	private static void getResults(int startRow, int startColumn, int endRow, int endColumn) {

		Map<Position, Position> tracePathMap = new HashMap<Position, Position>();
		Position goal = searchPath_minimumNumberOfMoves(startRow, startColumn, endRow, endColumn, tracePathMap);

		if (goal == null) {
			System.out.println("Impossible");
			return;
		}

		List<Position> pathWithMinimumMoves = new ArrayList<Position>();
		Position current = tracePathMap.get(goal);
		pathWithMinimumMoves.add(goal);

		while (current.row != startRow || current.column != startColumn) {
			pathWithMinimumMoves.add(current);
			current = tracePathMap.get(current);
		}

		int moves_shortestPath = pathWithMinimumMoves.size();
		System.out.println(moves_shortestPath);

		for (int i = pathWithMinimumMoves.size() - 1; i >= 0; i--) {
			System.out.print(pathWithMinimumMoves.get(i).moveToReachPosition + " ");
		}
	}

	/**
	 * The method applies breadth first search in order to find the path with least
	 * number of moves from start to goal.
	 * 
	 * There might be multiple paths for the minimum number of moves. In this case,
	 * the path that is printed as a result, is the path that prioritizes the moves
	 * in the following order: UpperLeft, UpperRight, Right, LowerRight, LowerLeft,
	 * Left.
	 * 
	 * The method also fills HashMap "tracePathMap", which is applied to trace 
   * the path with minimum number of moves.
	 * 
	 * @return Object of class "Position" that represents the goal. 
   *         If the goal is not reachable, then "null" is returned.
	 */
	private static Position searchPath_minimumNumberOfMoves(int startRow, int startColumn, int endRow, int endColumn,
			Map<Position, Position> tracePathMap) {
		LinkedList<Position> chessboardPositions = new LinkedList<Position>();
		Position current = new Position(startRow, startColumn);
		chessboardPositions.add(current);

		boolean[][] visited = new boolean[chessboardSide][chessboardSide];
		visited[0][0] = true;
		int row;
		int column;

		while (!chessboardPositions.isEmpty()) {
			current = chessboardPositions.removeFirst();

			if (current.row == endRow && current.column == endColumn) {
				return current;
			}

			/**
			 * Move: UpperLeft (UL).
			 */
			row = current.row - 2;
			column = current.column - 1;
			if (isOnChessboard(row, column) && !visited[row][column]) {
				Position next = new Position(row, column);
				next.moveToReachPosition = "UL";
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
				next.moveToReachPosition = "UR";
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
				next.moveToReachPosition = "R";
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
				next.moveToReachPosition = "LR";
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
				next.moveToReachPosition = "LL";
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
				next.moveToReachPosition = "L";
				chessboardPositions.add(next);
				visited[row][column] = true;
				tracePathMap.put(next, current);
			}
		}

		return null;
	}

	/**
	 * The method checks whether the neighboring row and column are within 
   * the boundaries of the chess board.
	 * 
	 * @return true, if they are on the chess board. Otherwise, returns false.
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
 * the type of the last move to reach this position (UL, UR, R, LR, LL, L).
 */
class Position {
	int row;
	int column;
	String moveToReachPosition;

	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
