package xapex.base;

import java.time.LocalDateTime;

public final class Id {

    private String name = "none";
    public String name(){return this.name;}
    public final boolean REBUILD;

    private final byte[] DATE = new byte[]{0, 0, 0, 0};
        public byte getDate(int i){return this.DATE[i];}
    private final byte[] TIME = new byte[]{0, 0, 0};
        public byte getTime(int i){return this.TIME[i];}
    private final short[] PROC = new short[]{0, 0, 0};
        public short getProc(int i){return this.PROC[i];}

    public Id(){this(true, true, true);}
    public Id(String name){this(); this.name = name;}
    public Id(String name, boolean date, boolean time, boolean proc){this(date, time, proc); this.name = name;}
    public Id(boolean date, boolean time, boolean proc){
        boolean[] mode = new boolean[]{date, time, proc};
        LocalDateTime ldt = LocalDateTime.now();
        if(mode[0]){
            this.DATE[0] = (byte) (ldt.getYear()/100);
            this.DATE[1] = (byte) (ldt.getYear()%100);
            this.DATE[2] = (byte) (ldt.getMonthValue());
            this.DATE[3] = (byte) (ldt.getDayOfMonth());}
        if(mode[1]){
            this.TIME[0] = (byte) (ldt.getHour());
            this.TIME[1] = (byte) (ldt.getMinute());
            this.TIME[2] = (byte) (ldt.getSecond());}
        if(mode[2]){
            this.PROC[0] = (short) (ldt.getNano()/1000000);
            this.PROC[1] = (short) (ldt.getNano()/1000%1000);
            this.PROC[2] = (short) (ldt.getNano()%1000);}
        this.REBUILD = false;
    }
    private Id(String fromString, boolean x){
        String[] dtp = fromString.split("\\|");
        int i = 0;
        for(String s : dtp[0].split(":")){
            this.DATE[i++] = Byte.parseByte(s);
        }
        i = 0;
        for(String s : dtp[1].split(":")){
            this.DATE[i++] = Byte.parseByte(s);
        }
        i = 0;
        for(String s : dtp[2].split(":")){
            this.DATE[i++] = Byte.parseByte(s);
        }
        this.REBUILD = true;
    }

    public static Id parse(String fromString){return new Id(fromString, true);}

    public String toString(){
        String str = "";
        if(this.DATE[0] < 10) str += "0"; str += this.DATE[0];
        if(this.DATE[1] < 10) str += "0"; str += (this.DATE[1] + ":");
        if(this.DATE[2] < 10) str += "0"; str += (this.DATE[2] + ":");
        if(this.DATE[3] < 10) str += "0"; str += (this.DATE[3] + "|");

        if(this.TIME[0] < 10) str += "0"; str += (this.TIME[0] + ":");
        if(this.TIME[1] < 10) str += "0"; str += (this.TIME[1] + ":");
        if(this.TIME[2] < 10) str += "0"; str += (this.TIME[2] + "|");

        if(this.PROC[0] < 100){if(this.PROC[0] < 10) str += "0"; str += "0";} str += (this.PROC[0] + ":");
        if(this.PROC[1] < 100){if(this.PROC[1] < 10) str += "0"; str += "0";} str += (this.PROC[1] + ":");
        if(this.PROC[2] < 100){if(this.PROC[2] < 10) str += "0"; str += "0";} str += this.PROC[2];
        return str;
    }

    public boolean compare(Id id){
        return this.DATE[0] == id.getDate(0) &&
                this.DATE[1] == id.getDate(1) &&
                this.DATE[2] == id.getDate(2) &&
                this.DATE[3] == id.getDate(3) &&

                this.TIME[0] == id.getTime(0) &&
                this.TIME[1] == id.getTime(1) &&
                this.TIME[2] == id.getTime(2) &&

                this.PROC[0] == id.getProc(0) &&
                this.PROC[1] == id.getProc(1) &&
                this.PROC[2] == id.getProc(2);
    }

    public long toLong(){
        String[] temp = this.toString().split("\\|");
        StringBuilder temp2 = new StringBuilder();
        for(String s : temp)
            temp2.append(s);
        String[] temp3 = temp2.toString().split(":");
        StringBuilder temp4 = new StringBuilder();
        for(String s : temp3)
            temp4.append(s);
        return Long.parseLong(temp4.substring(2));
    }

    public long diff(Id newer){return newer.toLong() - this.toLong();}

}
