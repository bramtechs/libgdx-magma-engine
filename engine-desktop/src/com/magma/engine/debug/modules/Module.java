package com.magma.engine.debug.modules;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public abstract class Module extends JPanel {

    public Module(String title)  {
        this.setBorder(BorderFactory.createTitledBorder(title));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setName(title);
    }

    public abstract void update();

    protected String format(String name, Object value) {
        return name + ": " + value.toString();
    }
}
