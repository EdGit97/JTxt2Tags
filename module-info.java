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
/**
 * <h2>Text-to-Tags API for Java Projects</h2>
 * <p>
 * The purpose of this project is to implement a subset of txt2tags markup,
 * in Java, that will process individual lines and paragraphs, not a complete 
 * document.  The results can then be received by a calling program for further 
 * processing. The reason for using txt2tags is to allow users that are not 
 * familiar with more robust markup languages to apply basic formatting to 
 * text that will later be included in other documents such as a web page.
 * </p>
 * <p>
 * Reviewing the txt2tags website, it can be seen that the language has 
 * somewhat exceeded the idea of a "light weight" markup language.  
 * Nevertheless, the basics are very usable by non-technical people.  This 
 * API presents two entry points that allow for the processing of a single 
 * line of text or a list/array of lines.
 * </p>
 * <h2>Supported txt2tag Targets</h2>
 * <ul>
 *     <li>HTML</li>
 *     <li>UNIX Manual Page</li>
 *     <li>Wiki</li>
 * </ul>
 * <h2>Supported txt2tag Syntax</h2>
 * <ul>
 *     <li>{@link org.ed.docGen.markup.Beautifiers#Bold Bold}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#BoldSeparator BoldSeparator}</li>
 *     <li>{@link org.ed.docGen.markup.Beautifiers#Italic Italic}</li>
 *     <li>{@link org.ed.docGen.markup.Beautifiers#Monospace Monospace}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#DefinitionList Definition List}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#NumberedTitleLevel1 Numbered Title Level 1}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#NumberedTitleLevel2 Numbered Title Level 2}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#NumberedTitleLevel3 Numbered Title Level 3}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#OrderedList Ordered List}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#Paragraph Paragraph}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#QuotedParagraph Quoted Paragraph}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#RawArea Raw Area}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#Separator Separator}</li>
 *     <li>{@link org.ed.docGen.markup.Beautifiers#SoftLineBreak Soft line-break}</li>
 *     <li>{@link org.ed.docGen.markup.Beautifiers#Strike Strike-out}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#Table Table}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#TableHeader Table Header}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#TaggedArea Tagged Area}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#TitleLevel1 Title Level 1}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#TitleLevel2 Title Level 2}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#TitleLevel3 Title Level 3}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#ToDo To-Do Line}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#ToDoBlock To-Do Block}</li>
 *     <li>{@link org.ed.docGen.markup.Beautifiers#Underline Underline}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#UnorderedList Unordered List}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#VerbatimArea Verbatim Area}</li>
 *     <li>{@link org.ed.docGen.markup.Markup#VerbatimLine Verbatim Line}</li>
 * </ul>
 * <p>See also:</p>
 * <ul>
 *     <li><a href='https://txt2tags.org/markup.html' target='_blank'>txt2tags - Markup Demo</a></li>
 *     <li><a href='https://en.wikipedia.org/wiki/Txt2tags' target='_blank'>Wikipedia - txt2tags</a></li>
 * </ul>
 * 
 */
module docGen {
	exports org.ed.docGen;
	exports org.ed.docGen.targets;
	exports org.ed.docGen.markup;
	opens org.ed.utilities;
}