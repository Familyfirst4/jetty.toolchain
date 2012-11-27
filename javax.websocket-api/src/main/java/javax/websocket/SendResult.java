//
//  ========================================================================
//  Copyright (c) 1995-2012 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package javax.websocket;

/**
 * The result of asynchronously sending a web socket message. A SendResult is
 * either ok indicating there was no problem, or is not OK in which case there
 * was a problem and it carries an exception to indicate what the problem was.
 * 
 * @since: DRAFT 002
 */
public class SendResult {
    private Throwable exception;

    /**
     * Construct a SendResult signifying a successful send carrying an no
     * exception.
     */
    public SendResult() {
	this(null);
    }

    /**
     * Construct a SendResult carrying an exception.
     * 
     * @param exception
     *            the exception causing a send failure.
     */
    public SendResult(Throwable exception) {
	this.exception = exception;
    }

    /**
     * The problem sending the message.
     * 
     * @return the problem.
     */
    public Throwable getException() {
	return this.exception;
    }

    /**
     * Determines if this result is ok or not.
     * 
     * @return whether the send was successful or not.
     */
    public boolean isOK() {
	return (exception == null);
    }
}
