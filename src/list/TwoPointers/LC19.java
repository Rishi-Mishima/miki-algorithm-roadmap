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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);

        ListNode left = dummy; 
        ListNode right = dummy; 

        for(int i = 0; i < n; i ++){
            right = right.next; // 右指针先向右走 n 步
        } 

        while(right.next != null){

            // 左右指针一起走
            left = left.next; 
            right = right.next; 
        }

// 左指针的下一个节点就是倒数第 n 个节点
        left.next = left.next.next; 
            return dummy.next; 
    }
}