import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        // Démarre Koin une seule fois au lancement, comme MainApplication côté Android
        MainViewControllerKt.startKoinIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}