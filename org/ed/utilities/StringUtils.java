/*
 * Text-to-Tags API for Java Projects
 * Copyright (C) 2025 Ed Swaneck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * License: https://www.gnu.org/licenses/gpl-3.0.html#license-text 
 */
package org.ed.utilities;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * Utility classes for modifying strings
 * @author Ed Swaneck
 * @version 1.01
 * @since 08-14-2025
 */
public class StringUtils {
	
	/** List of regular expression characters that need escaped */
	public static char [] escChars = { '.', '\\', '[', ']', '{', '}', '(', ')', 
                                       '*', '+', '?', '|', '^', '$', }; 	

	/**
	 * Default constructor
	 */
	public StringUtils() {
		super();
	}
	
	/**
	 * Escape the standard regular expression characters
	 * @param s The string to escape
	 * @return Input sting with all regular expression characters escaped
	 */
	public static String escRegex(String s) {
		String ns = String.valueOf(s);
		
		for (char c : escChars) {
			ns = ns.replaceAll("\\" + c, "\\\\" + c);
		}
		
		return ns;
		
	}
	
	/**
	 * Determine if a string represents a valid URL
	 * @param url The string to test
	 * @return true if the input is a valid URL, otherwise false
	 */
	public static boolean isUrl(String url) {
		boolean isUrl;
		
		try {
			URI.create(url).toURL();
			isUrl = true;
		} 
		catch (MalformedURLException | IllegalArgumentException e) {
			isUrl = false;
		}
		
		return isUrl;
		
	}

}
