### method1: iteration - 迭代

指针	含义
- prev	
    - 已经反转好的部分的头
- curr	
    - 当前正在处理的节点
- next	
    - 临时保存“下一个节点”

每一步核心操作（最重要）
```
ListNode next = curr.next;
```

> 👉 先保存下一个节点！
⚠️ 为什么？
- 因为你马上要改 curr.next，如果不存就丢链表了


`curr.next = prev;`
- 👉 反转指针方向

原来是：
`curr → next`

现在变成：
```
curr → prev
prev = curr;
```

👉 prev 前进一位

`curr = next;`

👉 curr 前进一位（继续处理下一个节点）

> 使用三个指针 prev、curr、next
遍历链表，每次将当前节点的 next 指向前一个节点，实现逐步反转
最终 prev 指向新的头节点

```
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
    public ListNode reverseList(ListNode head) {
        ListNode prev = null; 
        ListNode curr = head; 
        while(curr != null){
            ListNode next = curr.next; 
            curr.next = prev; 
            prev = curr; 
            curr = next; 
        }
        return prev; 
    }
}
```


### method2: 递归 - recursion

> 为什么递归必须写 base case，而迭代不用？
- 👉 递归必须“停下来”，迭代本身就有停止条件

```
if (head == null || head.next == null) {
    return head;
}
```

👉 这是 base case（终止条件）

如果没有的话就会一直调用

`head.next.next = head;
head.next = null;`

>❓ 为什么是 reverseList(head.next)，而不是 reverseList(head)？
👉 因为我们要“先处理后面的链表”，再处理当前节点 \

🔥 正确策略（递归思维）
👉 不要一上来处理 1
而是：
先把 2 → 3 → 4 反转好

🔑 本质区别
写法	含义
head	一直处理当前节点 ❌ 不前进
head.next	进入下一个节点 ✅ 递归推进

```
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
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null){
            return head; 
        }
        ListNode newHead = reverseList(head.next); 
        head.next.next = head; 
        head.next = null; 

        return newHead; 
    }
}

```