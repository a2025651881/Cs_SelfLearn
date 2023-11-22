public class leetcode_0207 {

    // Definition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        ListNode(int x, ListNode next) {
            val = x;
            next = next;
        }
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int sizeA = 0, sizeB = 0, diff = 0;
        ListNode sentinelA = new ListNode(0, headA), sentinelB = new ListNode(0, headB);
        ListNode pointA = sentinelA, pointB = sentinelB;
        while (pointA.next != null) {
            sizeA++;
            pointA = pointA.next;
        }
        while (pointB.next != null) {
            sizeB++;
            pointB = pointB.next;
        }
        pointA = sentinelA;
        pointB = sentinelB;
        diff = Math.abs(sizeA - sizeB);
        while (diff != 0) {
            diff--;
            if (sizeA > sizeB) {
                pointA = pointA.next;
            } else {
                pointB = pointB.next;
            }
        }
        while (pointA != null) {
            pointA = pointA.next;
            pointB = pointB.next;
            if (pointA == pointB)
                break;
        }
        return pointA;
    }

    public static void main(String[] args) {
        ListNode list, listB;
        list = new ListNode(1, null);
        list.next = new ListNode(2, null);
        listB = new ListNode(1, null);
        listB.next = list.next;
        // list.next.next = new ListNode(3, null);
        // list.next.next.next = new ListNode(4, null);
        // list.next.next.next.next = new ListNode(5, null);
        System.out.println(getIntersectionNode(list, listB));

    }
}
