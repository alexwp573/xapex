package xapex.base.data;

import xapex.base.Id;

public class Chain<T> {

    private String name = "";
        public String name(){return this.name;}
    private final Id id = new Id("Id");
        public Id id(){return this.id;}

    private Tie<T> head = null;
    private Tie<T> last = null;
    public void add(T element){
        if(this.head != null) this.last.add(element);
        else{
            this.head = new Tie<>(element);
            this.last = this.head;
        }
    }
    public boolean isEmpty(){return this.head == null;}
    public T check(){if(this.head != null) return this.head.element(); return null;}
    public T get(){
        if(this.head != null){
            T temp = this.head.element();
            this.head = this.head.next();
            return temp;}
        return null;
    }

    public static class Tie<T>{
        private final T element;
            public T element(){return this.element;}
        private Tie<T> next = null;
            public Tie<T> next(){return this.next;}
        public Tie<T> add(T element){
            this.next = new Tie<>(element);
            return this.next;
        }
        public Tie(T element){this.element = element;}
    }

    public Chain(T element, String name){this(element); this.name = name;}
    public Chain(T element){this.head = new Tie<>(element); this.last = this.head;}
    public Chain(String name){this.name = name;}
    public Chain(){}
}
