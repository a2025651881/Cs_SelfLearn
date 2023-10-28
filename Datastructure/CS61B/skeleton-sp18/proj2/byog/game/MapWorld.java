package byog.game;

import java.util.Random;
import java.util.Stack;

import byog.Core.Game;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class MapWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static final int size_x = 51;
    private static final int size_y = 31;

    private static long SEED = 7892793;
    private static final Random RANDOM = new Random(SEED);

    private static void Map_init(TETile[][] tiles) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++)
                tiles[i][j] = Tileset.NOTHING;
        }
        double gap_x = (double) (WIDTH - size_x) / 2;
        double gap_y = (double) (HEIGHT - size_y) / 2;
        for (int i = (int) Math.round(gap_x); i < WIDTH - Math.floor(gap_x); i++) {
            for (int j = (int) Math.round(gap_y); j < HEIGHT - Math.floor(gap_y); j++)
                tiles[i][j] = Tileset.WALL;
        }
    }

    /*
     * base cell in map
     * jointCells :相邻的四个Cell
     * visited : 是否被访问
     * ---[0]
     * [3] * [1]
     * ---[2]
     */
    private static class MapCell {
        MapCell[] jointCells = new MapCell[4];
        Boolean visited = false;
        int cell_x;
        int cell_y;

        public MapCell() {
            cell_x = 0;
            cell_y = 0;
        }
    }

    // 判断全局有无未访问节点
    private static Boolean hasNotVisited(MapCell[][] node) {
        for (int i = 0; i < size_x / 2; i++) {
            for (int j = 0; j < size_y / 2; j++) {
                if (node[i][j].visited == false)
                    return true;
            }
        }
        return false;
    }

    // 相邻节点有无未访问节点
    private static Boolean jointNotVisited(MapCell[] joinCells) {
        for (int i = 0; i < joinCells.length; i++) {
            if (joinCells[i] != null) {
                if (joinCells[i].visited == false)
                    return true;
            }
        }
        return false;
    }

    private static void removeWall(MapCell currentNode, int nextNodeIndex, TETile[][] tiles) {
        switch (nextNodeIndex) {
            case 0:
                tiles[currentNode.cell_x][currentNode.cell_y + 1] = Tileset.FLOOR;
                break;
            case 1:
                tiles[currentNode.cell_x + 1][currentNode.cell_y] = Tileset.FLOOR;
                break;
            case 2:
                tiles[currentNode.cell_x][currentNode.cell_y - 1] = Tileset.FLOOR;
                break;
            case 3:
                tiles[currentNode.cell_x - 1][currentNode.cell_y] = Tileset.FLOOR;
                break;
        }
    }

    /*
     * 这里采用深度优先算法
     * 1.Make the initial cell the current cell and mark it as visited
     * 2.While there are unvisited cells
     * --1.If the current cell has any neighbours which have not been visited
     * ----1.Choose randomly one of the unvisited neighbours
     * ----2.Push the current cell to the stack
     * ----3.Remove the wall between the current cell and the chosen cell
     * ----4.Make the chosen cell the current cell and mark it as visited
     * --2.Else if stack is not empty
     * ----1.Pop a cell from the stack
     * ----2.Make it the current cell
     */
    private static void backtracker(MapCell[][] node, TETile[][] tiles) {
        Stack<MapCell> st = new Stack<MapCell>();
        node[0][0].visited = true;
        MapCell pointer = node[0][0];
        int nextIndex;
        while (hasNotVisited(node)) {
            if (jointNotVisited(pointer.jointCells)) {
                while (true) {
                    nextIndex = RANDOM.nextInt(4);
                    if (pointer.jointCells[nextIndex] != null) {
                        break;
                    }
                }
                st.push(pointer);
                removeWall(pointer, nextIndex, tiles);
                pointer.jointCells[nextIndex].visited = true;
                pointer = pointer.jointCells[nextIndex];
            } else if (!st.isEmpty()) {
                pointer = st.pop();
            }
        }
    }

    private static void Map_generate(TETile[][] tiles) {
        int start_x = (int) Math.round((double) (WIDTH - size_x) / 2);
        int start_y = (int) Math.round((double) (HEIGHT - size_y) / 2);
        int now_x = 0, now_y = 0;

        MapCell[][] node = new MapCell[size_x / 2][size_y / 2];

        for (int i = 0; i < size_x / 2; i++) {
            for (int j = 0; j < size_y / 2; j++) {
                node[i][j] = new MapCell();
            }
        }

        for (int i = start_x + 1; i < WIDTH - Math.floor((double) (WIDTH - size_x) / 2); i += 2) {
            for (int j = start_y + 1; j < HEIGHT - Math.floor((double) (HEIGHT - size_y) / 2); j += 2) {
                tiles[i][j] = Tileset.FLOOR;
                node[now_x][now_y].cell_x = i;
                node[now_x][now_y].cell_y = j;
                // jointCells[0] 上方
                node[now_x][now_y].jointCells[0] = now_y < (size_y / 2 - 1) ? node[now_x][now_y + 1]
                        : null;
                // jointCells[1] 右侧
                node[now_x][now_y].jointCells[1] = now_x < (size_x / 2 - 1) ? node[now_x + 1][now_y]
                        : null;
                // jointCells[2] 下方
                node[now_x][now_y].jointCells[2] = now_y > 0 ? node[now_x][now_y - 1]
                        : null;
                // jointCells[3] 左侧
                node[now_x][now_y].jointCells[3] = now_x > 0 ? node[now_x - 1][now_y]
                        : null;
                now_y++;
            }
            now_x++;
            now_y = 0;
        }
        // 深度优先算法生成迷宫
        backtracker(node, tiles);

    }

    public static void main(String[] arg) {
        TERenderer ter = new TERenderer();
        // Renderer init
        ter.initialize(WIDTH, HEIGHT);

        // start page
        Game g = new Game();
        SEED = Long.parseLong(g.playWithKeyboard(WIDTH, HEIGHT));
        System.out.println(SEED);

        // set tiles
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];

        Map_init(tiles);
        Map_generate(tiles);
        // show
        ter.renderFrame(tiles);

    }
}
