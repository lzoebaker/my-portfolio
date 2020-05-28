/*
* Purpose: contains various JQuery functions that listen for events on main portfolio page
*/

import { update_background, NUM_IMAGES } from './background_functions.js';

let profile_image_paths = ["images/noogler.gif", "images/zoebakercircle.png"];


// Loads image paths into an arbitrary array, in order to preload images for improved performance.
function preload_images(image_paths){
  let image_arr = new Array(image_paths.length);
  for (let i = 0; i < image_paths.length; i++) {
    let temp_image = new Image(); 
    temp_image.src = image_paths[i]; 
    image_arr[i] = temp_image;
  }
  return image_arr;
}

let current_image_index = 1; // start on second image so as not to repeat landing image
$(document).ready(function(){

  let icon_images = preload_images(profile_image_paths);

  // if change background button is clicked, background image for the body is changed.
  $("#change-background").click(function(){
    update_background(current_image_index);
    current_image_index = (current_image_index + 1) % NUM_IMAGES;
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