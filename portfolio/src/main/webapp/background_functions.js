/*
* Purpose: contains functions for changing the background 
*/

export {update_background, NUM_IMAGES}; 

const SPRITE_URL = "url('images/background_sprites.png')"
const BACKGROUND_IMAGE_WIDTH = 2016;
const BACKGROUND_IMAGE_HEIGHT = 1512;
const VERT_PADDING = 40;
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
function get_background_position_string(index){
  let row = Math.floor(index / SPRITE_IMAGE_COLS);
  let vertical_offset = BACKGROUND_IMAGE_HEIGHT * row
  vertical_offset += (row+1) * VERT_PADDING;
  let col = index % SPRITE_IMAGE_COLS
  let horizontal_offset = BACKGROUND_IMAGE_WIDTH * col
  horizontal_offset += (col+1) * HORZ_PADDING;
    
  return SPRITE_URL + " -" + horizontal_offset.toString()+ "px -" + vertical_offset.toString() +"px";
}

// given a css position string, updates css attributes to display background
function update_background_css(background_position_string){
  $("body").css("background", background_position_string);
  $("body").css("background-attachment", "fixed");
}

// driver function, called by jquer_functions.js
function update_background(index){
  let position_string = get_background_position_string(index);
  update_background_css(position_string);
  // appropriate label displayed
  $("#background-caption").html("This image is of "+ labels[index]);
}

