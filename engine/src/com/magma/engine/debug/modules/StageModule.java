package com.magma.engine.debug.modules;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.badlogic.gdx.utils.Timer.Task;
import com.magma.engine.debug.MagmaLogger;
import com.magma.engine.stages.GameStage;
import com.magma.engine.stages.StageSwitchListener;
import com.magma.engine.stages.StageSwitcher;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;

public class StageModule extends Module implements StageSwitchListener {

    private final JLabel curStage, count;
    private final JTextArea root, rootUI;
    private GameStage activeStage;

    public StageModule() {
        super("Stage");
        curStage = new JLabel("Active stage:");
        count = new JLabel();
        root = new JTextArea();
        root.setEditable(false);
        rootUI = new JTextArea();
        rootUI.setEditable(false);
        JScrollPane scroll = new JScrollPane(root);
        JScrollPane scrollUI = new JScrollPane(rootUI);
        StageSwitcher.addListener(this);

        add(curStage);
        add(count);
        add(scroll);
        add(scrollUI);

        Timer.schedule(new Task() {
            @Override
            public void run() {
                // TODO: only run when visible
                if (activeStage != null) {
                    SnapshotArray<Actor> children = activeStage.getRoot().getChildren();
                    count.setText("Child count: " + children.size);
                    root.setText("Root: " + activeStage.getRoot().toString());
                    rootUI.setText("Root UI: " + activeStage.getUIStage().getRoot().toString());
                } else {
                    count.setText("(not available)");
                    root.setText("(not available)");
                }
            }

        }, 0, 3);
    }

    @Override
    public void update() {
    }

    @Override
    public void stageOpened(GameStage stage) {
        activeStage = stage;
        MagmaLogger.log("available");
        curStage.setText("Active stage: " + stage.getClass().getSimpleName());
    }

    @Override
    public void stageClosed(GameStage stage) {
        activeStage = null;
        curStage.setText("Active stage: (none)");
    }

}
