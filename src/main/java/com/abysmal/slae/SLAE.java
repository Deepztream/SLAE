package com.abysmal.slae;

import com.abysmal.slae.exception.AlreadyInitialisedException;

public class SLAE {

    private static boolean init = false;

    public static void initialise() {
        if (init)
            throw new AlreadyInitialisedException();

        init = true;
    }
}