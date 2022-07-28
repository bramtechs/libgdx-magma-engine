package com.magma.engine.tests;

import com.badlogic.gdx.Gdx;
import com.magma.engine.MagmaGame;
import com.magma.engine.stages.StageSwitcher;

public class TestGame extends MagmaGame {

	@Override
	protected void initStages() {
        StageSwitcher.open(new TestStage(batch));
        Gdx.app.exit();
	}

}
