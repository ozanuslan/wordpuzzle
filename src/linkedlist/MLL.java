package linkedlist;
import game.Word;

public class MLL {
    class Node {
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

        public void setNext(Node next) {
            this.next = next;
        }
    }

    class OuterNode {
        private Object data;
        private OuterNode down;
        private Node right;

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

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
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
                    Node iTemp = oTemp.getRight();
                    Node newNode = new Node(innerData);
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
                        Node iTemp = oTemp.getRight();
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
                Node iTemp = oTemp.getRight();
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

    private OuterNode getOuterAt(int index) {
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

    public Object getOuterDataAt(int index) {
        OuterNode o = getOuterAt(index);
        return o.getData();
    }

    private Node getInnerAt(int outerIndex, int innerIndex) {
        OuterNode o = getOuterAt(outerIndex);
        if (innerIndex < innerSize(o.getData())) {
            Node temp = o.getRight();
            for (int i = 0; i < innerIndex; i++) {
                temp = temp.getNext();
            }
            return temp;
        } else {
            return null;
        }
    }

    public Object getInnerDataAt(int outerIndex, int innerIndex) {
        Node i = getInnerAt(outerIndex, innerIndex);
        return i.getData();
    }

    public void removeInnerAt(int outerIndex, int innerIndex) {
        OuterNode oTemp = getOuterAt(outerIndex);
        if(innerIndex == 0){
            oTemp.setRight(oTemp.getRight().getNext());
        } else{
            Node iTemp = getInnerAt(outerIndex, innerIndex-1);
            if(innerIndex == innerSize(getOuterDataAt(outerIndex))-1){
                iTemp.setNext(null);
            } else{
                iTemp.setNext(iTemp.getNext().getNext());
            }
        }
    }
}