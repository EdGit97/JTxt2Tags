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
 * Target class for generating Wiki from txt2tags markup
 * @author Ed Swaneck
 * @version 1.0
 * @since 09-02-2025
 */
public class WikiTags extends TagSubstitutes {

	/**
	 * Convert verbatim line markup to equivalent Wiki tags
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
	 * Convert verbatim area markup to equivalent Wiki tags
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
	 * Convert raw area markup to equivalent Wiki tags
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
	 * Convert tagged area markup to equivalent Wiki tags
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
	 * Convert separator markup to equivalent Wiki tags
	 */
	private class SeparatorOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public SeparatorOps() {
			super("----");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return tagList.get(0);
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
	 * Convert bold separator markup to equivalent Wiki tags
	 */
	private class BoldSeparatorOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public BoldSeparatorOps() {
			super("----");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return tagList.get(0);
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
	 * Convert title level 1 markup to equivalent Wiki tags
	 */
	private class TitleLevel1Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel1Ops() {
			super("'''= ", " ='''");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return tagList.get(0);
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
			return tagList.get(1);
		}
		
	}
	
	/**
	 * Convert title level 2 markup to equivalent Wiki tags
	 */
	private class TitleLevel2Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel2Ops() {
			super("== ", " ==");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return tagList.get(0);
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
			return tagList.get(1);
		}
		
	}
	
