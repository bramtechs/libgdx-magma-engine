package com.magma.engine.maps

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.Disposable

class OrthogonalTiledMapActor(tileMap: OrthogonalTiledMapRenderer) : TiledMapActor<OrthogonalTiledMapRenderer>(tileMap), Disposable{
    override fun dispose() {
        remove()
        tileMap.dispose()
    }
}