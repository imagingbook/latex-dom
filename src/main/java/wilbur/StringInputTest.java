package wilbur;

import com.github.millefoglie.latex.document.LatexDocument;
import com.github.millefoglie.latex.parser.DefaultLatexParser;
import com.github.millefoglie.latex.parser.LatexParser;
import com.github.millefoglie.latex.parser.LatexParserException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class StringInputTest {

    // static String input = "a simple   newline\nin the text";
    // static String input = "a \\cmd in the text";
    // static String input = "a \\cmd {arg1}[arg2] in the text";
    // static String input = "a [bracket block] in the text";
    // static String input = "a comment%like this\n in the text";                                           // WRONG: there is no whitespace between 'this' and 'in'
    // static String input = "a word only and another $abra \\times 3$ \\footnote{foo}";
    // static String input = "multiple\n\nnew lines";
    static String input = "multiple \\textbf{new} lines";
    // static String input = " Plain text with escaped characters, such as \\%, \\$, \\#, \\_ and \\&.";   // special characters are treated as commands
    // static String input = "the line is broken \\\\*[6pt]before these words";                             // optional command not recognized
    // static String input = "this is \\begin{Stuff}[abra] an environment \\end{Stuff} and so";             // name of environment not available?
    // static String input = "this environment \\begin{Stuff}contains \\begin{foo}[abra=0]\nnested environment\n\\end{foo}\n\\end{Stuff}.";
    // static String input = " math text $x = 3^y \\in [0,1)$ with unbalanced brackets";                    // ERROR: cannot close scope
    // static String input = "This is \\verb*!verbatim \\foo %&§() text! followed by some more.";           // verb!...! not recognized

    // static String input = "numbers such as -5 or 70.654 are treated as normal text.";
    // static String input = "a European comma like 15,23 is OK in text, but is wrong in math mode!";

    // static String input = "dashes like -- and longer ones like --- are part of the text.";
    // static String input = "Using \"straight\" quotation marks.";
    // static String input = "Using “foreign” quotation marks is partially OK";
    // static String input = "Using ``standard'' quotation marks is preferred!";                            // quotation marks are separated
    // static String input = "Using \"`German\"' quotation marks is frequent.";                             // quotation marks are separated
    // static String input = "Using \"<French\"> quo\\-tation marks is less-frequent.";		                // quotation marks are separated, optional hyphens not handled

    // static String input =
    // 		"\\chapter[Schlussbemerkungen]%\n" +
    // 		"{Schlussbemerkungen%\n" +
    // 		"\\protect\\footnote{Diese Anmerkung dient nur dazu, die (in seltenen Fällen sinnvolle)" +
    // 		"			Verwendung von Fußnoten bei Foo zu demonstrieren.}}\n" +
    // 		"\\section{Introduction}";

    // static String input = "\\chapter[Schlussbemerkungen]";
    // static String input = "\\chapter[Schlussbemerkungen]{Schlussbemerkungen\\protect\\footnote{Diese Anmerkung dient nur dazu, die (in seltenen Fällen sinnvolle)\t\t\tVerwendung von Fußnoten bei Foo zu demonstrieren.}}";
    // static String input = "Schlussbemerkungen\\protect\\footnote{Diese Anmerkung dient nur dazu, die (in seltenen Fällen sinnvolle)\t\t\tVerwendung von Fußnoten bei Foo zu demonstrieren.}";

    // static String input = "this is \\begin{Stuff} an environment \\end{Stuff} and so";
    // static String input = "very \n\n\n simple";
    // static String input = "very \\bf simple";
    // static String input = "here we do a \\vspace10pt and continue";

    // static String input = "this is \\begin{Stuff}[abra] an environment and so";		        // Error is OK, missing environment
    // static String input = "this is \\begin{Stuff] an environment and so";				        // Error is OK, non-matching brackets!


    public static void main(String[] arges) throws IOException, LatexParserException {

        final String EOF = "\u001a";
        System.out.println("Input string:");
        System.out.println("------------------------------------------------------------");
        System.out.println(Utils.beautify(input));
        System.out.println("------------------------------------------------------------");
        System.out.println("length = " + input.length());

        InputStream is = new ByteArrayInputStream((input + EOF).getBytes());    // adding EOF here, otherwise last token is missed

        LatexParser parser = new DefaultLatexParser();
        LatexDocument doc = parser.parse(is);

        if (doc != null) {
            Utils.traverse(doc.getRoot(), 0);
            System.out.println("done");
        }
        else {
            System.out.println("*** parse failed ***");
        }
    }

}
