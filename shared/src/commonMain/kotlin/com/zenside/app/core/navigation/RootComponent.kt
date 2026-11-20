package com.zenside.app.core.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.zenside.app.auth.domain.User
import kotlinx.serialization.Serializable
import org.koin.core.Koin

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Login(val component: LoginComponent) : Child()
        data class Register(val component: RegisterComponent) : Child()
        data class Main(val component: MainComponent) : Child()
    }
}

interface LoginComponent {
    fun onLoggedIn(user: User)
    fun onNavigateToRegister()
}

interface RegisterComponent {
    fun onRegistered(user: User)
    fun onNavigateToLogin()
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val koin: Koin
) : RootComponent, ComponentContext by componentContext {

    @Serializable
    private sealed class Config {
        @Serializable
        data object Login : Config()

        @Serializable
        data object Register : Config()

        @Serializable
        data object Main : Config()
    }

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Login,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, context: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Login -> RootComponent.Child.Login(
                object : LoginComponent {
                    override fun onLoggedIn(user: User) {
                        navigation.replaceAll(Config.Main)
                    }

                    override fun onNavigateToRegister() {
                        navigation.pushNew(Config.Register)
                    }
                }
            )

            is Config.Register -> RootComponent.Child.Register(
                object : RegisterComponent {
                    override fun onRegistered(user: User) {
                        navigation.replaceAll(Config.Main)
                    }

                    override fun onNavigateToLogin() {
                        navigation.pop()
                    }
                }
            )

            is Config.Main -> RootComponent.Child.Main(
                DefaultMainComponent(componentContext = context, koin = koin)
            )
        }
}
