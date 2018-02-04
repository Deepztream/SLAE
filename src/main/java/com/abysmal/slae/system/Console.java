package com.abysmal.slae.system;

import java.util.Arrays;

import com.abysmal.slae.message.Message;

public class Console implements System {

	@Override
	public void handleMessage(Message message) {
		if (message.getData() == null) {
			java.lang.System.out.println("[" + message.getMessage() + "]");
		} else if (message.getData().getClass().isArray()) {
			java.lang.System.out.println(
					"[" + message.getMessage() + "] " + Arrays.toString((Object[]) message.getData()).toString());
		} else {
			java.lang.System.out.println("[" + message.getMessage() + "] " + message.getData().toString());
		}
	}

}