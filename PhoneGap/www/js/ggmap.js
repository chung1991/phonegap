// This example adds a search box to a map, using the Google Place Autocomplete
// feature. People can enter geographical searches. The search box will return a
// pick list containing a mix of places and predicted search terms.

// This example requires the Places library. Include the libraries=places
// parameter when you first load the API. For example:
// <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">
var map = null;
var markers = [];
var savedMarkers = {};

function centerMarker(lat, lon, name) {
    map.setCenter(new google.maps.LatLng(parseFloat(lat), parseFloat(lon)));
    map.setZoom(13);

    var key = lat + "/" + lon + "/" + name;
    var value = savedMarkers[key];
    if (value != null && value.marker != null) {
        value.marker.info.open(map, value.marker);
    }
}

function clearMarker(lat, lon, name) {
    var key = lat + "/" + lon + "/" + name;
    var value = savedMarkers[key];
    if (value != null && value.marker != null) {
        value.marker.setMap(null);
        delete savedMarkers[key];
    }
}

function reloadMarkers() {
    for (var key in savedMarkers) {
        var value = savedMarkers[key];

        if (value.marker == null) {
            var parts = key.split("/");

            var lat = parts[0];
            var lon = parts[1];
            var name = parts[2];

            var myLatlng = new google.maps.LatLng(parseFloat(lat), parseFloat(lon));
            var marker = new google.maps.Marker({
                position: myLatlng,
                map: map,
                draggable: false,
                title: name
            });

            var infoMarker = new google.maps.InfoWindow({
                content: value.eventDescription
            });
            marker.info = infoMarker;

            markers.push(marker);
            value.marker = marker;
        }
    }
}

function initAutocomplete() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 21.0277644, lng: 105.8341},
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });


    // Create the search box and for creating event
    var txtAddEventLocation = document.getElementById("txtAddEventLocation");
    var txtAddEventLocationSearchBox = new google.maps.places.SearchBox(txtAddEventLocation);

    var autocomplete = new google.maps.places.Autocomplete(txtAddEventLocation);
    google.maps.event.addListener(autocomplete, 'place_changed', function () {
        var place = autocomplete.getPlace();
        document.getElementById('txtAddEventLocation').value = place.name;
        document.getElementById('locationLat').value = place.geometry.location.lat();
        document.getElementById('locationLon').value = place.geometry.location.lng();
    });

    // Create the search box and link it to the UI element.
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function() {
        searchBox.setBounds(map.getBounds());
    });

    markers = [];
    //// Listen for the event fired when the user selects a prediction and retrieve
    //// more details for that place.
    //searchBox.addListener('places_changed', function() {
    //    var places = searchBox.getPlaces();
    //
    //    if (places.length == 0) {
    //        return;
    //    }
    //
    //    // Clear out the old markers.
    //    markers.forEach(function(marker) {
    //        marker.setMap(null);
    //    });
    //    markers = [];
    //
    //    // For each place, get the icon, name and location.
    //    var bounds = new google.maps.LatLngBounds();
    //    places.forEach(function(place) {
    //        var icon = {
    //            url: place.icon,
    //            size: new google.maps.Size(71, 71),
    //            origin: new google.maps.Point(0, 0),
    //            anchor: new google.maps.Point(17, 34),
    //            scaledSize: new google.maps.Size(25, 25)
    //        };
    //
    //        // Create a marker for each place.
    //        markers.push(new google.maps.Marker({
    //            map: map,
    //            icon: icon,
    //            title: place.name,
    //            position: place.geometry.location
    //        }));
    //
    //        if (place.geometry.viewport) {
    //            // Only geocodes have viewport.
    //            bounds.union(place.geometry.viewport);
    //        } else {
    //            bounds.extend(place.geometry.location);
    //        }
    //    });
    //    map.fitBounds(bounds);
    //});
}