package linkedlist;

import enigma.console.TextAttributes;
import game.Console;
import game.Word;

public class MLL {
    class InnerNode {
        private Object data;
        private InnerNode next;

        public InnerNode(Object data) {
            this.data = data;
            next = null;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public InnerNode getNext() {
            return next;
        }

        public void setNext(InnerNode next) {
            this.next = next;
        }
    }

    class OuterNode {
        private Object data;
        private OuterNode down;
        private InnerNode right;

        public OuterNode(Object data) {
            this.data = data;
            down = null;
            right = null;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public OuterNode getDown() {
            return down;
        }

        public void setDown(OuterNode down) {
            this.down = down;
        }

        public InnerNode getRight() {
            return right;
        }

        public void setRight(InnerNode right) {
            this.right = right;
        }
    }

    private OuterNode head;

    public MLL() {
        head = null;
    }

    public OuterNode getHead() {
        return head;
    }

    public void setHead(OuterNode head) {
        this.head = head;
    }

    public void addWordAlphabetically(Word word) {
        if (!hasOuter(word.getWord().charAt(0))) {
            addOuterNode(word.getWord().charAt(0));
            addInnerNode(word.getWord().charAt(0), word);
        } else {
            if (!hasInner(word.getWord().charAt(0), word)) {
                addInnerNode(word.getWord().charAt(0), word);
            }
        }
    }

    public void addOuterNode(Object data) {
        if (head == null) {
            OuterNode newNode = new OuterNode(data);
            head = newNode;
        } else {
            OuterNode temp = head;
            OuterNode newNode = new OuterNode(data);
            if ((char) data < (char) temp.getData()) {
                newNode.setDown(temp);
                head = newNode;
            } else {
                while (temp.getDown() != null && (char) data > (char) temp.getDown().getData()) {
                    temp = temp.getDown();
                }
                newNode.setDown(temp.getDown());
                temp.setDown(newNode);
            }
        }
    }

    public void addInnerNode(Object outerData, Object innerData) {
        if (head != null) {
            OuterNode oTemp = head;
            while (oTemp != null) {
                if ((char) outerData == (char) oTemp.getData()) {
                    InnerNode iTemp = oTemp.getRight();
                    InnerNode newNode = new InnerNode(innerData);
                    if (iTemp == null) {
                        oTemp.setRight(newNode);
                    } else {
                        if ((((Word) innerData).getWord()).compareTo(((Word) iTemp.getData()).getWord()) < 0) {
                            newNode.setNext(iTemp);
                            oTemp.setRight(newNode);
                        } else {
                            while (iTemp.getNext() != null && (((Word) innerData).getWord())
                                    .compareTo(((Word) iTemp.getNext().getData()).getWord()) > 0) {
                                iTemp = iTemp.getNext();
                            }

                            newNode.setNext(iTemp.getNext());
                            iTemp.setNext(newNode);
                        }
                    }
                }
                oTemp = oTemp.getDown();
            }
        }
    }

    public boolean hasOuter(Object data) {
        if (head == null) {
            return false;
        } else {
            OuterNode temp = head;
            while (temp != null) {
                if ((char) temp.getData() == (char) data) {
                    return true;
                }
                temp = temp.getDown();
            }
            return false;
        }
    }

    public boolean hasInner(Object outerData, Object innerData) {
        if (head == null) {
            return false;
        } else {
            if (hasOuter(outerData)) {
                OuterNode oTemp = head;
                while (oTemp != null) {
                    if ((char) oTemp.getData() == (char) outerData) {
                        InnerNode iTemp = oTemp.getRight();
                        if (iTemp == null) {
                            return false;
                        } else {
                            while (iTemp != null) {
                                if ((((Word) iTemp.getData()).getWord())
                                        .equalsIgnoreCase(((Word) innerData).getWord())) {
                                    return true;
                                }
                                iTemp = iTemp.getNext();
                            }
                        }
                    }
                    oTemp = oTemp.getDown();
                }
            }
            return false;
        }
    }

    public int outerSize() {
        int size = 0;
        if (head != null) {
            OuterNode temp = head;
            while (temp != null) {
                size++;
                temp = temp.getDown();
            }
        }
        return size;
    }

    public int innerSize(Object outerData) {
        int size = 0;
        OuterNode oTemp = head;
        while (oTemp != null) {
            if (outerData == oTemp.getData()) {
                InnerNode iTemp = oTemp.getRight();
                if (iTemp != null) {
                    while (iTemp != null) {
                        size++;
                        iTemp = iTemp.getNext();
                    }
                }
            }
            oTemp = oTemp.getDown();
        }
        return size;
    }

    private OuterNode getOuterByIndex(int index) {
        if (index < outerSize()) {
            OuterNode temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getDown();
            }
            return temp;
        } else {
            return null;
        }
    }

    public Object getOuterDataByIndex(int index) {
        OuterNode o = getOuterByIndex(index);
        return o.getData();
    }

    private InnerNode getInnerByIndex(int outerIndex, int innerIndex) {
        OuterNode o = getOuterByIndex(outerIndex);
        if (innerIndex < innerSize(o.getData())) {
            InnerNode temp = o.getRight();
            for (int i = 0; i < innerIndex; i++) {
                temp = temp.getNext();
            }
            return temp;
        } else {
            return null;
        }
    }

    public Object getInnerDataByIndex(int outerIndex, int innerIndex) {
        InnerNode i = getInnerByIndex(outerIndex, innerIndex);
        return i.getData();
    }

    public void display() {
        if (head != null) {
            OuterNode oTemp = head;
            while (oTemp != null) {
                Console.print(oTemp.getData() + " --> ");
                InnerNode iTemp = oTemp.getRight();
                while (iTemp != null) {
                    Console.print(((Word) iTemp.getData()).getWord() + " ");
                    iTemp = iTemp.getNext();
                }
                oTemp = oTemp.getDown();
                Console.println("");
            }
        }
    }

    public void display(TextAttributes t) {
        if (head != null) {
            OuterNode oTemp = head;
            while (oTemp != null) {
                Console.print(oTemp.getData() + " --> ", t);
                InnerNode iTemp = oTemp.getRight();
                while (iTemp != null) {
                    Console.print(((Word) iTemp.getData()).getWord() + " ", t);
                    iTemp = iTemp.getNext();
                }
                oTemp = oTemp.getDown();
                Console.println("");
            }
        }
    }
}