package wilbur;

import com.github.millefoglie.latex.node.LatexNode;


import java.util.Arrays;

public class Utils {
	
//	private static final char UNPRINTABLE_CHAR = '\u25A0'; 	// black square;
	private static final char UNPRINTABLE_CHAR = '�';
	
	public static char printableChar(char c) {
		return (Character.isLetterOrDigit(c) || (c >= 32 && c <= 126)) ?
				c : UNPRINTABLE_CHAR;
	}
	
	
	public static String makePrintable(String text) {
		return text.replace("\n", "\\n").replace("\t", "\\t");
	}
	
	
	public static String beautify(String s) {
		if (s == null) {
			return "null";
		}
		String s1 = s.replace("\r\n", "\n");
		return s1.replace("\n", "\\n").replace("\t", "\\t");
	}

	// public static String beautify(String s) {
	// 	if (s == null) {
	// 		return "null";
	// 	}
	// 	String s1 = s.replace("\r\n", "CRLF");
	// 	return s1.replace("\n", "RET").replace("\t", "TAB");
	// }


	static void traverse(LatexNode n, int level) {
		System.out.println(makeIndentString(level) + " |" + beautify(n.getContent()) + "| - " + n.getType() + " parent=" + getParentName(n));
		// System.out.println(makeIndentString(level) + " |" + n.getContent() + "| - " + n.getClass().getSimpleName());
		// System.out.println(makeIndentString(level) + " |" + beautify(n.getContent()) + "| - " + n.getClass().getSimpleName());

		LatexNode c = n.getFirstChild();
		while (c != null) {
			traverse(c, level + 1);
			c = c.getNextSibling();
		}
	}

	static String getParentName(LatexNode n) {
		LatexNode p = n.getParent();
		return (p == null) ? "null" : p.getType().name();
	}

	static private String makeIndentString(int level) {
		char[] charArray = new char[4 * level];
		Arrays.fill(charArray, ' ');
		return new String(charArray);
	}

}
