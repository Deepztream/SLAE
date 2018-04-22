package com.abysmal.slae.object;

import com.abysmal.slae.util.Callback;

import org.joml.Vector2d;
import org.joml.Vector2i;

public class HUDObject {

	Vector2i a, b;
	Callback callback;

	public HUDObject(Vector2i a, Vector2i b, Callback callback) {
		this.a = a;
		this.b = b;
		this.callback = callback;
	}

	public void click() {
		callback.call();
	}

	public boolean inside(Vector2d point) {
		if (point.x > a.x && point.y > a.y)
			if (point.x < b.x && point.y < b.y)
				return true;
		return false;
	}
}