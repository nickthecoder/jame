/**
 * 
 * Jame is a Java Native Interface (JNI) wrapper around the Simple Direct Media Library (SDL) and
 * its companion libraries SDL_Image, SDL_gfx, SDL_mixer and SDL_ttf.
 * <p>
 * It creates an object oriented view of SDL, and also attempts to round off some of the nastier
 * aspects of SDL. It adds additional blitting modes (taken from PyGame), which allow images with
 * alpha channels to be combined in sensible ways.
 * <p>
 * To use Jame, you first need to initialise the required modules :
 * {@link uk.co.nickthecoder.jame.Audio#init} and {@link uk.co.nickthecoder.jame.Video#init}
 * <p>
 * Then set the video mode : {@link uk.co.nickthecoder.jame.Video#setMode(int, int)}
 * <p>
 * Now load resources : {@link uk.co.nickthecoder.jame.Sound#Sound(String) new Sound(filename)} and
 * {@link uk.co.nickthecoder.jame.Surface#Surface(String) new Surface(filename)}
 * <p>
 * You can also use render {@link uk.co.nickthecoder.jame.TrueTypeFont}s.
 * <p>
 * Lastly, you should set up the main loop, which will poll for events :
 * {@link uk.co.nickthecoder.jame.Events#poll()}
 * <p>
 * Here's a simplistic outline of how to use Jame :
 * <code>
 * <pre>
 * 
 *  // Initialise the sound and video
 *  Audio.init();
 *  Video.init();
 *  Video.setWindowTitle("My Game");
 *  Video.setWindowIcon("icon.bmp");
 *  Video.setMode(800, 600);
 * 
 *  // Load all of your resources
 *  Surface loadedLogo = new Surface( "logo.png" );
 *  Surface logo = loadedLogo.convert();
 *  loadedLogo.free;
 *  loadedLogo = null;
 * 
 *  // This is the main game loop.
 *  while (true) {
 * 
 *      Surface display = Video.getDisplaySurface();
 *      // Draw a background, so that the previous frame is completely removed.
 *      display.fill(new RGBA(0, 0, 0));
 *      
 *      // Draw all of your game objects
 *      logo.blit( display, 200, 100 );      
 *      
 *      // Make the newly drawn frame visible.
 *      Video.flip();
 *      
 *      // Poll all events till there are no more.
 *      Event event = Events.poll();
 *      while (event != null) {
 *           if (event instanceof QuitEvent) {
 *               return;
 *           }
 *           // Handle the other types of events (KeyboardEvent, MouseButtonEvent, MouseMotionEvent).
 * 
 *           // Get the next event.   
 *           event = Events.poll();
 *      }
 *      
 *      // Perform game logic
 *      
 *      // Delay, to limit the frame rate to a given value.
 *      // For example, you may not want your frame rate to exceed 60fps. 
 *  
 *  }
 * 
 * </pre>
 * </code>
 */
package uk.co.nickthecoder.jame;


