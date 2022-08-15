package com.magma.engine.debug.ui;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public abstract class LabelUpdater extends Action {
	
	@Override
	public boolean act(float delta) {
		Label label = (Label) getActor();
		label.setText(updateText());
		return false;
	}
	
	public abstract String updateText();
}
