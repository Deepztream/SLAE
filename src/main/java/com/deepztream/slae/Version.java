package com.deepztream.slae;

/** Class for getting the current version of SLAE */
public class Version {

    public static final int MAJOR = 0,
                            MINOR = 0,
                            SUPER_MINOR = 1,
                            BUILD = 0;
    public static final boolean SNAPSHOT = false;

    public static String getVersion(){
        return MAJOR + "." + MINOR + "." + SUPER_MINOR + (SNAPSHOT ? " SNAPSHOT" : "");
    }

}