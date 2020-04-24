package linkedlist;

import enigma.console.TextAttributes;
import game.Console;
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

    public void insert(Object data) {
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

    public void addWordAlphabetically(Word w) {
        if (head == null) {
            Node newNode = new Node(w);
            head = newNode;
        } else {
            Node temp = head;
            Node newNode = new Node(w);
            if (w.getWord().compareTo(((Word) temp.getData()).getWord()) < 0) {
                newNode.setNext(temp);
                head = newNode;
            }
            while (temp.getNext() != null && w.getWord().compareTo(((Word) temp.getNext().getData()).getWord()) > 0) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }
    }

    public Word getWordByIndex(int index) {
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return (Word) temp.getData();
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

    public void displayUnusedWords(int x, int y) {
        if (head != null) {
            Node temp = head;
            Console.setCursorPosition(x, y);
            Console.print("Unused words: ");
            while (temp != null) {
                if (!((Word) temp.getData()).isComplete()) {
                    Console.print(((Word) temp.getData()).getWord() + " ");
                }
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

    private SLL findUniqueNodes() {
        SLL uniqueNodes = new SLL();
        Node temp = head;
        while (temp != null) {
            Object data = temp.getData();
            if (!uniqueNodes.search(data)) {
                uniqueNodes.insert(data);
            }

            temp = temp.getNext();
        }

        return uniqueNodes;
    }

    private int count(Object data) {
        int count = 0;
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                if (temp.getData() == data) {
                    count++;
                }
                temp = temp.getNext();
            }
        }

        return count;
    }
}