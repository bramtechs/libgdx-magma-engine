package com.magma.engine.debug;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.magma.engine.MagmaGame;
import com.magma.engine.debug.modules.InfoModule;
import com.magma.engine.debug.modules.MapModule;
import com.magma.engine.debug.modules.Module;
import com.magma.engine.debug.modules.StageModule;

public class Debugger extends JFrame implements Disposable {

    private static Debugger instance;

    private Array<Module> modules = new Array<Module>();

    private Debugger() {
        super("Debugger");
        instance = this; // prevents stack overflow do not remove

        // prevents the game from hanging when closing, due to the frame still being
        // available
        MagmaGame.disposeOnExit(this);

        // Construct the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setLocationRelativeTo(null);

        final Dimension SIZE = new Dimension(400, 700);
        setMinimumSize(SIZE);
        setPreferredSize(SIZE);

        // add menubar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                instance.setVisible(false);
            }
        });
        menuFile.add(menuItemExit);
        menuBar.add(menuFile);

        // adds menu bar to the frame
        setJMenuBar(menuBar);

        // generate modules
        addModule(new InfoModule());
        addModule(new MapModule());
        addModule(new StageModule());

        pack();
    }

    public static void addModule(Module module) {
        Debugger ins = getInstance();
        ins.modules.add(module);
        ins.getContentPane().add(module);
        ins.pack();
    }

    public static void update() {
        Debugger ins = getInstance();
        if (Gdx.input.isKeyJustPressed(Keys.F3)) {
            ins.setVisible(!getInstance().isVisible());
        }

        if (ins.isVisible()) {
            for (Module module : ins.modules) {
                module.update();
            }
        }
    }

    public static <T extends Module> Module getModule(Class<T> type) {
        for (Module mod : getInstance().modules) {
            MagmaLogger.log(type);
            if (mod.getClass() == type) {
                return mod;
            }
        }
        throw new NullPointerException("Cannot find module " + type);
    }

    public static Debugger getInstance() {
        if (instance == null) {
            instance = new Debugger();
        }
        return instance;
    }

    @Override
    public void dispose() {
        modules = null;
        super.dispose();
        MagmaLogger.log(this, "Debugger disposed");
    }
}
