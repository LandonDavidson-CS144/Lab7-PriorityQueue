public class Main {
    public static void main(String[] args) {
        PriorityQueue queue = new PriorityQueue(-1);
        int[] elements = {10, 20, 5, 7, 30, 13, 8, 100, 31, 52, 73, 14, 64, 150, 200, 79, 110, 113, 136, 148};
        for (int element: elements) {
            queue.enqueue(element);
        }

        queue.printTree();

        System.out.println("Length: " + queue.length());

        System.out.println("Front of Priority Queue: " + queue.frontValue());

        int dequeues = 10;
        System.out.println("Dequeueing " + dequeues + " times");
        for (int i = 0; i < dequeues; i++) {
            System.out.println("Removed: " + queue.dequeue());
        }
        queue.printTree();
        System.out.println("Length: " + queue.length());

        queue.clear();
        System.out.println("Queue cleared");
        queue.printTree();
    }
}

class PriorityQueue {
    int length = 0;
    int size = 4;
    int[] heap;
    int bound;
    public PriorityQueue(int bound) {
        heap = new int[size];
        this.bound = bound;
    }
    public void enqueue(int target) {
        if (length == bound) return;
        if (length == size) {
            size *= 2;
            int[] bigHeap = new int[size];
            System.arraycopy(heap, 0, bigHeap, 0, length);
            heap = bigHeap;
        }

        heap[length] = target;
        percUp(length);
        length++;
    }
    public void percUp(int i) {
        if (i == 0) return;
        while (i > 0) {
            int curNode = heap[i];
            int curParent = heap[(i - 1) / 2];
            if (curNode > curParent) {
                heap[i] = curParent;
                heap[(i - 1) / 2] = curNode;
            }
            i = (i - 1) / 2;
        }
    }
    public int frontValue() {return heap[0];}
    public int dequeue() {
        int target = heap[0];
        length--;
        heap[0] = heap[length];
        percDown(0);

        return target;
    }
    public void percDown(int parentIndex) {
        int prevValue = heap[parentIndex];
        int childIndex = parentIndex * 2 + 1;
        while (childIndex < length) {
            int maxValue = prevValue;
            int maxIndex = parentIndex;
            for (int j = 0; j < 2 && j + childIndex < length; j++) {
                if (heap[childIndex + j] > maxValue) {
                    maxValue = heap[childIndex + j];
                    maxIndex = childIndex + j;
                }
            }
            if (maxValue == prevValue) {
                return;
            } else {
                heap[parentIndex] = maxValue;
                heap[maxIndex] = prevValue;
                parentIndex = maxIndex;
                childIndex = parentIndex * 2 + 1;
            }
        }
    }
    public void clear() {
        length = 0;
        size = 4;
        heap = new int[size];
    }
    public int length() {return length;}
    public void printTree() {
        if (length == 0) System.out.println("Queue is empty");

        String pad = "  ";
        int height = getHeight();
        int start = 0;

        // For each level of the tree create new line and append values with appropriate padding
        for (int i = 0; i < height; i++) {
            // Initialize new StringBuilder for current line
            StringBuilder line = new StringBuilder();
            // Add padding to center tree
            addPadding(line, pad, (int)Math.pow(2, height - i - 1));
            // determine amount of elements on current line and for each append value with appropriate padding
            int elements = (int)Math.pow(2, i);
            for (int j = 0; j < elements; j++) {
                if (start + j == length) break;
                line.append(heap[start + j]);
                addPadding(line, pad, (int)Math.pow(2, height - i));
            }
            System.out.println(line);
            start += elements;
        }
    }
    private void addPadding(StringBuilder s, String pad, int multiplier) {
        s.append(String.valueOf(pad).repeat(Math.max(0, multiplier)));
    }
    private int getHeight() {return (int)(Math.ceil(Math.log((double)length + 1) / Math.log(2)));}
}
