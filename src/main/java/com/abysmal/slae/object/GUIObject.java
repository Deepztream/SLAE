package com.abysmal.slae.object;

import java.awt.Color;

import com.abysmal.slae.system.Render.IndexBuffer;
import com.abysmal.slae.system.Render.Shader;
import com.abysmal.slae.system.Render.VertexArray;
import com.abysmal.slae.system.Render.VertexBuffer;
import com.abysmal.slae.system.Render.VertexBufferLayout;

import org.joml.Rectanglef;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class GUIObject {

	private VertexArray va;
	private VertexBuffer vb;
	private VertexBufferLayout vl;
	private IndexBuffer ib;
	private Shader sh;
	private boolean initialised = false;

	private float[] vertexBuffer;
	private String shader;

	public GUIObject(Rectanglef r, Vector3f color, String shader) {
		this(r, new Vector3f[] {  }, shader);
	}

	public GUIObject(Rectanglef r, Color colour, String shader) {
		this(r, new Vector3f[] {
				new Vector3f(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f) }, shader);
	}

	public GUIObject(Rectanglef r, Vector3f[] c, String shader) {
		if (c.length < 4)
			c = new Vector3f[] { c[0], c[0], c[0], c[0] };
		this.vertexBuffer = new float[] { r.minX, r.minY, c[0].x, c[0].y, c[0].z, r.minX, r.maxY, c[1].x, c[1].y,
				c[1].z, r.maxX, r.maxY, c[2].x, c[2].y, c[2].z, r.maxX, r.minY, c[3].x, c[3].y, c[3].z };
		this.shader = shader;
	}

	public void render() {
		if (!initialised)
			init();
		sh.bind();
		va.bind();
		ib.bind();
		GL11.glDrawElements(GL11.GL_TRIANGLES, ib.getDrawCount(), GL11.GL_UNSIGNED_INT, 0);
	}

	public void init() {
		va = new VertexArray();
		vb = new VertexBuffer(vertexBuffer);
		vl = new VertexBufferLayout();
		ib = new IndexBuffer(new int[] { 0, 1, 2, 3, 2, 0 });
		sh = new Shader(shader);

		vl.push(GL11.GL_FLOAT, 2);
		vl.push(GL11.GL_FLOAT, 3);
		va.addBuffer(vb, vl);

		initialised = true;
	}

	public boolean isInitialised() {
		return initialised;
	}

}