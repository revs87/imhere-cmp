import SwiftUI
import GoogleMaps


class MapState: ObservableObject {
    @Published var latitude: Double = 0.0
    @Published var longitude: Double = 0.0
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
        marker.snippet = "Lat: \(String(format: "%.4f", mapState.latitude)), Long: \(String(format: "%.4f", mapState.longitude))"
        marker.map = uiView
        uiView.selectedMarker = marker
    }
}