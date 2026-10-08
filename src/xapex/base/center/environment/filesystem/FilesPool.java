package xapex.base.center.environment.filesystem;

import xapex.Xapex;
import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.environment.logs.Debugger;
import xapex.base.center.environment.logs.Logger;
import xapex.base.data.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class FilesPool {

    public final String NAME;
    public final List<File> FILES = new List<>();
        public String[] files(){
            return (String[]) (this.FILES.stream()
                    .map(f -> f.NAME)
                    .toArray());
        }

    public FilesPool(String name){
        this.NAME = name;
    }

    private String constructFullPath(String filePath, String fileName){
        return filePath + Xapex.ENVIRONMENT_CENTER.FILESYSTEM_MANAGER.fileSeparator() + fileName;
    }

    public boolean fileExists(String filePath, String fileName){
        return Files.exists(Path.of(constructFullPath(filePath, fileName)));
    }
    public boolean isOpened(String name){
        for(File f : this.FILES)
            if(f.NAME.compareTo(name) == 0)
                return true;
        return false;
    }

    public File getOpenAndCreateFile(String name, String fileName, String filePath){
        try{return this.file(name);}
        catch (NoSuchElementException noSuchElementException){
            try{return this.openFile(filePath, fileName, name);}
            catch (ConfigurationManager.NameOccupiedException | NoSuchElementException e) {
                try{return this.createFile(filePath, fileName, name);}
                catch(Exception exception){throw new RuntimeException(exception);}
            }
        }
    }

    public File createFile(String filePath, String fileName) throws ConfigurationManager.NameOccupiedException, IOException {
        return this.createFile(filePath, fileName, fileName);
    }
    public File createFile(String filePath, String fileName, String name) throws ConfigurationManager.NameOccupiedException, IOException {
        try{this.file(name);}
        catch(NoSuchElementException noSuchElementException){
            if(!this.fileExists(filePath, fileName)){
                Files.createFile(Path.of(this.constructFullPath(filePath, fileName)));
                return this.openFile(filePath, fileName, name);
            }
        }
        throw new ConfigurationManager.NameOccupiedException(fileName);
    }

    public String[] openFiles(String filesPath) throws NoSuchElementException, ConfigurationManager.NameOccupiedException, IOException {
        Object[] fileNames;
        try (Stream<Path> stream = Files.list(Path.of(filesPath))) {

            fileNames = (stream
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .toArray());
        } catch (Exception e) {throw new IOException(e);}
        for(Object s : fileNames){
            for(File f : this.FILES){
                if(((String)(s)).compareTo(f.NAME) == 0){
                    throw new ConfigurationManager.NameOccupiedException(((String)(s)));
                }
            }
        }
        for(Object o : fileNames)
            this.openFile(filesPath, ((String)(o)));
        String[] fileNamesToReturn = new String[fileNames.length];
        for(int i = 0; i < fileNames.length; i++) fileNamesToReturn[i] = (String) fileNames[i];
        return (String[]) fileNamesToReturn;
    }
    public File openFile(String filePath, String fileName) throws ConfigurationManager.NameOccupiedException {
        try{
            this.file(fileName);
            throw new ConfigurationManager.NameOccupiedException(fileName);
        }
        catch(NoSuchElementException nsee){
            return this.openFile(filePath, fileName, fileName);
        }
    }
    public File openFile(String filePath, String fileName, String name) throws ConfigurationManager.NameOccupiedException {
        try{
            this.file(name);
            throw new ConfigurationManager.NameOccupiedException(name);
        }
        catch(NoSuchElementException nsee){
            if(this.fileExists(filePath, fileName)){
                File temp = new File(filePath, fileName, name);
                this.FILES.add(temp);
                return temp;}
            else throw new NoSuchElementException(this.constructFullPath(filePath, fileName));
        }
    }

    public File file(String name) throws NoSuchElementException {
        for(File f : this.FILES)
            if(f.NAME.compareTo(name) == 0) return f;
        throw new NoSuchElementException(name);
    }

    public File clearFile(String name) throws NoSuchElementException {
        return this.file(name).clear();
    }
    public File writeToFile(String name, String text) throws NoSuchElementException{
        return this.file(name).write(text);
    }
    public File appendToFile(String name, String text) throws NoSuchElementException{
        return this.file(name).append(text);
    }
    public String readFile(String name) throws IOException, NoSuchElementException{
        return this.file(name).readFile();
    }
    public String[] readLinesFromFile(String name) throws IOException, NoSuchElementException{
        return this.file(name).readLines();
    }
    public String[] readTokensFromFile(String name) throws IOException, NoSuchElementException{
        return this.file(name).readTokens();
    }
    public Character[] readCharactersFromFile(String name) throws IOException, NoSuchElementException{
        return this.file(name).readChars();
    }

    public void closeFile(String name){
        for(File f : this.FILES)
            if(f.NAME.compareTo(name) == 0)
                this.FILES.get(f);
    }

}
