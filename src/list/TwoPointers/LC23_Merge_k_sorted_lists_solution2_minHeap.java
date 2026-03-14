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
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> (a.val - b.val)); 
        for(ListNode head : lists){
            if(head != null){
                pq.offer(head); // 入堆：head 被加入，堆自动排序，堆顶仍是最小节点
            }
        }

        ListNode dummy = new ListNode(); //dummy head 
        ListNode cur = dummy; 
        while(!pq.isEmpty()){
            ListNode node = pq.poll();  // 出堆：取出堆顶的最小节点，堆自动重新排序

            // 取出节点后，把该节点的下一个节点入堆
            if(node.next != null){
                pq.offer(node.next);  // 入堆：node.next 加入，堆重新排序
            }

            //cur 是结果链表的 “尾指针”，cur.next = node 是把当前最小节点 node 接到结果链表的末尾。
            cur.next = node; //从堆里取出的最小节点拼接到结果链表上
            cur = cur.next ;
        }
        return dummy.next; 
    }
}