package xapex.base.center.environment.filesystem;

import xapex.Xapex;
import xapex.base.data.List;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class File {

    private final List<Character> SEPARATORS = new List<>();
        public void addSeparator(char c){
            for(char ch : SEPARATORS)
                if(ch == c) return;
            this.SEPARATORS.add(c);
        }
        public void removeSeparator(char c){
            this.SEPARATORS.get(c);
        }


    public static class Cursor{

        private long FULL_LENGTHS;
            public long fullLengths(){return this.FULL_LENGTHS;}
        private long CURSOR;
            public long cursor(){return this.CURSOR;}

        public Cursor(){
            this.reset();
        }

        public void reset() {
            this.FULL_LENGTHS = 0L;
            this.CURSOR = 0L;
        }

        public Cursor move(Long l){
            if(Long.MAX_VALUE - this.CURSOR >= l){
                this.FULL_LENGTHS++;
                this.CURSOR = l - (Long.MAX_VALUE - this.CURSOR);
            }
            return this;
        }

        public Cursor move(Cursor c){
            this.FULL_LENGTHS += c.fullLengths();
            this.move(c.cursor());
            return this;
        }
    }

    public final String PATH;
        public String path(){return this.PATH;}
    public final String FILE;
        public String file(){return this.FILE;}
    public final String NAME;
        public String name(){return this.NAME;}

    private final Cursor CHARACTER_CURSOR = new Cursor();
        private final Cursor CC = this.CHARACTER_CURSOR;
    private final Cursor TOKEN_CURSOR = new Cursor();
        private final Cursor TC = this.TOKEN_CURSOR;
    private final Cursor LINE_CURSOR = new Cursor();
        private final Cursor LC = this.LINE_CURSOR;

    public File(String path, String fileName, String name){
        this.PATH = path;
        this.FILE = fileName;
        this.NAME = name;
    }

    private String constructFullPath(String filePath, String fileName){
        return filePath + Xapex.ENVIRONMENT_CENTER.FILESYSTEM_MANAGER.fileSeparator() + fileName;
    }

    public File clear() {
        try {
            Files.writeString(Path.of(this.constructFullPath(this.PATH, this.FILE)), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public File write(String data){
        try {
            Files.writeString(Path.of(this.constructFullPath(this.PATH, this.FILE)), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public File append(String data){
        try {
            Files.writeString(Path.of(this.constructFullPath(this.PATH, this.FILE)), data, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public File appendLine(String data) {
        return this.append("\n").append(data);
    }
    public String readFile() throws IOException{
        return Files.readString(Path.of(this.constructFullPath(this.PATH, this.FILE)));
    }
    public String[] readLines(){
        try {
            Object[] temp = Files.readAllLines(Path.of(this.constructFullPath(this.PATH, this.FILE))).toArray();
            String[] temp2 = new String[temp.length];
            for(int i = 0; i < temp.length; i++){
                temp2[i] = (String)(temp[i]);
            }
            return temp2;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] readTokens() throws IOException{
        List<String> temp = new List<>();
        try(Scanner scanner = new Scanner(new java.io.File(this.constructFullPath(this.PATH, this.FILE)))) {
            ;
            while(scanner.hasNext()){
                temp.add(scanner.next());
            }
            return (String[])(temp.stream().toArray());
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }
    public Character[] readChars() throws IOException{
        List<String> temp = new List<>();
        List<Character> temp2 = new List<>();
        try(Scanner scanner = new Scanner(new java.io.File(this.constructFullPath(this.PATH, this.FILE)))) {
            while(scanner.hasNextLine()){
                temp.add(scanner.next());
            }
            for(String s : temp){
                char[] splitedS = s.toCharArray();
                for(char c : splitedS){
                    temp2.add(c);
                }
            }
            return (Character[])(temp2.stream().toArray());
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }

}
