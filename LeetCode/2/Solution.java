/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Dummy head simplifies edge‐case handling
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;

        // Loop until both lists are exhausted and no carry remains
        while (l1 != null || l2 != null || carry != 0) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            int sum = x + y + carry;
            carry = sum / 10;
            int digit = sum % 10;

            // Append the computed digit to the result list
            curr.next = new ListNode(digit);
            curr = curr.next;

            // Advance input pointers if possible
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        // The result starts at dummy.next
        return dummy.next;
    }
} 