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

import java.util.List;

import org.ed.docGen.Constants;
import org.ed.docGen.markup.Beautifiers;
import org.ed.docGen.markup.ImageLinkData;
import org.ed.docGen.markup.Markup;
import org.ed.docGen.markup.TableCell;

/**
 * Target class for generating HTML from txt2tags markup
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-12-2025
 */
public class HtmlTags extends TagSubstitutes {
	
	/** Specifications for a table border */
	public static final String tblBorderSpecs = "border: 1px solid black;";
	
	/**
	 * Convert verbatim line markup to equivalent HTML tags
	 */
	private class VerbatimLineOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public VerbatimLineOps() {
			super("pre");
		}
		
		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder();

			output.append(assembleBlockTag(tagList.get(0), false));
			output.append(Constants.newLine);
			output.append(text);
			output.append(Constants.newLine);
			output.append(assembleBlockTag(tagList.get(0), true));
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return "";
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return "";
		}
		
	}
	
	/**
	 * Convert verbatim area markup to equivalent HTML tags
	 */
	private class VerbatimAreaOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public VerbatimAreaOps() {
			super("pre");
		}
		
		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return assembleBlockTag(tagList.get(0), false);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return assembleBlockTag(tagList.get(0), true);
		}
		
	}

	/**
	 * Convert raw area markup to equivalent HTML tags
	 */
	private class RawAreaOps extends BlockTagOps {
		
		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return "";
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return "";
		}
		
	}

	/**
	 * Convert tagged area markup to equivalent HTML tags
	 */
	private class TaggedAreaOps extends BlockTagOps {
		
		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return "";
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return "";
		}
		
	}

	/**
	 * Convert separator markup to equivalent HTML tags
	 */
	private class SeparatorOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public SeparatorOps() {
			super("hr");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder();

			output.append(assembleBlockTag(tagList.get(0), false));
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return "";
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return "";
		}
		
	}
	
	/**
	 * Convert bold separator markup to equivalent HTML tags
	 */
	private class BoldSeparatorOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public BoldSeparatorOps() {
			super("hr style='border-width: 2px;'");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder();

			output.append(assembleBlockTag(tagList.get(0), false));
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return "";
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return "";
		}
		
	}
	
	/**
	 * Convert title level 1 markup to equivalent HTML tags
	 */
	private class TitleLevel1Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel1Ops() {
			super("h1");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return assembleBlockTag(tagList.get(0), false);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return assembleBlockTag(tagList.get(0), true);
		}
		
	}
	
	/**
	 * Convert title level 2 markup to equivalent HTML tags
	 */
	private class TitleLevel2Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel2Ops() {
			super("h2");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return assembleBlockTag(tagList.get(0), false);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return assembleBlockTag(tagList.get(0), true);
		}
		
	}
	
	/**
	 * Convert title level 3 markup to equivalent HTML tags
	 */
	private class TitleLevel3Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel3Ops() {
			super("h3");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return assembleBlockTag(tagList.get(0), false);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return assembleBlockTag(tagList.get(0), true);
		}
		
	}
	
	/**
	 * Convert numbered title level 1 markup to equivalent HTML tags
	 */
	private class NumberedTitleLevel1Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public NumberedTitleLevel1Ops() {
			super("h1");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return assembleBlockTag(tagList.get(0), 0, false);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return assembleBlockTag(tagList.get(0), 0, true);
		}
		
	}
	
	/**
	 * Convert numbered title level 2 markup to equivalent HTML tags
	 */
	private class NumberedTitleLevel2Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public NumberedTitleLevel2Ops() {
			super("h2");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return assembleBlockTag(tagList.get(0), 1, false);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return assembleBlockTag(tagList.get(0), 1, true);
		}
		
	}
	
	/**
	 * Convert numbered title level 3 markup to equivalent HTML tags
	 */
	private class NumberedTitleLevel3Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public NumberedTitleLevel3Ops() {
			super("h3");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return assembleBlockTag(tagList.get(0), 2, false);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return assembleBlockTag(tagList.get(0), 2, true);
		}
		
	}

	/**
	 * Convert un-ordered list markup to equivalent HTML tags
	 */
	private class UnorderedListOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public UnorderedListOps() {
			super("ul", "li");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		@Override
		public String blockStartTags(String text) {
			return listTagStart(text, tagList);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		@Override
		public String blockItemTags(String text) {
			return listItemTags(text, tagList);
		}

		/*
		 * @see org.ed.docGen.targets.BlockTagOps#blockEndTags()
		 */
		@Override
		public String blockEndTags() {
			return listEndTags(tagList);
		}
		
	}
	
	/**
	 * Convert ordered list markup to equivalent HTML tags
	 */
	private class OrderedListOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public OrderedListOps() {
			super("ol", "li");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return listTagStart(text, tagList);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return listItemTags(text, tagList);
		}

		/*
		 * @see org.ed.docGen.targets.BlockTagOps#blockEndTags()
		 */
		@Override
		public String blockEndTags() {
			return listEndTags(tagList);
		}

	}
	
	/**
	 * Convert definition markup to equivalent HTML tags
	 */
	private class DefinitionListOps extends DefinitionBlockTagOps {
		
		/**
		 * Constructor
		 */
		public DefinitionListOps() {
			super("dl", "dt", "dd");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder();
			
			output.append(assembleBlockTag(tagList.get(0), false));
			output.append(Constants.newLine);
			output.append(assembleBlockTag(tagList.get(1), false));
			output.append(text);
			output.append(assembleBlockTag(tagList.get(1), true));
			output.append(assembleBlockTag(tagList.get(2), false));
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			StringBuilder output = new StringBuilder();

			output.append(assembleBlockTag(tagList.get(2), true));
			output.append(Constants.newLine);
			output.append(assembleBlockTag(tagList.get(1), false));
			output.append(text);
			output.append(assembleBlockTag(tagList.get(1), true));
			output.append(assembleBlockTag(tagList.get(2), false));
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			StringBuilder output = new StringBuilder();
			
			output.append(assembleBlockTag(tagList.get(2), true));
			output.append(Constants.newLine);
			output.append(assembleBlockTag(tagList.get(0), true));
			output.append(Constants.newLine);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.DefinitionBlockTagOps#generateDescriptionDefinition(java.lang.String, boolean)
		 */
		@Override
		public String generateDescriptionDefinition(String text, boolean endTag) {
			return text;
		}
		
	}
	
	/**
	 * Convert quoted paragraph markup to equivalent HTML tags
	 */
	private class QuotedParagraphOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public QuotedParagraphOps() {
			super("blockquote");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(assembleBlockTag(tagList.get(0), false));
			
			output.append(Constants.newLine);
			output.append(text);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			StringBuilder output = new StringBuilder();
			
			output.append(assembleBlockTag(tagList.get(0), true));
			output.append(Constants.newLine);
			
			return output.toString();
			
		}
		
	}
	
	/**
	 * Convert paragraph markup to equivalent HTML tags
	 */
	private class ParagraphOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public ParagraphOps() {
			super("p");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(assembleBlockTag(tagList.get(0), false));
			
			output.append(Constants.newLine);
			output.append(text);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return text;
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return assembleBlockTag(tagList.get(0), true) + Constants.newLine;
		}
		
	}
	
	/**
	 * Convert beautifier markup to equivalent HTML tags
	 */
	private class BeautifierOps extends BeautifierTagOps {

		/**
		 * Constructor
		 * @param tag Text of the start and end tag
		 */
		public BeautifierOps(String tag) {
			super(tag);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String itemTags(Beautifiers beautifier, String text) {
			String targetStartTag = assembleBeautifierTag(beautifier, false);
			String targetEndTag = assembleBeautifierTag(beautifier, true);
			
			return beautifier.beautify(text, targetStartTag, targetEndTag);

		}

	}
	
	/**
	 * Convert table markup to HTML table tags
	 */
	private class TableOps extends TableBlockTagOps {
		
		/**
		 * Constructor
		 */
		public TableOps() {
			super("table", "tr", "td");
		}

		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#blockStartTags(java.lang.String, boolean)
		 */
		@Override
		public String blockStartTags(String text, boolean border, boolean centered) {
			return tableStartTags(text, tagList, border, centered);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		@Override
		public String blockEndTags() {
			return tableEndTags(tagList);
		}
		
		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#generateRowTags(boolean)
		 */
		@Override
		public String generateRowTags(boolean endTag) {
			return makeRowTags(endTag);
		}

		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#blockItemTags(org.ed.docGen.markup.TableCell[])
		 */
		@Override
		public String blockItemTags(TableCell [] columns) {
			return tableItemTags(columns, tagList);
		}
		
	}
	
	/**
	 * Convert table markup to HTML table header tags
	 */
	private class TableHeaderOps extends TableBlockTagOps {
		
		/**
		 * Constructor
		 */
		public TableHeaderOps() {
			super("table", "tr", "th");
		}

		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#blockStartTags(java.lang.String, boolean)
		 */
		@Override
		public String blockStartTags(String text, boolean border, boolean centered) {
			return tableStartTags(text, tagList, border, centered);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		@Override
		public String blockEndTags() {
			return tableEndTags(tagList);
		}
		
		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#generateRowTags(boolean)
		 */
		@Override
		public String generateRowTags(boolean endTag) {
			return makeRowTags(endTag);
		}

		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#blockItemTags(org.ed.docGen.markup.TableCell[])
		 */
		@Override
		public String blockItemTags(TableCell [] columns) {
			return tableItemTags(columns, tagList);
		}
		
	}
	
	/**
	 * Convert image markup to an HTML img tag
	 */
	private class ImageOps extends ImageLinkTagOps {
		
		/**
		 * Constructor
		 */
		public ImageOps() {
			super("img src='", "' style='text-align: ", ";' alt=''");
		}

		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#itemTags(org.ed.docGen.markup.ImageLinkData)
		 */
		public String itemTags(ImageLinkData data) {
			StringBuilder img = new StringBuilder("<");
			
			img.append(getTagList().get(0));
			img.append(data.getFileSpec());
			img.append(getTagList().get(1));
			img.append(data.getAlign().name());
			img.append(getTagList().get(2));
			img.append(">");
			
			return img.toString();
			
		}
		
		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#alreadyProcessed(java.lang.String)
		 */
		public boolean alreadyProcessed(String potentialMarkup) {
			return false;
		}
		
	}
	
	/**
	 * Convert link markup to an HTML anchor tag
	 */
	private class LinkOps extends ImageLinkTagOps {
		
		/**
		 * Constructor
		 */
		public LinkOps() {
			super("a href='", "a");
		}

		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#itemTags(org.ed.docGen.markup.ImageLinkData)
		 */
		public String itemTags(ImageLinkData data) {
			StringBuilder a = new StringBuilder("<");
			
			a.append(getTagList().get(0));
			a.append(data.getFileSpec());
			a.append("'>");
			
			if (data.getLabel() != null && data.getLabel().length() > 0) {
				a.append(data.getLabel());
			}
			else {
				a.append(data.getFileSpec());
			}
			
			a.append(assembleBlockTag(getTagList().get(1), 0, true));
			
			return a.toString();
			
		}
		
		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#alreadyProcessed(java.lang.String)
		 */
		public boolean alreadyProcessed(String potentialMarkup) {
			return false;
		}
		
	}
	
	/**
	 * Constructor
	 */
	public HtmlTags() {
		super();
		
		// The set of block transformers 
		blockTargets.put(Markup.VerbatimLine, new VerbatimLineOps()); 
		blockTargets.put(Markup.VerbatimArea, new VerbatimAreaOps());
		blockTargets.put(Markup.RawArea, new RawAreaOps());
		blockTargets.put(Markup.TaggedArea, new TaggedAreaOps());
		blockTargets.put(Markup.Separator, new SeparatorOps());
		blockTargets.put(Markup.BoldSeparator, new BoldSeparatorOps());
		blockTargets.put(Markup.TitleLevel1, new TitleLevel1Ops());
		blockTargets.put(Markup.TitleLevel2, new TitleLevel2Ops());
		blockTargets.put(Markup.TitleLevel3, new TitleLevel3Ops());
		blockTargets.put(Markup.NumberedTitleLevel1, new NumberedTitleLevel1Ops());
		blockTargets.put(Markup.NumberedTitleLevel2, new NumberedTitleLevel2Ops());
		blockTargets.put(Markup.NumberedTitleLevel3, new NumberedTitleLevel3Ops());
		blockTargets.put(Markup.UnorderedList, new UnorderedListOps());
		blockTargets.put(Markup.OrderedList, new OrderedListOps());
		blockTargets.put(Markup.DefinitionList, new DefinitionListOps());
		blockTargets.put(Markup.Table, new TableOps());
		blockTargets.put(Markup.TableHeader, new TableHeaderOps());
		blockTargets.put(Markup.QuotedParagraph, new QuotedParagraphOps());
		blockTargets.put(Markup.Paragraph, new ParagraphOps());
		
		// The set of beautifier transformers 
		beautifierTargets.put(Beautifiers.SoftLineBreak, new BeautifierOps("br"));
		beautifierTargets.put(Beautifiers.Bold, new BeautifierOps("strong"));
		beautifierTargets.put(Beautifiers.Italic, new BeautifierOps("i"));
		beautifierTargets.put(Beautifiers.Underline, new BeautifierOps("u"));
		beautifierTargets.put(Beautifiers.Strike, new BeautifierOps("del"));
		beautifierTargets.put(Beautifiers.Monospace, new BeautifierOps("code"));
		
		// Image and link transformers
		imageTargets = new ImageOps();
		linkTargets = new LinkOps();
		
	}
	
	/*
	 * @see org.ed.docGen.targets.TagSubstitutes#assembleBlockTag(java.lang.String, boolean)
	 */
	protected String assembleBlockTag(String tag, int titleLevel, boolean endTag) {
		StringBuilder t = new StringBuilder();
		
		if (tag != null && tag.length() > 0) {
			t.append('<');
			
			if (endTag) {
				String [] contents = tag.split(" ");
				
				t.append('/');
				t.append(contents[0]);
				
			}
			else {
				t.append(tag);
			}
			
			t.append('>');
			
			if (!endTag) {
				t.append(generateTitleCounter(titleLevel));
			}
			
		}
		
		return t.toString();
		
	}

	/*
	 * @see org.ed.docGen.targets.TagSubstitutes#assembleBeautifierTag(org.ed.docGen.Beautifiers, boolean)
	 */
	protected String assembleBeautifierTag(Beautifiers markup, boolean endTag) {
		StringBuilder t = new StringBuilder();
		TagOps tags = beautifierTargets.get(markup);
		
		if (tags != null && !tags.getTagList().isEmpty()) {
			t.append('<');
			
			if (endTag) {
				t.append('/');
			}
			
			t.append(tags.getTagList().get(0));
			t.append('>');
			
		}
		
		return t.toString();
		
	}
	
	/**
	 * Construct the tags needed to start a list block 
	 * @param text Text to be included in the block start
	 * @param tagList List of tags associated with the type of list
	 * @return All text and tags needed to define the start of a list block
	 */
	private String listTagStart(String text, List<String> tagList) {
		StringBuilder output = new StringBuilder();
		
		output.append(assembleBlockTag(tagList.get(0), false));
		output.append(Constants.newLine);
		output.append(assembleBlockTag(tagList.get(1), false));
		output.append(text);
		
		return output.toString();
		
	}

	/**
	 * Construct the tags needed for the content of a list item. 
	 * @param text Text to be included in the block start
	 * @param tagList List of tags associated with the type of list
	 * @return All text and tags needed to define a line of a list
	 */
	private String listItemTags(String text, List<String> tagList) {
		StringBuilder output = new StringBuilder();

		output.append(assembleBlockTag(tagList.get(1), true));
		output.append(Constants.newLine);
		output.append(assembleBlockTag(tagList.get(1), false));
		output.append(text);
		
		return output.toString();
		
	}

	/**
	 * Construct the tags needed to close a list block 
	 * @param tagList List of tags associated with the type of list
	 * @return All tags needed to define the end of a list block
	 */
	private String listEndTags(List<String> tagList) {
		StringBuilder output = new StringBuilder();
		
		output.append(assembleBlockTag(tagList.get(1), true));
		output.append(Constants.newLine);
		output.append(assembleBlockTag(tagList.get(0), true));
		output.append(Constants.newLine);
		
		return output.toString();
		
	}
	
	/**
	 * Generate a cell open/close tag
	 * @param td Data for formatting a table cell
	 * @param tagList List of tags to use with this substitution
	 * @param endTag true if this is to be an end tag
	 * @return A complete HTML cell tag
	 */
	protected String assembleCellTags(TableCell td,
			                          List<String> tagList, 
			                          boolean endTag) {
		StringBuilder t = new StringBuilder();
		StringBuilder styles = new StringBuilder();
		
		t.append('<');
		
		if (endTag) {
			t.append('/');
		}

		t.append(tagList.get(2));
		
		if (!endTag) {
			if (td.getColspan() > 1) {
				t.append(" colspan='");
				t.append(td.getColspan());
				t.append("'");
			}
			
			if (!td.getAlign().equals(Constants.TextAlign.left)) {
				styles.append("text-align: ");
				styles.append(td.getAlign().name());
				styles.append(";");
			}
			
			if (td.isHasBorder()) {
				styles.append(tblBorderSpecs);
			}
			
			if (styles.length() > 0) {
				t.append(" styles='");
				t.append(styles);
				t.append("'");
			}
			
		}
		
		t.append('>');
			
		return t.toString();
		
	}
	
	/**
	 * Generate table row tags
	 * @param endTag true to generate an end tag, false to generate a start tag
	 * @return A complete row delimiter tag
	 */
	protected String makeRowTags(boolean endTag) {
		StringBuilder tag = new StringBuilder();
		TagOps tags = blockTargets.get(Markup.Table);

		tag.append('<');
		
		if (endTag) {
			tag.append('/');
		}

		tag.append(tags.getTagList().get(1));
		tag.append('>');
		
		if (!endTag) {
			tag.append(Constants.newLine);
		}
		
		return tag.toString();
		
	}
	
	/**
	 * Construct the tags needed at the start of a table 
	 * @param text Text to be included in the table start
	 * @param tagList Tags associated with the table markup
	 * @param border true to include a border, otherwise false
	 * @param centered true to center the entire table, otherwise false
	 * @return All text and tags needed to define the start of a table
	 */
	protected String tableStartTags(String text, 
			                        List<String> tagList, 
			                        boolean border, 
			                        boolean centered) {
		StringBuilder output = new StringBuilder();
		StringBuilder style = new StringBuilder();

		output.append('<');
		output.append(tagList.get(0));
		
		if (border) {
			style.append(" style='");
			style.append(tblBorderSpecs);
			style.append("'");
		}
		
		if (centered) {
			style.append("margin-left: auto; margin-right: auto;");
		}
		
		if (style.length() > 0) {
			output.append(" style='");
			output.append(style);
			output.append("'");
		}
		
		output.append('>');
		
		output.append(Constants.newLine);
		
		return output.toString();
		
	}

	/**
	 * Construct the tags needed for the body of a table 
	 * @param cells Cells that comprise a row
	 * @param tagList Tags associated with the table markup
	 * @return All text and tags needed to define a row of cells
	 */
	protected String tableItemTags(TableCell [] cells, List<String> tagList) {
		StringBuilder row = new StringBuilder();

		for (TableCell cell : cells) {
			if (cell.getText() != null) {
				row.append(assembleCellTags(cell, tagList, false));
				row.append(cell.getText());
				row.append(assembleCellTags(cell, tagList, true));
				row.append(Constants.newLine);
			}
			
		}
		
		return row.toString();
	}

	/**
	 * Construct the tags needed to close a table
	 * @param tagList Tags associated with the table markup
	 * @return All tags needed to define the end of a table
	 */
	protected String tableEndTags(List<String> tagList) {
		StringBuilder output = new StringBuilder();
		
		output.append(assembleBlockTag(tagList.get(0), true));
		output.append(Constants.newLine);
		
		return output.toString();
		
	}
	
}