	/**
	 * Convert title level 3 markup to equivalent Wiki tags
	 */
	private class TitleLevel3Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel3Ops() {
			super("=== ", " ===");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return tagList.get(0);
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
			return tagList.get(1);
		}
		
	}
	
	/**
	 * Convert numbered title level 1 markup to equivalent Wiki tags
	 */
	private class NumberedTitleLevel1Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public NumberedTitleLevel1Ops() {
			super("'''= ", " ='''");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(tagList.get(0));
			
			output.append(generateTitleCounter(0));
			
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
			return tagList.get(1);
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
			super("== ", " ==");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(tagList.get(0));
			
			output.append(generateTitleCounter(1));
			
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
			return tagList.get(1);
		}
		
	}
	
	/**
	 * Convert numbered title level 3 markup to equivalent Wiki tags
	 */
	private class NumberedTitleLevel3Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public NumberedTitleLevel3Ops() {
			super("=== ", " ===");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(tagList.get(0));
			
			output.append(generateTitleCounter(2));
			
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
			return tagList.get(1);
		}
		
	}

	/**
	 * Convert un-ordered list markup to equivalent Wiki tags
	 */
	private class UnorderedListOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public UnorderedListOps() {
			super("*");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		@Override
		public String blockStartTags(String text) {
			return listItemTags(text, tagList);
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
			return "";
		}
		
	}
	
	/**
	 * Convert ordered list markup to equivalent Wiki tags
	 */
	private class OrderedListOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public OrderedListOps() {
			super("#");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return listItemTags(text, tagList);
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
			return "";
		}

	}
	
	/**
	 * Convert definition markup to equivalent Wiki tags
	 */
	private class DefinitionListOps extends DefinitionBlockTagOps {
		
		/**
		 * Constructor
		 */
		public DefinitionListOps() {
			super(";", ";", ":");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder();
			
			output.append(tagList.get(0));
			output.append(' ');
			output.append(text);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			StringBuilder output = new StringBuilder();

			output.append(tagList.get(1));
			output.append(' ');
			output.append(text);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return "";
		}

		/*
		 * @see org.ed.docGen.targets.DefinitionBlockTagOps#generateDescriptionDefinition(java.lang.String, boolean)
		 */
		@Override
		public String generateDescriptionDefinition(String text, boolean endTag) {
			StringBuilder output = new StringBuilder();

			output.append(tagList.get(2));
			output.append(' ');
			output.append(text);
			
			return output.toString();
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
			output.append(" ".repeat(4));
			output.append(text);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			StringBuilder output = new StringBuilder();

			output.append(" ".repeat(4));
			output.append(text);
			
			return output.toString();
			
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
	 * Convert paragraph markup to equivalent Wiki tags
	 */
	private class ParagraphOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public ParagraphOps() {
			super("");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return text;
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
	 * Convert beautifier markup to equivalent Wiki tags
	 */
	private class BeautifierOps extends BeautifierTagOps {
		private boolean needsBrackets;

		/**
		 * Constructor
		 * @param tag Text of the start and end tag
		 * @param needsBrackets true to add open/closing angle brackets to the tag
		 */
		public BeautifierOps(String tag, boolean needsBrackets) {
			
			super(tag);
			
			this.needsBrackets = needsBrackets;
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String itemTags(Beautifiers beautifier, String text) {
			String targetStartTag = assembleBeautifierTag(beautifier, false);
			String targetEndTag = assembleBeautifierTag(beautifier, true);
			
			return beautifier.beautify(text, targetStartTag, targetEndTag);

		}

		/**
		 * Getter
		 * @return true to add open/closing angle brackets to the tag
		 */
		public boolean isNeedsBrackets() {
			return needsBrackets;
		}

	}
	
	/**
	 * Convert table markup to Wiki table tags
	 */
	private class TableOps extends TableBlockTagOps {
		
		/**
		 * Constructor
		 */
		public TableOps() {
			super("{| cellpadding=\"4\" ", "|", "||", "|-", "|}");
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
			return tagList.get(4) + Constants.newLine;
		}
		
		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#generateRowTags(boolean)
		 */
		@Override
		public String generateRowTags(boolean endTag) {
			return tableRowTags(tagList, endTag);
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
	 * Convert table markup to Wiki table header tags
	 */
	private class TableHeaderOps extends TableBlockTagOps {
		
		/**
		 * Constructor
		 */
		public TableHeaderOps() {
			super("{| cellpadding=\"4\" ", "!", "!!", "|-", Constants.newLine + "|}");
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
			return tagList.get(4) + Constants.newLine;
		}
		
		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#generateRowTags(boolean)
		 */
		@Override
		public String generateRowTags(boolean endTag) {
			return tableRowTags(tagList, endTag);
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
	 * Convert image markup to an Wiki img tag
	 */
	private class ImageOps extends ImageLinkTagOps {
		
		/** Start tag of a Wiki image */
		public static final String startTag = "[[Image:"; 
		
		/** End tag of a Wiki image */
		public static final String endTag = "]]"; 
		
		/**
		 * Constructor
		 */
		public ImageOps() {
			super(startTag, endTag);
		}

		/*
		 * @see org.ed.docGen.targets.BeautifierTagOps#itemTags(org.ed.docGen.Beautifiers, java.lang.String)
		 */
		public String itemTags(ImageLinkData data) {
			StringBuilder img = new StringBuilder(startTag);
			
			img.append(data.getFileSpec());
			img.append(endTag);
			
			return img.toString();
			
		}
		
		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#alreadyProcessed(java.lang.String)
		 */
		public boolean alreadyProcessed(String potentialMarkup) {
			
			if (potentialMarkup != null && potentialMarkup.length() > 0) {
				return potentialMarkup.startsWith(startTag);
			}
			else {
				return false;
			}
			
		}
		
	}
	
	/**
	 * Convert link markup to an Wiki anchor tag
	 */
	private class LinkOps extends ImageLinkTagOps {
		
		/** Start tag of a Wiki link */
		public static final String startTag = "["; 
		
		/** End tag of a Wiki link */
		public static final String endTag = "]"; 
		
		
		/**
		 * Constructor
		 */
		public LinkOps() {
			super(startTag, endTag);
		}

		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#itemTags(org.ed.docGen.markup.ImageLinkData)
		 */
		public String itemTags(ImageLinkData data) {
			StringBuilder tag = new StringBuilder(startTag);
			
			tag.append(data.getFileSpec());
			
			if (data.getLabel() != null && data.getLabel().length() > 0) {
				if (data.getLabel().startsWith(ImageOps.startTag)) {
					tag.append(" (");
					tag.append(data.getLabel().substring(ImageOps.startTag.length(), 
							                             data.getLabel().length() - ImageOps.endTag.length()));
					tag.append(')');
					
				}
				else {
					tag.append(' ');
					tag.append(data.getLabel());
				}
				
			}
			
			tag.append(endTag);
			
			return tag.toString();
			
		}
		
		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#alreadyProcessed(java.lang.String)
		 */
		public boolean alreadyProcessed(String potentialMarkup) {
			
			if (potentialMarkup != null && potentialMarkup.length() > 0) {
				return potentialMarkup.endsWith(")" + endTag);
			}
			else {
				return false;
			}
			
		}
		
	}
	
	/**
	 * Constructor
	 */
	public WikiTags() {
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
		beautifierTargets.put(Beautifiers.SoftLineBreak, new BeautifierOps("\\\\", false));
		beautifierTargets.put(Beautifiers.Bold, new BeautifierOps("'''", false));
		beautifierTargets.put(Beautifiers.Underline, new BeautifierOps("u", true));
		beautifierTargets.put(Beautifiers.Italic, new BeautifierOps("''", false));
		beautifierTargets.put(Beautifiers.Strike, new BeautifierOps("{{Strikethroughdiv", false));
		beautifierTargets.put(Beautifiers.Monospace, new BeautifierOps("code", true));
		
		// Image and link transformers
		imageTargets = new ImageOps();
		linkTargets = new LinkOps();
		
	}
	
	/*
	 * @see org.ed.docGen.targets.TagSubstitutes#assembleBlockTag(java.lang.String, int, boolean)
	 */
	@Override
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
	 * @see org.ed.docGen.targets.TagSubstitutes#assembleBeautifierTag(org.ed.docGen.markup.Beautifiers, boolean)
	 */
	@Override
	protected String assembleBeautifierTag(Beautifiers markup, boolean endTag) {
		WikiTags.BeautifierOps tags = (WikiTags.BeautifierOps) beautifierTargets.get(markup);
		StringBuilder t = new StringBuilder();
		
		if (tags != null && !tags.getTagList().isEmpty()) {
			
			if (tags.isNeedsBrackets()) {
				t.append('<');
				
				if (endTag) {
					t.append('/');
				}
				
			}
			
			t.append(tags.getTagList().get(0));
			
			if (tags.isNeedsBrackets()) {
				t.append('>');
			}
			
		}
		
		return t.toString();
		
	}

	/**
	 * Construct the tags needed for the content of a list item. 
	 * @param text Text to be included in the block start
	 * @param tagList List of tags associated with the type of list
	 * @return All text and tags needed to define a line of a list
	 */
	private String listItemTags(String text, List<String> tagList) {
		StringBuilder output = new StringBuilder();

		output.append(tagList.get(0).repeat(listDepth + 1));
		output.append(' ');
		output.append(text);
		
		return output.toString();
		
	}

	/**
	 * Construct the tag needed at the start of a table 
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

		output.append(tagList.get(0));
		
		if (border) {
			output.append("border=\"1\" ");
		}
		
		if (centered) {
			output.append("align=\"center\" ");
		}

		output.append(Constants.newLine);
		
		return output.toString();
		
	}
	
	/**
	 * Close the current row and start a new one
	 * @param tagList Tags associated with the table markup
	 * @param endTag true to produce a row end tag, otherwise false
	 * @return Tags needed to start a row
	 */
	protected String tableRowTags(List<String> tagList, boolean endTag) {
		StringBuilder row = new StringBuilder(tagList.get(3)); 
		
		row.append(Constants.newLine);
		row.append(tagList.get(1));
		
		return (endTag ? "" : row.toString());

	}

	/**
	 * Construct the tags needed for the body of a table 
	 * @param cells Cells that comprise a row
	 * @param tagList Tags associated with the table markup
	 * @return All text and tags needed to define a table cell
	 */
	public String tableItemTags(TableCell [] cells, List<String> tagList) {
		StringBuilder row = new StringBuilder();
		
		for (int i = 0; i < cells.length; i++) {
		    TableCell cell = cells[i];
		    	
			if (cell.getText() != null && cell.getText().trim().length() > 0) {
				if (i > 0) {
					row.append(tagList.get(2));
				}
				
				row.append(' ');
				row.append(cell.getText());
				row.append(' ');
				
			}
			
		}
		
		return row.toString();
	}

}
