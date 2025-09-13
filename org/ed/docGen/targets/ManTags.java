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
import org.ed.docGen.markup.ImageTypes;
import org.ed.docGen.markup.Markup;
import org.ed.docGen.markup.TableCell;
import org.ed.utilities.NumericUtils;

/**
 * Target class for generating UNIX Manual markup from txt2tags markup
 * @author Ed Swaneck
 * @version 1.0
 * @since 09-05-2025
 * @see <a href="https://linux.die.net/man/7/man" target="_blank">man(7) - Linux man page</a>
 */
public class ManTags extends TagSubstitutes {
	
	/**
	 * Class to hold table wide parameters 
	 */
	private class TableOptions {
		private boolean centered;
		private boolean border;
		
		/**
		 * Constructor
		 * @param centered true if the table is to be centered
		 * @param border true if the table has a border
		 */
		public TableOptions(boolean centered, boolean border) {
			this.centered = centered;
			this.border = border;
		}

		/**
		 * Getter
		 * @return true if the table is to be centered
		 */
		public boolean isCentered() {
			return centered;
		}

		/**
		 * Getter
		 * @return true if the table has a border
		 */
		public boolean isBorder() {
			return border;
		}
		
	}

	/**
	 * Convert verbatim line markup to equivalent man tags
	 */
	private class VerbatimLineOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public VerbatimLineOps() {
			super(".nf", ".fi");
		}
		
		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder();

			output.append(tagList.get(0));
			output.append(Constants.newLine);
			output.append(text);
			output.append(Constants.newLine);
			output.append(tagList.get(1));
			
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
	 * Convert verbatim area markup to equivalent man tags
	 */
	private class VerbatimAreaOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public VerbatimAreaOps() {
			super(".nf", ".fi");
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
	 * Convert raw area markup to equivalent man tags
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
	 * Convert tagged area markup to equivalent man tags
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
	 * Convert separator markup to equivalent man tags
	 */
	private class SeparatorOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public SeparatorOps() {
			super();
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return "-".repeat(20);
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
	 * Convert bold separator markup to equivalent man tags
	 */
	private class BoldSeparatorOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public BoldSeparatorOps() {
			super("=".repeat(20));
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
	 * Convert title level 1 markup to equivalent man tags
	 */
	private class TitleLevel1Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel1Ops() {
			super(".TH ");
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
			return "";
		}
		
	}
	
	/**
	 * Convert title level 2 markup to equivalent man tags
	 */
	private class TitleLevel2Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel2Ops() {
			super(".SH");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return tagList.get(0) + " 2 ";
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
	 * Convert title level 3 markup to equivalent man tags
	 */
	private class TitleLevel3Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public TitleLevel3Ops() {
			super(".SS");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return tagList.get(0) + " 3 ";
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
	 * Convert numbered title level 1 markup to equivalent man tags
	 */
	private class NumberedTitleLevel1Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public NumberedTitleLevel1Ops() {
			super(".TH ");
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
			return "";
		}
		
	}
	
	/**
	 * Convert numbered title level 2 markup to equivalent man tags
	 */
	private class NumberedTitleLevel2Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public NumberedTitleLevel2Ops() {
			super(".SS");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(tagList.get(0));
			
			output.append(" 2 ");
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
			return "";
		}
		
	}
	
	/**
	 * Convert numbered title level 3 markup to equivalent man tags
	 */
	private class NumberedTitleLevel3Ops extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public NumberedTitleLevel3Ops() {
			super(".SS");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(tagList.get(0));
			
			output.append(" 3 ");
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
			return "";
		}
		
	}
	
	/**
	 * Convert un-ordered list markup to equivalent man tags
	 */
	private class UnorderedListOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public UnorderedListOps() {
			super(".RS", ".IP", ".RE");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		@Override
		public String blockStartTags(String text) {
			return tagList.get(0) + Constants.newLine + listItemTags(text, tagList, this);
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		@Override
		public String blockItemTags(String text) {
			return listItemTags(text, tagList, this);
		}

		/*
		 * @see org.ed.docGen.targets.BlockTagOps#blockEndTags()
		 */
		@Override
		public String blockEndTags() {
			return tagList.get(2) + Constants.newLine; 
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
			super(".RS", ".IP", ".RE");
			
			if (listDepth < listCounter.length) {
				listCounter[listDepth] = 0;
			}
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			return tagList.get(0) + Constants.newLine + listItemTags(text, tagList, this); 
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			return listItemTags(text, tagList, this);
		}

		/*
		 * @see org.ed.docGen.targets.BlockTagOps#blockEndTags()
		 */
		@Override
		public String blockEndTags() {
			return tagList.get(2) + Constants.newLine; 
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
			super(".TP");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder();
			
			output.append(tagList.get(0));
			output.append(Constants.newLine);
			output.append(text);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			StringBuilder output = new StringBuilder();

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
			return text;
		}

	}
	
