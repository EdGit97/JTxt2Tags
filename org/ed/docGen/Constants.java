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
package org.ed.docGen;

/**
 * List of constants used in the generator
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-09-2025
 */
public class Constants {
	
	/** A newline character */
	public static final String newLine = "\n";
	
	/** A tab character */
	public static final String tab = "\t";
	
	/** Minimum length to define a separator line */
	public static final int minSepLen = 20;
	
	/** Table cell alignment values */
	public enum TextAlign {
		
		/** Align the text to the left */
		left, 
		
		/** Align the text to the right */
		right, 
		
		/** Center the text */
		center;
		
	}
	
	/**
	 * Default constructor
	 */
	public Constants() {
		super();
	}

}
