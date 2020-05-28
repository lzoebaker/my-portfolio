/*
* Purpose: contains various JQuery functions that listen for events on main portfolio page
*/
// For each individual background img, width is 2016px height is 1512px
const BACKGROUND_IMG_WIDTH = 2016;
const BACKGROUND_IMG_HEIGHT = 1512;
const VERT_PADDING = 40;
const HORZ_PADDING = 20;
const SPRITE_IMG_ROWS = 2;
const SPRITE_IMG_COLS = 3;
const NUM_IMAGES = 5;

labels = ["the summit of Mount Bierdstadt.", 
          "the summit of Mount Yale.", 
          "the summit of Mount Elbert, the tallest point in Colorado.",
          "the Garden of the Gods.", 
          "Lake Dillon, seen from the vantage point of driving along Dillon Dam road."];

profile_img_paths = ["images/noogler.gif",
                     "images/zoebakercircle.png"
                    ];

function preload_imgs(img_arr, img_paths){
  for (let i = 0; i < img_paths.length; i++) {
      img_arr[i] = new Image();
      img_arr[i].src = img_paths[i];
  }
}

$(document).ready(function(){
    // preload profile icon images + gif
    preload_imgs(new Array(2), profile_img_paths);

    // if change background button is clicked, background image for the body is changed.
    // each background image corresponds to a pixel location in a larger sprite image. 
    // if change background button is clicked, background image for the body is changed. 
  let current_image_index = 1; // start on second image so as not to repeat landing image
  $("#change-background").click(function(){
      current_image_index = current_image_index % NUM_IMAGES;
      
      let row = Math.floor(current_image_index / SPRITE_IMG_COLS);
      let vertical_offset = BACKGROUND_IMG_HEIGHT * row
      vertical_offset += (row+1) * VERT_PADDING;
      let col = current_image_index % SPRITE_IMG_COLS
      let horizontal_offset = BACKGROUND_IMG_WIDTH * col
      horizontal_offset += (col+1) * HORZ_PADDING;
      
      let string_background = "url('images/background_sprites.png') -" + horizontal_offset.toString()+ "px -" + vertical_offset.toString() +"px";
      $("body").css("background", string_background);
      $("body").css("background-attachment", "fixed");
       
      // Appropriate image label is displayed.
      $("#background-caption").html("This image is of "+ labels[current_image_index]);
      current_image_index++;            
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