import SwiftUI

struct ContentView: View {
    var body: some View {
        ComposeAppView()
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}
