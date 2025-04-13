import java.util.*;

public class Main {
    public class MazeSolver {

    }
}
class Maze{
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

}