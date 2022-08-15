package com.magma.engine.stages;

interface StageSwitchListener {
	fun stageOpened(stage: GameStage) {}
	fun stageClosed(stage: GameStage) {}
	fun stageResized(width: Int, height: Int) {}
}
