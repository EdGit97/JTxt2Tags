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
package org.ed.utilities;

/**
 * Common methods for processing numeric values in a string 
 * @author Ed Swaneck
 * @version 1.0
 * @since 09-06-2025
 */
public class NumericUtils {
	
	/**
	 * Constructor
	 */
	public NumericUtils() {
		super();
	}
	
	/**
	 * Holder for Arabic to Roman Numerals converter data
	 */
	protected static class ArabicRoman {
		
		/** Arabic representation of the value */
	    protected int arabic;
	    
	    /** Roman numeral representation of the value */
	    protected String roman;

	    /**
	     * Constructor
	     * @param arabic Arabic representation of the value
	     * @param roman Roman numeral representation of the value
	     */
	    public ArabicRoman(int arabic, String roman) {
	        this.arabic = arabic;
	        this.roman = roman;
	    }

	    /**
	     * Getter
	     * @return Arabic representation of the value
	     */
	    public int getArabic() {
	        return arabic;
	    }

	    /**
	     * Getter
	     * @return Roman numeral representation of the value
	     */
	    public String getRoman() {
	        return roman;
	    }
		
	}
	
	/**
	 * Convert a number to Roman Numerals
	 * @param arabic integer to be converted to Roman Numerals
	 * @return Roman Numeral representation of the input if the input is less than 0 or greater than 3999
	 */
	public static String arabicToRoman(long arabic) {
	    StringBuilder roman = new StringBuilder();
		ArabicRoman [] data = { new ArabicRoman(1000, "M"), new ArabicRoman(900, "CM"), 
                                new ArabicRoman(500, "D"), new ArabicRoman(400, "CD"), 
                                new ArabicRoman(100, "C"), new ArabicRoman(90, "XC"),
                                new ArabicRoman(50, "L"), new ArabicRoman(40, "XL"), 
                                new ArabicRoman(10, "X"), new ArabicRoman(9, "IX"), 
                                new ArabicRoman(5, "V"), new ArabicRoman(4, "IV"),
                                new ArabicRoman(1, "I") };
	    
	    if (arabic > 0 && arabic < 4000) {
	        for (ArabicRoman ar : data) {
	            for (int i = 0; i < arabic / ar.getArabic(); i++) {
	                roman.append(ar.getRoman());
	            }

	            arabic %= ar.getArabic();

	        }

	    }
	    else {
	        roman.append(String.valueOf(arabic));
	    }

	    return roman.toString();

	}
	
	/**
	 * Convert a list header to lower case alpha
	 * @param value The value to convert
	 * @return The alphabetic result
	 */
	public static char numericToLowerAlpha(long value) {
		return (char) ('a' + value);
	}

}
