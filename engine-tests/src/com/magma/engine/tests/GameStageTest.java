package com.magma.engine.tests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStageTest extends Tester {
    public GameStageTest(SpriteBatch batch){
        // create gamestage and check viewport sizes
        // via two methods

        //check(stage.getUIStage().getViewport().getWorldWidth(),ctx.getUIWidth(),"viewport size mismatch");
        //check(stage.getUIStage().getViewport().getWorldWidth(),ctx.getUI().getWorldWidth(),"viewport size mismatch");
        
        check(true,true,"example test");
    }
}
