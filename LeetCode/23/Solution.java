class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        // Min‐heap ordered by node.val
        PriorityQueue<ListNode> heap = new PriorityQueue<>(
            Comparator.comparingInt(node -> node.val)
        );

        // 1) Initialize heap with the head of each non‐null list
        for (ListNode head : lists) {
            if (head != null) {
                heap.offer(head);
            }
        }

        // 2) Repeatedly extract the smallest node and append it
        ListNode dummy = new ListNode(0), tail = dummy;
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            tail.next = node;
            tail = tail.next;
            // If there's more in this list, push the next node
            if (node.next != null) {
                heap.offer(node.next);
            }
        }

        return dummy.next;
    }
} 