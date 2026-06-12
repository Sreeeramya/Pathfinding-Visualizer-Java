import java.util.LinkedList;
import java.util.Queue;

public class BFSSolver {

    private Maze maze;

    private int rows;

    private int cols;

    private int[][] parentX;

    private int[][] parentY;

    public BFSSolver(Maze maze) {

        this.maze = maze;

        rows = maze.getRows();

        cols = maze.getCols();

        parentX = new int[rows][cols];

        parentY = new int[rows][cols];
    }

    public boolean solve(MazePanel panel) {

        Cell[][] grid = maze.getGrid();

        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{0, 0});

        grid[0][0].setVisited(true);

        int[] dx = {1, -1, 0, 0};

        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int x = current[0];

            int y = current[1];

            grid[x][y].setCurrent(true);

            // Reached destination
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

                    grid[nx][ny].setVisited(true);

                    parentX[nx][ny] = x;

                    parentY[nx][ny] = y;
                    grid[nx][ny].setFrontier(true);

                    queue.add(new int[]{nx, ny});
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
                    grid[x][y].setFrontier(false);
                    grid[x][y].setCurrent(false);
                }
            }
        }

        return false;
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
