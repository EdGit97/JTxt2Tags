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
 * Class to generate target markup tags for a block 
 * markup with extensions for a description list   
 * @author Ed Swaneck
 * @version 1.0
 * @since 09-04-2025
 */
public abstract class DefinitionBlockTagOps extends BlockTagOps {
	
	/**
	 * Constructor
	 * @param tags List of tags associated with a target markup
	 */
	public DefinitionBlockTagOps(String... tags) {
		super(tags);
	}

	/**
	 * Generate a description definition line
	 * @param text Text to be included on the line
	 * @param endTag true to generate an end tag otherwise false
	 * @return A complete definition with any required markup
	 */
	public abstract String generateDescriptionDefinition(String text, boolean endTag);

}
