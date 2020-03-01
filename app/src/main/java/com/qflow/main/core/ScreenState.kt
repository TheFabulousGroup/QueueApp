package com.qflow.main.core

/**
 * ScreenState main structure for the views.
 * Either it renders a loading state
 * Or renders whatever we want to
 * */
sealed class ScreenState<out T>
{
    object Loading: ScreenState<Nothing>()
    class Render<T>(val renderState: T) : ScreenState<T>()
}
