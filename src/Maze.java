import java.util.Random;

public class Maze {

    private int rows;

    private int cols;

    private Cell[][] grid;
    private int startRow = 0;

    private int startCol = 0;

    private int endRow;

    private int endCol;

    private Random random;

    public Maze(int rows, int cols) {

        this.rows = rows;

        this.cols = cols;
        endRow = rows - 1;

        endCol = cols - 1;

        grid = new Cell[rows][cols];

        random = new Random();

        initializeGrid();

        generateMaze();
    }
    public int getStartRow() {

        return startRow;
    }

    public int getStartCol() {

        return startCol;
    }

    public int getEndRow() {

        return endRow;
    }

    public int getEndCol() {

        return endCol;
    }
    public void setStart(int row, int col) {

        startRow = row;

        startCol = col;
    }

    public void setEnd(int row, int col) {

        endRow = row;

        endCol = col;
    }

    private void initializeGrid() {

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                grid[row][col] = new Cell(row, col);
            }
        }
    }

    public void generateMaze() {

        // First make everything walls
        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                Cell cell = grid[row][col];

                cell.setWall(true);

                cell.setVisited(false);

                cell.setPath(false);
                cell.setCurrent(false);
                cell.setFrontier(false);
            }
        }

        carvePath(1, 1);

        // Start
        grid[0][0].setWall(false);

        grid[1][0].setWall(false);

        // End
        grid[rows - 1][cols - 1].setWall(false);

        grid[rows - 2][cols - 1].setWall(false);
    }
    private void carvePath(int row, int col) {

        grid[row][col].setWall(false);

        int[] directions = {0, 1, 2, 3};

        shuffleArray(directions);

        for (int dir : directions) {

            int newRow = row;

            int newCol = col;

            switch (dir) {

                case 0 -> newRow -= 2; // up

                case 1 -> newRow += 2; // down

                case 2 -> newCol -= 2; // left

                case 3 -> newCol += 2; // right
            }

            if (
                    newRow > 0 &&
                            newCol > 0 &&
                            newRow < rows - 1 &&
                            newCol < cols - 1 &&
                            grid[newRow][newCol].isWall()
            ) {

                // Remove wall between cells
                grid[(row + newRow) / 2]
                        [(col + newCol) / 2]
                        .setWall(false);

                carvePath(newRow, newCol);
            }
        }
    }
    private void shuffleArray(int[] array) {

        Random random = new Random();

        for (int i = array.length - 1; i > 0; i--) {

            int index = random.nextInt(i + 1);

            int temp = array[index];

            array[index] = array[i];

            array[i] = temp;
        }
    }

    public Cell[][] getGrid() {

        return grid;
    }

    public int getRows() {

        return rows;
    }

    public int getCols() {

        return cols;
    }
}