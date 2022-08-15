package com.magma.engine.utils

import com.badlogic.gdx.utils.reflect.ClassReflection
import com.badlogic.gdx.utils.reflect.Field
import com.badlogic.gdx.utils.reflect.ReflectionException

abstract class FieldsPad {
    fun <T> setValue(name: String, value: T) {
        val field: Field
        try {
            field = ClassReflection.getField(javaClass, name)
            field[this] = value
        } catch (e: ReflectionException) {
            throw e
        }
    }

    fun <T> getValue(name: String): T {
        val field: Field
        return try {
            field = ClassReflection.getField(javaClass, name)
            field[this] as T
        } catch (e: ReflectionException) {
            throw e
        }
    }
}