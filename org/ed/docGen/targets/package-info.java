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
 * Classes to support the generation of output to a specified target 
 * markup. Target specific classes, such as HtmlTags perform the actual 
 * translation and code generation for the output.
 * 
 * <p style='font-size: larger; font-weight: bold;'>Creating a New Target Processor</p>
 * <ol>
 *     <li>
 *         Create a new class for the target markup.  The class should
 *         extend org.ed.docGen.targets.TagSubstitute.  
 *     </li>
 *     <li>
 *         Using org.ed.docGen.targets.HtmlTags as a template, copy all
 *         of the private classes that extend BlockTagOps to the new class. 
 *     </li>
 *     <li>
 *         Change the tags listed in the constructor of each of the 
 *         private classes to the tags used by the new target class.
 *     </li>
 *     <li>
 *         Make any code changes necessary to support the unique aspects
 *         of the particular markup.
 *     </li>
 * </ol>
 */
package org.ed.docGen.targets;