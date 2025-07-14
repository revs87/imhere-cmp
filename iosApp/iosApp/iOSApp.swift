import SwiftUI
import GoogleMaps
import FirebaseCore
import Firebase

@main
struct iOSApp: App {
    init() {
        FirebaseApp.configure()

        if let path = Bundle.main.path(forResource: "Secrets", ofType: "plist"),
           let dict = NSDictionary(contentsOfFile: path) as? [String: Any] {

            if let mapsApiKey = dict["mapsApiKey"] as? String {
                GMSServices.provideAPIKey(mapsApiKey)
            } else {
                print("Error: 'mapsApiKey' not found in Secrets.plist")
            }
        } else {
            print("Error: Secrets.plist not found or could not be loaded.")
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}