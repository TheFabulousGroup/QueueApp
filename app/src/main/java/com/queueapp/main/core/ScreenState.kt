package org.visionapp.myopia.kotlin.core

/*
* @author  Iván Fernández Rico, Globalincubator
*/
sealed class ScreenState<out T>
{
    object Loading: ScreenState<Nothing>()
    class Render<T>(val renderState: T) : ScreenState<T>()
}
