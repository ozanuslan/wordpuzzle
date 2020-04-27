package linkedlist;

import enigma.console.TextAttributes;
import game.Board;
import game.Console;
import game.Coordinate;
import game.Word;

public class SLL {
    public class Node {
        private Object data;
        private Node next;

        public Node(Object data) {
            this.data = data;
            next = null;
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

        public void setNext(Node link) {
            this.next = link;
        }
    }

    private Node head;

    public SLL() {
        head = null;
    }

    public void add(Object data) {
        if (head != null) {
            Node temp = head;

            while (temp.getNext() != null) {
                temp = temp.getNext();
            }

            temp.setNext(new Node(data));
        } else {
            Node newNode = new Node(data);
            head = newNode;
        }
    }

    public void add(int index, Object data){
        if(index < size()){
            Node temp = head;
            for(int i = 0; i < index; i++){
                temp = temp.getNext();
            }
            temp.setData(data);
        }
    }

    public Object get(int index) {
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getData();
    }

    public void delete(Object data) {
        if (head != null) {
            while (head != null && head.getData() == data) {
                head = head.getNext();
            }

            Node temp = head;
            Node prev = null;

            while (temp != null) {
                if (temp.getData() == data) {
                    prev.setNext(temp.getNext());
                    temp = prev;
                }

                prev = temp;
                temp = temp.getNext();
            }
        }
    }

    public void display() {
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                Console.print(temp.getData() + " ");
                temp = temp.getNext();
            }
        }
    }

    public void display(TextAttributes t) {
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                Console.print(temp.getData() + " ", t);
                temp = temp.getNext();
            }
        }
    }

    public boolean search(Object data) {
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                if (data == temp.getData()) {
                    return true;
                }
                temp = temp.getNext();
            }
        }

        return false;
    }

    public int size() {
        int size = 0;
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                size++;
                temp = temp.getNext();
            }
        }

        return size;
    }

    public void sort(){
        int size=  size();
        Object temp;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(((Word)get(i)).getWord().compareTo(((Word)get(j)).getWord())<0){
                    temp = get(i);
                    add(i,get(j));
                    add(j,temp);
                }
            }
        }
    }
}