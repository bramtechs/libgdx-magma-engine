package com.magma.engine.debug.modules

import com.magma.engine.assets.MagmaLoader.allMapNames
import com.magma.engine.maps.MapStage
import com.magma.engine.stages.GameStage
import com.magma.engine.stages.StageSwitchListener
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JLabel

// TODO make for every new MapStage
class MapModule : Module("Map details"), StageSwitchListener {
    private val current: JLabel = JLabel()
    private val triggerCount: JLabel = JLabel()
    private val trigger: JCheckBox = JCheckBox("Show trigger outlines")
    private val load: JButton = JButton("Load map")
    private val comboBox: JComboBox<String>

    private var chosenMap : String? = null // must be deferred
    private var mapStage: MapStage? = null

    init {
        // instantiate the widgets
        trigger.addActionListener {
            val on = trigger.isSelected
            mapStage?.setTriggersVisible(on)
        }

        // load map list
        val maps = allMapNames
        comboBox = JComboBox()
        comboBox.maximumSize = Dimension(200, 50)
        for (map in maps) {
            comboBox.addItem(map)
        }
        add(comboBox)

        load.addActionListener { // choose the choosen map from the combobox
            chosenMap = comboBox.selectedItem as String
        }

        // add the widgets to the panel
        add(current)
        add(triggerCount)
        add(trigger)
        add(load)
    }

    override fun update() {
        var curMap = "None loaded"
        current.text = format("Loaded map", curMap)
        if (mapStage == null) return
        val count: Int = mapStage!!.triggers.count
        triggerCount.text = format("Trigger count", count)
        curMap = mapStage!!.tmxName
        current.text = format("Loaded map", curMap)
        if (chosenMap != null) {
            // load the choosen map on the LWJGL thread
            mapStage!!.unloadMap()
            mapStage!!.loadMap(chosenMap!!)
            chosenMap = null
        }
    }

    override fun stageOpened(stage: GameStage) {
        if (stage is MapStage) {
            mapStage = stage
        }
    }

    override fun stageClosed(stage: GameStage) {
        if (stage === mapStage) {
            mapStage = null
        }
    }
}