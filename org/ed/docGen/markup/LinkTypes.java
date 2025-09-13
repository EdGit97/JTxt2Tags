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

import java.net.MalformedURLException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ed.docGen.Constants;
import org.ed.docGen.targets.TagSubstitutes;

/**
 * Types of link markup and associated search regular expressions
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-28-2025
 */
public enum LinkTypes {

	/** A bare link not denoted by any markup.  Regular expression description:
	 * <ol>
     *     <li>\[ matches the character [ with index 91 literally</li>
     *     <li>Match a single character not present in the list below [^\]]</li>
     *     <li>\] matches the character ] with index 93 literally</li>
     *     <li>\s matches any whitespace character (equivalent to [\r\n\t\f\v ])</li>
     *     <li>Negative Lookahead (?![^\[]*\])</li>
     *     <li>Assert that the Regex below does not match</li>
     *     <li>Match a single character not present in the list below [^\[]</li>
     *     <li>* matches the previous token between zero and unlimited times, as many times as possible, giving back as needed (greedy)</li>
     *     <li>\[ matches the character [ with index 91 literally</li>
     *     <li>\] matches the character ] with index 93 literally</li>
	 * </ol>
     */
	bare("(?<!\\[[^\\]])\\s(?![^\\[]*\\])"), 
	
	/** A link surrounded by [].   Regular expression description:
	 * <ol>
     *     <li>\[ matches the character [ with index 91 literally</li>
     *     <li>\S matches any non-whitespace character (equivalent to [^\r\n\t\f\v ])</li>
     *     <li>. matches any character (except for line terminators)</li>
     *     <li>? matches the previous token between zero and unlimited times, as few times as possible, expanding as needed (lazy)</li>
     *     <li>\] matches the character ] with index 93 literally</li>
	 * </ol>
	 */
	named("\\[\\S.*?\\]");
	
	/**
	 * Find the complete link if an image is detected inside of a link
	 * <ol>
     *    <li>\[ matches the character [ with index 91 literally</li>
     *    <li>\[ matches the character [ with index 91 literally</li>
     *    <li>\S - matches any non-whitespace character (equivalent to [^\r\n\t\f\v ])</li>
     *    <li>* matches the previous token between zero and unlimited times, as many times as possible, giving back as needed (greedy)</li>
     *    <li>1st Capturing Group (\.png|\.jpg|\.jpeg|...)</li>
     *    <li>1st Alternative \.&lt;Image Extension&gt;</li>
     *    <li>\. matches the character . with index 46 literally</li>
     *    <li>matches the characters &lt;Image Extension&gt; literally (case insensitive)</li>
     *    <li>\] matches the character ] with index 93</li>
     *    <li>\s matches any whitespace character (equivalent to [\r\n\t\f\v ])</li>
     *    <li>. matches any character (except for line terminators)</li>
     *    <li>* matches the previous token between zero and unlimited times, as many times as possible, giving back as needed (greedy)</li>
     *    <li>\] matches the character ] with index 93 literally</li>
     * </ol> 
	 */
	public static final String imgLinkStart = "\\[\\[\\S*(";
	
	/** End of the image/link regular expression */
	public static final String imgLinkEnd = ")\\]\\s.*\\]";
	
	private String regex;

	/**
	 * Constructor
	 * @param regex The regular expression associated with this markup
	 */
	private LinkTypes(String regex) {
		this.regex = regex;
	}

