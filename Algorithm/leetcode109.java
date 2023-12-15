import java.util.ArrayList;
import java.util.HashMap;

class leetcode109 {
    HashMap<Integer, Integer> map = new HashMap<>();

    public class TreeNode {
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

    private TreeNode Tree(int[] inorder, int[] postorder) {
        ArrayList<Integer> leftInorder = new ArrayList<>(), leftPostOrder = new ArrayList<>(),
                rightInorder = new ArrayList<>(), rightPostorder = new ArrayList<>();
        if (inorder.length == 0)
            return null;
        TreeNode node = new TreeNode(postorder[postorder.length - 1]);
        for (int i = 0; i < map.get(postorder[postorder.length - 1]); i++) {
            leftInorder.add(inorder[i]);
        }
        for (int i = 0; i < leftInorder.size(); i++) {
            leftPostOrder.add(postorder[i]);
        }
        for (int i = leftInorder.size() + 1; i < inorder.length; i++) {
            rightInorder.add(inorder[i]);
        }
        for (int i = leftPostOrder.size(); i < postorder.length - 1; i++) {
            rightPostorder.add(postorder[i]);
        }
        node.left = Tree(leftInorder.stream().mapToInt(k -> k).toArray(),
                leftPostOrder.stream().mapToInt(k -> k).toArray());
        node.right = Tree(rightInorder.stream().mapToInt(k -> k).toArray(),
                rightPostorder.stream().mapToInt(k -> k).toArray());
        return node;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return Tree(inorder, postorder);
    }

    public static void main(String[] args) {
        leetcode109 temp = new leetcode109();
        temp.buildTree(new int[] { 9, 3, 15, 20, 7 }, new int[] { 9, 15, 7, 20, 3 });
    }
}