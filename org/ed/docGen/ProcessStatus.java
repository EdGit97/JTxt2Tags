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

import java.util.Iterator;
import java.util.Stack;

import org.ed.docGen.markup.Markup;
import org.ed.docGen.targets.TagSubstitutes;

/**
 * The current status of a job, including the results, current depth 
 * of any list, whether or not to continue the process of a block of 
 * text, the current target markup and the results of the last line 
 * processed.
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-15-2025
 */
public class ProcessStatus {
	
	/** Mode after processing a line */
	protected Markup mode = null;
	
	/** Results of processing a line */
	protected String outLine = null;
	
	/** true to cause the current line to be reprocessed, otherwise false */
	protected boolean reprocess = false;
	
	/** The current depth of the markup.  Used by lists all others zero */
	protected Stack<Markup> depth = new Stack<>();
	
	/** true to continue with the current block, false to start a new block */
	protected boolean continuation = true;
	
	/** Target markup tag set */
	protected TagSubstitutes targetTags;
	
	/** Count of blank lines for ending lists */
	protected int blankLineCount;
	
	/** true if, when processing a table, the table has borders, otherwise false */
	protected boolean tableBorder;
	
	/**
	 * Default Constructor
	 */
	public ProcessStatus() {
		super();
	}
	
	/**
	 * Constructor
	 * @param mode Mode after processing a line
	 * @param outLine Results of processing a line
	 * @param reprocess true to cause the current line to be reprocessed, otherwise false
	 */
	public ProcessStatus(Markup mode, String outLine, boolean reprocess) {
		this.mode = mode;
		this.outLine = outLine;
		this.reprocess = reprocess;
	}

	/**
	 * Determine the mode based on the markup in the input line
	 * @param inLine The line of text to evaluate
	 */
	public void determineMode(String inLine) {
		
		if (mode == null && inLine != null && inLine.trim().length() > 0) {
			Iterator<Markup> mui = Markup.blocks.iterator();
			
			while (mui.hasNext() && mode == null) {
				Markup m = mui.next();
				
				if (m.isThis(inLine)) {
					mode = m;
				}
				
			}
			
		}
		else if (inLine == null || inLine.trim().length() <= 0) {
			if (mode != null && !mode.isEndTagRequired()) {
				mode = null;
			}
			
		}
		else if (inLine.equals(mode.getEndTag())) {
			mode = null;
		}
		else if (Markup.getStartTagMap().containsKey(inLine)) {
			mode = Markup.getStartTagMap().get(inLine);
		}
		else {
			String [] parts = inLine.split(" ");
			
			if (parts.length > 1 && Markup.getStartTagMap().containsKey(parts[0] + " ")) {
				mode = Markup.getStartTagMap().get(parts[0] + " ");
			}
			else if (parts.length == 1) {
				if (inLine.startsWith(Markup.Separator.getStartTag())) {
					mode = Markup.Separator;
				}
				else if (inLine.startsWith(Markup.BoldSeparator.getStartTag())) {
					mode = Markup.BoldSeparator;
				}
				
			}
			
		}
		
		tableBorder = (Markup.Table.equals(mode) || Markup.TableHeader.equals(mode)) &&
                      inLine.endsWith(Markup.TableHeader.getEndTag());
		
	}
	
	/**
	 * Getter
	 * @return Mode after processing a line
	 */
	public Markup getMode() {
		return mode;
	}
	
	/**
	 * Setter
	 * @param mode Current processing mode
	 */
	public void setMode(Markup mode) {
		this.mode = mode;
	}
	
	/**
	 * Getter
	 * @return Results of processing a line
	 */
	public String getOutLine() {
		return outLine;
	}
	
	/**
	 * Setter
	 * @param outLine results of the last operation
	 */
	public void setOutLine(String outLine) {
		this.outLine = outLine;
	}
	
	/**
	 * Check if the input line needs to be reprocessed
	 * @return true true to cause the current line to be reprocessed, otherwise false
	 */
	public boolean getReprocess() {
		return reprocess;
	}
	
	/**
	 * Setter
	 * @param reprocess true to 
	 */
	public void setReprocess(boolean reprocess) {
		this.reprocess = reprocess;
	}

	/**
	 * Getter
	 * @return The current depth of the markup.  Used by lists all others zero
	 */
	public int getDepth() {
		return depth.size();
	}

	/**
	 * Push a mode onto the depth stack
	 * @param markup The mode to push onto the depth stack
	 */
	public void pushDepth(Markup markup) {
		depth.push(markup);
		targetTags.setListDepth(depth.size());
	}

	/**
	 * Pop a mode off of the depth stack
	 * @return The mode on the top of the stack or null if the stack is empty
	 */
	public Markup popDepth() {
		
		if (depth.isEmpty()) {
			targetTags.setListDepth(0);
			
			return null;
			
		}
		else {
			targetTags.setListDepth(targetTags.getListDepth() - 1);
			
			return depth.pop();
		}
		
	}

	/**
	 * Get the current amount of indent
	 * @return The sum of the indents for a list
	 */
	public int getCurrentIndent() {
		int indent = 0;
		
		for (Markup m : depth) {
			indent += m.getIndent();
		}
		
		return indent;
		
	}

	/**
	 * Generate target output for the beginning of a block
	 * @param text Input text to be processed
	 * @return Input modified for the target output
	 */
	public String runStartBlockOp(String text) {
		return targetTags.runStartBlockOp(mode, text);
	}
	
	/**
	 * Generate target output the body of a block
	 * @param text Input text to be processed
	 * @return Input modified for the target output
	 */
	public String runItemBlockOp(String text) {
		return targetTags.runItemOp(mode, text);
	}

	/**
	 * Generate target output for the end of a block
	 * @return Input modified for the target output
	 */
	public String runEndBlockOp() {
		return targetTags.runEndBlockOp(mode);
	}

	/**
	 * Getter
	 * @return true to continue with the current block, false to start a new block
	 */
	public boolean isContinuation() {
		return continuation;
	}

	/**
	 * Setter
	 * @param continuation true to continue with the current block, false to start a new block
	 */
	public void setContinuation(boolean continuation) {
		this.continuation = continuation;
	}

	/**
	 * Getter
	 * @return Target markup tag set
	 */
	public TagSubstitutes getTargetTags() {
		return targetTags;
	}

	/**
	 * Setter
	 * @param targetTags Target markup tag set
	 */
	public void setTargetTags(TagSubstitutes targetTags) {
		this.targetTags = targetTags;
	}
	
	/**
	 * Getter
	 * @return Count of blank lines for ending lists
	 */
	public int getBlankLineCount() {
		return blankLineCount;
	}

	/**
	 * Setter
	 * @param blankLineCount Count of blank lines for ending lists
	 */
	public void setBlankLineCount(int blankLineCount) {
		this.blankLineCount = blankLineCount;
	}

	/**
	 * Getter
	 * @return true if the table has borders, otherwise false
	 */
	public boolean isTableBorder() {
		return tableBorder;
	}

	/**
	 * Setter
	 * @param tableBorder true if the table has borders, otherwise false
	 */
	public void setTableBorder(boolean tableBorder) {
		this.tableBorder = tableBorder;
	}

	
}