	/**
	 * Process the data for a markup
	 * @param inLine The string to process
	 * @param targetTags Tags to use in the output
	 * @return The output resulting from substituting the target image tags for the txt2tags markup 
	 */
	public static String process(String inLine, TagSubstitutes targetTags) {
		String outLine = String.valueOf(inLine);
		
		// Process the image links
		Pattern linkPattern = Pattern.compile(makeLinkImageRegex(), Pattern.CASE_INSENSITIVE);
		Matcher linkMatcher = linkPattern.matcher(outLine);
		int nextStartPos = 0;
		
		while (nextStartPos < outLine.length() && linkMatcher.find(nextStartPos)) {
			int startPos = linkMatcher.start();
			int endPos = linkMatcher.end();
			int imgEnd = outLine.indexOf(ImageTypes.getEndMarkup(), startPos);
			ImageLinkData ild = new ImageLinkData(outLine.substring(startPos + 2, imgEnd), 
					                              ImageTypes.determineTextAlign(outLine, startPos, endPos));
			String image = targetTags.getImageTargets().itemTags(ild);
			
			// Split out the URL
			int urlPos = outLine.lastIndexOf(' ', endPos);
			
			ild = new ImageLinkData(outLine.substring(urlPos + 1, endPos - 1),
					                ild.getAlign(),
					                image);
			
			String link = targetTags.getLinkTargets().itemTags(ild);
			
			// Substitute the target tag for the markup
			outLine = outLine.substring(0, startPos) + link + outLine.substring(endPos);
			
			nextStartPos = startPos + link.length();
			linkMatcher = linkPattern.matcher(outLine);
			
		}
		
		// Process named links
		linkPattern = Pattern.compile(LinkTypes.named.getRegex(), Pattern.CASE_INSENSITIVE);
		linkMatcher = linkPattern.matcher(outLine);
		nextStartPos = 0;
		
		while (nextStartPos < outLine.length() && linkMatcher.find(nextStartPos)) {
			int startPos = linkMatcher.start();
			int endPos = linkMatcher.end();
			int urlPos = outLine.lastIndexOf(' ', endPos - 1);
			
			// If an image was already processed, skip it.  Issue with wiki processing
			if (targetTags.getImageTargets().alreadyProcessed(outLine.substring(linkMatcher.start())) ||
				targetTags.getLinkTargets().alreadyProcessed(outLine.substring(linkMatcher.start()))) {
				nextStartPos = endPos + 1;
			}
			else {
				ImageLinkData ild = new ImageLinkData(outLine.substring(urlPos + 1, endPos - 1),
						                              Constants.TextAlign.left,
						                              outLine.substring(startPos + 1, urlPos));
				
				String link = targetTags.getLinkTargets().itemTags(ild);
				
				// Substitute the target tag for the markup
				outLine = outLine.substring(0, startPos) + link + outLine.substring(endPos);
				
				nextStartPos = startPos + link.length();
				
			}
			
			linkMatcher = linkPattern.matcher(outLine);
			
		}
		
		// Process bare links
		String [] lineParts = outLine.split(" ");
		
		for (String part : lineParts) {
			boolean isUrl;
			
			try {
				URI.create(part).toURL();
				isUrl = true;
			} 
			catch (MalformedURLException | IllegalArgumentException e) {
				isUrl = false;
			}
			
			if (isUrl) {
				ImageLinkData ild = new ImageLinkData(part, Constants.TextAlign.left);
				String link = targetTags.getLinkTargets().itemTags(ild).trim();
				int linkPos = outLine.indexOf(part);

				// Substitute the target tag for the markup
				outLine = outLine.substring(0, linkPos) + link + outLine.substring(linkPos + part.length());
				
			}
			
		}
		
		return outLine;
		
	}
	
	/**
	 * Create the regular expression that will be used to generate a link
	 * @return The regular expression that will find links that contain images
	 */
	public static String makeLinkImageRegex() {
		StringBuilder regex = new StringBuilder(imgLinkStart);
		
		regex.append(ImageTypes.assembleRegexTypes());
		regex.append(imgLinkEnd);
		
		return regex.toString();
		
	}
	
	/**
	 * Getter
	 * @return The txt2tags image start tag
	 */
	public static String getStartMarkup() {
		return "[";
	}
	
	/**
	 * Getter
	 * @return The txt2tags image end tag
	 */
	public static String getEndMarkup() {
		return "]";
	}
	
	/**
	 * Getter
	 * @return The regular expression associated with this markup
	 */
	public String getRegex() {
		return regex;
	}
	
}
