> 检查一个链表是否有环,如果有环,找到链表的环的起点

对比两道题：
- 141. 环形链表 → 返回 boolean（有环/无环）
- 142. 环形链表 II → 返回 ListNode（入环节点/null）

### Method1: HashMAP(SET)

同LC141, 创建哈希表,然后判断是否已经存在

``` java

/** Definition for a single linked list
 * class ListNode{
 *      int val; 
 *      ListNode next;
 *      ListNode(int x){
 *              val = x; 
 *              next = null;   
 * } 
 * }
 * 
 **/ 
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode pos = head; 
        Set<ListNode> visited = new HashSet<ListNode>();

        while(pos != null){
            if(visited.contains(pos)){
                return pos; 
            }else{
                visited.add(pos); 
            }
            pos = pos.next; 
        }

        return null; 
    }
}
```
