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

    
}


