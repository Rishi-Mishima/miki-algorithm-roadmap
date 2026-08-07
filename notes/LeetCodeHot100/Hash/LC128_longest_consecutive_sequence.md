### 核心思想与精髓

这道题最绝妙的地方在于解决了一个难题：**如何在不排序的情况下，避免对同一个序列重复计数？**

1. **利用 HashSet 提供 $O(1)$ 的查找速度**：哈希集合可以在常数时间内判断某个数是否存在。
2. **只从“序列起点”开始计数（核心优化）**：
   - 如果数字 `x` 的前驱 `x - 1` **存在于集合中**，说明 `x` 一定不是连续序列的开头（因为更长序列可以从 `x - 1` 或更早开始），直接**跳过**。
   - 只有当 `x - 1` **不存在于集合中**时，才确定 `x` 是一个连续序列的**绝对起点**，此时才启动 `while` 循环向后查找 `x + 1, x + 2...`。

### 模拟执行示例

假设输入 `nums = [100, 4, 200, 1, 3, 2]`

1. **存入 HashSet**：`num_set = {100, 4, 200, 1, 3, 2}`
2. **遍历 HashSet**：
   - **遇到 100**：99 不在集合里 $\rightarrow$ **是起点**。向后找 101（不存在），当前长度 1。`longest = 1`
   - **遇到 4**：3 在集合里 $\rightarrow$ **不是起点，直接跳过**！
   - **遇到 200**：199 不在集合里 $\rightarrow$ **是起点**。向后找 201（不存在），当前长度 1。`longest = 1`
   - **遇到 1**：0 不在集合里 $\rightarrow$ **是起点**。向后依次找到 2, 3, 4，当前长度 4。`longest = 4`
   - **遇到 3**：2 在集合里 $\rightarrow$ **不是起点，直接跳过**！
   - **遇到 2**：1 在集合里 $\rightarrow$ **不是起点，直接跳过**！
3. **最终返回**：`4`（对应序列 `[1, 2, 3, 4]`）

### 代码逐行拆解

- **第 3–6 行：去重与写入**

  Java

  ```
  Set<Integer> num_set = new HashSet<Integer>();
  for (int num : nums) {
      num_set.add(num);
  }
  ```

  把数组元素转存到 `HashSet` 中。这一步有两个作用：去重，以及为后续提供 $O(1)$ 时间复杂度的查找能力。

  

  **第 10 行：判断起点（关键句）**

  Java

  ```
  if (!num_set.contains(num - 1))
  ```

  检查比当前数字小 1 的数是否存在。如果不存在，说明当前数字是某个连续序列的**最小值**（起点）。



**第 11–16 行：不断向后延伸**



```java
int currentNum = num;
int currentStreak = 1;

while (num_set.contains(currentNum + 1)) {
    currentNum += 1;
    currentStreak += 1;
}
```

确定是起点后，用一个 `while` 循环不断寻找 `currentNum + 1`，每找到一个，序列长度 `currentStreak` 增加 1，直到断开为止。



- **第 18 行：更新最大值**

  Java

  ```
  longestStreak = Math.max(longestStreak, currentStreak);
  ```

  将当前序列的长度与历史最大长度比较，保留较长者。



#### `currentNum`

`currentNum += 1;` 的核心作用是**推动“指针”向前移动**，让 `while` 循环能够去检查下一个相邻的数字。

如果不用 `currentNum += 1;`，程序就会陷入**死循环**。

**`currentStreak += 1;`**：负责**计数**（记录当前连续序列的**长度**是多少）。

**`currentNum += 1;`**：负责**更新查找目标**（告诉循环“下一个该去找谁”）。

假设集合里有 `[1, 2, 3]`，当前从起点 `num = 1` 开始：

**如果没有 `currentNum += 1;`：**

1. 初始 `currentNum = 1`，`currentStreak = 1`。
2. 第一次循环：检查 `num_set.contains(1 + 1)` 即 `2`，存在！`currentStreak` 变成 2。
3. 第二次循环：因为 `currentNum` 依然是 `1`，它又去检查 `1 + 1` 即 `2`，依然存在！
4. **结果**：程序永远在检查数字 `2`，`while` 循环无法终止（死循环）。

**有了 `currentNum += 1;` 之后：**

1. 初始 `currentNum = 1`，`currentStreak = 1`。
2. 第一次循环：检查 `1 + 1`（即 `2`），存在  - >  **`currentNum` 变成 2**，`currentStreak` 变成 2。
3. 第二次循环：检查 `2 + 1`（即 `3`），存在 - > **`currentNum` 变成 3**，`currentStreak` 变成 3。
4. 第三次循环：检查 `3 + 1`（即 `4`），不存在 - >  循环正常退出！



### 复杂度分析

- **时间复杂度**：$O(n)$
  - 看起来代码里有 `for` 循环嵌套 `while` 循环，像 $O(n^2)$，但实际上**每个数字最多只会被访问两次**：
    1. 在 `for` 循环中被遍历 1 次。
    2. 如果它是某个序列的一部分（且不是起点），在 `if` 判断中会被 $O(1)$ 忽略；如果是起点，会在 `while` 循环中被访问 1 次。
  - 因此总时间开销是线性的 $O(n)$。
- **空间复杂度**：$O(n)$
  - 需要一个 HashSet 存储数组中的所有元素，消耗 $O(n)$ 的额外空间。





### 完整答案

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        
        // 去重写入
        Set<Integer> num_set = new HashSet<Integer>(); 
        for (int num : nums){
            num_set.add(num); 
        }

        int longestStreak = 0; 

        for (int num : nums){
            if(!num_set.contains(num - 1 )){
                int currentNum = num; 
                int currentStreak = 1;

                while(num_set.contains(currentNum + 1)){
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak); 
            }
        }
        return longestStreak; 
    }
}
```

