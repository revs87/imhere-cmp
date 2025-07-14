import SwiftUI
import ComposeApp
import GoogleMaps

struct ComposeAppView: UIViewControllerRepresentable {
    @StateObject var mapState = MapState()

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(

            mapUIControllerFactory: { () -> UIViewController in
                return UIHostingController(rootView: MapView(mapState: self.mapState))
            },

            mapUIControllerUpdate: { (lat: NSNumber, long: NSNumber) -> Void in
                DispatchQueue.main.async {
                    mapState.latitude = lat.doubleValue
                    mapState.longitude = long.doubleValue
                }
            }

        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        print("updateUIViewController: \(mapState.latitude), \(mapState.longitude) -> \(uiViewController.children.first)")

        if let firstChild = uiViewController.children.first {
            // Now try to cast it to UIHostingController<MapView>
            if let hostingController = firstChild as? UIHostingController<MapView> {
                // Access its rootView (which is your MapView struct instance)
                let currentMapView = hostingController.rootView as MapView
                print("Successfully accessed UIHostingController. Its rootView (MapView) has latitude: \(currentMapView.mapState.latitude)")

                let updatedSwiftMapView = MapView(mapState: self.mapState)

                DispatchQueue.main.async {
                    hostingController.rootView = updatedSwiftMapView
                    print("Set new rootView on UIHostingController with Lat: \(self.mapState.latitude)")
                }
            } else {
                print("Failed to cast the first child to UIHostingController<MapView>. Actual type: \(type(of: firstChild))")
            }
        } else {
            print("The uiViewController has no children.")
        }
    }
}
