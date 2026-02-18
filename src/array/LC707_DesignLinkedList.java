class MyLinkedList {

    private int[] data;
    private int size;
    private int DEFAULT_CAP = 4;

    public MyLinkedList() {
        data = new int[DEFAULT_CAP]; // 长度是 4
        size = 0;
    }

    //变量	含义
    //data.length	数组容量（capacity）= 4
    //size	当前实际元素个数 = 0

    public int get(int index) {
        if(index < 0 || index >= size) return -1;
        return data[index];
    }

    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    public void addAtTail(int val) {
        // 数组需要扩容
        //size + 1 : 确保数组还能再放 1 个元素。
        ensureCapacity(size + 1);
        data[size++] = val;
        //data[size] = val;
        //size = size + 1;
    }

    public void addAtIndex(int index, int val) {
        if(index > size) return;
        if(index < 0) index = 0;

        ensureCapacity(size + 1);

        //向右移动元素 - 给 index 位置“腾空”，把后面的元素整体向右移动一格。
        for(int i = size - 1; i >= index; i--){
            data[i + 1 ] = data[i];
        }

        data[index] = val;
        size++;
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) return;
        //向左移动
        for(int i = index + 1; i < size; i++){
            data[i - 1] = data[i];
        }
        size--;
    }

    //扩容
    private void ensureCapacity(int minCap){
        if(minCap <= data.length) return;

        int newCap = data.length * 2;
        int[] newData = new int[newCap];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */