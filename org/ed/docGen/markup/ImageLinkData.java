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
 * Image data that will be passed to the tag renderer
 * @author Ed Swaneck
 * @version 1.0
 * @since 09-07-2025
 */
public class ImageLinkData {
	
	/** File specification of the image */
	protected String fileSpec;
	
	/** Horizontal alignment of the image */
	protected Constants.TextAlign align;
	
	/** Label for a link */
	protected String label;
	
	/**
	 * Constructor
	 * @param fileSpec File specification of the image
	 * @param align Horizontal alignment of the image
	 */
	public ImageLinkData(String fileSpec, Constants.TextAlign align) {
		this.fileSpec = fileSpec;
		this.align = align;
		this.label = "";
	}

	/**
	 * Constructor
	 * @param fileSpec File specification of the image
	 * @param align Horizontal alignment of the image
	 * @param label Label for a link 
	 */
	public ImageLinkData(String fileSpec, Constants.TextAlign align, String label) {
		this.fileSpec = fileSpec;
		this.align = align;
		this.label = label;
	}

	/**
	 * Getter
	 * @return File specification of the image
	 */
	public String getFileSpec() {
		return fileSpec;
	}

	/**
	 * Getter
	 * @return Horizontal alignment of the image
	 */
	public Constants.TextAlign getAlign() {
		return align;
	}

	/**
	 * Getter
	 * @return Label for a link
	 */
	public String getLabel() {
		return label;
	}
	
}
