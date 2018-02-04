package com.abysmal.slae.system;

import com.abysmal.slae.message.Message;

public class Console implements System {

	@Override
	public void handleMessage(Message message) {
        java.lang.System.out.println("[" + message.getMessage() + "] " + message.getData());
	}

}