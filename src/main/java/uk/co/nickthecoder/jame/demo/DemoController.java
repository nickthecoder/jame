/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials are made available under the terms of
 * the GNU Public License v3.0 which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.demo;

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
import uk.co.nickthecoder.jame.event.EventForWindow;
import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.QuitEvent;
import uk.co.nickthecoder.jame.event.StopPropagation;
import uk.co.nickthecoder.jame.util.KeyboardFilter;

/**
 * Interactive demos.
 * <p>
 * Keys :
 * </p>
 * <ul>
 * <li>ctrl+F = Toggle Fullscreen with mode change</li>
 * <li>ctrl+G = Toggle Fullscreen Desktop</li>
 * <li>1 = Toggle Alpha (only used by some demos).</li>
 * </ul>
 */
public class DemoController implements Demo
{
    public Demo currentDemo;

    public Window window;

    public int refreshRate;

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

    public boolean fullscreen = false;
    /**
     * Increments each frame, until it reaches {@link #INFO_TICKS}, at which point, statistic are displayed
     * and infoCounter is reset. See {@link #showInfo(DemoController)}.
     */
    public int infoCounter;

    /**
     * Limit for {@link #infoCounter}.
     */
    public static final int INFO_TICKS = 100;

    public DemoController()
    {
    }

    @Override
    public void begin(DemoController controller)
        throws JameException
    {
        Window.init();
        infoCounter = 0;

        window = new ControllerWindow();
        refreshRate = window.getRefreshRate();
        renderer = new Renderer(window, Renderer.PRESENTVSYNC);

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

        Surface iconSurface = new Surface("icon.png");
        SizedTexture iconTexture = new SizedTexture(renderer, iconSurface);
        window.setIcon(iconSurface);

        Surface angrySurface = new Surface("resources/face-angry.png");
        SizedTexture angryTexture = new SizedTexture(renderer, angrySurface);

        Surface twinsSurface = new Surface("resources/face-twins.png");
        SizedTexture twinsTexture = new SizedTexture(renderer, twinsSurface);

        Surface laughSurface = new Surface("resources/face-laugh.png");
        SizedTexture laughTexture = new SizedTexture(renderer, laughSurface);

        System.out.println("Icon " + iconSurface);
        System.out.println("Icon " + iconTexture);
        System.out.println("Angry " + angrySurface);
        System.out.println("Angry " + angryTexture);
        System.out.println();

        smallFont = new TrueTypeFont("resources/Vera.ttf", 16);

        menuItems = new ArrayList<MenuItem>();
        addMenuItem("Texture Empty Rectangles", new TextureRectanglesDemo());
        addMenuItem("Texture Filled Rectangles", new TextureFilledRectanglesDemo());
        addMenuItem("Surface Filled Rectangles", new SurfaceFilledRectanglesDemo());

        addMenuItem("Texture Sprites", new SpriteDemo(iconTexture));
        addMenuItem("Surface Sprites", new SpriteDemo(iconSurface));
        addMenuItem("Larger Textures", new SpriteDemo(angryTexture, laughTexture));

        addMenuItem("Texture Clip", new ClippingDemo(5, angryTexture, laughTexture));
        addMenuItem("Surface Clip", new ClippingDemo(5, angrySurface, laughSurface));

        addMenuItem("Rotate & Scale", new RotateAndScaleDemo(10, twinsTexture, angryTexture));

        addMenuItem("Keyboard Events", new KeyboardEventDemo(this));
        addMenuItem("Mouse", new MouseDemo(10, angryTexture, laughTexture));

        addMenuItem("Drop File", new DropDemo(smallFont));
        
        addMenuItem("Text Editing", new TextEditingDemo( renderer, smallFont ));
    }

    @Override
    public void end(DemoController controller)
        throws JameException
    {
        renderer.destroy();
        window.destroy();
    }

    /**
     * Notified by a {@link Demo} that the demo has ended (and therefore the menu should be displayed again).
     * 
     * @param demo
     */
    public void ended(Demo demo)
    {
        System.out.println("Ended demo : " + demo);
        currentDemo = this;
    }

    @Override
    public void event(DemoController controller, Event event)
        throws JameException
    {
        if (event instanceof KeyboardEvent) {

            KeyboardEvent ke = (KeyboardEvent) event;
            int value = ke.symbol;

            if (KeyboardFilter.ctrlPress.accept(ke)) {
                System.out.println("!!! YES ctrl key pressed");
                if (ke.symbol == 'f') {
                    fullscreen = !fullscreen;
                    window.setFullScreen(fullscreen ? Window.FULLSCREEN : 0);
                }
                if (ke.symbol == 'g') {
                    fullscreen = !fullscreen;
                    window.setFullScreen(fullscreen ? Window.FULLSCREEN_DESKTOP : 0);
                }
            }

            if (KeyboardFilter.regularPress.accept(ke)) {
                for (MenuItem menuItem : menuItems) {
                    if (menuItem.letter == value) {
                        Demo demo = menuItem.demo;

                        this.currentDemo = demo;
                        demo.begin(this);
                        return;
                    }
                }
            }

        }
    }

    @Override
    public void display(DemoController controller)
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
                currentDemo.showInfo(this);
            } catch (JameException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showInfo(DemoController controller)
    {
        long millis = System.currentTimeMillis() - lastMillis;
        long fps = INFO_TICKS * 1000 / millis;
        if (fps + 1 < refreshRate) {
            System.out.println("Dropping frames. " + INFO_TICKS * 1000 / millis + "fps");
        }
        lastMillis = System.currentTimeMillis();
    }

    public void go()
        throws JameException
    {
        currentDemo = this;

        this.begin(this);

        while (!quitting) {
            currentDemo.display(this);

            while (true) {
                Event event = Events.poll();
                if (event == null) {
                    break;
                }
                if (event instanceof QuitEvent) {
                    System.out.println("Ok, I'll quit");
                    quitting = true;
                    currentDemo.end(this);
                } else {
                    try {
                        currentDemo.event(this, event);
                    } catch (StopPropagation sp) {
                        // Do nothing.
                    }
                }

            }

            checkInfo();
        }

        this.end(this);
        System.out.println("Ended");
    }

    public void addMenuItem(String label, Demo demo)
    {
        int n = menuItems.size();
        MenuItem menuItem = new MenuItem((char) ('a' + n), label, demo);
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
        public Demo demo;

        public MenuItem(char letter, String label, Demo demo)
        {
            this.letter = letter;
            this.demo = demo;

            Surface surface = smallFont.renderBlended(letter + ") " + label, RGBA.WHITE);
            texture = new Texture(renderer, surface);
        }

        public void display()
        {
            renderer.copy(texture, x, y);
        }
    }

    public class ControllerWindow extends Window
    {

        public ControllerWindow()
        {
            super("Jame Demos", 640, 480, true, 0);
        }

        @Override
        public void onEvent(EventForWindow event)
        {
            // System.out.println("ControllerWindow " + event);
        }
    }

    public static void main(String[] argv) throws Exception
    {
        DemoController controller = new DemoController();
        controller.go();
        System.out.println("End of Main");
    }

}
