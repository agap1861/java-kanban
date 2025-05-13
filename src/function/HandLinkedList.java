package function;

import TypeOfTask.Task;

import java.util.ArrayList;
import java.util.List;

public class HandLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public Node<T> addLast(T element) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(oldTail, element, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.setNext(newNode);
        }

        size++;
        return newNode;
    }

    public void clear() {

        head = null;
        tail = null;
        size = 0;
    }

    public List<T> getDataHistory() {
        Node<T> curHead = head;
        List<T> history = new ArrayList<>();
        if (curHead == null) {
            return history;
        }
        history.add(curHead.getData());
        while (curHead.getNext() != null) {

            curHead = curHead.getNext();
            history.add(curHead.getData());
        }
        return history;
    }

    public void removeNode(Node<T> removeNode) {
        if (removeNode.getNext() == null && removeNode.getPrev() == null) {
            clear();
            return;
        }
        if (removeNode.getPrev() == null) {
            Node<T> newHead = removeNode.getNext();
            head = newHead;
            size--;
            newHead.setPrev(null);
        } else if (removeNode.getNext() == null) {
            Node<T> newTail = removeNode.getPrev();
            tail = newTail;
            size--;
            newTail.setNext(null);
        } else {
            Node<T> prevNode = removeNode.getPrev();
            Node<T> nextNode = removeNode.getNext();
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
            size--;
        }






      /*  if (list.getSize() == 1) {
            list.clear();
            return;
        }
        if (removeNode.getPrev()==null){
            Node<Task> newHead = removeNode.getNext();
            list.setHead(newHead);
            list.setSize(list.getSize()-1);
            newHead.setPrev(null);
            list.getDataHistory().remove(removeNode.getData());

        } else if (removeNode.getNext()==null) {
            Node<Task> newTail = removeNode.getPrev();
            list.setTail(newTail);
            list.setSize(list.getSize()-1);
            newTail.setNext(null);
            list.getDataHistory().remove(removeNode.getData());

        }else {
            Node<Task> prevNode = removeNode.getPrev();
            Node<Task> nextNode = removeNode.getNext();
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
            list.setSize(list.getSize()-1);
            list.getDataHistory().remove(removeNode.getData());
        }*/


    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }


}

