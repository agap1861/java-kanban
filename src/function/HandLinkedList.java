package function;

import type.of.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;


    public Node<T> addLast(T element) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(oldTail, element, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.setNext(newNode);
        }


        return newNode;
    }

    public void clear() {

        head = null;
        tail = null;

    }

    public List<T> getDataHistory() {
        Node<T> curHead = head;

        if (curHead == null) {
            return List.of();
        }
        List<T> history = new ArrayList<>();
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

            newHead.setPrev(null);
        } else if (removeNode.getNext() == null) {
            Node<T> newTail = removeNode.getPrev();
            tail = newTail;

            newTail.setNext(null);
        } else {
            Node<T> prevNode = removeNode.getPrev();
            Node<T> nextNode = removeNode.getNext();
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);

        }


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

