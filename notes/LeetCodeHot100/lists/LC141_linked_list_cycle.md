### 方法一: 哈希表 (Hash MAP)

> 最容易想到的方法是遍历所有节点，每次遍历到一个节点时，判断该节点此前是否被访问过。

具体地，我们可以使用**哈希表**来存储所有已经访问过的节点。每次我们到达一个节点，如果该节点已经存在于哈希表中，则说明该链表是环形链表，否则就将该节点加入哈希表中。重复这一过程，直到我们遍历完整个链表即可。

``` java 
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        Set<ListNode> seen = new HashSet<ListNode>();
        while(head != null){
            if(!seen.add(head)){
                return true;
            }
            head = head.next;
        }

        return false; 
    }
}
```

时间复杂度 : O(N)
- 因为需要遍历每一个节点
空间复杂度: O(N)

---

### 方法二: 快慢指针
>  我们定义两个指针，一快一慢。慢指针每次只移动一步，而快指针每次移动两步

初始时，慢指针在位置 `head`，而快指针在位置 `head.next`。这样一来，如果在移动的过程中，快指针反过来追上慢指针，就说明该链表为环形链表。
- 否则快指针将到达链表<font color="red">尾部</font>，该链表不为环形链表。

``` java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false; 

        ListNode slow = head; 
        ListNode fast  = head.next; 
        while(slow != fast){
            if(fast == null || fast.next == null){
                return false; 
            }

            slow = slow.next; 
            fast = fast.next.next;
        }

        return true; 
    }
}
```
时间复杂度：O(N)，其中 N 是链表中的节点数。
空间复杂度：O(1)。我们只使用了两个指针的额外空间。


