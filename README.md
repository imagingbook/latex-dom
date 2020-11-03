# LaTeX DOM Parser

An experimental version of a LaTeX DOM Parser that converts a LaTeX document to a tree of nodes the represent various elements, such as text, spaces, commands, etc. The tree always starts with a `RootNode`.

The current API is subject to future changes.

## Example

```java
InputStream is = Files.newInputStream(path);
LatexParser parser = new LatexParser();
LatexDocument doc = parser.parse(is);
RootNode root = doc.getRoot();
```
