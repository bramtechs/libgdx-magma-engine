package com.magma.engine.stages;

public interface StageSwitchListener {
	public void stageOpened(GameStage stage);
	public void stageClosed(GameStage stage);
    public void resize(int width, int height);
}
