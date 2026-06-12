import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazePanel extends JPanel {

    int rows = 20;
    int cols = 20;

    int cellSize = 30;
    private int animationDelay = 30;
    private boolean drawMode = true;
    private boolean placeStartMode = false;

    private boolean placeEndMode = false;


    private Maze maze;
    private BFSSolver solver;
    private DFSSolver dfsSolver;
    private AStarSolver aStarSolver;

    Random random = new Random();

    public MazePanel() {
        setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );
        setBackground(new Color(24, 24, 24));
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));

        maze = new Maze(rows, cols);
        solver = new BFSSolver(maze);
        dfsSolver = new DFSSolver(maze);
        aStarSolver = new AStarSolver(maze);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                if (!drawMode) {

                    return;
                }

                int col = e.getX() / cellSize;

                int row = e.getY() / cellSize;

                if (
                        row >= 0 &&
                                col >= 0 &&
                                row < rows &&
                                col < cols
                ) {

                    // Prevent editing start/end
                    if (
                            (row == 0 && col == 0) ||
                                    (row == rows - 1 && col == cols - 1)
                    ) {

                        return;
                    }

                    if (placeStartMode) {

                        maze.setStart(row, col);

                        maze.getGrid()[row][col].setWall(false);

                        repaint();

                        return;
                    }

                    if (placeEndMode) {

                        maze.setEnd(row, col);

                        maze.getGrid()[row][col].setWall(false);

                        repaint();

                        return;
                    }

                    Cell cell = maze.getGrid()[row][col];

                    cell.setWall(!cell.isWall());

                    repaint();
                }
            }
            @Override
            public void mouseDragged(MouseEvent e) {

                if (!drawMode) {

                    return;
                }

                int col = e.getX() / cellSize;

                int row = e.getY() / cellSize;

                if (
                        row >= 0 &&
                                col >= 0 &&
                                row < rows &&
                                col < cols
                ) {

                    if (
                            (row == 0 && col == 0) ||
                                    (row == rows - 1 && col == cols - 1)
                    ) {

                        return;
                    }

                    Cell cell = maze.getGrid()[row][col];

                    cell.setWall(true);

                    repaint();
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {

                if (!drawMode) {

                    return;
                }

                int col = e.getX() / cellSize;

                int row = e.getY() / cellSize;

                if (
                        row >= 0 &&
                                col >= 0 &&
                                row < rows &&
                                col < cols
                ) {

                    if (
                            (row == 0 && col == 0) ||
                                    (row == rows - 1 && col == cols - 1)
                    ) {

                        return;
                    }

                    Cell cell = maze.getGrid()[row][col];

                    cell.setWall(true);

                    repaint();
                }
            }
        });
    }
    public void setPlaceStartMode(boolean value) {

        placeStartMode = value;
    }

    public void setPlaceEndMode(boolean value) {

        placeEndMode = value;
    }
    public void setAnimationDelay(int delay) {

        animationDelay = delay;
    }
    public int getAnimationDelay() {

        return animationDelay;
    }
    public void solveDFS() {

        boolean found = dfsSolver.solve(0, 0, this);

        if (found) {

            System.out.println("DFS Path Found!");
        }
        else {

            System.out.println("No DFS Path Found");
        }

        repaint();
    }
    public void solveAStar() {

        boolean found = aStarSolver.solve(this);

        if (found) {

            System.out.println("A* Path Found!");
        }
        else {

            System.out.println("No A* Path Found");
        }

        repaint();
    }
    public int getVisitedCount() {

        int count = 0;

        Cell[][] grid = maze.getGrid();

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                if (grid[row][col].isVisited()) {

                    count++;
                }
            }
        }

        return count;
    }
    public int getPathLength() {

        int count = 0;

        Cell[][] grid = maze.getGrid();

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                if (grid[row][col].isPath()) {

                    count++;
                }
            }
        }

        return count;
    }
    public void updateMazeSize(int size) {

        rows = size;

        cols = size;

        // Dynamic cell sizing
        if(size == 11){

            cellSize = 55;
        }
        else if(size == 21){

            cellSize = 32;
        }


        maze = new Maze(rows, cols);

        solver = new BFSSolver(maze);

        dfsSolver = new DFSSolver(maze);

        aStarSolver = new AStarSolver(maze);

        setPreferredSize(
                new Dimension(cols * cellSize, rows * cellSize)
        );

        revalidate();

        repaint();
    }
    public void solveMaze() {

        boolean found = solver.solve(this);

        if (found) {

            System.out.println("Path Found!");
        }
        else {

            System.out.println("No Path Found");
        }

        repaint();
    }

    public void generateMaze() {

        maze.generateMaze();

        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Cell[][] grid = maze.getGrid();
        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                if (grid[row][col].isWall()) {

                    g.setColor(new Color(35,35,35));
                }
                else if (grid[row][col].isCurrent()) {

                    g.setColor(new Color(230, 126, 34));
                }
                else if (grid[row][col].isFrontier()) {

                    g.setColor(new Color(155,89,182));
                }
                else if (grid[row][col].isPath()) {

                    g.setColor(new Color(52,152,219));
                }
                else if (grid[row][col].isVisited()) {

                    g.setColor(new Color(241,196,15));
                }
                else {

                    g.setColor(new Color(20,20,20));
                }

                g.fillRoundRect(
                        col * cellSize + 1,
                        row * cellSize + 1,
                        cellSize - 2,
                        cellSize - 2,
                        8,
                        8
                );

                g.setColor(new Color(35, 35, 35));

                g.drawRect(
                        col * cellSize,
                        row * cellSize,
                        cellSize,
                        cellSize
                );
            }
        }

        // START CELL
        g.setColor(new Color(46, 204, 113));

        g.fillRect(
                maze.getStartCol() * cellSize,
                maze.getStartRow() * cellSize,
                cellSize,
                cellSize
        );

        // END CELL
        g.setColor(new Color(231, 76, 60));

        g.fillRect(
                maze.getEndCol() * cellSize,
                maze.getEndRow() * cellSize,
                cellSize,
                cellSize
        );
    }
}