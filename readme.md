# Txt2Tags for Java
The purpose of this project is to implement a subset of txt2tags markup, in Java, that will process individual lines and paragraphs, not a complete document.  The results can then be received by a calling program for further processing. The reason for using txt2tags is to allow users that are not 
familiar with more robust markup languages to apply basic formatting to text that will later be included in other documents such as a web page.

## Supported txt2tags Targets
- HTML
- UNIX Manual Page
- Wiki

## Supported txt2tags Syntax
Most txt2tags markup is supported.  Following a complete list of the 
implemented markup.

- Bold
- BoldSeparator
- Italic
- Monospace
- Definition List
- Numbered Title Level 1
- Numbered Title Level 2
- Numbered Title Level 3
- Ordered List
- Paragraph
- Quoted Paragraph
- Raw Area
- Separator
- Soft line-break
- Strike-out
- Table
- Table Header
- Tagged Area
- Title Level 1
- Title Level 2
- Title Level 3
- To-Do Line
- To-Do Block
- Underline
- Unordered List
- Verbatim Area
- Verbatim Line

## Usage
The API is designed to process a single line of text, a Java list or a Java 
array of lines of text.

### Convert a single line to HTML

```
import org.ed.docGen.ProcessLine;
import org.ed.docGen.targets.TagSubstitutes;

TagSubstitutes ts = new org.ed.docGen.targets.HtmlTags();
ProcessLine pl = new ProcessLine(ts);
String result = pl.process("A **line** of //txt2tags// markup.");
````

### Convert an array of lines to Wiki text

```
import org.ed.docGen.ProcessLines;
import org.ed.docGen.targets.TagSubstitutes;

String [] data = { "A non-bordered __table__ to process",
                   "|| Header 1A | Header 1B | Header 1C",
                   "| Data 2A | Data 2B | Data 2C" };

TagSubstitutes ts = new WikiTags();                   
ProcessLines lp = new ProcessLines(ts);
String [] output = lp.process(data);                   
```

### Convert a list of lines to UNIX Manual markup

```
import org.ed.docGen.ProcessLines;
import org.ed.docGen.targets.TagSubstitutes;

List<String> data = new ArrayList<>();

data.add("An ``unordered`` list");
data.add("- List line 1");
data.add("- List line 2");

TagSubstitutes ts = new ManTags();                   
ProcessLines lp = new ProcessLines(ts);
String [] output = lp.process(data);                   
```

### Convert some text to HTML
```
import org.ed.docGen.ProcessLines;
import org.ed.docGen.targets.TagSubstitutes;

StringBuilder text = new StringBuilder();

text.append("A line that will be a paragraph");
text.append("\n");
text.add("An ``unordered`` list");
text.append("\n");
text.add("- List line 1");
text.append("\n");
text.add("- List line 2");
text.append("\n");

TagSubstitutes ts = new org.ed.docGen.targets.HtmlTags();
ProcessText pt = new ProcessText(ts);
String result = pt.process(text);
```
