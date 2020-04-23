package linkedlist;

import game.Console;
import game.User;

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

    public void displayFromHead(int x, int y) {
        Node temp = head;
        int place = 0;
        while (temp != null) {
            Console.setCursorPosition(x, y + place);
            Console.print((place + 1) + " " + ((User) temp.getData()).getName() + " " + ((User) temp.getData()).getScore());
            temp = temp.getNext();
            place++;
        }
    }

    public void displayFromTail(int x, int y) {
        Node temp = tail;
        int place = size();
        while (temp != null) {
            place--;
            Console.setCursorPosition(x, y + place);
            Console.print((place+1) + " " + ((User) temp.getData()).getName() + " " + ((User) temp.getData()).getScore());
            temp = temp.getPrev();
        }
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
}