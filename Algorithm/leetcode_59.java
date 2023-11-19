public class leetcode_59 {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        // 四个状态量
        int[] state_x = new int[] { 1, 0, -1, 0 }, state_y = new int[] { 0, 1, 0, -1 };
        int x = 0, y = 0, stateSwitch = 0, pre_x = 0, pre_y = 0;
        for (int i = 0; i < Math.pow(n, 2); i++) {
            // 发生两种情况，状态回溯，状态切换
            // 1.到达矩阵边界。
            // 2.下个位置已有元素填充。
            if (x > n - 1 || x < 0 || y > n - 1) {
                stateSwitch = (stateSwitch + 1) % 4;
                x = pre_x + state_x[stateSwitch];
                y = pre_y + state_y[stateSwitch];
            } else if (matrix[y][x] != 0) {
                stateSwitch = (stateSwitch + 1) % 4;
                x = pre_x + state_x[stateSwitch];
                y = pre_y + state_y[stateSwitch];
            }
            matrix[y][x] = i + 1;
            pre_x = x;
            pre_y = y;
            x += state_x[stateSwitch];
            y += state_y[stateSwitch];
        }
        return matrix;
    }
}
