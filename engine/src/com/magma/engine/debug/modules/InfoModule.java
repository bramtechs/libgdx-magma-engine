package com.magma.engine.debug.modules;

import javax.swing.JLabel;
import com.badlogic.gdx.Gdx;

public class InfoModule extends Module {

    private final JLabel fps, mem;

    public InfoModule() {
        super("Info");

        fps = new JLabel();
        mem = new JLabel();

        add(fps);
        add(mem);
    }

    @Override
	public void update() {
	    fps.setText(format("FPS",Gdx.graphics.getFramesPerSecond()));
        mem.setText(format("Memory",getMemory()));
	}

    public String getMemory() {
        final int mb = 1024 * 1024;
        long mem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mb;
        long totalMem = Runtime.getRuntime().totalMemory() / mb;
        long maxMem = Runtime.getRuntime().maxMemory() / mb;
        return Long.toString(mem) + " | reserved " + Long.toString(totalMem) + " | cap " + Long.toString(maxMem)
                + " mb";
    }
}
