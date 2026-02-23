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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // dummy head
        ListNode dummy = new ListNode(-1), p = dummy;
        ListNode p1 = list1, p2 = list2;

        // compare two pointers
        while(p1 != null && p2 != null){
            // the smaller nodes should be touched to the new list
            if(p1.val < p2.val){
                p.next = p1;
                p1= p1.next;
            }else{
                p.next = p2;
                p2 = p2.next;
            }

            // the p pointer should keep moving
            p = p.next;
        }

        if(p1 != null){
            p.next = p1;
        }

        if(p2 != null){
            p.next = p2;
        }

        //最后Return的是Dummy,因为P已经在不停移动了
        return dummy.next;
    }
}