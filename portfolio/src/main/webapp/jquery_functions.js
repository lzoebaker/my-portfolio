/*
* Purpose: contains various JQuery functions that listen for events on main portfolio page
*/

import { updateBackground, NUM_IMAGES } from './background_functions.js';

let profileImagePaths = ["images/noogler.gif", "images/zoebakercircle.png"];


// Loads image paths into an arbitrary array, in order to preload images for improved performance.
function preloadImages(imagePaths){
  let imageArr = new Array(imagePaths.length);
  for (let i = 0; i < imagePaths.length; i++) {
    let tempImage = new Image(); 
    tempImage.src = imagePaths[i]; 
    imageArr[i] = tempImage;
  }
  return imageArr;
}

let currentImageIndex = 1; // start on second image so as not to repeat landing image
$(document).ready(function(){

  let iconImages = preloadImages(profileImagePaths);

  // if change background button is clicked, background image for the body is changed.
  $("#change-background").click(function(){
    updateBackground(currentImageIndex);
    currentImageIndex = (currentImageIndex + 1) % NUM_IMAGES;
  });

  // if mouse enters profile image attribute, change image to animated noogler.gif. Once mouse leaves,
  // change back to normal profile icon
   $(".profile").hover(function(){
        $(".profile").attr("src", "images/noogler.gif")
    },
    function(){
        $(".profile").attr("src", "images/zoebakercircle.png")
    });

});