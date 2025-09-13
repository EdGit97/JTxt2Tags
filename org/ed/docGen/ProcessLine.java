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
 * Process a line of text as txt2tags
 * <p>
 *    Example, convert a line of txt2tag to HTML:
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
 * @since 08-09-2025
 */
public class ProcessLine {
	
	/** Current processing status  */
	protected ProcessStatus status;
	
	/**
	 * Constructor
	 * @param tags Target markup tag set
	 */
	public ProcessLine(TagSubstitutes tags) {
		status = new ProcessStatus();
		status.setTargetTags(tags);
	}
	
	/**
	 * Process a line of the text
	 * @param inLine The line to process
	 * @return The processed line
	 */
	public String process(String inLine) {
		StringBuilder outputBuffer = new StringBuilder();
		
		status.setContinuation(true);
		
		do {
			if (status.getMode() == null) {
				status.determineMode(inLine);
				status.setContinuation(false);
				
			}

			status.getMode().process(inLine, status);
			
			if (status.getReprocess()) {
				outputBuffer.append(status.getOutLine());
			}
			
		} while (status.getReprocess());

		outputBuffer.append(status.getOutLine());
		
		return outputBuffer.toString();
		
	}
	
	/**
	 * Close out the document if anything is still open
	 * @return Any end tags required to close out the document
	 */
	public String closeDocument() {
		String outputBuffer = "";
		
		if (status.getMode() != null) {
			outputBuffer = status.runEndBlockOp();
		}
		
		return outputBuffer;
		
	}
	
	/**
	 * Getter
	 * @return Current processing status
	 */
	public ProcessStatus getStatus() {
		return status;
	}
	
}
