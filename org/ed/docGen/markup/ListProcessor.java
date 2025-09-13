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

import org.ed.docGen.ProcessStatus;
import org.ed.utilities.StringUtils;

/**
 * Process a line of a list
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-31-2025
 */
public class ListProcessor {

	/**
	 * Constructor
	 */
	public ListProcessor() {
		super();
	}

	/**
	 * Process a list
	 * @param inLine The current line to be processed
	 * @param status Current status
	 * @param markup List type
	 */
	public void processList(String inLine, ProcessStatus status, Markup markup) {
		
		if (status.isContinuation()) {
			if (inLine.trim().length() <= 0) {
				status.setBlankLineCount(status.getBlankLineCount() + 1);
				status.setOutLine("");
				
				if (status.getBlankLineCount() > 1) {
					closeEntireList(status, markup);					
					status.setBlankLineCount(0);
					status.setMode(null);
				}
				
				status.setReprocess(false);
				
			}
			else if (inLine.trim().length() == 1) {
				status.setOutLine(status.runEndBlockOp());
				
				if (status.getDepth() > 0) {
					status.setMode(status.popDepth());
				}
				else {
					status.setMode(null);
				}
				
				status.setBlankLineCount(0);
				
			}
			else if (countLeadingSpace(inLine) > status.getCurrentIndent()) {
				status.getMode().setIndent(countLeadingSpace(inLine));
				status.pushDepth(status.getMode());
				status.setMode(null);
				status.setReprocess(true);
				status.setBlankLineCount(0);
				status.setOutLine("");
			}
			else if (countLeadingSpace(inLine) < status.getCurrentIndent()) {
				status.setOutLine(status.runEndBlockOp());
				status.setMode(status.popDepth());
				status.setReprocess(true);
				status.setBlankLineCount(0);
				
			}
			else if (!inLine.trim().startsWith(markup.getStartTag())) {
				closeEntireList(status, markup);
				status.setMode(null);
				status.setReprocess(true);
				status.setBlankLineCount(0);
				
			}
			else {
				String text = MarkupUtils.runInlineSubstitutions(inLine.trim().replaceFirst(StringUtils.escRegex(markup.getStartTag()), ""), status.getTargetTags());
				
				status.setOutLine(status.runItemBlockOp(text));
				status.setMode(markup);
				status.setReprocess(false);
				status.setBlankLineCount(0);
				
			}
			
		}
		else {
			String text = MarkupUtils.runInlineSubstitutions(inLine.trim().replaceFirst(StringUtils.escRegex(status.getMode().getStartTag()), ""), status.getTargetTags());
			
			status.setOutLine(status.runStartBlockOp(text));
			status.setMode(markup);
			status.setReprocess(false);
			status.setBlankLineCount(0);
			
		}
		
	}
	
	/**
	 * Close all levels of a list 
	 * @param inLine The input line
	 * @param status The current status
	 * @param markup The markup to close all levels of a list
	 */
	private void closeEntireList(ProcessStatus status, Markup markup) {
		StringBuilder listClose = new StringBuilder();
		status.pushDepth(markup);
		
		while (status.getDepth() > 0) {
			status.setMode(status.popDepth());
			listClose.append(status.runEndBlockOp());
		}
		
		status.setOutLine(listClose.toString());
		
	}

	/**
	 * Determine the number of leading space characters on a line
	 * @param inLine The line to evaluate
	 * @return The count of whitespace characters at the beginning of the line
	 */
	private int countLeadingSpace(String inLine) {
		int i = 0;
		
		while (inLine.charAt(i) == ' ') {
			i++;
		}
		
		return i;
		
	}
	
}
