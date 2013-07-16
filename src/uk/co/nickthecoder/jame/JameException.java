/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

/**
 * A general purpose Exception used throughout Jame.
 * JameExceptions are thrown when an exception is to be expected, such as loading an image, or parsing a color.
 *
 * Most JameExceptions are thrown when the status code from an SDL method is non-zero, in which case the
 * JameException.getMessage() will return the results from SDL's GetError function.
 * @see JameRuntimeException
 */
public class JameException extends Exception
{
    static final long serialVersionUID = -7698188548272507636L;

    public JameException()
    {
        super();
    }

    public JameException( Exception e )
    {
        super( e );
    }
    
    public JameException( String message )
    {
        super( message );
    }

}
