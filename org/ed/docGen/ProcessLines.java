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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ed.docGen.targets.TagSubstitutes;

/**
 * Process a group of txt2tag lines
 * <p>
 *    Example, convert some lines of txt2tag to HTML:
 * </p>
 * <ol>
 *     <li>A list/array of txt2tags formatted lines, <code>data</code></li>
 *     <li><code>ProcessLines lp = new ProcessLines(new HtmlTags());</code></li>
 *     <li><code>String [] output = lp.process(data);</code></li>
 * </ol>
 * <p>
 *     The output array will contain HTML markup that can be further
 *     processed.
 * </p>
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-30-2025
 */
public class ProcessLines extends ProcessLine {
	
	/**
	 * Constructor
	 * @param tags Target markup tag set
	 */
	public ProcessLines(TagSubstitutes tags) {
		super(tags);
	}
	
	/**
	 * Process a list of lines
	 * @param lines The lines of txt2tags markup to process
	 * @return The list of lines formatted in the target markup language 
	 */
	public List<String> process(List<String> lines) {
		List<String> output = new ArrayList<>();
		
		for (String line : lines) {
			output.add(super.process(line));
		}
		
		output.add(super.closeDocument());
		
		return output;
		
	}

	/**
	 * Process an array of lines
	 * @param lines The lines of txt2tags markup to process
	 * @return Array of lines formatted in the target markup language 
	 */
	public String [] process(String [] lines) {
		List<String> output =  process(Arrays.asList(lines));
		
		return output.toArray(new String[output.size()]);
		
	}

}
