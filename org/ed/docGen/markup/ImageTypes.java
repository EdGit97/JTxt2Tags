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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ed.docGen.Constants;
import org.ed.docGen.targets.ImageLinkTagOps;
import org.ed.docGen.targets.TagSubstitutes;

/**
 * List of supported image types
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-09-2025
 */
public enum ImageTypes {
	
	/** Animated Portable Network Graphics */
	apng, 
	
	/** Portable Network Graphics */
	png, 
	
	/** AV1 Image File Format */
	avif, 
	
	/** Graphics Interchange Format */
	gif, 
	
	/** Joint Photographic Expert Group image */
	jpg, 
	
	/** Joint Photographic Expert Group image */
	jpeg, 
	
	/** Joint Photographic Expert Group image */
	jfif, 
	
	/** Joint Photographic Expert Group image */
	pjpeg, 
	
	/** Joint Photographic Expert Group image */
	pjp, 
	
	/** Scalable Vector Graphics */
	svg, 
	
	/** Web Picture format */
	webp, 
	
	/** Bitmap file */
	bmp, 
	
	/** Microsoft Icon */
	ico, 
	
	/** Microsoft Icon */
	cur, 
	
	/** Tagged Image File Format */
	tif, 
	
	/** Tagged Image File Format */
	tiff;
	
	/**
	 * Generate the regular expression to search for an image
	 * The regular expression will have the form \[(?!\[)\S*(&lt;.imgType|.imgType...)\]
	 * Which has the following meaning:
	 * <ol>
	 *     <li>\[ matches the character [ with index 91 literally (case sensitive)</li>
	 *     <li>Negative Lookahead (?!\[) - \[ matches the character [ with index 91 literally (case sensitive)</li>
	 *     <li>\S matches any non-whitespace character (equivalent to [^\r\n\t\f\v ])</li>
	 *     <li>* matches the previous token between zero and unlimited times, as many times as possible, giving back as needed (greedy)</li>
	 *     <li>1st Capturing Group (\.apng|\.png|...) - An alternative for each enumerated value in this enumeration</li>
	 *     <li>1st Alternative \.png</li>
	 *     <li>\] matches the character ] with index 93 literally (case sensitive)</li>     
	 * </ol>
	 * @return The regular expression to use when searching for an image
	 */
	public static String imageRegex() {
		final String regexStart = "\\[(?!\\[)\\S*("; 
		final String regexEnd = ")\\]";
		StringBuilder regex = new StringBuilder(regexStart);
		
		regex.append(assembleRegexTypes());
		regex.append(regexEnd);
		
		return regex.toString();
		
	}
	
	/**
	 * Process the data for a markup
	 * @param inLine The string to process
	 * @param targetTags Tags to use in the output
	 * @return The output resulting from substituting the target image tags for the txt2tags markup 
	 */
	public static String process(String inLine, TagSubstitutes targetTags) {
		String outLine = String.valueOf(inLine);
		Pattern imgPattern = Pattern.compile(imageRegex(), Pattern.CASE_INSENSITIVE);
		Matcher imgMatcher = imgPattern.matcher(outLine);
		int nextStartPos = 0;

		while (nextStartPos < outLine.length() && imgMatcher.find(nextStartPos)) {
			int imgStart = imgMatcher.start();
			int imgEnd = imgMatcher.end();
			
			// If an image inside of a link, skip
			if (outLine.charAt(imgStart - 1) == '[') {
				nextStartPos = imgEnd + 1;
			}
			else {
				ImageLinkTagOps ilto = targetTags.getImageTargets();
				Constants.TextAlign align = determineTextAlign(outLine, imgStart, imgEnd);
				ImageLinkData ild = new ImageLinkData(outLine.substring(imgStart + 1, imgEnd - 1), align);
				String targetMarkup = ilto.itemTags(ild);
				
				outLine = outLine.substring(0, imgStart) + targetMarkup +
						  (imgEnd < outLine.length() ? outLine.substring(imgEnd) : "");
				
				nextStartPos = imgStart + targetMarkup.length();

			}
			
			imgMatcher = imgPattern.matcher(outLine);
			
		}
		
		return outLine;
		
	}
	
	/**
	 * Determine the alignment of an image
	 * @param text String that contains the image markup
	 * @param startPos Position of the beginning of the image markup
	 * @param endPos Position of the end of the image markup
	 * @return left, center or right
	 */
	public static Constants.TextAlign determineTextAlign(String text, int startPos, int endPos) {
		Constants.TextAlign align;

		if (startPos <= 0) {
			align = Constants.TextAlign.left;
		}
		else if (endPos >= text.length() - 1) {
			align = Constants.TextAlign.right;
		}
		else {
			align = Constants.TextAlign.center;
		}
		
		return align;
		
	}
	
	/**
	 * Generate a regular expression capturing group for the image types 
	 * @return The images types formatted as a capturing group
	 */
	public static String assembleRegexTypes() {
		StringBuilder regex = new StringBuilder();

		for (ImageTypes ext : values()) {
			if (regex.length() > 0) {
				regex.append("|");
			}
			
			regex.append("\\.");
			regex.append(ext);
			
		}
		
		return regex.toString();
		
	}

	/**
	 * Determine if a file name represents an image file
	 * @param data The string to evaluate
	 * @return true if data ends with one of the ImageType values, otherwise false
	 */
	public static boolean isImage(String data) {
		ImageTypes [] types = values();
		boolean img = false;
		
		if (data != null && data.length() > 0) {
			String fn = data.toLowerCase();
			
			for (int i = 0; i < types.length && !img; i++) {
				img = fn.endsWith("." + types[i]);
			}
			
		}
		
		return img;
		
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
	
}
