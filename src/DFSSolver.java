public class DFSSolver {

    private Maze maze;

    private int rows;

    private int cols;

    public DFSSolver(Maze maze) {

        this.maze = maze;

        rows = maze.getRows();

        cols = maze.getCols();
    }

    public boolean solve(int x, int y, MazePanel panel) {

        Cell[][] grid = maze.getGrid();

        // Boundary checks
        if (
                x < 0 ||
                        y < 0 ||
                        x >= rows ||
                        y >= cols
        ) {

            return false;
        }

        // Wall or visited
        if (
                grid[x][y].isWall() ||
                        grid[x][y].isVisited()
        ) {

            return false;
        }

        // Mark visited
        grid[x][y].setVisited(true);

        grid[x][y].setCurrent(true);
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

            grid[x][y].setPath(true);

            return true;
        }

        // Explore directions
        int[] dx = {1, -1, 0, 0};

        int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {

            int nx = x + dx[i];

            int ny = y + dy[i];

            if (solve(nx, ny, panel)) {

                grid[x][y].setPath(true);

                return true;
            }
        }

        return false;
    }
}
