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
    public ListNode middleNode(ListNode head) {
        // 无需虚拟头节点，快慢指针直接从head出发
        ListNode slow = head; // 慢指针：每次走1步
        ListNode fast = head; // 快指针：每次走2步

        // 循环条件：快指针没到末尾（偶数节点）或快指针的下一个节点没到末尾（奇数节点）
        while (fast != null && fast.next != null) {
            fast = fast.next.next; // 快指针走2步
            slow = slow.next;      // 慢指针走1步
        }

        // 循环结束时，slow就是中间节点
        return slow;
    }
}