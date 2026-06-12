import java.util.PriorityQueue;
import java.util.Comparator;

public class AStarSolver {

    private Maze maze;

    private int rows;

    private int cols;

    private int[][] parentX;

    private int[][] parentY;

    public AStarSolver(Maze maze) {

        this.maze = maze;

        rows = maze.getRows();

        cols = maze.getCols();

        parentX = new int[rows][cols];

        parentY = new int[rows][cols];
    }

    public boolean solve(MazePanel panel) {

        Cell[][] grid = maze.getGrid();

        PriorityQueue<Cell> openSet =
                new PriorityQueue<>(Comparator.comparingInt(Cell::getFCost));

        Cell start = grid[0][0];

        start.setGCost(0);

        start.setHCost(heuristic(0, 0));

        start.setFCost(start.getGCost() + start.getHCost());

        openSet.add(start);

        int[] dx = {1, -1, 0, 0};

        int[] dy = {0, 0, 1, -1};

        while (!openSet.isEmpty()) {

            Cell current = openSet.poll();

            int x = current.getRow();

            int y = current.getCol();
            grid[x][y].setFrontier(false);
            grid[x][y].setCurrent(true);

            current.setVisited(true);
            int repaintCounter = 0;
            repaintCounter++;

            if (repaintCounter % 5 == 0) {

                panel.repaint();
            }

            try {

                Thread.sleep(panel.getAnimationDelay());

            }
            catch (Exception e) {

                e.printStackTrace();
            }
            grid[x][y].setCurrent(false);

            // Destination reached
            if (x == rows - 1 && y == cols - 1) {

                reconstructPath(grid);

                return true;
            }

            for (int i = 0; i < 4; i++) {

                int nx = x + dx[i];

                int ny = y + dy[i];

                if (
                        nx >= 0 &&
                                ny >= 0 &&
                                nx < rows &&
                                ny < cols &&
                                !grid[nx][ny].isWall() &&
                                !grid[nx][ny].isVisited()
                ) {

                    Cell neighbor = grid[nx][ny];

                    int newG = current.getGCost() + 1;

                    int newH = heuristic(nx, ny);

                    int newF = newG + newH;

                    neighbor.setGCost(newG);

                    neighbor.setHCost(newH);

                    neighbor.setFCost(newF);

                    parentX[nx][ny] = x;

                    parentY[nx][ny] = y;
                    neighbor.setFrontier(true);
                    openSet.add(neighbor);
                }
            }
        }

        return false;
    }

    private int heuristic(int x, int y) {

        return Math.abs(x - (rows - 1))
                + Math.abs(y - (cols - 1));
    }

    private void reconstructPath(Cell[][] grid) {

        int x = rows - 1;

        int y = cols - 1;

        while (!(x == 0 && y == 0)) {

            grid[x][y].setPath(true);

            int tempX = parentX[x][y];

            int tempY = parentY[x][y];

            x = tempX;

            y = tempY;
        }

        grid[0][0].setPath(true);
    }
}
