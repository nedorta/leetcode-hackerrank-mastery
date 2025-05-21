class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        
        // 1) Weave cloned nodes into original list
        Node curr = head;
        while (curr != null) {
            Node clone = new Node(curr.val);
            clone.next = curr.next;
            curr.next = clone;
            curr = clone.next;
        }
        
        // 2) Assign random pointers on the clones
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }
        
        // 3) Unweave to restore original list and extract the clone list
        curr = head;
        Node newHead = head.next;
        Node copy = newHead;
        while (curr != null) {
            curr.next = curr.next.next;
            if (copy.next != null) {
                copy.next = copy.next.next;
            }
            curr = curr.next;
            copy = copy.next;
        }
        
        return newHead;
    }
} 