	/**
	 * Convert quoted paragraph markup to equivalent man tags
	 */
	private class QuotedParagraphOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public QuotedParagraphOps() {
			super(".P");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(tagList.get(0));
			
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
			return "";
		}
		
	}
	
	/**
	 * Convert paragraph markup to equivalent man tags
	 */
	private class ParagraphOps extends BlockTagOps {
		
		/**
		 * Constructor
		 */
		public ParagraphOps() {
			super(".P");
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockStartTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockStartTags(String text) {
			StringBuilder output = new StringBuilder(tagList.get(0));
			
			output.append(Constants.newLine);
			output.append(text);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockItemTags(org.ed.docGen.ProcessStatus, java.lang.String)
		 */
		public String blockItemTags(String text) {
			StringBuilder output = new StringBuilder();
			
			output.append(text);
			
			return output.toString();
			
		}

		/*
		 * @see org.ed.docGen.targets.TagSubstitutes.tagOps#blockEndTags(org.ed.docGen.ProcessStatus)
		 */
		public String blockEndTags() {
			return "";
		}
		
	}
	
	/**
	 * Convert beautifier markup to equivalent man tags
	 */
	private class BeautifierOps extends BeautifierTagOps {

		/**
		 * Constructor
		 * @param tag Text of the start and end tag
		 */
		public BeautifierOps(String... tag) {
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
	 * Convert table markup to man table tags
	 */
	private class TableOps extends TableBlockTagOps {
		private TableOptions tOps = null;
		
		/**
		 * Constructor
		 */
		public TableOps() {
			super(".TS" + Constants.newLine, " ", "^", ".TE");
		}

		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#blockStartTags(java.lang.String, boolean, boolean)
		 */
		@Override
		public String blockStartTags(String text, boolean border, boolean centered) {
			tOps = new TableOptions(centered, border);
			
			return tagList.get(0);
			
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
			return "";
		}

		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#blockItemTags(org.ed.docGen.markup.TableCell[])
		 */
		@Override
		public String blockItemTags(TableCell [] columns) {
			StringBuilder row = new StringBuilder();
			
			if (tOps != null) {
				row.append(generateTableParms(tOps, columns));
				tOps = null;
			}
			
			row.append(' ');
			
			for (int i = 0; i < columns.length; i++) {
				TableCell column = columns[i];
				
				if (column.getText() != null) {
					row.append((i > 0 ? "^" : ""));
					row.append(column.getText());
				}
				
			}
			
			return row.toString();

		}
		
	}
	
	/**
	 * Convert table header markup to man table tags
	 */
	private class TableHeaderOps extends TableBlockTagOps {
		private TableOptions tOps = null;
		
		/**
		 * Constructor
		 */
		public TableHeaderOps() {
			super(".TS" + Constants.newLine, " ", "^");
		}

		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#blockStartTags(java.lang.String, boolean)
		 */
		@Override
		public String blockStartTags(String text, boolean border, boolean centered) {
			tOps = new TableOptions(centered, border);
			
			return tagList.get(0);
			
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
			return "";
		}

		/*
		 * @see org.ed.docGen.targets.TableBlockTagOps#blockItemTags(org.ed.docGen.markup.TableCell[])
		 */
		@Override
		public String blockItemTags(TableCell [] columns) {
			StringBuilder row = new StringBuilder();
			
			if (tOps != null) {
				row.append(generateTableParms(tOps, columns));
				tOps = null;
			}
			
			row.append(' ');
			
			for (int i = 0; i < columns.length; i++) {
				TableCell column = columns[i];
				
				if (column.getText() != null) {
					row.append((i > 0 ? "^" : ""));
					row.append("\\fB");
					row.append(column.getText());
					row.append("\\fR");
				}
				
			}
			
			return row.toString();

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
			super("");
		}

		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#itemTags(org.ed.docGen.markup.ImageLinkData)
		 */
		public String itemTags(ImageLinkData data) {
			return data.getFileSpec();
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
			super("(", ")");
		}

		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#itemTags(org.ed.docGen.markup.ImageLinkData)
		 */
		public String itemTags(ImageLinkData data) {
			StringBuilder link = new StringBuilder();
			
			if (ImageTypes.isImage(data.getLabel())) {
				link.append(tagList.get(0));
				link.append(data.getLabel());
				link.append(tagList.get(1));
			}
			else {
				link.append(data.getLabel());
			}
			
			link.append(' ');
			link.append(tagList.get(0));
			link.append(data.getFileSpec());
			link.append(tagList.get(1));
			
			return link.toString();
			
		}
		
		/*
		 * @see org.ed.docGen.targets.ImageLinkTagOps#alreadyProcessed(java.lang.String)
		 */
		public boolean alreadyProcessed(String potentialMarkup) {
			return false;
		}
		
	}
	
	private int [] listCounter = { 0, 0, 0, 0, 0 };
	
	/**
	 * Constructor
	 */
	public ManTags() {
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
		beautifierTargets.put(Beautifiers.SoftLineBreak, new BeautifierOps(""));
		beautifierTargets.put(Beautifiers.Bold, new BeautifierOps("\\fB", "\\fR"));
		beautifierTargets.put(Beautifiers.Italic, new BeautifierOps("\\fI", "\\fR"));
		beautifierTargets.put(Beautifiers.Underline, new BeautifierOps(""));
		beautifierTargets.put(Beautifiers.Strike, new BeautifierOps(""));
		beautifierTargets.put(Beautifiers.Monospace, new BeautifierOps(""));
		
		// Image and link transformers
		imageTargets = new ImageOps();
		linkTargets = new LinkOps();
		
	}
	
	/**
	 * Construct the tags needed for the content of a list item. 
	 * @param text Text to be included in the block start
	 * @param tagList List of tags associated with the type of list
	 * @param markup Type of the list
	 * @return All text and tags needed to define a line of a list
	 */
	private String listItemTags(String text, List<String> tagList, BlockTagOps markup) {
		StringBuilder output = new StringBuilder(tagList.get(1));
		
		if (UnorderedListOps.class.equals(markup.getClass())) {
			output.append(" \\(bu ");
		}
		else {
			output.append(" ");
			
			if (listDepth < listCounter.length) {
				output.append(counterConvert(listDepth, ++listCounter[listDepth]));
			}
			else {
				output.append("x");
			}
			
			output.append(" ");
		}
		
		output.append(String.valueOf(3 * listDepth));
		output.append(Constants.newLine);
		output.append(text);
		
		return output.toString();
		
	}

	/**
	 * Generate the table wide parameters for a table
	 * @param tOps Table options that may be included on the options line
	 * @param columns Cell data for the row
	 * @return A man page table options line
	 */
	protected String generateTableParms(TableOptions tOps, TableCell [] columns) {
		StringBuilder row = new StringBuilder();
		
		if (tOps.isBorder()) {
			row.append("allbox, ");
		}
		
		if (tOps.isCentered()) {
			row.append("center, ");
		}
		
		row.append("tab(^); ");
		
		for (TableCell column : columns) {
			row.append(column.getAlign().name().charAt(0));
		}
		
		row.append('.');
		row.append(Constants.newLine);
		
		return row.toString();
		
	}

	/**
	 * Construct the tags needed to close a table
	 * @param tagList Tags associated with the table markup
	 * @return All tags needed to define the end of a table
	 */
	protected String tableEndTags(List<String> tagList) {
		StringBuilder output = new StringBuilder();
		
		output.append(tagList.get(3));
		output.append(Constants.newLine);
		
		return output.toString();
		
	}

	/*
	 * @see org.ed.docGen.targets.TagSubstitutes#assembleBlockTag(java.lang.String, int, boolean)
	 */
	@Override
	protected String assembleBlockTag(String tag, int titleLevel, boolean endTag) {
		// Not used
		return null;
	}

	/*
	 * @see org.ed.docGen.targets.TagSubstitutes#assembleBeautifierTag(org.ed.docGen.markup.Beautifiers, boolean)
	 */
	@Override
	protected String assembleBeautifierTag(Beautifiers markup, boolean endTag) {
		StringBuilder t = new StringBuilder();
		TagOps tags = beautifierTargets.get(markup);
		
		if (tags != null && tags.getTagList().size() > 1) {
			t.append(tags.getTagList().get(endTag ? 1 : 0));
		}
		
		return t.toString();
		
	}
	
	/**
	 * Convert a list counter value to the proper display for the depth 
	 * @param depth Current depth of the list
	 * @param value Numeric value to convert
	 * @return The formatted value to display
	 */
	private String counterConvert(int depth, int value) {
		
		if (depth == 0 || depth == 3) {
			return String.valueOf(value);
		}
		else if (depth == 1 || depth == 4) {
			return String.valueOf(NumericUtils.numericToLowerAlpha(value));
		}
		else {
			return NumericUtils.arabicToRoman(value);
		}
		
	}

}
