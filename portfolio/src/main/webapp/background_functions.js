/*
* Purpose: contains functions for changing the background 
*/

export {updateBackground, NUM_IMAGES}; 

const SPRITE_URL = "url('images/background_sprites.png')"
const BACKGROUND_IMAGE_WIDTH = 2016;
const BACKGROUND_IMAGE_HEIGHT = 1512;
const VERT_SHIFT = 200;
const VERT_PADDING = 20;
const HORZ_PADDING = 20;
const SPRITE_IMAGE_ROWS = 2;
const SPRITE_IMAGE_COLS = 3;
const NUM_IMAGES = 5;

let labels = ["the summit of Mount Bierdstadt.", 
             "the summit of Mount Yale.", 
             "the summit of Mount Elbert, the tallest point in Colorado.",
             "the Garden of the Gods.", 
             "Lake Dillon, seen from the vantage point of driving along Dillon Dam road."];

// returns css string corresponding to correct position of bacgkround image in sprite sheet
function getBackgroundPositionString(index){
  let row = Math.floor(index / SPRITE_IMAGE_COLS);
  let verticalOffset = BACKGROUND_IMAGE_HEIGHT * row
  verticalOffset += (row+1) * VERT_PADDING + VERT_SHIFT;
  let col = index % SPRITE_IMAGE_COLS
  let horizontalOffset = BACKGROUND_IMAGE_WIDTH * col
  horizontalOffset += (col+1) * HORZ_PADDING;
    
  return SPRITE_URL + " -" + horizontalOffset.toString()+ "px -" + verticalOffset.toString() +"px";
}

// given a css position string, updates css attributes to display background
function updateBackgroundCss(backgroundPositionString){
  $("body").css("background", backgroundPositionString);
  $("body").css("background-attachment", "fixed");
}

// driver function, called by jquer_functions.js
function updateBackground(index){
  let positionString = getBackgroundPositionString(index);
  updateBackgroundCss(positionString);
  // appropriate label displayed
  $("#background-caption").html("This image is of "+ labels[index]);
}

