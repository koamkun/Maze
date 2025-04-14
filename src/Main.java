import java.util.*;

public class Main {
    public static void main(String[] args) {

    }
    }

class Maze {
    char WALL = '#';
    char PATH = ' ';
    char VISITED = '.';

    int[] dRow = {-1, 1, 0, 0};
    int[] dCol = {0, 0, -1, 1};

    char[][] grid;
    boolean[][] visited;
    int rows, cols;
    Random rand = new Random();


    public Maze (int rows, int cols){
        this.rows = (rows % 2 == 0) ? rows + 1 : rows;
        this.cols = (cols % 2 == 0) ? cols + 1 : cols;
        this.grid = new char[this.rows][this.cols];
        this.visited = new boolean[this.rows][this.cols];
    }



    public void generate() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = WALL;

        carvePath(1, 1);
        grid[1][1] = PATH;
        grid[rows - 2][cols - 2] = PATH;
    }

    private void shuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    private boolean isInBounds(int r, int c) {
        return r > 0 && r < rows - 1 && c > 0 && c < cols - 1;
    }

    private void carvePath(int r, int c) {
        int[] dirs = {0, 1, 2, 3};
        shuffle(dirs);

        for (int i : dirs) {
            int dr = dRow[i] * 2;
            int dc = dCol[i] * 2;
            int nr = r + dr;
            int nc = c + dc;

            if (isInBounds(nr, nc) && grid[nr][nc] == WALL) {
                grid[r + dr / 2][c + dc / 2] = PATH;
                grid[nr][nc] = PATH;
                carvePath(nr, nc);
            }
        }
    }


    private boolean solveDFS(int r, int c) {
        if (!isInBounds(r, c) || grid[r][c] != PATH || visited[r][c])
            return false;

        visited[r][c] = true;

        if (r == rows - 2 && c == cols - 2) {
            grid[r][c] = VISITED;
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int nr = r + dRow[i];
            int nc = c + dCol[i];
            if (solveDFS(nr, nc)) {
                grid[r][c] = VISITED;
                return true;
            }
        }

        return false;
    }

    public boolean solve() {
        visited = new boolean[rows][cols];
        return solveDFS(1, 1);
    }



}



