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

import java.util.ArrayList;
import java.util.List;

import org.ed.docGen.Constants;
import org.ed.docGen.ProcessStatus;
import org.ed.docGen.targets.TableBlockTagOps;
import org.ed.docGen.targets.TagSubstitutes;

/**
 * Process a line of a table
 * @author Ed Swaneck
 * @version 1.0
 * @since 08-30-2025
 */
public class TableProcessor {
	
	/**
	 * Constructor
	 */
	public TableProcessor() {
		super();
	}
	
	/**
	 * Determine if this line represents table markup
	 * @param inLine The line to evaluate for table markup
	 * @param markup Type of table row being processed (Table, TableHeader)
	 * @return true if the line represents table markup, otherwise false
	 */
	public static boolean isTableLine(String inLine, Markup markup) {
		
		return inLine != null && inLine.trim().startsWith(markup.getStartTag());
		
	}

	/**
	 * Process table markup
	 * @param inLine The line to evaluate
	 * @param status Current status
	 * @param markup Type of table row being processed (Table, TableHeader)
	 */
	public void processTable(String inLine, ProcessStatus status, Markup markup) {

		if (status.isContinuation()) {
			if (status.getMode().equals(Markup.TableHeader) && 
				Markup.Table.isThis(inLine)) {
				status.setMode(Markup.Table);
				status.setOutLine("");
				status.setReprocess(true);
				
			}
			else if (status.getMode().equals(Markup.Table) && 
					 Markup.TableHeader.isThis(inLine)) {
				status.setMode(Markup.TableHeader);
				status.setOutLine("");
				status.setReprocess(true);
				
			}
			else if (!inLine.trim().startsWith(status.getMode().getStartTag())) {
				status.setOutLine(status.runEndBlockOp());
				status.setMode(null);
				status.setReprocess(inLine.trim().length() > 0);
				
			}
			else {
				status.setOutLine(processRow(inLine, status, markup, false));
				status.setMode(status.getMode());
				status.setReprocess(false);
				
			}
			
		}
		else {
			status.setOutLine(processRow(inLine, status, markup, true));
			status.setMode(status.getMode());
			status.setReprocess(false);
			
		}
		
		status.setBlankLineCount(0);
		
	}

	/**
	 * Process a row of a table
	 * @param inLine The line to process
	 * @param status Current status of the process
	 * @param markup Type of table row being processed (Table, TableHeader)
	 * @param tableStart true if starting a new table, otherwise false
	 * @return
	 */
	private String processRow(String inLine, ProcessStatus status, Markup markup, boolean tableStart) {
		TableBlockTagOps op = ((TableBlockTagOps) status.getTargetTags().getBlockTargets().get(markup));
		TableCell [] cols = preprocessTableRow(inLine.trim(), status.getTargetTags(), status.isTableBorder(), false);
		StringBuilder output = new StringBuilder();
		
		if (tableStart) {
			output.append(op.blockStartTags(inLine, status.isTableBorder(), inLine.startsWith(" ")));
		}
		
		output.append(op.generateRowTags(false));
		output.append(op.blockItemTags(cols));
		output.append(op.generateRowTags(true));
		
		return output.toString();
		
	}
	
	/**
	 * Split the rows into columns and remove a space from the beginning and end of each column
	 * @param row The row to pre-process
	 * @param status Current status of the process
	 * @param header true if this is a header row, otherwise false
	 * @return The pre-processed columns
	 */
	private TableCell [] preprocessTableRow(String row, TagSubstitutes targetTags, boolean isTableBorder, boolean header) {
		List<String> cols = splitRow(row); 
		TableCell [] cells = new TableCell[cols.size()];
		
		for (int i = 0; i < cols.size(); i++) {
			if (cols.get(i).length() <= 0 && i > 0) {
				cells[i] = new TableCell(null); 
				setColSpan(cells, i);
				
			}
			else {
				String col = cols.get(i);
				
				// Remove a space from the beginning and end of each column
				col = col.substring(1, col.length() - (col.endsWith(" ") ? 1 : 0));
				
				// Determine if the cell is left, right or center aligned
				int leftSp = spaceCount(col, true);
				int rightSp = spaceCount(col, false);
	
				if (leftSp == rightSp && leftSp > 0 && rightSp > 0) {
					cells[i] = new TableCell(MarkupUtils.runInlineSubstitutions(col.trim(), targetTags), 
							                 Constants.TextAlign.center);
				}
				else if (leftSp > rightSp) {
					cells[i] = new TableCell(MarkupUtils.runInlineSubstitutions(col.trim(), targetTags), 
							                 Constants.TextAlign.right);
				}
				else {
					cells[i] = new TableCell(MarkupUtils.runInlineSubstitutions(col.trim(), targetTags), 
							                 Constants.TextAlign.left);
				}
				
				cells[i].setHasBorder(isTableBorder);
				
			}
			
		}
		
		return cells;
		
	}

	/**
	 * Split a table row at the separators
	 * @param row The row to process
	 * @return The row parts separated into an array 
	 */
	private List<String> splitRow(String row) {
		String bareRow = row.substring(row.startsWith(Markup.TableHeader.getStartTag()) ? 2 : 1, 
									   row.length() - (row.endsWith(Markup.TableHeader.getEndTag()) ? 1 : 0));
		List<String> cols = new ArrayList<>();
		
		// Insure the the proper number of columns are counted if the row ends with a separator
		if (bareRow.endsWith(Markup.TableHeader.getEndTag())) {
			bareRow += Markup.TableHeader.getEndTag();
		}
		
		while (bareRow.length() > 0) {
			int sepPos = bareRow.indexOf(Markup.Table.getEndTag().trim());
			
			if (sepPos >= 0) {
				cols.add(bareRow.substring(0, sepPos));
				bareRow = bareRow.substring(sepPos + 1); 
			}
			else {
				cols.add(bareRow);
				bareRow = "";
			}
			
		}
        
		return cols;
		
	}
	
	/**
	 * Count the leaded/trailing spaces
	 * @param text Count the leaded/trailing spaces in this text
	 * @param left true to count space on the left, false to count spaces on the right
	 * @return The count of leaded/trailing spaces
	 */
	private int spaceCount(String text, boolean left) {
		int count = 0;
		int pos = left ? 0 : text.length() - 1;
		
		if (left) {
			while (pos < text.length() && text.charAt(pos) == ' ') {
				count++;
				pos++;
			}
		}
		else {
			while (pos >= 0 && text.charAt(pos) == ' ') {
				count++;
				pos--;
			}
		}
		
		return count;
		
	}

	/**
	 * Set any column span values
	 * @param cells The list of cells being processed
	 * @param curPos Current cell that is being processed
	 */
	private void setColSpan(TableCell [] cells, int curPos) {
		int spanPos = curPos - 1;
		
		while (spanPos >= 0 && cells[spanPos].getText() == null) {
			spanPos--;
		}
		
		cells[spanPos].incColspan();
		
	}
	
}
