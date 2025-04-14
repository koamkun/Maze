import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Создаем лабиринт размером 21x21
        Maze maze = new Maze(21, 21);
        maze.generate(); // Генерируем лабиринт

        System.out.println("Generated Maze:");
        maze.print(); // Печатаем сгенерированный лабиринт

        // Пытаемся решить лабиринт
        if (maze.solve()) {
            System.out.println("\nSolved Maze:");
            maze.print(); // Печатаем лабиринт с отмеченным путем
        } else {
            System.out.println("No path found.");
        }
    }

    // Класс, представляющий лабиринт
    static class Maze {
        // Символы для отображения стен, пути и посещенных клеток
        char WALL = '#';
        char PATH = ' ';
        char VISITED = '.';

        // Смещения для движения вверх, вниз, влево, вправо
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        char[][] grid;         // Сетка лабиринта
        boolean[][] visited;   // Массив для отслеживания посещенных клеток
        int rows, cols;        // Размеры лабиринта
        Random rand = new Random(); // Генератор случайных чисел

        // Конструктор: создает лабиринт, всегда нечетного размера
        public Maze(int rows, int cols) {
            this.rows = (rows % 2 == 0) ? rows + 1 : rows;
            this.cols = (cols % 2 == 0) ? cols + 1 : cols;
            this.grid = new char[this.rows][this.cols];
            this.visited = new boolean[this.rows][this.cols];
        }

        // Перемешивает массив направлений
        private void shuffle(int[] array) {
            for (int i = array.length - 1; i > 0; i--) {
                int index = rand.nextInt(i + 1);
                int temp = array[i];
                array[i] = array[index];
                array[index] = temp;
            }
        }

        // Генерация лабиринта
        public void generate() {
            // Заполняем сетку стенами
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++)
                    grid[r][c] = WALL;

            // Начинаем вырезать путь из точки (1,1)
            carvePath(1, 1);

            // Устанавливаем вход и выход
            grid[1][1] = PATH;
            grid[rows - 2][cols - 2] = PATH;
        }

        // Проверка, что координаты в пределах допустимого диапазона
        private boolean isInBounds(int r, int c) {
            return r > 0 && r < rows - 1 && c > 0 && c < cols - 1;
        }

        // Рекурсивная функция для вырезания пути в лабиринте
        private void carvePath(int r, int c) {
            int[] dirs = {0, 1, 2, 3};
            shuffle(dirs); // Случайный порядок направлений

            for (int i : dirs) {
                int dr = dRow[i] * 2;
                int dc = dCol[i] * 2;
                int nr = r + dr;
                int nc = c + dc;

                // Если следующая клетка в пределах и ещё не была вырезана
                if (isInBounds(nr, nc) && grid[nr][nc] == WALL) {
                    // Очищаем стену между текущей и следующей клеткой
                    grid[r + dr / 2][c + dc / 2] = PATH;
                    grid[nr][nc] = PATH;
                    carvePath(nr, nc); // Продолжаем резать от новой клетки
                }
            }
        }

        // Рекурсивное решение лабиринта с помощью поиска в глубину
        private boolean solveDFS(int r, int c) {
            // Проверка на границы и проходимость
            if (!isInBounds(r, c) || grid[r][c] != PATH || visited[r][c])
                return false;

            visited[r][c] = true;

            // Достигли выхода
            if (r == rows - 2 && c == cols - 2) {
                grid[r][c] = VISITED;
                return true;
            }

            // Пробуем все 4 направления
            for (int i = 0; i < 4; i++) {
                int nr = r + dRow[i];
                int nc = c + dCol[i];
                if (solveDFS(nr, nc)) {
                    grid[r][c] = VISITED; // Отмечаем путь
                    return true;
                }
            }

            return false;
        }

        // Начинает решение лабиринта
        public boolean solve() {
            visited = new boolean[rows][cols]; // Обнуляем посещения
            return solveDFS(1, 1); // Начинаем с точки входа
        }

        // Выводит лабиринт в консоль
        public void print() {
            for (char[] row : grid) {
                for (char cell : row) {
                    System.out.print(cell);
                }
                System.out.println();
            }
        }
    }
}
