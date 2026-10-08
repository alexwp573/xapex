package xapex.base;

import java.util.Arrays;

public class Version {

    private final int version;
    private final Version specificVersion;
    public Version(int[] version){
        if(version.length > 1){
            this.version = version[0];
            this.specificVersion = new Version(Arrays.copyOfRange(version, 1, version.length));
        }
        else{
            this.version = version[0];
            this.specificVersion = null;
        }
    }
    public String toString(){
        if(this.specificVersion == null){return "" + this.version;}
        else{return (this.version + "." + this.specificVersion);}
    }
    public static boolean compare(Version a, Version b){
        return a.toString().compareTo(b.toString()) == 0;
    }

}
