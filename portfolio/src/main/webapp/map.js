/** Creates a map and adds it to the page. */
const LATITUDE = 37.422;
const LONGITUDE = -122.084;
const ZOOM_FACTOR = 16;

function createMap() {
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: LATITUDE, lng: LONGITUDE}, zoom: ZOOM_FACTOR});
}
