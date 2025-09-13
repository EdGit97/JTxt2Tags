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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ed.docGen.Constants;
import org.ed.docGen.ProcessStatus;
import org.ed.docGen.targets.DefinitionBlockTagOps;
import org.ed.utilities.StringUtils;

/**
 * List of txt2tags markup and associated data
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-11-2025
 */
public enum Markup {

	/** 
	 * A single line starting with three back ticks and a space and
	 * ending at the end of the line.  Beautifiers are not processed. 
	 * Example:<br>
	 * ``` some text&lt;new line>
	 */
	VerbatimLine("``` ", Constants.newLine, false, true, true) {
		
		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartTagOnly(inLine); 
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes)
		 */
		public void process(String inLine, ProcessStatus status) {
			String text = inLine.substring(this.getStartTag().length());
			
			status.setOutLine(status.runStartBlockOp(text));
			status.setMode(null);
			status.setReprocess(false);
			
		}
		
		
	},
	
	/** 
	 * A group of lines starting with a line of only three back ticks
	 * and ending with a line of only three back ticks.<br>
	 * Example:<br>
	 * ```<br>
	 * Any text will be displayed exactly as<br>
	 * entered, respecting spaces and new lines.<br>
	 * Beautifiers will not be processed.<br>
	 * ```
	 */
	VerbatimArea("```", false, true, false) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return inLine != null && inLine.trim().equals(getStartTag()); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processArea(this, inLine, status);
		}

	},

	/** 
	 * A group of lines starting with a line of only three double quotes
	 * and ending with a line of only three double quotes.<br>
	 * Example:<br>
	 * """<br>
	 * Any text will be displayed as entered.<br>
	 * Beautifiers are not processed and spacing will not be preserved.<br>
	 * """
	 */
	RawArea("\"\"\"", false, true, false) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return inLine != null && inLine.trim().equals(getStartTag()); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processArea(this, inLine, status);
		}

	},
	
	/** 
	 * A group of lines starting with a line of only three single quotes
	 * and ending with a line of only three single quotes.<br>
	 * Example:<br>
	 * '''<br>
	 * Any text will be displayed as entered.<br>
	 * Beautifiers are not processed and spacing will not be preserved.<br>
	 * '''
	 */
	TaggedArea("'''", false, true, false) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return inLine != null && inLine.trim().equals(getStartTag()); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processArea(this, inLine, status);
		}

	},
	
	/** 
	 * A line starting with at least 20 hyphens<br>
	 * Example:<br>
	 * --------------------
	 */
	Separator("-".repeat(Constants.minSepLen), Constants.newLine, false, false, true) {
		
        /*
         * @see org.ed.docGen.Markup#isThis(java.lang.String)
         */
		public boolean isThis(String inLine) {
			return inLine != null && inLine.startsWith(getStartTag());
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			
			status.setOutLine(status.runStartBlockOp(inLine));
			status.setMode(null);
			status.setReprocess(false);
			
		}
		
	},

	/** 
	 * A line starting with at least 20 equals signs<br>
	 * Example:<br>
	 * ====================
	 */
	BoldSeparator("=".repeat(Constants.minSepLen), Constants.newLine, false, false, true) {
		
        /*
         * @see org.ed.docGen.Markup#isThis(java.lang.String)
         */
		public boolean isThis(String inLine) {
			return inLine != null && inLine.startsWith(getStartTag());
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			
			status.setOutLine(status.runStartBlockOp(inLine));
			status.setMode(null);
			status.setReprocess(false);
			
		}
		
	},
	
	/** 
	 * A line starting with an equals sign and a single space and 
	 * ending with a single space and an equals sign.br>
	 * Example:<br>
	 * = Title Text =
	 */
	TitleLevel1("= ", " =", true, true, true) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartAndEndTags(inLine);
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processOneLine(this, inLine, status);			
		}
		
	},

	/** 
	 * A line starting with two equals signs and a single space and 
	 * ending with a single space and two equals signs<br>
	 * Example:<br>
	 * == Title Text ==
	 */
	TitleLevel2("== ", " ==",true, true, true) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartAndEndTags(inLine);
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processOneLine(this, inLine, status);			
		}
		
	},

	/** 
	 * A line starting with three equals signs and a single space and 
	 * ending with a single space and three equals signs<br>
	 * Example:<br>
	 * === Title Text ===
	 */
	TitleLevel3("=== ", " ===", true, true, true) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartAndEndTags(inLine);
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processOneLine(this, inLine, status);			
		}
		
	},

	/** 
	 * A line starting with a plus sign and a single space and 
	 * ending with a single space and a plus sign.<br>
	 * Example:<br>
	 * + Title Text +
	 */
	NumberedTitleLevel1("+ ", " +", true, true, true) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartAndEndTags(inLine);
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processOneLine(this, inLine, status);			
		}
		
	},

	/** 
	 * A line starting with two plus signs and a single space and 
	 * ending with a single space and two plus signs.<br> 
	 * Example:<br>
	 * ++ Title Text ++
	 */
	NumberedTitleLevel2("++ ", " ++", true, true, true) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartAndEndTags(inLine);
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processOneLine(this, inLine, status);			
		}
		
	},

	/** 
	 * A line starting with three plus signs and a single space and 
	 * ending with a single space and three plus signs.<br>
	 * Example:<br>
	 * +++ Title Text +++
	 */
	NumberedTitleLevel3("+++ ", " +++", true, true, true) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartAndEndTags(inLine);
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			processOneLine(this, inLine, status);			
		}
		
	},

	/** 
	 * A group of lines starting with a line of only three percent signs
	 * and ending with a line of only three percent signs.<br>
	 * Example:<br>
	 * %%%<br>
	 * Any text will be ignored.<br>
	 * %%%
	 */
	ToDoBlock("%%%", "%%%", false, false, true) {
		
		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return inLine != null && inLine.trim().equals(getStartTag()); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			
			if (this.getStartTag().equals(inLine) && !status.isContinuation()) {
				status.setMode(this);
			}
			else if (this.getEndTag().equals(inLine)) {
				status.setMode(null);
			}
			else {
				status.setMode(this);
			}
			
			status.setOutLine(null);
			status.setReprocess(false);
			
		}

	},
	
	/** 
	 * A single line starting with a percent sign and a space and
	 * ending at the end of the line. 
	 * Example:<br>
	 * % This text is ignored&lt;new line>
	 */
	ToDo("% ", Constants.newLine, false, false, true) {
		
		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartTagOnly(inLine); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			
			status.setMode(null);
			status.setOutLine(null);
			status.setReprocess(false);
			
		}
		
	},
	
	/** 
	 * A line starting with a hyphen and a space, then more lines starting 
	 * with a hyphen and a space followed by a line that contains only a hyphen
	 * or a line not starting with a hyphen.<br>
	 * Example:<br>
	 * - Line one of the list<br>
	 * - Line two of the list<br>
	 * -
	 */
	UnorderedList("- ", true, false, false) {
		
		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartTagOnly(inLine.trim()); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			ListProcessor lp = new ListProcessor();
			
			lp.processList(inLine, status, this);
			
		}
		
	},

	/** 
	 * A line starting with a plus sign and a space, then more lines starting 
	 * with a plus sign and a space followed by a line that contains only a plus
	 * sign or a line not starting with a plus sign.<br> 
	 * Example:<br>
	 * + Line one of the list<br>
	 * + Line two of the list<br>
	 * +
	 */
	OrderedList("+ ", true, false, false) {
		
		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartTagOnly(inLine.trim()); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			ListProcessor lp = new ListProcessor();
			
			lp.processList(inLine, status, this);
			
		}
		
	},

	/** 
	 * A line starting with a colon and a space, followed by a line starting 
	 * with two spaces, followed by another line that starts with a colon and
	 * a space, etc.  Ending with a line that is a single colon or a line
	 * that starts on the first column and does not start with a colon and a 
	 * space.<br>   
	 * Example:<br>
	 * : Term 1<br>
	 *   Term 1 definition<br>
	 * : Term 2<br>
	 *   Term 2 definition<br>
	 * :
	 */
	DefinitionList(": ", true, false, false) {
		
		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartTagOnly(inLine); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			StringBuilder outLine = new StringBuilder();
			
			if (status.isContinuation()) {
				if (inLine.trim().length() <= 0 ||
					(inLine.trim().length() == 1 && 
					 inLine.trim().charAt(0) == this.getStartTag().charAt(0))) {
					status.setOutLine(status.runEndBlockOp());
					status.setMode(null);
					
				}
				else if (inLine.startsWith(this.getStartTag())) {
					String text = MarkupUtils.runInlineSubstitutions(inLine.trim().replaceFirst(StringUtils.escRegex(this.getStartTag()), ""), status.getTargetTags());
					
					outLine.append(status.runItemBlockOp(text));
					status.setMode(this);
					status.setOutLine(outLine.toString());
					
				}
				else {
					DefinitionBlockTagOps op = ((DefinitionBlockTagOps) status.getTargetTags().getBlockTargets().get(this));
					
					outLine.append(op.generateDescriptionDefinition(MarkupUtils.runInlineSubstitutions(inLine.trim(), status.getTargetTags()), false));

					status.setMode(this);
					status.setOutLine(outLine.toString());
					
				}
				
			}
			else {
				String text = MarkupUtils.runInlineSubstitutions(inLine.trim().replaceFirst(StringUtils.escRegex(this.getStartTag()), ""), status.getTargetTags());
				
				outLine.append(status.runStartBlockOp(text));
				status.setMode(this);
				status.setOutLine(outLine.toString());
				
			}
			
			status.setReprocess(false);
			
		}
		
	},
	
	/** 
	 * A line starting with a vertical bar (|) and containing
	 * more vertical bars to delimit the columns.  If the first 
	 * line ends with a vertical bar the table and all of the
	 * cells will have a solid border.  The table ends with a 
	 * line that is a single vertical bar or a line that does 
	 * not start with a vertical bar.  To center the table,
	 * place spaces before the first vertical bar.  Each cell
	 * must have a starting and ending space.  Placing more 
	 * spaces on the left or the right will cause the cell to
	 * align to the left or the right.<br> 
	 * Example, bordered table:<br>
	 * | Cell 1a | Cell 1b | Cell 1c |<br>
	 * | Cell 2a | Cell 2b | Cell 2c |<br>
	 * | Cell 3a | Cell 3b | Cell 3c |
	 */
	Table("| ", "|", true, false, false) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return TableProcessor.isTableLine(inLine, this);
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			TableProcessor tp = new TableProcessor();
			
			tp.processTable(inLine, status, this);
			
		}
		
	},

	/** 
	 * A line starting with two vertical bars (||) and containing
	 * more vertical bars to delimit the columns.  If the first 
	 * line ends with a vertical bar the table and all of the
	 * cells will have a solid border.  The table ends with a 
	 * line that is a single vertical bar or a line that does 
	 * not start with a vertical bar.  To center the table,
	 * place spaces before the first vertical bar.  Each cell
	 * must have a starting and ending space.  Placing more 
	 * spaces on the left or the right will cause the cell to
	 * align to the left or the right.<br> 
	 * Example, bordered table with the first line as a header:<br>
	 * || Cell 1a | Cell 1b | Cell 1c |<br>
	 * | Cell 2a | Cell 2b | Cell 2c |<br>
	 * | Cell 3a | Cell 3b | Cell 3c |
	 */
	TableHeader("|| ", "|", true, true, true) {

		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return TableProcessor.isTableLine(inLine, this);
		}
		
		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			TableProcessor tp = new TableProcessor();
			
			tp.processTable(inLine, status, this);
			
		}
		
	},

	/** 
	 * A line starting with a tab character.  Every succeeding line
	 * that begins with a tab will be included in the indented paragraph.
	 * End with a blank line or a line that does not start with a tab.<br>
	 * Example:<br>
	 *     Quoted line 1.<br>
	 *     Quoted line 2.
	 */
	QuotedParagraph(Constants.tab, "", true, false, false) {
		
		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return isOneLinerWithStartTagOnly(inLine); 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			
			if (status.isContinuation()) {
				if (inLine.trim().length() <= 0) {
					status.setMode(null);
					status.setOutLine(status.runEndBlockOp());
					status.setReprocess(false);
				}
				else if (blockStartTags.containsKey(inLine) || !inLine.startsWith(this.getStartTag())) {
					status.setOutLine(status.runEndBlockOp());
					status.setMode(null);
					status.setReprocess(true);
				}
				else {
					String text = MarkupUtils.runInlineSubstitutions(inLine.trim(), status.getTargetTags());
					
					status.setMode(this);
					status.setOutLine(status.runItemBlockOp(text));
					status.setReprocess(false);
				}
				
			}
			else {
				String text = MarkupUtils.runInlineSubstitutions(inLine.trim(), status.getTargetTags());
				
				status.setMode(this);
				status.setOutLine(status.runStartBlockOp(text));
				status.setReprocess(false);
				
			}
			
		}
		
	},
	
	/** 
	 * A line of text that does not match any other markup. The paragraph
	 * is terminated by a blank line or a line that matches some other
	 * markup.<br>
	 * Example:<br>
	 * Some text that will be part of a paragraph.
	 */
	Paragraph("", true, false, false) {
		
		/*
		 * @see org.ed.docGen.Markup#isThis(java.lang.String)
		 */
		public boolean isThis(String inLine) {
			return inLine != null && inLine.length() > 0; 
		}

		/*
		 * @see org.ed.docGen.Markup#process(java.lang.String, org.ed.docGen.targets.TagSubstitutes, boolean)
		 */
		public void process(String inLine, ProcessStatus status) {
			
			if (status.isContinuation()) {
				if (inLine.trim().length() <= 0) {
					status.setOutLine(status.runEndBlockOp());
					status.setMode(null);
					status.setReprocess(false);
				}
				else if (blockStartTags.containsKey(inLine)) {
					status.setOutLine(status.runEndBlockOp());
					status.setMode(null);
					status.setReprocess(true);
				}
				else {
					String [] parts = inLine.split(" ");
					
					if (parts.length > 1 && blockStartTags.containsKey(parts[0] + " ")) {
						status.setOutLine(status.runEndBlockOp());
						status.setMode(null);
						status.setReprocess(true);
					}
					else if (parts.length == 1 &&
							 (inLine.startsWith(Separator.getStartTag()) ||
							  inLine.startsWith(BoldSeparator.getStartTag()))) {
						status.setOutLine(status.runEndBlockOp());
						status.setMode(null);
						status.setReprocess(true);
					}
					else {
						status.setMode(this);
						status.setOutLine(MarkupUtils.runInlineSubstitutions(inLine, status.getTargetTags()));
						status.setReprocess(false);
					}
					
				}
				
			}
			else {
				String text = MarkupUtils.runInlineSubstitutions(inLine, status.getTargetTags());
				
				status.setOutLine(status.runStartBlockOp(text));
				status.setMode(this);
				status.setReprocess(false);
				
			}
			
		}
		
	};
	
	/** Map to allow fast matching of block markup */
	public static final EnumSet<Markup> blocks = EnumSet.of(Markup.VerbatimLine,
			                                                Markup.VerbatimArea,
			                                                Markup.RawArea,
			                                                Markup.TaggedArea,
			                                                Markup.Separator,
			                                                Markup.BoldSeparator,
			                                                Markup.TitleLevel1,
			                                                Markup.TitleLevel2,
			                                                Markup.TitleLevel3,
			                                                Markup.NumberedTitleLevel1,
			                                                Markup.NumberedTitleLevel2,
			                                                Markup.NumberedTitleLevel3,
			                                                Markup.ToDoBlock,
			                                                Markup.ToDo,
			                                                Markup.UnorderedList,
			                                                Markup.OrderedList,
			                                                Markup.DefinitionList,
			                                                Markup.Table,
			                                                Markup.TableHeader,
			                                                Markup.QuotedParagraph,
			                                                Markup.Paragraph);

	private static final Map<String, Markup> blockStartTags = new HashMap<>();
	private static final Map<String, Markup> blockEndTags = new HashMap<>();
	
	private String startTag;
	private String endTag;
	private boolean runBeautifiers;
	private boolean endTagRequired;
	private boolean oneLineResults;
	private int indent;
	
	/**
	 * Initialize the tag maps
	 */
	static {
		for (Markup m : Markup.values()) {
			blockStartTags.put(m.getStartTag(), m);
			blockEndTags.put(m.getEndTag(), m);
		}
	}
	
	/**
	 * Constructor
	 * @param startTag txt2tags tag that begins the mode
	 * @param runBeautifiers true to run beautifiers on each line
	 * @param endTagRequired true if the end tag is required to end the mode, otherwise false
	 * @param oneLineResults true if the results will be on a single line, false for multiple lines
	 */
	private Markup(String startTag, 
			       boolean runBeautifiers, 
			       boolean endTagRequired, 
			       boolean oneLineResults) {
		this.startTag = startTag;
		this.endTag = startTag;
		this.runBeautifiers = runBeautifiers;
		this.endTagRequired = endTagRequired;
		this.oneLineResults = oneLineResults;
	}

	/**
	 * Constructor
	 * @param startTag txt2tags tag that begins the mode
	 * @param endTag txt2tags tag that ends the mode
	 * @param runBeautifiers true to run beautifiers on each line 
	 * @param endTagRequired true if the end tag is required to end the mode, otherwise false
	 * @param oneLineResults true if the results will be on a single line, false for multiple lines
	 */
	private Markup(String startTag, 
			       String endTag, 
			       boolean runBeautifiers, 
			       boolean endTagRequired, 
			       boolean oneLineResults) {
		this.startTag = startTag;
		this.endTag = endTag;
		this.runBeautifiers = runBeautifiers;
		this.endTagRequired = endTagRequired;
		this.oneLineResults = oneLineResults;
	}

	/**
	 * Determine if the line represents markup of the instantiated type
	 * @param inLine The line to evaluate
	 * @return true if the line represents markup for the type, otherwise false
	 */
	public abstract boolean isThis(String inLine);
	
	/**
	 * Process the data for a markup
	 * @param inLine The string to process
	 * @param status Current status of the process
	 */
	public abstract void process(String inLine, ProcessStatus status);

	/**
	 * Check for markup that is a single line of text with only a start tag 
	 * @param inLine The line to process
	 * @return true if the line represents some single line markup, otherwise false 
	 */
	public boolean isOneLinerWithStartTagOnly(String inLine) {
		
		return inLine != null && 
			   inLine.length() > 0 && 
			   inLine.startsWith(getStartTag());
				   
	}
	
	/**
	 * Check for markup that is a single line of text with start and end tags 
	 * @param inLine The line to process
	 * @return true if the line represents some single line markup, otherwise false 
	 */
	public boolean isOneLinerWithStartAndEndTags(String inLine) {
		return inLine != null && inLine.startsWith(getStartTag()) &&
				   inLine.endsWith(getEndTag());
	}
	
	/**
	 * Process an area
	 * @param markup The type of markup to process
	 * @param inLine The string to process
	 * @param status The current processing state
	 * @param continuation true if this is a continuation from the previous line
	 */
	private static void processArea(Markup markup, 
			                        String inLine, 
			                        ProcessStatus status) {
		StringBuilder outLine = new StringBuilder();
		
		if (markup.getStartTag().equals(inLine) && !status.isContinuation()) {
			outLine.append(status.runStartBlockOp(inLine));
			status.setMode(markup);
		}
		else if (markup.getEndTag().equals(inLine)) {
			outLine.append(status.runEndBlockOp());
			status.setMode(null);
		}
		else {
			outLine.append(inLine);
			status.setMode(markup);
		}
		
		status.setOutLine(outLine.toString());
		status.setReprocess(false);
		
	}

	/**
	 * Process a single line markup
	 * @param markup The type of markup to process
	 * @param inLine The string to process
	 * @param status Current status of the process
	 */
	private static void processOneLine(Markup markup, String inLine, ProcessStatus status) {
		String replace = status.runStartBlockOp(inLine);
		String outLine = String.valueOf(inLine);
		
		outLine = outLine.replaceFirst(StringUtils.escRegex(markup.getStartTag()), replace);
		replace = status.runEndBlockOp();
		outLine = outLine.replaceFirst(StringUtils.escRegex(markup.getEndTag()), replace);

		// ToDo: Process images and links
		outLine = MarkupUtils.runInlineSubstitutions(outLine, status.getTargetTags());
		
		status.setMode(null);
		status.setOutLine(outLine.toString());
		status.setReprocess(false);
		
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
	 * @return run the beautifiers after initial processing
	 */
	public boolean isRunBeautifiers() {
		return runBeautifiers;
	}
	
	/**
	 * Getter
	 * @return true if the section requires a matching end tag to complete, otherwise false
	 */
	public boolean isEndTagRequired() {
		return endTagRequired;
	}

	/**
	 * Getter
	 * @return true if the results will be on a single line, false for multiple lines
	 */
	public boolean isOneLineResults() {
		return oneLineResults;
	}

	/**
	 * Getter
	 * @return The map of the block tag start tags
	 */
	public static Map<String, Markup> getStartTagMap() {
		return blockStartTags;
	}
	
	/**
	 * Getter
	 * @return The map of the block tag end tags
	 */
	public static Map<String, Markup> getEndTagMap() {
		return blockEndTags;
	}

	/**
	 * Getter
	 * @return The amount of indent for this markup
	 */
	public int getIndent() {
		return indent;
	}

	/**
	 * Setter 
	 * @param indent The amount of indent for this markup
	 */
	public void setIndent(int indent) {
		this.indent = indent;
	}
	
}
