package linkedlist;

public class SLL {
    public class Node {
        private Object data;
        private Node link;
    
        public Node(Object data){
            this.data = data;
            link = null;
        }
    
        public Object getData() {
            return data;
        }
    
        public void setData(Object data) {
            this.data = data;
        }
    
        public Node getLink() {
            return link;
        }
    
        public void setLink(Node link) {
            this.link = link;
        }
    }
    
    private Node head;

    public SLL() {
        head = null;
    }

    public void insert(Object data) {
        if (head != null) {
            Node temp = head;

            while (temp.getLink() != null) {
                temp = temp.getLink();
            }

            temp.setLink(new Node(data));
        } else {
            Node newNode = new Node(data);
            head = newNode;
        }
    }

    public void delete(Object data) {
        if (head != null) {
            while (head != null && head.getData() == data){
                head = head.getLink();
            }

            Node temp = head;
            Node prev = null;

            while (temp != null) {
                if (temp.getData() == data) {
                    prev.setLink(temp.getLink());
                    temp = prev;
                }

                prev = temp;
                temp = temp.getLink();
            }
        }
    }

    public void display() {
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.getData()+" ");
                temp = temp.getLink();
            }
            System.out.println();
        }
    }

    public boolean search(Object data) {
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                if (data == temp.getData()) {
                    return true;
                }
                temp = temp.getLink();
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
                temp = temp.getLink();
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

            temp = temp.getLink();
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
                temp = temp.getLink();
            }
        }

        return count;
    }

    public void barGraph() {
        SLL uniqueNodes = findUniqueNodes();
        int size = size();
        Node temp = head;
        Object data;
        int nodeCount;
        for (int i = 0; i < size; i++) {
            data = temp.getData();

            if (uniqueNodes.search(data)) {
                nodeCount = count(data);
                System.out.print(data + " ");
                for (int j = 0; j < nodeCount; j++) {
                    System.out.print("*");
                }
                System.out.println();

                uniqueNodes.delete(data);
            }

            temp = temp.getLink();
        }
    }
}