## 方法1: 排序	

#### 完整解法

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap <String, List<String>>(); 

        for (String str: strs){
            char[] arr = str.toCharArray(); 
            Arrays.sort(arr); 

            // 将排序后的字符数组重新组装成字符串 "aet"，作为分组标识。
            String key = new String(arr); 

            // 检查 map 中是否已有 "aet" 这个 Key。如果有就取出对应的列表；如果没有，则返回一个全新的空列表 new ArrayList<String>()。
            List<String> list = map.getOrDefault(key, new ArrayList<>()); 

            //把当前原始单词（如 "tea"）加进列表中。
            list.add(str); 

            //将更新后的列表放回 map。
            map.put(key, list); 

        }

        // map.values() 会取出哈希表中所有的列表值（即分组好的结果）。
        //利用 new ArrayList<>(...) 将其直接构造并返回目标要求的 List<List<String>> 格式
        return new ArrayList<List<String>>(map.values()); 
    }
}
```



为什么是`<String, List<Stirng >>`

1. **一组可能有多个字符串**：排序后相同的 key（如 `"aet"`）对应多个原始字符串（`"eat"`, `"tea"`, `"ate"`），单个 `String` 只能存一个值，无法容纳一组。
2. **典型解法是用 `HashMap<String, List<String>>`**：key 是排序后的字符串，value 是属于同一组的所有原始字符串列表。



1. `list.add(str)` 和 `map.put(sortedString, list)`

**`list` 里本来有什么？**

```java
List<String> list = map.getOrDefault(sortedString, new ArrayList<>());
```

应用代码

这行代码的意思是：去 `map` 里找 key 为 `sortedString` 的值。

- **找到了** → 返回之前已经存好的那个 `List`
- **没找到** → 返回一个新的空 `ArrayList`

所以 `list` 里可能是**之前已经归到同一组的字符串**，也可能是空的。



2. `return new ArrayList<>(map.values())`

`map.values()` 返回的是 map 中**所有 value 的集合**，即所有的分组 list。

以上面的例子来说：

```
map.values() = [["eat", "tea"], ["tan"]]
```

`new ArrayList<>(...)` 把它转成 `List<List<String>>`，正好是题目要求的返回类型：

```
[["eat", "tea"], ["tan"]]
```



