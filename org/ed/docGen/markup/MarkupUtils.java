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
package org.ed.docGen.markup;

import org.ed.docGen.targets.TagSubstitutes;

/**
 * Utility classes for processing markup
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-31-2025
 */
public class MarkupUtils {
	
	/**
	 * Constructor
	 */
	public MarkupUtils() {
		super();
	}

	/**
	 * Run all beautifier, image and link operations against the line
	 * @param inLine The line to process
	 * @param resultTags Target markup tag set
	 * @return Output line after beautifiers were run
	 */
	public static String runInlineSubstitutions(String inLine, TagSubstitutes resultTags) {
		String outLine = String.valueOf(inLine);
		
		for (Beautifiers b : Beautifiers.values()) {
			outLine = b.process(outLine, resultTags);
		}
		
		outLine = ImageTypes.process(outLine, resultTags);
		outLine = LinkTypes.process(outLine, resultTags);
		
		return outLine;
		
	}

}
