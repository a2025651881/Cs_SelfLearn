public class leetcode_19 {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode point = head, pre = head;
        ListNode tempPoint = new ListNode(0, head);
        int size = 0;
        while (point.next != null) {
            size++;
            if (size == n + 1) {
                size = 0;
                tempPoint.next = pre;
                pre = point.next;
            }
            point = point.next;
        }
        size++;
        if (size == n + 1) {
            size = 0;
            tempPoint.next = pre;
        }
        if (head.next == null)
            return null;
        // 已经数了 size 个，再往前推 ( n- size) 个
        // n-(n-size) = size 个
        for (int i = 0; i < size + 1; i++) {
            tempPoint = tempPoint.next;
        }
        tempPoint.next = tempPoint.next == null ? null : tempPoint.next.next;

        return head;
    }

    public static void main(String[] args) {
        ListNode list;
        list = new ListNode(1, null);
        list.next = new ListNode(2, null);
        // list.next.next = new ListNode(3, null);
        // list.next.next.next = new ListNode(4, null);
        // list.next.next.next.next = new ListNode(5, null);
        System.out.println(removeNthFromEnd(list, 2));

    }
}
