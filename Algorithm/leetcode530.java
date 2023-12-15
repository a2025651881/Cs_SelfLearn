
public class leetcode530 {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    int min = Integer.MAX_VALUE, pre_value = Integer.MAX_VALUE;

    private void inOrder(TreeNode node) {
        if (node == null)
            return;
        inOrder(node.left);
        min = min > Math.abs(node.val - pre_value) ? Math.abs(node.val - pre_value) : min;
        pre_value = node.val;
        inOrder(node.right);
    }

    public int getMinimumDifference(TreeNode root) {
        inOrder(root);
        return min;
    }

    public static void main(String[] args) {
        leetcode530 temp = new leetcode530();
        TreeNode root = new TreeNode(236);
        root.left = new TreeNode(104);
        root.right = new TreeNode(701);
        root.left.right = new TreeNode(227);
        root.right.right = new TreeNode(911);
        System.out.println(temp.getMinimumDifference(root));
    }
}
