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

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for the generation of target tags from markup 
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-20-2025
 */
public abstract class TagOps {
	
	/** List of tags that will be substituted for the markup */
	protected List<String> tagList = new ArrayList<>();
	
	/**
	 * Constructor
	 * @param tags List of tags associated with a target markup
	 */
	public TagOps(String... tags) {
		
		super();
		
		for (String t : tags) {
			tagList.add(t);
		}
		
	}
	
	/**
	 * Getter
	 * @return The list of tags associated with this instance
	 */
	public List<String> getTagList() {
		return tagList;
	}

}
