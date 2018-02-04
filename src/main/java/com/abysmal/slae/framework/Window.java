package com.abysmal.slae.framework;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.abysmal.slae.Configuration;
import com.abysmal.slae.Version;
import com.abysmal.slae.exception.AlreadyInitialisedException;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Checks;

public class Window {

    private long windowID = NULL;
    private int height, width;
    private double aspectRatio = 16.0 / 9.0;
    private String title = "SLAE " + Version.MAJOR + "." + Version.MINOR + "." + Version.SUPER_MINOR;
    private boolean fullscreen = false;
    private RenderCallbackI renderCallback = null;

    private static Window window = new Window();

    private Window() {
        width = Configuration.DEFAULT_WIDTH;
        aspectRatio = Configuration.DEFAULT_ASPECT_RATIO;
        fullscreen = Configuration.DEFAULT_FULLSCREEN;
        height = (int) (width / aspectRatio);
    }

    public static void createWindow() {
        if(window.windowID != NULL){
            throw new AlreadyInitialisedException();
        }

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Failed to initialise GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, Version.getOpenGLMajor());
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, Version.getOpenGLMinor());
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        long monitor = glfwGetPrimaryMonitor();

        if (window.fullscreen) {
            GLFWVidMode vidmode = glfwGetVideoMode(monitor);
            window.width = vidmode.width();
            window.height = vidmode.height();
        }

        window.windowID = glfwCreateWindow(window.width, window.height, window.title,
                window.fullscreen ? monitor : NULL, NULL);

        if (window.windowID == NULL) {
            throw new RuntimeException("Failed to create GLHF window");
        }

        glfwMakeContextCurrent(window.windowID);
        GL.createCapabilities();
    }

    public static void showWindow() {
        glfwShowWindow(window.windowID);
        glfwSwapInterval(Configuration.VSYNC ? 1 : 0);

        window.renderCallback.render(window.windowID);
    }

    public static void setWidth(int width) {
        window.width = width;
    }

    public static void setAspectRatio(double aspectRatio) {
        window.aspectRatio = aspectRatio;
    }

    public static void setFullscreen(boolean fullscreen) {
        window.fullscreen = fullscreen;
    }

    public static void setTitle(String title) {
        window.title = title;
        try {
            Checks.check(window.windowID);
            glfwSetWindowTitle(window.windowID, title);
        } catch (NullPointerException e) {
        }
    }

    public static RenderCallbackI setRenderCallback(RenderCallbackI callback) {
        RenderCallbackI old = window.renderCallback;
        window.renderCallback = callback;
        return old;
    }

    @FunctionalInterface
    public interface RenderCallbackI {
        public void render(long windowID);
    }

}