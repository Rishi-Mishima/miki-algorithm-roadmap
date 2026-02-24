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
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists(lists, 0, lists.length); 
    }

    //合并从 lists[i] 到 lists[j-1] 的链表
    public ListNode mergeKLists(ListNode[] lists, int i, int j){
        int m = j - i; // 计算当前区间内的链表数量
        if(m ==0) return null; // 区间内无链表，返回空
        if(m == 1) return lists[i]; // 区间内只有1个链表，直接返回，无需合并

        //合并左半部分
        ListNode left = mergeKLists(lists, i, i + m/2); 
        // 合并右半部分
        ListNode right = mergeKLists(lists, i+m/2, j);
        // 最后把左半和右半合并
        return mergeTwoLists(left, right);


    }

    // LC21. 合并两个有序链表
    public ListNode mergeTwoLists(ListNode list1, ListNode list2){
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy; 
        ListNode p1 = list1, p2 = list2; 

        while(p1 != null && p2 != null){
            if(p1.val < p2.val){
                p.next = p1;
                p1 = p1.next; 
            }else{
                p.next = p2; 
                p2 = p2.next;
            }
            p = p.next; 
        }

        if(p1 != null){
            p.next = p1; 
        }

        if(p2 != null){
            p.next = p2;
        }

        return dummy.next; 
    }
}