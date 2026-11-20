# iosApp

No Xcode or macOS is available on the machine this scaffold was built on, so
no `.xcodeproj` is included here (a hand-generated/fake one would be
worthless and misleading).

To run ZenSide on iOS:

1. On a Mac with Xcode installed, create a new iOS App project (SwiftUI or
   UIKit) in this `iosApp/` directory.
2. Point it at the `shared` module's iOS output. Two supported options:
   - **Direct framework embedding**: build the Kotlin/Native framework with
     `./gradlew :shared:assembleXCFramework` (or let Xcode invoke the
     Gradle build via an embedAndSign build phase, the standard KMP
     wizard template) and link the resulting `shared.xcframework`.
   - **CocoaPods**: add the `org.jetbrains.kotlin.native.cocoapods` plugin
     to `shared/build.gradle.kts` and run `pod install` from an Xcode
     project with a `Podfile` pointing at the shared module's podspec.
3. From Swift, call the shared module's root composable bridge — a
   `UIViewController` wrapping `ZenSideApp(rootComponent)` via
   Compose Multiplatform's `ComposeUIViewController`. Create the
   `DefaultRootComponent` with an iOS `LifecycleRegistry`
   (`com.arkivanov.essenty.lifecycle`) and call `initKoin()` once at app
   launch, the same way `androidApp`/`desktopApp` do.

The `shared` module's `iosMain` source set already contains the actual
`getKoinPlatformModule()` (SQLDelight native driver) needed for this to
compile and link — verified via `./gradlew :shared:compileKotlinMetadata`
and the iOS target compile tasks, since no Xcode/macOS is available here to
actually run or test the app.
