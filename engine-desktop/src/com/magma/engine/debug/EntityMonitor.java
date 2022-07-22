package com.magma.engine.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.magma.engine.assets.MagmaLoader;
import com.magma.engine.entities.Entity;
import com.magma.engine.entities.EntityComponent;

public class EntityMonitor extends Group {
	
	private static EntityMonitor instance;
	
	private Group root;
	private Array<Actor> actors;
	
	public EntityMonitor() {
		this.actors = new Array<Actor>();
		if (instance == null) {
			instance = this;
		}else {
			throw new IllegalStateException("There can only be one EntityMonitor at once!");
		}
	}
	
	private void init() {
		this.root = getParent();
		if (this.root == null) {
			throw new NullPointerException("EntityMonitor has no parent");
		}
		Timer.schedule(new Task() {
			@Override
			public void run() {
				scan();
			}
		}, 0, 5f);
	}
	
	@Override
	public void act(float delta) {
		if (root == null) {
			init();
		}
		super.act(delta);
	}
	
	private void injectUI(final Actor actor, final Entity entity) {
		Group group = new Group() {
			@Override
			public void act(float delta) {
				this.setPosition(actor.getX(),actor.getY());
				super.act(delta);
			}
		};
		Label infoLabel = new Label("test",MagmaLoader.getDebugSkin()) {
			@Override
			public void act(float delta) {
				String text = actor.getClass().getSimpleName() + "\n";
				text += "x: " + actor.getX() + " y: " + actor.getY() + "\n";
				text += entity.toString() + "\n";
				for (Action action : actor.getActions()) {
					text += "-> " + action.toString() + "\n";
				}
				setText(text);
				super.act(delta);
			}
		};
		infoLabel.setPosition(20, 0);
		infoLabel.setFontScale(0.6f);
		//infoLabel.setScale(1f/MapStage.getTileSize());
		
		group.addActor(infoLabel);		
		addActor(group);
		
		Gdx.app.log("EntityMonitor", "Added monitor for " + actor.getClass().getSimpleName());
	}
	
	private void crawl(Group group) {
		for (Actor child : group.getChildren()) {
			if (child == this) {
				continue; //ignore myself
			}
			if (child instanceof Group) {
				crawl(group);
			}else {
				if (!actors.contains(child,true)) {
					if (child instanceof EntityComponent) {
						Entity entity = ((EntityComponent) child).getEntity();
						injectUI(child, entity);
					}
					actors.add(child);
				}
			}
		}
	}
		
	public void scan() {
		if (!isVisible()) {
			return;
		}
		
		int oldSize = actors.size;
		crawl(root);
		if (oldSize != actors.size) {
			Gdx.app.log("EntityMonitor", "Found "  + (actors.size-oldSize) + " actors");
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			scan();
		}
	}
	
	public static EntityMonitor getInstance() {
		return instance;
	}

	public static boolean isActive() {
		if (instance == null) {
			return false;
		}
		return instance.isVisible();
	}
}
