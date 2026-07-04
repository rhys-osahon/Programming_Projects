package main;

import java.util.ArrayList;

public class BST<T extends Comparable> {
    private BSTNode root;
    private class BSTNode {
        private T value;
        private BSTNode childL;
        private BSTNode childR;
        public BSTNode(T v) {
            value = v;
            childL = null;
            childR = null;
        }
        public BSTNode(T v, BSTNode l, BSTNode r) {
            value = v;
            childL = l;
            childR = r;
        }
        public T getValue() {
            return value;
        }
        public BSTNode getLeft() {
            return childL;
        }
        public BSTNode getRight() {
            return childR;
        }
        public void add(T v) {
            int compareNum = value.compareTo(v);
            if (compareNum > 0) {
                if (childL == null) {
                    childL = new BSTNode(v);
                } else {
                    childL.add(v);
                }
            } else if (compareNum < 0) {
                if (childR == null) {
                    childR = new BSTNode(v);
                } else {
                    childR.add(v);
                }
            }
        }
        public boolean contains(T v) {
            int compareNum = value.compareTo(v);
            if (compareNum == 0) {
                return true;
            } else if (compareNum > 0) {
                if (childL != null) {
                    return childL.contains(v);
                } else {
                    return false;
                }
            } else {
                if (childR != null) {
                    return childR.contains(v);
                } else {
                    return false;
                }
            }
        }
        public ArrayList<T> inOrderList() {
            ArrayList<T> output = new ArrayList<>();
            if (childL != null) {
                for (T val : childL.inOrderList()) {
                    output.add(val);
                }
            }
            output.add(value);
            if (childR != null) {
                for (T val : childR.inOrderList()) {
                    output.add(val);
                }
            }
            return output;
        }
    }
    public BST() {
        root = null;
    }
    public BST(T v) {
        root = new BSTNode(v);
    }
    public void add(T v) {
        if (isEmpty()) {
            root = new BSTNode(v);
        } else {
            root.add(v);
        }
    }
    public boolean contains(T v) {
        return root.contains(v);
    }
    public boolean isEmpty() {
        return root == null;
    }
    public ArrayList<T> inOrderList() {
        if (isEmpty()) {
            return new ArrayList<T>();
        }
        return root.inOrderList();
    }
}
