package xapex.base.data;

import xapex.base.Id;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class List<T> implements Iterable<T> {

    private class ListElement implements Iterator<T> {

        private int currentIndex = 0;

        public boolean hasNext(){return this.currentIndex < size;}
        public T next(){
            if(this.hasNext()) return ((T)(data[this.currentIndex++]));
            throw new NoSuchElementException();}

    }

    private String name = null;
        public String name(){return this.name;}
    private final Id id = new Id("ID");
        public Id id(){return this.id;}

    // ---------------------------
    // PRIVATE OPERATIONS & FIELDS
    private int size = 0; // count of indexes from 0 to index of last element
    private int count = 0; // count of all elements
    private void recalculate(){this.size(); this.count();} // calculates size and count

    private Object[] data = new Object[1];
    public Object[] getData(){return this.data;}
    private void resize(){ // doubles capacity for elements
        Object[] temp = new Object[this.data.length * 2];
        System.arraycopy(this.data, 0, temp, 0, this.data.length);
        for(int i = this.size + 1; i < temp.length; i++) temp[i] = null;
        this.data = temp;
    }
    private void checkCapacity(){ // checks capacity of elements for one extra, resizes if needed
        if(this.size >= this.data.length) this.resize();
    }
    private void moveUp(int startIndex, int endIndex){ // moves all elements for next index starting from given index (int startIndex) ending at (int endIndex)
        this.checkCapacity();
        if(startIndex < endIndex &&
                startIndex >= 0 &&
                endIndex <= this.size &&
                this.data[endIndex] == null){
            for(int i = endIndex; i > startIndex; i--) this.data[i] = this.data[i-1];
            this.data[startIndex] = null;
            this.recalculate();
        }
    }
    private void moveDown(int startIndex, int endIndex){ // moves all elements for prev index starting from given index (int startIndex) ending at (int endIndex)
        if(startIndex < endIndex &&
                startIndex >= 0 &&
                endIndex <= this.size &&
                this.data[startIndex] == null){
            for(int i = startIndex; i < endIndex; i++) this.data[i] = this.data[i+1];
            this.data[endIndex] = null;
            this.recalculate();
        }
    }

    // -----------------
    // PUBLIC OPERATIONS
    // ------------
    // CONSTRUCTORS
    // - implementation:
    // 		DataStruct.Table<T> VAR_NAME = new DataStruct().new Table<>();
    public List(){ // creates object with-out any element
        this.data[0] = null;}
    public List(String name){ // creates object with-out any element + name
        this(); this.name = name;}
    public List(T element){ // creates object with one element (T element)
        this.data[0] = element; this.recalculate();}
    public List(T element, String name){ // creates object with one element (T element) + name
        this(element); this.name = name;}

    // ------
    // ADDERS
    public void insert(T element){ // inserts element (T element) at index 0
        this.checkCapacity();
        this.moveUp(0, this.size);
        this.data[0] = element;
        this.recalculate();
    }
    public void add(T element){ // adds element (T element) at the end
        this.checkCapacity();
        this.data[this.size] = element;
        this.recalculate();
    }
    public void push(T element, int index){ // adds element (T element) at index (int index)
        this.checkCapacity();
        if(index < this.size) this.moveUp(index, this.size);
        this.data[index] = element;
        this.recalculate();
    }
    public boolean put(T element, int index){ // adds element (T element) at index (int index) if that index is null. Returns true when index is null and false when it's not null
        this.checkCapacity();
        if(this.data[index] == null){
            this.data[index] = element;
            this.recalculate();
            return true;
        } return false;
    }
    public int fill(T element){ // finds first appearance of null and puts element (T element) there
        this.checkCapacity();
        for(int i = 0; i < this.data.length; i++) if(this.data[i] == null){this.data[i] = element; this.recalculate(); return i;}
        return -1;
    }

    // --------
    // REMOVERS
    public T remove(){ // returns and removes first element
        Object elementToReturn = this.data[0];
        this.data[0] = null;
        this.recalculate();
        return ((T)(elementToReturn));
    }
    public T take(){ // returns and removes last element
        Object elementToReturn = this.data[this.size-1];
        this.data[this.size-1] = null;
        this.recalculate();
        return ((T)(elementToReturn));
    }
    public T pull(int index){ // returns and removes element from index (int index) and pulls all elements from bigger indexes down
        Object elementToReturn = this.data[index];
        this.data[index] = null;
        this.moveDown(index, this.size-1);
        this.recalculate();
        return ((T)(elementToReturn));
    }
    public T pull(T element){ // returns and removes element and pulls all elements from bigger indexes down
        Object elementToReturn = null;
        for(int i = 0; i < this.size(); i++){
            if(this.data[i] == (Object)(element)){
                elementToReturn = this.data[i];
                this.data[i] = null;
                this.moveDown(i, this.size-1);
            }
        }
        this.recalculate();
        return ((T)(elementToReturn));
    }
    public T pull(){ // returns and removes element from index 0 and pulls all elements from bigger indexes down
        Object elementToReturn = this.data[0];
        this.data[0] = null;
        this.moveDown(0, this.size-1);
        this.recalculate();
        return ((T)(elementToReturn));
    }
    public T get(int index){ // returns and removes element from index (int index) and puts null at index (int index)
        Object elementToReturn = this.data[index];
        this.data[index] = null;
        this.recalculate();
        return ((T)(elementToReturn));
    }
    public T get(T element){ // returns and removes element and puts null at index of that element
        Object elementToReturn = null;
        for(int i = 0; i > this.size(); i++) if(this.data[i] == element){elementToReturn = this.data[i]; this.data[i] = null;};
        this.recalculate();
        return ((T)(elementToReturn));
    }
    public void erase(){
        Arrays.fill(this.data, null);
    }

    // ------------
    // MODIFICATORS
    public T replace(T element, int index){ // returns element from index (int index) and changes it for (T element)
        if(this.data[index] != null) this.count++;
        if(index >= this.size) this.size = index;
        Object elementToReturn = this.data[index];
        this.data[index] = element;
        this.recalculate();
        return ((T)(elementToReturn));
    }
    public void replace(int index1, int index2){ // changes elements on two indexes (int index1, int index2)
        Object elementHolder = this.data[index1];
        this.data[index1] = this.data[index2];
        this.data[index2] = elementHolder;
        this.recalculate();
    }
    public int squeeze(){ // returns count of nulls between element 0 and last element, pulls all elements down and puts nulls on last indexes
        int nullsCount = 0;
        for(int i = 0; i < this.size; i++)if(this.data[i] == null){nullsCount++; this.moveDown(i, this.size);}
        this.recalculate();
        return nullsCount;
    }
    public void move(int index1, int index2){ // moves element from index (int index1) to index (int index2) and pushes all other elements between them up/down
        Object elementHolder = this.get(index1);
        if(index1 < index2) this.moveDown(index1, index2);
        else if(index1 > index2) this.moveUp(index2, index1);
        this.data[index2] = elementHolder;
        this.recalculate();
    }
    public int trim(){ // resizes down the elements array to size --- not finished
        return 0;
    }

    // --------
    // CHECKERS
    public T check(int index){ // returns element at index (int index)
        return ((T)(this.data[index]));
    }
    public T checkLast(){ // returns last element
        return ((T)(this.data[this.size-1]));
    }
    public String toString(){ // {test on String elements} returns all elements as a string
        StringBuilder stringToPrint = new StringBuilder("Elements count: " + this.count + "\t\tSize: " + this.size + "\t\tCapacity: " + this.data.length + "\n");
        for(int i = 0; i < this.data.length; i++){
            stringToPrint.append("Element index: ").append(i).append("\tElement value: ").append(this.data[i]).append("\n");
        }
        return stringToPrint.toString();
    }
    public int size(){ // calculates, saves and returns count of indexes from 0 to index of last element
        int tempSize = 0;
        for(int i = 0; i < this.data.length; i++) if(this.data[i] != null) tempSize = i+1;
        this.size = tempSize;
        return this.size;
    }
    public int count(){ // calculates, saves and returns count of all elements
        int tempCount = 0;
        for (Object datum : this.data) if (datum != null) tempCount++;
        this.count = tempCount;
        return this.count;
    }

    // -----
    // OTHER
    public Iterator<T> iterator(){return new ListElement();}
    //public void sort(Comparator<T> comparator){Arrays.sort(this.data, 0, this.size, comparator);}
    public Stream<T> stream() {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

}
