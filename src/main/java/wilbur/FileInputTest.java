package wilbur;

import com.github.millefoglie.latex.document.LatexDocument;
import com.github.millefoglie.latex.node.LatexNode;
import com.github.millefoglie.latex.parser.DefaultLatexParser;
import com.github.millefoglie.latex.parser.LatexParser;
import com.github.millefoglie.latex.parser.LatexParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileInputTest {

    static String texFileName = "tex/test-newline-mac.tex";                          // PROBLEM: second line is missing
    // static String texFileName = "tex/test-newline-mac-ansi.tex";                     // PROBLEM: second line is missing
    // static String texFileName = "tex/test-newline-unix.tex";                         // PROBLEM: second line is missing
    // static String texFileName = "tex/test-newline-unix-ansi.tex";                    // PROBLEM: second line is missing
    // static String texFileName = "tex/test-newline-windows.tex";                      // PROBLEM: second line is missing
    // static String texFileName = "tex/test-newline-windows-ansi.tex";                 // PROBLEM: second line is missing
    // static String texFileName = "tex/test-two-numbers-in-line.tex";                  // OK

	// static String texFileName = "tex/test-environments.tex";                         // OK
    // static String texFileName = "tex/test-comment-lines.tex";
	// static String texFileName = "tex/test-verbatim.tex";

    // static String texFileName = "tex/intro.tex";                                     // fails (this is a valid LaTeX file)
    // static String texFileName = "tex/intro2.tex";                                    // fails (this is a valid LaTeX file)

    public static void main(String[] arges) throws IOException, LatexParserException {

        Path path = FileSystems.getDefault().getPath(texFileName);
        File file = path.toFile();
        if (!file.exists()) {
            System.out.println("file not found: " + file.getAbsolutePath());
            return;
        }

        InputStream is = Files.newInputStream(path);
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
