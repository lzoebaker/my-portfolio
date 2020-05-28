/*
* Purpose: contains helper functions used in the jquery_functions.js file
*/

export {preloadImages, detectMobileDevice}; 

const MOBILE_SCREEN_WIDTH = 600; // pixel threshold for what to consider as a mobile device

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

// returns true if being accessed by a mobile device
function detectMobileDevice(){
    if ($(window).width() < MOBILE_SCREEN_WIDTH) {
        return true;
    }
    else{
        return false;
    }
}