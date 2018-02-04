package com.abysmal.slae;

import com.abysmal.slae.exception.AlreadyInitialisedException;
import com.abysmal.slae.framework.Window;
import com.abysmal.slae.message.MessageBus;
import com.abysmal.slae.system.*;

public class SLAE {

    private static boolean init = false;

    public static void initialise() {
        if (init)
            throw new AlreadyInitialisedException();
        init = true;

        MessageBus.getBus().addSystem(new Console());
        Window.createWindow();
        Window.setRenderCallback((id) -> {
            while(true)
            java.lang.System.out.println("Render me!");
        });
        
        Window.showWindow();
    }
}