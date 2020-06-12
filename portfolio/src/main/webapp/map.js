/** Creates a map and adds it to the page. */
const ZOOM_FACTOR = 10;
const LOCATIONS = ('[{"latitude":"39.750520", "longitude":"-105.222603", \
                      "title":"Colorado School of Mines", "description":"My college."}, \
                     {"latitude":"40.151216", "longitude":"-105.166999", \
                      "title":"Silver Creek High School", "description":"My high school."}, \
                     {"latitude":"40.022038", "longitude":"-105.254843", \
                      "title":"Google Boulder", "description":"Where I am working this summer! (virtually)"}]');

function createMapAndMarkers() {
    locations = JSON.parse(LOCATIONS);
    centeredLocation = calculateCenterCoordinate(locations);
    map = createMap(ZOOM_FACTOR, centeredLocation[0], centeredLocation[1]);
    addAllMarkers(map, locations);
}

function extractCoordinates(location) {
    return [parseFloat(location.latitude), parseFloat(location.longitude)];
}

function calculateCenterCoordinate(locations) {
  latitudeSum = 0;
  longitudeSum = 0;
  for (let i = 0; i < locations.length; i++) {
    coordinates = extractCoordinates(locations[i])
    latitudeSum += coordinates[0];
    longitudeSum += coordinates[1];
  }  
  return [latitudeSum / locations.length, longitudeSum / locations.length];
}

function createMap(zoomFactor, latitude, longitude) {
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: latitude, lng: longitude}, zoom: zoomFactor});
  return map;
}

function addAllMarkers(map, locations) {
  for (let i = 0; i < locations.length; i++) {
    coordinates = extractCoordinates(locations[i])
    addMarker(map, coordinates[0], coordinates[1], locations[i].title, locations[i].description);
  }
}

function addMarker(map, lat, lng, title, description) {
  const marker = new google.maps.Marker(
      {position: {lat: lat, lng: lng}, map: map, title: title});

  const infoWindow = new google.maps.InfoWindow({content: description});
  marker.addListener('click', () => {
    infoWindow.open(map, marker);
  });
}
