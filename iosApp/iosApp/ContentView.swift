import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps
import CoreLocation
import Combine

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}

class MapState: ObservableObject {
    @Published var latitude: Double = 0.0
    @Published var longitude: Double = 0.0
}

struct ComposeView: UIViewControllerRepresentable {
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

struct MapView: UIViewRepresentable {
    var mapState: MapState

    private let marker = GMSMarker()

    func makeUIView(context: Context) -> GMSMapView {
        let camera = GMSCameraPosition.camera(
            withLatitude: mapState.latitude,
            longitude: mapState.longitude,
            zoom: 16.0
        )
        let mapView = GMSMapView.map(withFrame: .zero, camera: camera)
        mapView.settings.compassButton = true
        mapView.settings.myLocationButton = true

        marker.position = CLLocationCoordinate2D(latitude: mapState.latitude, longitude: mapState.longitude)
        marker.title = "Your Location"
        marker.snippet = "snippet"
        marker.map = mapView

        return mapView
    }

    // This method is now automatically called whenever a @Published property
    // in mapState changes.
    func updateUIView(_ uiView: GMSMapView, context: Context) {
        print("updateUIView: \(mapState.latitude), \(mapState.longitude)")

        let newCoordinate = CLLocationCoordinate2D(latitude: mapState.latitude, longitude: mapState.longitude)

        // Animate the map and move the marker to the new location.
        uiView.animate(toLocation: newCoordinate)
        marker.position = newCoordinate
    }
}
