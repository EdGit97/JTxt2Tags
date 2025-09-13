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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ed.docGen.Constants;
import org.ed.docGen.markup.Beautifiers;
import org.ed.docGen.markup.Markup;

/**
 * Base class for target tag substitution classes 
 * See {@link  org.ed.docGen.targets.HtmlTags  HtmlTags} for an example.
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-12-2025
 */
public abstract class TagSubstitutes {
	
	/** Counters for the number titles */
	protected int [] titleCounters = { 0, 0, 0 };
	
	/** Holder for the table cell alignment */
	protected Constants.TextAlign textAlign = Constants.TextAlign.left;
	
	/** List depth counter */
	protected int listDepth = 0;
		
	/** 
	 * The substitution for block rules. Map contains a markup and 
	 * the list of substitution tags.  The list represent the first 
	 * level, then second level, etc.  Example: Ordered list would 
	 * have: OL, LI.
	 */
	protected Map<Markup, BlockTagOps> blockTargets = new HashMap<>();
	
	/** 
	 * The substitution for beautifier rules. Map contains a markup 
	 * and the list of substitution tags.  The list represent the first 
	 * level, then second level, etc.  Example: HTML Ordered list would 
	 * have: OL, LI.
	 */
	protected Map<Beautifiers, BeautifierTagOps> beautifierTargets = new HashMap<>();
	
	/** 
	 * The substitution for image rules. Provides a connection between the image 
	 * types and substitution tags.
	 */
	protected ImageLinkTagOps imageTargets; 
	
	/** 
	 * The substitution for link rules. Provides a connection between the link 
	 * types and substitution tags.
	 */
	protected ImageLinkTagOps linkTargets; 
	
	/**
	 * Default constructor
	 */
	public TagSubstitutes() {
		super();
	}
	
	/**
	 * Build a list of tags associated with substitution
	 * @param tags The list of tags
	 * @return The tags compiled into a List
	 */
	protected List<String> buildTagList(String... tags) {
		List<String> tl = new ArrayList<>();
		
		for (String t : tags) {
			tl.add(t);
		}
		
		return tl;
		
	}
	
	/**
	 * Assemble a block tag for the output
	 * @param tag The tag to assemble
	 * @param titleLevel The zero based counter level to increment and display (0, 1 or 2)
	 * @param endTag true to generate an end style tag, false for a start tag
	 * @return The formatted tag
	 */
	protected abstract String assembleBlockTag(String tag, int titleLevel, boolean endTag);

	/**
	 * Assemble a block tag for the output
	 * @param tag The tag to assemble
	 * @param endTag true to generate an end style tag, false for a start tag
	 * @return The formatted tag
	 */
	protected String assembleBlockTag(String tag, boolean endTag) {
		return assembleBlockTag(tag, -1, endTag);
	}
	
	/**
	 * Assemble a beautifier tag for the output
	 * @param markup The markup associated with the tags
	 * @param endTag true to generate an end style tag, false for a start tag
	 * @return The formatted tag
	 */
	protected abstract String assembleBeautifierTag(Beautifiers markup, boolean endTag);
	
	/**
	 * Getter
	 * @return The numbered title counters
	 */
	public int[] getTitleCounters() {
		return titleCounters;
	}
	
	/**
	 * Increment the title counters
	 * @param pos Position of the title counter to increment
	 */
	public void incrementCounters(int pos) {
		
		if (pos >= 0 && pos < titleCounters.length) {
			titleCounters[pos]++;
			
			for (int i = pos + 1; i < titleCounters.length; i++) {
				titleCounters[i] = 0;
			}
			
		}
		
	}
	
	/**
	 * Reset the title counters to ones
	 */
	public void resetTitleCounters() {
		
		for (int i = 0; i < titleCounters.length; i++) {
			titleCounters[i] = 1;
		}
		
	}
	
	/**
	 * Generate the counters for the Numbered Titles
	 * @param level The zero based counter level to increment and display (0, 1 or 2)
	 * @return Number text of the numbered title
	 */
	public String generateTitleCounter(int level) {
		StringBuilder counters = new StringBuilder();
		
		if (level >= 0 && level < titleCounters.length) {
			
			incrementCounters(level);
			
			for (int i = 0; i <= level; i++) {
				if (counters.length() > 0) {
					counters.append('.');
				}
				
				counters.append(titleCounters[i]);
				
			}
			
			counters.append(". ");
			
		}
		

		return counters.toString();
		
	}
	
	/**
	 * Getter
	 * @return The current cell text alignment
	 */
	public Constants.TextAlign getTextAlign() {
		return textAlign;
	}

	/**
	 * Setter
	 * @param textAlign The current cell text alignment
	 */
	public void setTextAlign(Constants.TextAlign textAlign) {
		this.textAlign = textAlign;
	}

	/**
	 * Getter
	 * @return The current list depth
	 */
	public int getListDepth() {
		return listDepth;
	}

	/**
	 * Setter
	 * @param listDepth The current list depth
	 */
	public void setListDepth(int listDepth) {
		this.listDepth = listDepth;
	}

	/**
	 * Getter
	 * @return The markup target map
	 */
	public Map<Markup, BlockTagOps> getBlockTargets() {
		return blockTargets;
	}

	/**
	 * Getter
	 * @return The markup target map
	 */
	public Map<Beautifiers, BeautifierTagOps> getBeautifierTargets() {
		return beautifierTargets;
	}

	/**
	 * Getter
	 * @return The markup target for images
	 */
	public ImageLinkTagOps getImageTargets() {
		return imageTargets;
	}

	/**
	 * Getter
	 * @return The markup target for links
	 */
	public ImageLinkTagOps getLinkTargets() {
		return linkTargets;
	}

	/**
	 * Run the block start process for a block target
	 * @param markup Generate the starting block for this markup
	 * @param text Line to be processed
	 * @return The process text with any required starting markup
	 */
	public String runStartBlockOp(Markup markup, String text) {
		return blockTargets.get(markup).blockStartTags(text);		
	}
	
	/**
	 * Run the block body process for a block target
	 * @param markup Generate the starting block for this markup
	 * @param text Line to be processed
	 * @return The process text with any required starting markup
	 */
	public String runItemOp(Markup markup, String text) {
		return blockTargets.get(markup).blockItemTags(text);		
	}
	
	/**
	 * Run the block end process for a block target
	 * @param markup Generate the starting block for this markup
	 * @return The process text with any required starting markup
	 */
	public String runEndBlockOp(Markup markup) {
		return blockTargets.get(markup).blockEndTags();		
	}
	
}
