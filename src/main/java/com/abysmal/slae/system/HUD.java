package com.abysmal.slae.system;

import java.util.ArrayList;

import com.abysmal.slae.SLAE;
import com.abysmal.slae.framework.Input;
import com.abysmal.slae.framework.Window;
import com.abysmal.slae.message.Message;
import com.abysmal.slae.object.HUDObject;
import com.abysmal.slae.util.MouseAction;

public class HUD implements System {

	static ArrayList<HUDObject> HUDObjects = new ArrayList<HUDObject>();

	public static void init() {
		new Thread(() -> {
			while (SLAE.isRunning()) {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
				}
				if (Input.inputQueue.hasNext()) {
					MouseAction click = Input.inputQueue.next();
					if (click.window == Window.getWindowID())
						for (HUDObject HUD : HUDObjects)
							if (HUD.inside(click.pos)) {
								HUD.click(click.button, click.action, click.mods);
								break;
							}
				}
			}
		}, "SLAE HUD").start();
	}

	@Override
	public void handleMessage(Message message) {
		switch (message.getMessage().toLowerCase()) {
		case "add hudobject":
			HUDObjects.add((HUDObject) ((Object[]) message.getData())[0]);
			break;
		case "slae init":
			init();
			break;
		}
	}
}