package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.*;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.MouseButtonEvent;
import uk.co.nickthecoder.jame.event.MouseMotionEvent;
import uk.co.nickthecoder.jame.event.QuitEvent;

public class TestVideo {
	public static void main(String[] argv) throws Exception {
		Video.init();
		Video.setWindowTitle("Jame TestVideo");
		Video.setWindowIcon("icon.bmp");

		Surface screen = Video.setMode(640, 480);

		System.out.println("Screen is " + screen.getWidth() + " by "
				+ screen.getHeight());
		screen.fill(new Rect(100, 100, 300, 300), 123456789);
		screen.fill(new Rect(50, 50, 200, 200), new RGBA(255, 0, 0));
		screen.fill(new Rect(250, 75, 200, 300), new RGBA(0, 0, 255, 128));
		// Note the alpha will be ignored, as screen does not have an alpha
		// channel

		Surface alien = new Surface("resources/alien.png");
		Surface convertedAlien = alien.convert();

		System.out.println("Alien is " + alien.getWidth() + " by "
				+ alien.getHeight());
		alien.blit(screen, 40, 70);
		alien.blit(new Rect(0, 0, 20, 20), screen, new Rect(80, 200, 20, 20));
		alien.blit(new Rect(0, 20, 38, 18), screen, new Rect(80, 240, 38, 18));
		convertedAlien.blit(new Rect(20, 0, 18, 38), screen, new Rect(130, 200,
				18, 38));

		alien.blit(screen, -10, 70);

		// Tests SDL's setAlpha
		// These methods will do what you'd expect for an RGBA surface.
		alien.setAlphaEnabled(false);
		alien.blit(screen, 100, 150);

		alien.setAlphaEnabled(true);
		alien.blit(screen, 140, 150);

		RGBA red = new RGBA(255, 0, 0);
		RGBA halfRed = new RGBA(255, 0, 0, 128);
		RGBA blue = new RGBA(0, 0, 255);

		Surface line = new Surface(10, 90, false);
		line.fill(new RGBA(255, 0, 0, 0));
		line.blit(screen, 400, 10);

		Surface solidBorder = new Surface(100, 100, false);
		solidBorder.fill(new Rect(0, 0, 100, 10), red);
		solidBorder.fill(new Rect(0, 90, 100, 10), red);
		solidBorder.fill(new Rect(0, 0, 10, 100), red);
		solidBorder.fill(new Rect(90, 0, 10, 100), red);
		solidBorder.blit(screen, 290, 290);

		Surface solidBorderTurn = solidBorder.rotoZoom(30, 1, true);
		solidBorderTurn.blit(screen, 290, 340);

		Surface border = new Surface(100, 100, true);
		border.fill(new Rect(0, 0, 100, 10), halfRed);
		border.fill(new Rect(0, 90, 100, 10), halfRed);
		border.fill(new Rect(0, 0, 10, 100), halfRed);
		border.fill(new Rect(90, 0, 10, 100), halfRed);
		border.blit(screen, 350, 350);

		Surface borderTurn = border.rotoZoom(30, 1, true);
		borderTurn.blit(screen, 350, 400);

		TrueTypeFont font = new TrueTypeFont("resources/Vera.ttf", 32);
		Surface helloSolid = font.renderSolid("Hello Solid", red);
		helloSolid.blit(screen, 430, 0);

		Surface helloShaded = font.renderShaded("Hello Shaded", red, blue);
		helloShaded.blit(screen, 430, 40);

		Surface helloBlended = font.renderBlended("Hello Blended", red);
		helloBlended.blit(screen, 430, 80);

		// Tests SDL's setAlpha for RGB surfaces (RGBA was done above with the
		// alien).
		helloShaded.setPerSurfaceAlpha(128);
		helloShaded.blit(screen, 430, 120);

		helloShaded.setPerSurfaceAlpha(64);
		helloShaded.blit(screen, 430, 160);

		helloShaded.setPerSurfaceAlpha( 255 );
		helloShaded.blit(screen, 430, 200);

		System.out.println("Testing overlapping");
		Surface square = new Surface(10, 10, true);
		square.fill(red);
		alien.blit(screen, 350, 200);
		System.out.println("Testing overlapping");

		// Test overlapping. Draw squares all around the alien, except where it
		// touches
		for (int y = -50; y < 50; y++) {
			for (int x = -50; x < 50; x++) {
				// System.out.println( "Testing x " + x );
				if (!square.overlaps(alien, x, y, 64)) {
					square.blit(screen, 350 + x, 200 + y);
				}
			}
		}

		// Test the calls to pygames blitter
		Rect srcRect = new Rect(0, 0, alien.getWidth(), alien.getHeight());
		Surface alien2 = new Surface(50, 50, true);
		alien.blit(srcRect, alien2, 4,4, Surface.BlendMode.COMPOSITE );
		alien.blit(srcRect, alien2, 4,4, Surface.BlendMode.COMPOSITE);
		alien2.blit(screen, 0, 200);

		screen.flip();

		Events.enableKeyTranslation(true);

		while (true) {
			Event event = Events.poll();
			if (event == null) {
				continue;
			}
			if (event instanceof QuitEvent) {
				System.out.println("Ok, I'll quit");
				break;
			}
			if (event instanceof KeyboardEvent) {
				System.out.println("Keyboard event : " + event);
			}
			if (event instanceof MouseButtonEvent) {
				System.out.println("Mouse Button event : " + event);
			}
			if (event instanceof MouseMotionEvent) {
				System.out.println("Mouse Motion event : " + event);
			}

			Thread.sleep(20);
		}
	}
}
