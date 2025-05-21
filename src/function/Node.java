package function;

public class Node<E> {
    private Node<E> prev;
    private E data;
    private Node<E> next;


    public Node(Node<E> prev, E data, Node<E> next) {
        this.prev = prev;
        this.data = data;
        this.next = next;
    }


    public void setNext(Node<E> next) {
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public void setData(E data) {
        this.data = data;
    }
}