/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials are made available under the terms of
 * the GNU Public License v3.0 which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.test;

import java.util.ArrayList;
import java.util.List;

import uk.co.nickthecoder.jame.Events;
import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Renderer;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.Texture;
import uk.co.nickthecoder.jame.TrueTypeFont;
import uk.co.nickthecoder.jame.Window;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.ModifierKey;
import uk.co.nickthecoder.jame.event.QuitEvent;

public class TestController implements Test
{
    public Test currentTest;

    public Window window;

    public Renderer renderer;

    /**
     * A surface without alpha channel the same size as the window
     */
    public Surface surface;

    /**
     * A surface WITH an alpha channel the same size as the window
     */
    public Surface alphaSurface;

    /**
     * A texture used to draw onto the whole window using {@link #surface}
     */
    public Texture texture;

    /**
     * A texture used to draw onto the whole window using {@link #alphaSurface}.
     */
    public Texture alphaTexture;

    /**
     * Used to draw the menu items, and may also be used for other purposes.
     */
    public TrueTypeFont smallFont;

    public List<MenuItem> menuItems;

    /**
     * Causes the main loop to end when quitting==true.
     */
    public boolean quitting = false;

    /**
     * Increments each frame, until it reaches {@link #INFO_TICKS}, at which point, statistic are displayed
     * and infoCounter is reset. See {@link #showInfo(TestController)}.
     */
    public int infoCounter;

    /**
     * Limit for {@link #infoCounter}.
     */
    public static final int INFO_TICKS = 100;

    public TestController()
    {
    }

    public void begin(TestController controller)
        throws JameException
    {
        Window.init();
        infoCounter = 0;

        window = new Window("Jame Tests", 640, 480, true, 0);
        renderer = new Renderer(window);

        surface = window.createSurface(640, 480, false);
        texture = window.createTexture(640, 480, false);

        alphaSurface = window.createSurface(640, 480, true);
        alphaTexture = window.createTexture(640, 480, true);

        System.out.println();
        System.out.println(window);
        System.out.println(renderer);
        System.out.println(surface);
        System.out.println(texture);
        System.out.println("Alpha " + alphaSurface);
        System.out.println("Alpha " + alphaTexture);
        System.out.println();

        Surface icon = new Surface("resources/alien.png");
        window.setIcon(icon);
        icon.free();

        smallFont = new TrueTypeFont("resources/Vera.ttf", 16);

        menuItems = new ArrayList<MenuItem>();
        addMenuItem("Texture Empty Rectangles", new TextureRectanglesTest());
        addMenuItem("Texture Filled Rectangles", new TextureFilledRectanglesTest());
        addMenuItem("Surface Filled Rectangles", new SurfaceFilledRectanglesTest());
    }

    public void end(TestController controller)
        throws JameException
    {
        renderer.destroy();
        window.destroy();
    }

    /**
     * Notified by a Test that it has ended (and therefore the menu should be displayed again).
     * 
     * @param test
     */
    public void ended(Test test)
    {
        System.out.println("Ended test : " + test);
        currentTest = this;
    }

    public boolean fullscreen = false;

    public void event(TestController controller, Event event)
        throws JameException
    {
        if (event instanceof KeyboardEvent) {

            KeyboardEvent ke = (KeyboardEvent) event;
            if (ke.isPressed()) {
                int value = ke.symbol;

                for (MenuItem menuItem : menuItems) {
                    if (menuItem.letter == value) {
                        Test test = menuItem.test;

                        this.currentTest = test;
                        test.begin(this);
                        return;
                    }
                }

                if (ke.modifier(ModifierKey.CTRL) && (ke.symbol == 'f')) {
                    fullscreen = !fullscreen;
                    window.setFullScreen(fullscreen ? Window.FULLSCREEN : 0);
                }
            }
        }
    }

    @Override
    public void display(TestController controller)
        throws JameException
    {
        renderer.setDrawColor(RGBA.BLACK);
        renderer.clear();

        for (MenuItem menuItem : menuItems) {
            menuItem.display();
        }

        renderer.present();

    }

    long lastMillis = System.currentTimeMillis();

    public void checkInfo()
    {
        infoCounter++;
        if (infoCounter > INFO_TICKS) {
            infoCounter = 0;
            try {
                currentTest.showInfo(this);
            } catch (JameException e) {
                e.printStackTrace();
            }
        }
    }

    public void showInfo(TestController controller)
    {
        long millis = System.currentTimeMillis() - lastMillis;

        System.out.println("Frames : " + INFO_TICKS + " in " + millis + " = " + INFO_TICKS * 1000 / millis + "fps");
        lastMillis = System.currentTimeMillis();
    }

    public void go()
        throws JameException
    {
        currentTest = this;

        this.begin(this);

        while (!quitting) {
            currentTest.display(this);

            while (true) {
                Event event = Events.poll();
                if (event == null) {
                    break;
                }
                if (event instanceof QuitEvent) {
                    System.out.println("Ok, I'll quit");
                    quitting = true;
                    currentTest.end(this);
                } else {
                    currentTest.event(this, event);
                }

            }

            checkInfo();
        }

        this.end(this);
        System.out.println("Ended");
    }

    public void addMenuItem(String label, Test test)
    {
        int n = menuItems.size();
        MenuItem menuItem = new MenuItem((char) ('a' + n), label, test);
        menuItem.x = 20;
        menuItem.y = 20 + n * 30;

        menuItems.add(menuItem);
    }

    public void showSurface(boolean alpha)
    {
        if (alpha) {
            alphaTexture.update(alphaSurface);
            renderer.setDrawColor(RGBA.BLACK);
            renderer.clear();
            renderer.copy(alphaTexture, 0, 0);
        } else {
            texture.update(surface);
            renderer.copy(texture, 0, 0);
        }
        renderer.present();
    }

    public class MenuItem
    {
        public Texture texture;
        public int x = 0;
        public int y = 0;
        public char letter;
        public Test test;

        public MenuItem(char letter, String label, Test test)
        {
            this.letter = letter;
            this.test = test;

            Surface surface = smallFont.renderBlended(letter + ") " + label, RGBA.WHITE);
            texture = new Texture(renderer, surface);
        }

        public void display()
        {
            renderer.copy(texture, x, y);
        }
    }

    public static void main(String[] argv) throws Exception
    {
        TestController controller = new TestController();
        controller.go();
        System.out.println("End of Main");
    }
}
