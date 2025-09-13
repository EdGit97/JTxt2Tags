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

/**
 * Class to generate target markup tags for a block markup  
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-20-2025
 */
public abstract class BlockTagOps extends TagOps {

	/**
	 * Constructor
	 * @param tags List of tags associated with a target markup
	 */
	public BlockTagOps(String... tags) {
		super(tags);
	}
	
	/**
	 * Construct the tags needed at the start of a block 
	 * @param text Text to be included in the block start
	 * @return All text and tags needed to define the start of a block
	 */
	public abstract String blockStartTags(String text);
	
	/**
	 * Construct the tags needed for the body of a block 
	 * @param text Text to be included in the block
	 * @return All text and tags needed to define the interior of a block
	 */
	public abstract String blockItemTags(String text);
	
	/**
	 * Construct the tags needed to close a block 
	 * @return All tags needed to define the end of a block
	 */
	public abstract String blockEndTags();
	
	
}
