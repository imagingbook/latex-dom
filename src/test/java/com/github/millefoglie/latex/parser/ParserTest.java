package com.github.millefoglie.latex.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import com.github.millefoglie.latex.nodes.RootNode;

public class ParserTest {

    @Test
    public void testFileContentImmutability() throws IOException, LatexParserException {
        Path path = Paths.get("src/test/resources/intro.tex");
        InputStream is = Files.newInputStream(path);
        LatexParser parser = new LatexParser();
        RootNode root = parser.parse(is).getRoot();
        String str = root.stringify();

        byte[] inputBytes = Files.readAllBytes(path);
        byte[] outputBytes = str.getBytes();

        Assert.assertArrayEquals(inputBytes, outputBytes);
    }

}
