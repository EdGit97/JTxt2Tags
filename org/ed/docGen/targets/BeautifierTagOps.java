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
package org.ed.docGen.targets;

import org.ed.docGen.markup.Beautifiers;

/**
 * Class to generate target markup tags for beautifiers  
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-20-2025
 */
public abstract class BeautifierTagOps extends TagOps {

	/**
	 * Constructor
	 * @param tags List of tags associated with a target markup
	 */
	public BeautifierTagOps(String... tags) {
		super(tags);
	}
	
	/**
	 * Construct the tags needed for a beautifier 
	 * @param beautifier The beautifier to be replaced 
	 * @param text Text to be included in the block
	 * @return All text and tags needed to define the interior of a beautified section
	 */
	public abstract String itemTags(Beautifiers beautifier, String text);
	
}
