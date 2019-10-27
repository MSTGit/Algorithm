package com.xd.Set;

import com.xd.Set.Tree.BinaryTree;
import com.xd.Set.Tree.RBTree;

public class TreeSet<E> implements Set<E> {
    private RBTree<E> tree = new RBTree<>();
    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E element) {
        return tree.contains(element);
    }

    @Override
    public void add(E element) {
        tree.add(element);
    }

    @Override
    public void remove(E element) {
        tree.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        System.out.println(size());
       tree.inOrder(new BinaryTree.Visitor<E>() {
           @Override
           public void visit(E element) {
               visitor.visit(element);
           }
       });
    }
}
