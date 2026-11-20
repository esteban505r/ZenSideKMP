package com.zenside.app.core.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.zenside.app.posturas.domain.Postura
import kotlinx.serialization.Serializable
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf

interface MainComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onTabSelected(tab: MainTab)
    val selectedTab: Value<MainTab>

    sealed class Child {
        data class PosturasList(val component: PosturasListComponent) : Child()
        data class PosturaDetail(val component: PosturaDetailComponent) : Child()
        data class RutinasList(val component: RutinasListComponent) : Child()
    }
}

enum class MainTab { POSTURAS, RUTINAS }

interface PosturasListComponent {
    fun onPosturaClicked(postura: Postura)
}

interface PosturaDetailComponent {
    val posturaId: Int
    fun onBack()
}

interface RutinasListComponent

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val koin: Koin
) : MainComponent, ComponentContext by componentContext {

    @Serializable
    private sealed class Config {
        @Serializable
        data object PosturasList : Config()

        @Serializable
        data class PosturaDetail(val id: Int) : Config()

        @Serializable
        data object RutinasList : Config()
    }

    private val navigation = StackNavigation<Config>()
    private val _selectedTab = MutableValueHolder(MainTab.POSTURAS)
    override val selectedTab: Value<MainTab> get() = _selectedTab.value

    override val stack: Value<ChildStack<*, MainComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.PosturasList,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override fun onTabSelected(tab: MainTab) {
        _selectedTab.set(tab)
        when (tab) {
            MainTab.POSTURAS -> navigation.pushNew(Config.PosturasList)
            MainTab.RUTINAS -> navigation.pushNew(Config.RutinasList)
        }
    }

    private fun createChild(config: Config, context: ComponentContext): MainComponent.Child =
        when (config) {
            is Config.PosturasList -> MainComponent.Child.PosturasList(
                object : PosturasListComponent {
                    override fun onPosturaClicked(postura: Postura) {
                        navigation.pushNew(Config.PosturaDetail(postura.id))
                    }
                }
            )

            is Config.PosturaDetail -> MainComponent.Child.PosturaDetail(
                object : PosturaDetailComponent {
                    override val posturaId: Int = config.id
                    override fun onBack() {
                        navigation.pop()
                    }
                }
            )

            is Config.RutinasList -> MainComponent.Child.RutinasList(object : RutinasListComponent {})
        }
}

/** Minimal mutable [Value] holder, avoiding an extra Decompose dependency for a single enum. */
private class MutableValueHolder<T : Any>(initial: T) {
    private val impl = com.arkivanov.decompose.value.MutableValue(initial)
    val value: Value<T> get() = impl
    fun set(newValue: T) {
        impl.value = newValue
    }
}
