package com.magma.engine.debug;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Crasher extends JFrame {

    private Crasher(String htmlPath) {
        super("Whoops, something went horribly wrong!");

        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension size = new Dimension(600, 600);
        setPreferredSize(size);
        setMinimumSize(size);

        try {
            JEditorPane editor = new JEditorPane("file://"+htmlPath);
            editor.setEditable(false);
            getContentPane().add(editor);
            pack();

        } catch (IOException ec) {
            JLabel label = new JLabel("Could not display content.");
            getContentPane().add(label);
            ec.printStackTrace();
        }
        setVisible(true);
    }

    public static Crasher showCrashDialog(Exception e) {
        MagmaLogger.logException(e);
        String path = MagmaLoggerDesktop.flush(true);
        return new Crasher(path);
    }
}
