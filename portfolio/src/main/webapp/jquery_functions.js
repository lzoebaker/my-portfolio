/*
* Purpose: contains various JQuery functions that listen for events on main portfolio page
*/

import { updateBackground, NUM_IMAGES } from './background_functions.js';
import { preloadImages, detectMobileDevice } from './helper_functions.js';

const GIF_IMAGE = "images/noogler.gif";
const DEFAULT_PROFILE_IMAGE = "images/zoebakercircle.png"
let profileImagePaths = [GIF_IMAGE, DEFAULT_PROFILE_IMAGE];

let currentImageIndex = 1; // start on second image so as not to repeat landing image

$(document).ready(function(){
  let isMobile = detectMobileDevice();

  let iconImages = preloadImages(profileImagePaths);
 
  if (!isMobile){
    // if change background button is clicked, background image for the body is changed.
    $("#change-background").click(function(){
      updateBackground(currentImageIndex);
      currentImageIndex = (currentImageIndex + 1) % NUM_IMAGES;
    });

    // if mouse enters profile image attribute, change image to animated noogler.gif. Once mouse leaves,
    // change back to normal profile icon
    $(".profile").hover(function(){
        $(".profile").attr("src", GIF_IMAGE);
    },
    function(){
        $(".profile").attr("src", DEFAULT_PROFILE_IMAGE);
    });
  } else { // if mobile, actively click to change (hover does not work as expected)
    $(".profile").toggle(function(){
      $(".profile").attr("src", GIF_IMAGE);
    },
    function(){
      $(".profile").attr("src", DEFAULT_PROFILE_IMAGE);
    });   
}
});