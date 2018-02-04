package com.abysmal.slae;

import com.abysmal.slae.exception.AlreadyInitialisedException;
import com.abysmal.slae.framework.Window;
import com.abysmal.slae.message.Message;
import com.abysmal.slae.message.MessageBus;
import com.abysmal.slae.system.*;
import com.abysmal.slae.system.System;

import org.lwjgl.opengl.GL11;

public class SLAE {

	private static boolean init = false;
	private static boolean running = false;

	public static void initialise(System game) {
		if (init)
			throw new AlreadyInitialisedException("SLAE has already been initialised!");
		init = true;

		java.lang.System.out.println("SLAE\t" + Version.getVersion());
		java.lang.System.out.println("LWJGL\t" + org.lwjgl.Version.getVersion());

		MessageBus.getBus().addSystem(game);
		MessageBus.getBus().addSystem(new Console());
		Render.init();
		new Thread(() -> {
			Window.createWindow();
			Window.setRenderCallback((id) -> {
				Render.render();
			});

			running = true;
			java.lang.System.out.println("OpenGL\t" + GL11.glGetString(GL11.GL_VERSION));
			MessageBus.getBus().postMessage(new Message("SLAE Init", null));

			new Thread(() -> {
				while (running) {
					MessageBus.getBus().pushMessage();
				}
			}, "SLAE-messageDispatcher").start();

			Window.showWindow();
			running = false;
		}, "SLAE-openGL").start();
	}

	public static boolean isRunning() {
		return running;
	}
}