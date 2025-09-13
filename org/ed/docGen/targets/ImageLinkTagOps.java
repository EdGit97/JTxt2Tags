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

import org.ed.docGen.markup.ImageLinkData;

/**
 * Class to generate target markup tags for image and link markup  
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-28-2025
 */
public abstract class ImageLinkTagOps extends TagOps {

	/**
	 * Constructor
	 * @param tags List of tags associated with a target markup
	 */
	public ImageLinkTagOps(String... tags) {
		super(tags);
	}
	
	/**
	 * Construct the tags needed for an image 
	 * @param data Data needed to construct the tag
	 * @return All text and tags needed to define the image
	 */
	public abstract String itemTags(ImageLinkData data);
	
	/**
	 * Check a potential markup to see if it has already been processed 
	 * @param potentialMarkup The data to evaluate
	 * @return true if the potential markup starts with a start target tag
	 */
	public abstract boolean alreadyProcessed(String potentialMarkup);
	
}
