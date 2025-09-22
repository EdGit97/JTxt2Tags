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

import org.ed.docGen.targets.TagSubstitutes;

/**
 * Process one or more lines of text as txt2tags
 * <p>
 *    Example, convert txt2tag text to HTML:
 * </p>
 * <ol>
 *     <li>A string formatted as txt2tags, <code>data</code></li>
 *     <li><code>ProcessLine lp = new ProcessLine(new HtmlTags());</code></li>
 *     <li><code>String output = lp.process(data);</code></li>
 * </ol>
 * <p>
 *     output will contain HTML markup that can be further processed.
 * </p>
 * @author Ed Swaneck
 * @version 1.0
 * @since 09-19-2025
 */
public class ProcessText {
	
	/** The line processor to process an individual line */
	protected ProcessLine lineProcessor;
	
	/**
	 * Constructor
	 * @param tags Target markup tag set
	 */
	public ProcessText(TagSubstitutes tags) {
		lineProcessor = new ProcessLine(tags); 
	}
	
	/**
	 * Process some text
	 * @param text Text containing txt2tags markup to process
	 * @return The text formatted in the target markup language 
	 */
	public String process(String text) {
		String [] lines = text == null ? new String[0] : text.split(Constants.newLine);
		StringBuilder output = new StringBuilder();
		
		for (String line : lines) {
			String result = lineProcessor.process(line);
			
			output.append(result);
			
			if (!result.endsWith(Constants.newLine)) {
				output.append(Constants.newLine);
			}
			
		}
		
		output.append(lineProcessor.closeDocument());
		
		return output.toString();
		
	}

}
