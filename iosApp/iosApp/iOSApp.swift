import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
    init() {
        GMSServices.provideAPIKey("mapsApiKey")
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}