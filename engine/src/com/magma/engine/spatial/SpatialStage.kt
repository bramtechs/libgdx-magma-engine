package com.magma.engine.spatial

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.utils.Timer
import com.magma.engine.stages.GameStage
import com.magma.engine.stages.ViewportContext

open class SpatialStage(private val batch: SpriteBatch, protected val modelBatch: ModelBatch) :
    GameStage(batch) {
	protected val modelBuilder: ModelBuilder = ModelBuilder()
    protected val env: Environment
    private val origin: ModelInstance
    private val boxModel: Model
    private val fps: FirstPersonCameraController

    init {
        val camera = ViewportContext.view.camera
        if (camera == null) {
            throw NullPointerException("No camera!")
        } else if (camera is PerspectiveCamera) {
            camera.far = 1000f
            camera.near = 0.01f
            camera.position[15f, 15f] = 15f
            camera.lookAt(0f, 0f, 0f)
        }

        // generate world center
        val mat = Material(ColorAttribute.createDiffuse(Color.RED))
        boxModel = modelBuilder.createBox(
            0.5f,
            0.5f,
            0.5f,
            mat,
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong()
        )
        origin = ModelInstance(boxModel)

        // lighting
        env = Environment()
        env.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        env.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, -0.2f, -0.8f, 0.7f))
        fps = FirstPersonCameraController(persCamera)
        fps.setVelocity(10f)
        fps.setDegreesPerPixel(2f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                Gdx.app.log("Camera", "" + camera.position)
            }
        }, 0f, 2f)
        Gdx.input.inputProcessor = fps
    }

    override fun act(delta: Float) {
        fps.update(delta)
        super.act(delta)
    }

    override fun dispose() {
        boxModel.dispose()
        super.dispose()
    }

    protected open fun draw3d() {}
    override fun draw() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        modelBatch.begin(camera)
        modelBatch.render(origin, env)
        draw3d()
        modelBatch.end()
        super.draw()
    }
}