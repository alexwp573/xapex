package xapex.base;

public enum Status {

    INITIALIZED("INIT", "INITIALIZED"),
    CONFIGURING("CNFG", "CONFIGURING"),

    CONFIGURED("CNFD", "CONFIGURED"),
    LOGGING("LOGG", "LOGGING"),

    LOGGED("LOGD", "LOGGED"),
    PERSONALIZING("PRSG", "PERSONALIZING"),

    PERSONALIZED("PRSD", "PERSONALIZED"),
    RUNNING("RUNG", "RUNNING"),

    SAVING("SAVG", "SAVING"),
    SAVED("SAVD", "SAVED"),

    CLOSING("CLSG", "CLOSING"),
    CLOSED("CLSD", "CLOSED");

    public final String ID;
    public final String NAME;
    Status(String id, String name){this.ID = id; this.NAME = name;}

}
