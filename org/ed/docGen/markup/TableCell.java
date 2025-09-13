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

import org.ed.docGen.Constants;

/**
 * Data for a table cell
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-23-2025
 */
public class TableCell {
	
	/** Content of the cell */
	protected String text;
	
	/** Horizontal position of the text in the cell */
	protected Constants.TextAlign align;
	
	/** true if the table/cell should have a border, otherwise false */
	protected boolean hasBorder;
	
	/** Column span */
	protected int colspan;
	
	/**
	 * Constructor
	 * @param text Content of the cell
	 */
	public TableCell(String text) {
		this.text = text;
		this.align = Constants.TextAlign.left;
		this.colspan = 1;
	}

	/**
	 * Constructor
	 * @param text Content of the cell
	 * @param align Horizontal position of the text in the cell
	 */
	public TableCell(String text, Constants.TextAlign align) {
		this.text = text;
		this.align = align;
		this.colspan = 1;
	}

	/**
	 * Getter
	 * @return Content of the cell
	 */
	public String getText() {
		return text;
	}

	/**
	 * Getter
	 * @return Horizontal position of the text in the cell
	 */
	public Constants.TextAlign getAlign() {
		return align;
	}

	/**
	 * Getter
	 * @return The number of columns this cell will span
	 */
	public int getColspan() {
		return colspan;
	}

	/**
	 * Increment the column span counter
	 */
	public void incColspan() {
		this.colspan++;
	}

	/**
	 * Getter
	 * @return true if the cell should have a border, otherwise false
	 */
	public boolean isHasBorder() {
		return hasBorder;
	}

	/**
	 * Setter
	 * @param hasBorder true if the cell should have a border, otherwise false
	 */
	public void setHasBorder(boolean hasBorder) {
		this.hasBorder = hasBorder;
	}
	
}
