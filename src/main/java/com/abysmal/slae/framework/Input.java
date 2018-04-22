package com.abysmal.slae.framework;

import com.abysmal.slae.util.datastructure.Queue;
import com.abysmal.slae.util.MouseAction;

import org.joml.Vector2d;
import static org.lwjgl.glfw.GLFW.*;

public class Input {
	
	protected static double xpos = 0, ypos = 0; 
	public static Queue<MouseAction> inputQueue = new Queue<MouseAction>();

	public Input() {
		glfwSetCursorPosCallback(Window.getWindowID(), (w, x, y) -> {
			xpos = x;
			ypos = y;
		});

		glfwSetMouseButtonCallback(Window.getWindowID(), (w, b, a, m) -> {
			inputQueue.add(new MouseAction(w, b, a, m, getMousePos()));
		});
	}
	
	public static Vector2d getMousePos() {
		return new Vector2d(xpos, ypos);
	}
}