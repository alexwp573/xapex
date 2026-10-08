package xapex.base.data;

import xapex.base.Id;

public class Stack<T> {

    private String name = "";
        public String name(){return this.name;}
    private final Id id = new Id("Id");
        public Id id(){return this.id;}

    private Element<T> top = null;
    private Element<T> bot = null;
    public void add(T element){
        if(this.top == null){
            this.bot = new Element<>(element, null);
            this.top = this.bot;
        }
        else{
            this.top = new Element<>(element, this.top);
        }
    }
    public boolean isEmpty(){return this.top == null;}
    public T check(){if(this.top != null) return this.top.element(); return null;}
    public T get(){
        if(this.top != null){
            T temp = this.top.element();
            this.top = this.top.next();
            return temp;}
        return null;
    }

    public static class Element<T>{
        private final T element;
        public T element(){return this.element;}
        private final Element<T> next;
        public Element<T> next(){return this.next;}
        public Element(T element, Element<T> next){this.element = element; this.next = next;}
    }

    public Stack(T element, String name){this(element); this.name = name;}
    public Stack(T element){this.add(element);}
    public Stack(String name){this.name = name;}
    public Stack(){}

}
