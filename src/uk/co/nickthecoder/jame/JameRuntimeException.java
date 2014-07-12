/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials are made available under the terms of
 * the GNU Public License v3.0 which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

/**
 * A general purpose Exception used throughout Jame. JameRuntimeExceptions are thrown by methods when an exception is unexpected, such as
 * when blitting an image.
 * 
 * Most JameRuntimeExceptions are thrown when the status code from an SDL method is non-zero, in which case the
 * JameRuntimeException.getMessage() will return the results from SDL's GetError function.
 * 
 * @see JameException
 */
public class JameRuntimeException extends RuntimeException
{
    static final long serialVersionUID = -7698188548272507637L;

    public JameRuntimeException()
    {
        super();
    }

    public JameRuntimeException( Exception e )
    {
        super(e);
    }

    public JameRuntimeException( String message )
    {
        super(message);
    }

}
