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

    public Object getElementByIndex(int index) {
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

    public void markWordAsSolution(String wordToMark) {
        Node temp = head;
        while (temp != null) {
            if (wordToMark.equalsIgnoreCase(((Word) temp.getData()).getWord())) {
                ((Word) temp.getData()).setSolution(true);
                break;
            }
            temp = temp.getNext();
        }
    }

    public void setWordCoords(Coordinate[] c, String wordToSetCoords) {
        Node temp = head;
        while (temp != null) {
            if (wordToSetCoords.equalsIgnoreCase(((Word) temp.getData()).getWord())) {
                ((Word) temp.getData()).setCoords(c);
                ;
                break;
            }
            temp = temp.getNext();
        }
    }

    public void displaySolutionWords(int x, int y, boolean hasDisplayFrame) {
        int generalOffset;
        if (hasDisplayFrame) {
            generalOffset = 1;
            displayFrame(x, y);
        } else {
            generalOffset = 0;
        }
        int listSize = size();
        int maxWordLen = -1;
        int rowOffset = 0;
        int columnOffset = 0;
        for (int i = 0; i < listSize; i++) {
            if (((Word) getElementByIndex(i)).isSolution()) {
                Console.setCursorPosition(x + generalOffset + columnOffset, y + generalOffset + rowOffset);
                Console.print("[");
                if (((Word) getElementByIndex(i)).isComplete()) {
                    Console.print("X");
                } else {
                    Console.print(" ");
                }
                Console.print("]" + ((Word) getElementByIndex(i)).getWord().toUpperCase());
                rowOffset++;
                maxWordLen = ((Word) getElementByIndex(i)).getWord().length();
                if (rowOffset >= Board.ROWCOUNT - 1 && i != listSize - 1) {
                    rowOffset = 0;
                    columnOffset += maxWordLen;
                    maxWordLen = 0;
                }
            }
        }
    }

    private void displayFrame(int x, int y) {
        int frameRowCount = Board.ROWCOUNT + 2;
        int frameRowLength = Board.ROWLENGTH * 3 - 5;
        for (int i = 0; i < frameRowCount; i++) {
            for (int j = 0; j < frameRowLength; j++) {
                Console.setCursorPosition(x + j, y + i);
                if (i == 0 && j == 0) {
                    Console.print("╔");
                } else if (i == 0 && j == 1) {
                    Console.print("WORD-LIST");
                    j+=8;
                } else if (i == 0 && j == frameRowLength - 1) {
                    Console.print("╗");
                } else if (i == frameRowCount - 1 && j == 0) {
                    Console.print("╚");
                } else if (i == frameRowCount - 1 && j == frameRowLength - 1) {
                    Console.print("╝");
                } else if ((i == 0 && j > 8 && j < frameRowLength - 1) || (i == frameRowCount - 1 && j > 0 && j < frameRowLength - 1)) {
                    Console.print("═");
                } else if ((j == 0 || j == frameRowLength - 1) && (i > 0 && i < frameRowCount - 1)) {
                    Console.print("║");
                }
            }
        }
    }
}