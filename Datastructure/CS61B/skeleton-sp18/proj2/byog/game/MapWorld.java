package byog.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.Stack;

import byog.StdDraw;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class MapWorld implements Serializable {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static final int size_x = 51;
    private static final int size_y = 31;
    private static final int RADIUS = 5;

    public long SEED = 7892793;
    public Random RANDOM = new Random(SEED);
    private int player_x;
    private int player_y;
    private TETile[][] Tiles;
    private TETile[][] inSightTiles;

    public void setTiles(TETile[][] tiles, TETile[][] inSighTeTiles) {
        Tiles = tiles;
        inSightTiles = inSighTeTiles;
    }

    public void Map_init() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Tiles[i][j] = Tileset.NOTHING;
                inSightTiles[i][j] = Tileset.NOTHING;
            }
        }
        double gap_x = (double) (WIDTH - size_x) / 2;
        double gap_y = (double) (HEIGHT - size_y) / 2;
        for (int i = (int) Math.round(gap_x); i < WIDTH - Math.floor(gap_x); i++) {
            for (int j = (int) Math.round(gap_y); j < HEIGHT - Math.floor(gap_y); j++)
                Tiles[i][j] = Tileset.WALL;
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
    private class MapCell {
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
    private Boolean hasNotVisited(MapCell[][] node) {
        for (int i = 0; i < size_x / 2; i++) {
            for (int j = 0; j < size_y / 2; j++) {
                if (node[i][j].visited == false)
                    return true;
            }
        }
        return false;
    }

    // 相邻节点有无未访问节点
    private Boolean jointNotVisited(MapCell[] joinCells) {
        for (int i = 0; i < joinCells.length; i++) {
            if (joinCells[i] != null) {
                if (joinCells[i].visited == false)
                    return true;
            }
        }
        return false;
    }

    private void removeWall(MapCell currentNode, int nextNodeIndex) {
        switch (nextNodeIndex) {
            case 0:
                Tiles[currentNode.cell_x][currentNode.cell_y + 1] = Tileset.FLOOR;
                break;
            case 1:
                Tiles[currentNode.cell_x + 1][currentNode.cell_y] = Tileset.FLOOR;
                break;
            case 2:
                Tiles[currentNode.cell_x][currentNode.cell_y - 1] = Tileset.FLOOR;
                break;
            case 3:
                Tiles[currentNode.cell_x - 1][currentNode.cell_y] = Tileset.FLOOR;
                break;
        }
    }

    // 绘制顶部 GUI
    public void drawTopGui() {
        int X = (int) Math.floor(StdDraw.mouseX());
        int Y = (int) Math.floor(StdDraw.mouseY());
        StdDraw.setPenColor(StdDraw.WHITE);
        String state;
        if (4 <= X && 4 <= Y && X <= 55 && Y <= 35) {
            state = Tiles[X][Y].equals(Tileset.WALL) ? "WALL" : "FLOOR";
        } else {
            state = "NOTING";
        }
        StdDraw.text(0.09 * WIDTH, 0.97 * HEIGHT, state);
        StdDraw.line(0, 0.95 * HEIGHT, WIDTH, 0.95 * HEIGHT);
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
    private void backtracker(MapCell[][] node) {
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
                removeWall(pointer, nextIndex);
                pointer.jointCells[nextIndex].visited = true;
                pointer = pointer.jointCells[nextIndex];
            } else if (!st.isEmpty()) {
                pointer = st.pop();
            }
        }
    }

    public void Map_generate() {
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
                Tiles[i][j] = Tileset.FLOOR;
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
        backtracker(node);

    }

    /*
     * 这里同样采用 以下顺序 返回运动方向 ：
     * ---[0]
     * [3] * [1]
     * ---[2]
     */
    public int lisenKey() {
        char c = ' ';
        try {
            c = StdDraw.nextKeyTyped();
        } catch (java.util.NoSuchElementException e) {
        }
        switch (c) {
            case 'w':
                return 0;
            case 'd':
                return 1;
            case 's':
                return 2;
            case 'a':
                return 3;
            case 'o':
                return 4;
            default:
                return -1;
        }
    }

    // 计算视野范围 ( RADIUS == 3 ) 内 的方块
    public void insightTiles() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                inSightTiles[i][j] = Tileset.NOTHING;
                if (Math.sqrt(Math.pow(i - player_x, 2) + Math.pow(j - player_y, 2)) < RADIUS) {
                    inSightTiles[i][j] = Tiles[i][j];
                }
            }
        }
    }

    // 渲染迷宫
    public void renderScreen(MapWorld world, Boolean flage) {
        int numXTiles = Tiles.length;
        int numYTiles = Tiles[0].length;
        int playerLocateX = RANDOM.nextInt((int) size_x / 2); // Player 随机位置
        int playerLocateY = RANDOM.nextInt((int) size_y / 2);

        // 以正常形式开启时，需要赋初值
        if (!flage) {
            player_x = (int) Math.round((double) (WIDTH - size_x) / 2) + playerLocateX * 2 + 1; // 地图左下角坐标
            player_y = (int) Math.round((double) (HEIGHT - size_y) / 2) + playerLocateY * 2 + 1;
        }
        while (true) {
            StdDraw.clear(StdDraw.BLACK);
            insightTiles();
            for (int x = 0; x < numXTiles; x += 1) {
                for (int y = 0; y < numYTiles; y += 1) {
                    if (inSightTiles[x][y] == null) {
                        throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                                + " is null.");
                    }
                    inSightTiles[x][y].draw(x, y);
                }
            }
            drawTopGui();
            int direction = lisenKey();
            // 生成 PlAYER , LOCKDOOR
            Tiles[player_x][player_y] = Tileset.FLOOR;
            if (direction == 0) {
                player_y++;
                player_y = Tiles[player_x][player_y].character() == '#' ? player_y - 1 : player_y;
            } else if (direction == 1) {
                player_x++;
                player_x = Tiles[player_x][player_y].character() == '#' ? player_x - 1 : player_x;
            } else if (direction == 2) {
                player_y--;
                player_y = Tiles[player_x][player_y].character() == '#' ? player_y + 1 : player_y;
            } else if (direction == 3) {
                player_x--;
                player_x = Tiles[player_x][player_y].character() == '#' ? player_x + 1 : player_x;
            } else if (direction == 4) {
                try {
                    System.out.println("1111");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                            new FileOutputStream(new File("Map2.txt")));
                    objectOutputStream.writeObject(world);
                    objectOutputStream.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            Tiles[player_x][player_y] = Tileset.PLAYER;

            StdDraw.show();
            StdDraw.pause(50);
        }

    }

}
