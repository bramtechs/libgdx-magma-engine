package com.magma.engine.debug.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.badlogic.gdx.maps.Map;
import com.magma.engine.assets.MagmaLoader;
import com.magma.engine.maps.MapSession;
import com.magma.engine.maps.MapStage;
import com.magma.engine.stages.GameStage;
import com.magma.engine.stages.StageSwitchListener;

public class MapModule extends Module implements StageSwitchListener {

    private final JLabel current, triggerCount;
    private final JCheckBox trigger;
    private final JButton load;

    private JComboBox<String> comboBox;
    private String choosenMap; // nessesary

    private MapStage map;

    public MapModule() {
        super("Map details");
        current = new JLabel();
        triggerCount = new JLabel();

        // instantiate the widgets
        trigger = new JCheckBox("Show trigger outlines");
        trigger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                boolean on = trigger.isSelected();
                if (map != null)
                    map.setTriggersVisible(on);
            }
        });

        // load map list
        ArrayList<String> maps = MagmaLoader.getAllMapNames();
        comboBox = new JComboBox<String>();
        comboBox.setMaximumSize(new Dimension(200, 50));
        for (String map : maps) {
            comboBox.addItem(map);
        }
        add(comboBox);

        load = new JButton("Load map");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // choose the choosen map from the combobox
                choosenMap = (String) comboBox.getSelectedItem();
            }
        });

        // add the widgets to the panel
        add(current);
        add(triggerCount);
        add(trigger);
        add(load);
    }

    @Override
    public void update() {
        String curMap = "None loaded";
        current.setText(format("Loaded map", curMap));

        if (map == null)
            return;

        MapSession session = MapSession.getSession();
        if (session == null)
            return;

        int count = session.getTriggers().getCount();
        triggerCount.setText(format("Trigger count", count));

        curMap = session.getTmxName();
        current.setText(format("Loaded map", curMap));

        if (choosenMap != null) {
            // load the choosen map on the LWJGL thread
            map.requestMap(choosenMap);
            choosenMap = null;
        }
    }

    @Override
    public void stageOpened(GameStage stage) {
        if (stage instanceof MapStage) {
            map = (MapStage) stage;
        }
    }

    @Override
    public void stageClosed(GameStage stage) {
        if (stage == map) {
            map = null;
        }
    }
}
