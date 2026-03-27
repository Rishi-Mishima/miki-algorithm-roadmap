Palindrome Linked List 

### Method1: Copy Links into Array (two pointers) 
一共为两个步骤：

1. 复制链表值到数组列表中。
2. 使用双指针法判断是否为回文。

```java
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
    public boolean isPalindrome(ListNode head) {
        List<Integer> vals = new ArrayList<Integer>();

        // 将链表的值复制到数组中
        ListNode currentNode = head; 
        while(currentNode != null){
            vals.add(currentNode.val);
            currentNode = currentNode.next;
        }

        // 使用双指针判断是否回文
        int front = 0; 
        int back = vals.size() - 1; 
        while(front < back){
            if(!vals.get(front).equals(vals.get(back))){
                return false; 
            }
            front++; 
            back--; 
        }
        return true; 
    }
}
```



#### 易错点: 为什么用`equals`? 

```java
if(!vals.get(front).equals(vals.get(back)))
```

核心原因：
Integer 是对象，不是基本类型

`List<Integer> vals = new ArrayList<>();`
- `Integer`是包装类(对象), 不是`int`

`==` vs `equals()` 的区别
- `==`比较的是：两个对象的地址（引用）是否一样
- `equals`比较的是：两个对象的值是否相等

----
### Method2: 递归
- currentNode 指针是先到尾节点，由于递归的特性再从后往前进行比较。
- frontPointer 是递归函数外的指针。
- 若 `currentNode.val != frontPointer.val` 则返回 false。反之，frontPointer 向前移动并返回 true。

``` java
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
    private ListNode frontPointer; 

    private boolean recursivelyCheck(ListNode currentNode){
        if(currentNode != null){
            if(!recursivelyCheck (currentNode.next)) return false;
            if(currentNode.val != frontPointer.val) return false;

            frontPointer = frontPointer.next; 
        }
        return true;
    }

    public boolean isPalindrome(ListNode head) {
        frontPointer = head; 
        return recursivelyCheck(head);
    }
}
```

> 递归到底 再比较

NOTE: 
- 这种方法不仅使用了 O(n) 的空间，且比第一种方法更差，因为在许多语言中，堆栈帧的开销很大（如 Python），并且最大的运行时堆栈深度为 1000（可以增加，但是有可能导致底层解释程序内存出错）。为每个节点创建堆栈帧极大的限制了算法能够处理的最大链表大小。

---
### Method 3: 快慢指针
整个流程可以分为以下五个步骤：
1. 找到前半部分链表的尾节点。
2. 反转后半部分链表。
3. 判断是否回文。
4. 恢复链表。
5. 返回结果。

#### step1: 计算节点数量

- 执行步骤一，我们可以计算链表节点的数量，然后**遍历链表**找到前半部分的尾节点。
- 我们也可以使用**快慢指针**在一次遍历中找到：慢指针一次走一步，快指针一次走两步，快慢指针同时出发。当快指针移动到链表的末尾时，慢指针恰好到链表的中间。通过慢指针将链表分为两部分。
- 若链表有奇数个节点，则中间的节点应该看作是前半部分

``` java
//分割链表
        private ListNode endOfFirstHalf(ListNode head){
            ListNode slow = head; 
            ListNode fast = head; 
            while(fast.next != null && fast.next.next != null){
                fast = fast.next.next; 
                slow = slow.next;
            }
            return slow; 
        }
```
必须写`while(fast.next != null && fast.next.next != null)`
- 因为要防止`fast = fast.next.next;`空指针异常

#### step2: 反转链表
- 参考 [LC206.反转链表 ](https://leetcode.cn/problems/reverse-linked-list/)

``` java
 // 反转链表
        private ListNode reverseList(ListNode head){
            ListNode prev = null; 
            ListNode curr = head; 
            while( curr != null){
                ListNode nextTemp = curr.next; 
                curr.next = prev; 
                prev = curr; 
                curr = nextTemp; 
            }

            return prev; 
        }
```

#### step3: 比较两段链表
步骤三比较两个部分的值，当后半部分到达末尾则比较完成，可以忽略计数情况中的中间节点。 

#### step4: 再次反转恢复链表
步骤四与步骤二使用的函数相同，再反转一次恢复链表本身。

> 答案
``` java
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
    public boolean isPalindrome(ListNode head) {
        if (head == null) return true; 

        // 找到前半部分链表的尾节点并反转后半部分链表
        ListNode firstHalfEnd = endOfFirstHalf(head);
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // 是否回文
        ListNode p1 = head; 
        ListNode p2 = secondHalfStart; 
        boolean result = true; 
        while( result && p2 != null){
            if (p1.val != p2.val) result = false;

            p1 = p1.next; 
            p2 = p2.next; 
        }

         // 还原链表并返回结果
        firstHalfEnd.next = reverseList(secondHalfStart);
        return result;
    }

            //分割链表
        private ListNode endOfFirstHalf(ListNode head){
            ListNode slow = head; 
            ListNode fast = head; 
            while(fast.next != null && fast.next.next != null){
                fast = fast.next.next; 
                slow = slow.next;
            }
            return slow; 
        }

        // 反转链表
        private ListNode reverseList(ListNode head){
            ListNode prev = null; 
            ListNode curr = head; 
            while( curr != null){
                ListNode nextTemp = curr.next; 
                curr.next = prev; 
                prev = curr; 
                curr = nextTemp; 
            }

            return prev; 
        }
}
```