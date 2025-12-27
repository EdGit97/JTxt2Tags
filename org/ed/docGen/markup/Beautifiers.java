/*
 * Text-to-Tags API for Java Projects
 * Copyright (C) 2025 Ed Swaneck
 * Copyright 2010-2019 Jendrik Seipp for regular expressions for Bold,
 * Underline, Strike and Monospace.
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ed.docGen.Constants;
import org.ed.docGen.targets.TagSubstitutes;

/**
 * List of txt2tags beautifiers and associated data
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-29-2025
 */
public enum Beautifiers {
	
	/** 
	 * Render a soft line break (not a new paragraph).
	 * Line break is delimited as a single backslash with no  
	 * spaces before and no spaces after the backslash.<br> 
	 * Example:<br>
	 * some text\
	 */
	SoftLineBreak("\\", Constants.newLine, "\\\\$") {
		
		/*
		 * @see org.ed.docGen.markup.Beautifiers#getStartTagEscaped()
		 */
		@Override
		public String getStartTagEscaped() {
			return "\\" + getStartTag();
		}
		
	},

	/** 
	 * Render a section of a line as bold text. Bold
	 * text is delimited by two asterisks immediately before
	 * and immediately after the text to be rendered bold.<br> 
	 * Example:<br>
	 * **some bold text**
	 */
	Bold("**", "(^|\\s)\\*\\*([^\\s](|.*?[^\\s])\\**)\\*\\*"),

	/** 
	 * Render a section of a line as italic text. Italic
	 * text is delimited by two forward slashes immediately 
	 * before and immediately after the text to be rendered italic.
	 * The regular expression will ignore http:// and https:// as the
	 * end delimiter.<br> 
	 * Example:<br>
	 * //some italic text//
	 */
	Italic("//", "(^|\\s)//([^\\s](|.*?[^\\s])/*)(?<!http:|https:)//"),

	/** 
	 * Render a section of a line as underlined text. Underlined
	 * text is delimited by two underscores immediately 
	 * before and immediately after the text to be rendered underlined.<br> 
	 * Example:<br>
	 * __some underlined text__
	 */
	Underline("__", "(^|\\s)__([^\\s_](|.*?[^\\s_])_*)__"),

	/** 
	 * Render a section of a line as deleted text. Deleted
	 * text is delimited by two hyphens immediately 
	 * before and immediately after the text to be rendered deleted.<br> 
	 * Example:<br>
	 * --some struck out text--
	 */
	Strike("--", "(^|\\s)--([^\\s](|.*?[^\\s])-*)--"),

	/** 
	 * Render a section of a line as mono-spaced text. Mono-spaced
	 * text is delimited by two backticks immediately 
	 * before and immediately after the text to be rendered mono-spaced.<br> 
	 * Example:<br>
	 * ``some monospaced text``
	 */
	Monospace("``", "(^|\\s)``([^\\s](|.*?[^\\s])`*)``");

	private static final String bracketRegex = "\\[.*\\]";
	
	private String startTag;
	private String endTag;
	private String regex;
	
	/**
	 * Constructor
	 * @param startTag txt2tags tag that begins the mode
	 */
	private Beautifiers(String startTag, String regex) {
		this.startTag = startTag;
		this.endTag = startTag;
		this.regex = regex;
	}

	/**
	 * Constructor
	 * @param startTag txt2tags tag that begins the mode
	 * @param endTag txt2tags tag that ends the mode
	 */
	private Beautifiers(String startTag, String endTag, String regex) {
		this.startTag = startTag;
		this.endTag = endTag;
		this.regex = regex;
	}

	/**
	 * Process the data for a markup
	 * @param inLine The string to process
	 * @param targetTags Tags to use in the output
	 * @return The resulting output or null if the mode should be closed and the 
	 *         input reevaluated by another markup
	 */
	public String process(String inLine, TagSubstitutes targetTags) {
		return targetTags.getBeautifierTargets().get(this).itemTags(this, inLine);
	}
	
	/**
	 * Find the markup for a beautifier and replace it with the target syntax
	 * @param inLine The line to be modified
	 * @param targetStartTag Tag the matcher will use to replace the beginning beautifier marker
	 * @param targetEndTag Tag the matcher will use to replace the ending beautifier marker
	 * @return The input line with all markup changed to target tags
	 */
	public String beautify(String inLine, String targetStartTag, String targetEndTag) {
		String outLine = String.valueOf(inLine);
		Pattern beautyPattern = Pattern.compile(this.getRegex(), Pattern.CASE_INSENSITIVE);
		Matcher beautyMatcher = beautyPattern.matcher(outLine);
		Pattern bracketPattern = Pattern.compile(bracketRegex);
		Matcher bracketMatcher = bracketPattern.matcher(outLine);
		int nextStartPos = 0;

		while (nextStartPos < outLine.length() && beautyMatcher.find(nextStartPos)) {
			int beautyStart = beautyMatcher.start();
			int beautyEnd = beautyMatcher.end();
			int bracketStart = -1;
			int bracketEnd = -1;
			
			if (bracketMatcher.find(nextStartPos)) {
				bracketStart = bracketMatcher.start();
				bracketEnd = bracketMatcher.end();
			}
			
			// If the start of the match is a space, move to the next character
			if (outLine.charAt(beautyStart) == ' ') {
				beautyStart++;
			}
			
			if (beautyStart > bracketStart && beautyEnd < bracketEnd) {
				nextStartPos = bracketEnd + 1;
			}
			else {
				// Substitute the end tag
				if (beautyEnd - beautyStart > 1) {
					outLine = outLine.substring(0, beautyEnd - this.getEndTag().length()) + targetEndTag +
							  outLine.substring(beautyEnd);
				}
				
				// Substitute the start tag.
				outLine = outLine.substring(0, beautyStart) + targetStartTag +
						  outLine.substring(beautyStart + this.getStartTag().length());
				
				nextStartPos = outLine.lastIndexOf(targetEndTag) + targetEndTag.length();

			}
			
			beautyMatcher = beautyPattern.matcher(outLine);
			bracketMatcher = bracketPattern.matcher(outLine);
			
		}
		
		return outLine;
		
	}
	
	/**
	 * Getter
	 * @return Token that begins a markup section
	 */
	public String getStartTag() {
		return startTag;
	}

	/**
	 * Getter
	 * @return Token that ends a markup section
	 */
	public String getEndTag() {
		return endTag;
	}

	/**
	 * Getter
	 * @return The regular expression associated with this markup
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * Getter
	 * @return Token that begins a markup section with regular expression characters escaped 
	 */
	public String getStartTagEscaped() {
		return startTag;
	}
	
	/**
	 * Getter
	 * @return Token that ends a markup section with regular expression characters escaped 
	 */
	public String getEndTagEscaped() {
		return startTag;
	}
	
}
