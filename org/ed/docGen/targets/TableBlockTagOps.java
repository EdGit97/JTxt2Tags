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

import org.ed.docGen.markup.TableCell;

/**
 * Class to generate target markup tags for a table  
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-27-2025
 */
public abstract class TableBlockTagOps extends BlockTagOps {

	/**
	 * Constructor
	 * @param tags List of tags associated with a target markup
	 */
	public TableBlockTagOps(String... tags) {
		super(tags);
	}
	
	/*
	 * @see org.ed.docGen.targets.BlockTagOps#blockStartTags(java.lang.String)
	 */
	@Override
	public String blockStartTags(String text) {
		return blockStartTags(text, false, false);
	}

	/**
	 * Construct the tags needed at the start of a block 
	 * @param text Text to be included in the block start
	 * @param border true to include a border around the table, otherwise false
	 * @param centered true to center the table on the page, otherwise false
	 * @return All text and tags needed to define the start of a table
	 */
	public abstract String blockStartTags(String text, boolean border, boolean centered);
	
	/*
	 * @see org.ed.docGen.targets.BlockTagOps#blockItemTags(java.lang.String)
	 */
	@Override
	public String blockItemTags(String text) {
		return text;
	}

	/*
	 * @see org.ed.docGen.targets.BlockTagOps#blockEndTags()
	 */
	@Override
	public abstract String blockEndTags();

	/**
	 * Generate table row start/end tags 
	 * @param endTag true to generate an end tag, false to generate a start tag
	 * @return A complete row delimiter tag
	 */
	public abstract String generateRowTags(boolean endTag);
	
	/**
	 * Construct the tags needed for a cell 
	 * @param columns Columns that make up a table row
	 * @return All text and tags needed to define all of the cells of a row
	 */
	public abstract String blockItemTags(TableCell [] columns);
	
}
