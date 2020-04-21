package linkedlist;

public class DLL {
    public class Node {
        private Object data;
        private Node next;
        private Node prev;

        public Node(Object data) {
            this.data = data;
            next = null;
            prev = null;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    private Node head;
    private Node tail;

    public DLL() {
        head = null;
        tail = null;
    }

    public void add(Object data) {
        if (head != null && tail != null) {
            Node newNode = new Node(data);
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        } else {
            Node newNode = new Node(data);
            head = newNode;
            tail = newNode;
        }
    }

    public void displayFromHead() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.getData() + " ");
            temp = temp.getNext();
        }
        System.out.println();
    }

    public void displayFromTail() {
        Node temp = tail;
        while (temp != null) {
            System.out.print(temp.getData() + " ");
            temp = temp.getPrev();
        }
        System.out.println();
    }

    public int size() {
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.getNext();
        }
        return size;
    }

    public void swap(int x) {
        x -= 1;
        Node temp = head;
        Object mem1 = null;
        Object mem2 = null;

        for (int i = 0; i < x; i++) {
            temp = temp.getNext();
        }
        mem1 = temp.getData();
        temp = tail;

        for (int i = 0; i < x; i++) {
            temp = temp.getPrev();
        }
        mem2 = temp.getData();
        temp.setData(mem1);

        temp = head;
        for (int i = 0; i < x; i++) {
            temp = temp.getNext();
        }
        temp.setData(mem2);
    }
}