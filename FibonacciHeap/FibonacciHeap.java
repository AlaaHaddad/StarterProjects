/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    private HeapNode first;
    private int size;
    private HeapNode min;
    private int NumOfMarked;
    private int NumOfTrees;
    private static int cuts = 0;
    private static int links = 0;

    public FibonacciHeap() {
        size = 0;
        min = null;
        first = null;
        NumOfMarked = 0;
        NumOfTrees = 0;
    }

    /**
     * public int size()
     * <p>
     * Returns the number of elements in the heap.
     */
    public int size() {return this.size;}
    
    /**
     * public HeapNode findMin()
     * <p>
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     */
    public HeapNode findMin() {return this.min;}

    public HeapNode getFirst() {return this.first;}
    public void setFirst(HeapNode first) {this.first = first;}
    public int getNumOfMarked() {return this.NumOfMarked;}
    public void setNumOfMarked(int num) {this.NumOfMarked = num;}
    public int getNumOfTrees() {return this.NumOfTrees;}
    public void setNumOfTrees(int num) {this.NumOfTrees = num;}
    public void setMin(HeapNode min) {this.min = min;}
    public void setSize(int size) {this.size = size;}


    public void link(HeapNode node,HeapNode node2) {
        if(node2.getKey()>node.getKey()){
            link(node2,node);
            return;
        }
        links++;
        if(node==getFirst()){
            setFirst(node.getNext());
        }
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        if(node2.getSon()==null){
            node.setPrev(node);
            node.setNext(node);
        } else {
            node2.getSon().getPrev().setNext(node);
            node.setPrev(node2.getSon().getPrev());
            node.setNext(node2.getSon());
            node2.getSon().setPrev(node);
        }
        node.setParent(node2);
        node2.setSon(node);
        node2.setRank(node2.getRank()+1);
        setNumOfTrees(getNumOfTrees()-1);
    }

    public void LinkTheTrees() {
        HeapNode[] roots = new HeapNode[1 + ((int) (Math.log(this.size()) / Math.log((1 + Math.sqrt(5)) / 2)))];
        HeapNode node = getFirst();
        int num = getNumOfTrees();
        for (int i = 0; i < num; i++) {
            HeapNode next = node.getNext();
            if (roots[node.getRank()] == null) {
                roots[node.getRank()] = node;
            } else {
                HeapNode son = roots[node.getRank()];
                HeapNode parent = node;
                link(son, parent);
                if (son.getKey() < parent.getKey()) {
                    parent = son;
                    son = node;
                }
                roots[son.getRank()] = null;
                while (roots[parent.getRank()] != null) {
                    int parent_rank = parent.getRank();
                    link(roots[parent_rank], parent);
                    if (parent.getKey() > roots[parent_rank].getKey()) {
                        parent = roots[parent_rank];
                    }
                    roots[parent_rank] = null;
                }
                roots[parent.getRank()] = parent;
            }
            node = next;
        }
        int i = 0;
        while (roots[i] == null) {
            i++;
        }
        setFirst(roots[i]);
        for (HeapNode node1 : roots) {
            if (node1 != null) {
                node1.setNext(getFirst());
                node1.setPrev(getFirst().getPrev());
                getFirst().getPrev().setNext(node1);
                getFirst().setPrev(node1);
            }
        }
    }

    public void cut(HeapNode node,HeapNode node2) {
        cuts++;
        node.setParent(null);
        if(node.getMarked()) {
            node.setMarked(false);
            setNumOfMarked(getNumOfMarked()-1);
        }
        node2.setRank(node2.getRank()-1);
        if(node.getNext() == node) {
            node2.setSon(null);
        } else {
            node2.setSon(node.getNext());
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }
        node.setPrev(getFirst().getPrev());
        node.getPrev().setNext(node);
        getFirst().setPrev(node);
        node.setNext(getFirst());
        setFirst(node);
        setNumOfTrees(getNumOfTrees()+1);

    }

    public void cascading_cut(HeapNode node, HeapNode node2) {
        cut(node,node2);
        if(node2.getParent() != null) {
            if(node2.getMarked()) {
                cascading_cut(node2, node2.getParent());
            } else {
                node2.setMarked(true);
                setNumOfMarked(getNumOfMarked()+1);
            }
        }
    }

    public void SetParentsNull(HeapNode node){
        HeapNode clone = node;
        while (node.getParent() != null || node != clone){
            node.setParent(null);
            if(node.getMarked()){
                setNumOfMarked(getNumOfMarked()-1);
            }
            node.setMarked(false);
            node=node.getNext();
        }
    }

    /**
     * public boolean isEmpty()
     * <p>
     * Returns true if and only if the heap is empty.
     */
    public boolean isEmpty() {
        return (size() == 0);
    }

    /**
     * public HeapNode insert(int key)
     * <p>
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     * <p>
     * Returns the newly created node.
     */
    public HeapNode insert(int key) {
        HeapNode new_node = new HeapNode(key);
        if (this.isEmpty()) {
            setFirst(new_node);
            setMin(new_node);
        } else {
            new_node.setPrev(getFirst().getPrev());
            new_node.getPrev().setNext(new_node);
            getFirst().setPrev(new_node);
            new_node.setNext(getFirst());
            setFirst(new_node);
            if (new_node.getKey() < findMin().getKey()) {
                setMin(new_node);
            }
        }
        setSize(size()+1);
        setNumOfTrees(getNumOfTrees()+1);
        return new_node;
    }

    /**
     * public void deleteMin()
     * <p>
     * Deletes the node containing the minimum key.
     */
    public void deleteMin() {
        if(this.isEmpty()) {
            return;
        }
        HeapNode son = findMin().getSon();
        HeapNode node1;
        if(size() == 1) {
            setNumOfTrees(getNumOfTrees()-1);
            setSize(size()-1);
            setMin(null);
            setFirst(null);
            return;
        }
        if (getNumOfTrees() == 1) {
            setFirst(son);
            setNumOfTrees(findMin().getRank());
            SetParentsNull(getFirst());
        } else if(son==null) {
            HeapNode next = findMin().getNext();
            findMin().getPrev().setNext(next);
            setNumOfTrees(getNumOfTrees()-1);
            next.setPrev(findMin().getPrev());
            if(findMin() == getFirst()) {
                setFirst(findMin().getNext());
            }
        } else {
            setNumOfTrees(getNumOfTrees()+findMin().getRank()-1);
            HeapNode son2 = son.getPrev();
            son.setPrev(findMin().getPrev());
            findMin().getPrev().setNext(son);
            son2.setNext(findMin().getNext());
            findMin().getNext().setPrev(son2);
            SetParentsNull(findMin().getSon());
            if(findMin() == getFirst()) {
                setFirst(son);
            }
        }
        setSize(size()-1);
        LinkTheTrees();
        node1 = getFirst();
        HeapNode min = node1;
        while(node1.getNext() != getFirst()) {
            node1 = node1.getNext();
            if(node1.getKey()<min.getKey()) {
                min = node1;
            }
        }
        setMin(min);
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * <p>
     * Melds heap2 with the current heap.
     */
    public void meld(FibonacciHeap heap2) {
        HeapNode next = getFirst().getPrev();
        HeapNode prev = heap2.getFirst().getPrev();
        getFirst().setPrev(prev);
        prev.setNext(getFirst());
        next.setNext(heap2.getFirst());
        heap2.getFirst().setPrev(next);
        if(heap2.findMin().getKey() < findMin().getKey()) {
            setMin(heap2.findMin());
        }
       setSize(size()+heap2.size());
        setNumOfTrees(getNumOfTrees()+heap2.getNumOfTrees());
        setNumOfMarked(getNumOfMarked()+heap2.getNumOfMarked());
    }

    /**
     * public int[] countersRep()
     * <p>
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
     */
    public int[] countersRep() {
        if(this.isEmpty()) {
            return new int[0];
        }
        int max = findMin().getRank();
        HeapNode node = findMin().getNext();
        while(node != findMin()) {
            if (node.getRank()>max) {
                max = node.getRank();
            }
            node = node.getNext();
        }
        int[] arr = new int[max+1];
        node = getFirst();
        arr[node.getRank()]++;
        while(node.getNext() != getFirst()) {
            node = node.getNext();
            arr[node.getRank()]++;
        }
        return arr; //	 to be replaced by student code
    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     */
    public void delete(HeapNode x) {
        if(x == findMin()) {
            deleteMin();
            return;
        }
        decreaseKey(x,x.getKey()-findMin().getKey()+1);
        deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        if(delta == 0) {
            return;
        }
        x.setKey(x.getKey()-delta);
        if(x == findMin()) {
            return;
        }
        if(x.getParent() != null) {
            if(x.getParent().getKey()<x.getKey()) {
                return;
            }
            cascading_cut(x, x.getParent());
        }
        if(x.getKey()<findMin().getKey()) {
            setMin(x);
        }
    }

    /**
     * public int potential()
     * <p>
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * <p>
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     */
    public int potential() {
        return getNumOfTrees()+(getNumOfMarked()*2);
    }

    /**
     * public static int totalLinks()
     * <p>
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     */
    public static int totalLinks() {
        return links;
    }

    /**
     * public static int totalCuts()
     * <p>
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts() {
        return cuts;
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * <p>
     * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     * <p>
     * ###CRITICAL### : you are NOT allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k) {
        if(k>H.size()) {
            k = H.size();
        }
        if(k == 1) {
            return new int[] {H.findMin().getKey()};
        }
        int[] arr = new int[k];
        int i=0;
        arr[i] = H.findMin().getKey();
        i++;
        HeapNode[] cand = new HeapNode[k];
        int s = 0;
        while(s<k) {
            HeapNode clone = H.findMin().getSon();
            HeapNode min = clone;
            while (clone.getNext() != H.findMin().getSon()) {
                clone = clone.getNext();
                if (s == 0) {
                    if(clone.getKey()<min.getKey()) {
                        min = clone;
                    }
                } else {
                    if(clone.getKey()<min.getKey() && clone.getKey()>cand[s-1].getKey()) {
                        min = clone;
                    }
                }
            }
            cand[s] = min;
            s++;
        }
        while(i<k) {
            HeapNode min = cand[0];
            int index = 0;
            int min_index = 0;
            for (HeapNode node : cand) {
                if (node == null) {
                    break;
                }
                if (node.getKey() < min.getKey()) {
                    min = node;
                    min_index = index;
                }
                index++;
            }
            arr[i] = min.getKey();
            i++;
            if(cand[min_index].getSon() == null) {
                int t = min_index;
                while(t+1<k && cand[t+1] != null) {
                    HeapNode tmp = cand[t+1];
                    cand[t+1] = cand[t];
                    cand[t] = tmp;
                    t++;
                }
                cand[t] = null;
            } else {
                HeapNode child = cand[min_index].getSon();
                HeapNode node = child;
                HeapNode min_child = child;
                while (node.getNext() != child) {
                    node = node.getNext();
                    if (node.getKey() < min_child.getKey()) {
                        min_child = node;
                    }
                }
                if(cand[min_index].getParent() == H.findMin()) {
                    cand[min_index] = min_child;
                } else {
                    node = cand[min_index];
                    while (node.getNext() != cand[min_index]) {
                        node = node.getNext();
                        if (node.getKey() < min_child.getKey()) {
                            min_child = node;
                        }
                    }
                    cand[min_index] = min_child;
                }
            }
        }
        return arr;
    }

    /**
     * public class HeapNode
     * <p>
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in another file.
     */
    public static class HeapNode {
        private int rank;
        private int key;
        private boolean marked;
        private HeapNode next;
        private HeapNode prev;
        private HeapNode parent;
        private HeapNode son;

        public HeapNode(int key) {
            this.key = key;
            this.next = this;
            this.prev = this;
            this.rank = 0;
            this.marked = false;
        }
        public int getRank() {return this.rank;}
        public void setRank(int k) {this.rank = k;}
        public int getKey() {return this.key;}
        public void setKey(int k) {this.key = k;}
        public boolean getMarked() {return this.marked;}
        public void setMarked(boolean x) {this.marked = x;}
        public HeapNode getNext() {return this.next;}
        public void setNext(HeapNode node) {this.next = node;}
        public HeapNode getPrev() {return this.prev;}
        public void setPrev(HeapNode node) {this.prev = node;}
        public HeapNode getParent() {return this.parent;}
        public void setParent(HeapNode node) {this.parent = node;}
        public HeapNode getSon() {return this.son;}
        public void setSon(HeapNode node) {this.son = node;}
        }
}