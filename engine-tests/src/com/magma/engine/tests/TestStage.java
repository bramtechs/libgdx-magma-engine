package com.magma.engine.tests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.magma.engine.stages.GameStage;
import com.magma.engine.stages.ViewportContext;

public class TestStage extends GameStage{
    public TestStage(SpriteBatch batch){
        super(ViewportContext.createRetro(30, 20, 640, 480),batch);
        new GameStageTest(batch);
    }
}
