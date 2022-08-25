package com.magma.engine.chars

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.magma.engine.assets.Shapes
import com.magma.engine.collision.TriggerListener
import com.magma.engine.collision.Triggered

// a physical 'block' that appears in front of moving characters so collisions can be intercepted in time
class CharacterCollider(private val char: Character) : Actor(), Triggered, TriggerListener{

    var isColliding: Boolean = false

    init{

    }

    fun canMove(x: Float, y: Float):  Boolean{
        if (char.shape !is Rectangle){
            throw IllegalArgumentException("Character collider needs rectangular feet")
        }
        val feet = char.shape as Rectangle;
        setBounds(feet.x+x,feet.y+y,feet.width,feet.height);
        return true;
        //return isColliding;
    }

    override fun onTriggered(actor: Actor) {
        //if (actor is SolidTrigger){

        //}
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        Shapes.instance.filledRectangle(x,y,width,height, Color.PURPLE)
        super.draw(batch, parentAlpha)
    }
}