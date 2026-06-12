public class Cell {

    private int row;

    private int col;

    private boolean wall;

    private boolean visited;

    private boolean path;
    private boolean current;
    private boolean frontier;
    private int gCost;

    private int hCost;

    private int fCost;

    public Cell(int row, int col) {

        this.row = row;

        this.col = col;

        this.wall = false;

        this.visited = false;

        this.path = false;
    }

    public int getRow() {

        return row;
    }
    public boolean isCurrent() {

        return current;
    }
    public boolean isFrontier() {

        return frontier;
    }

    public void setFrontier(boolean frontier) {

        this.frontier = frontier;
    }

    public void setCurrent(boolean current) {

        this.current = current;
    }

    public int getCol() {

        return col;
    }

    public boolean isWall() {

        return wall;
    }

    public void setWall(boolean wall) {

        this.wall = wall;
    }

    public boolean isVisited() {

        return visited;
    }

    public void setVisited(boolean visited) {

        this.visited = visited;
    }

    public boolean isPath() {

        return path;
    }

    public void setPath(boolean path) {

        this.path = path;
    }
    public int getGCost() {

        return gCost;
    }

    public void setGCost(int gCost) {

        this.gCost = gCost;
    }

    public int getHCost() {

        return hCost;
    }

    public void setHCost(int hCost) {

        this.hCost = hCost;
    }

    public int getFCost() {

        return fCost;
    }

    public void setFCost(int fCost) {

        this.fCost = fCost;
    }
}