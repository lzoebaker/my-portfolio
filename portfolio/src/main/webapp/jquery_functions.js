/*
* Purpose: contains various JQuery functions that listen for events on main portfolio page
*/

let background_img_paths = new Array(5);
background_img_paths = ["../images/mountbierstadt.jpg", 
                        "../images/mountyale.jpg",
                        "../images/gardenofgods.jpg",
                        "../images/lakedillon.jpg",
                        "../images/mountelbert.jpg"
                       ];
profile_img_paths = ["images/noogler.gif",
                     "images/zoebakercircle.png"
                    ];
labels = ["the summit of Mount Bierdstadt.", 
          "the summit of Mount Yale.", 
          "the Garden of the Gods.", 
          "Lake Dillon, seen from the vantage point of driving along Dillon Dam road.",
          "the summit of Mount Elbert, the tallest point in Colorado."];

function preload_imgs(img_arr, img_paths){
  for (let i = 0; i < img_paths.length; i++) {
      img_arr[i] = new Image();
      img_arr[i].src = img_paths[i];
  }
}

$(document).ready(function(){
    // preload all images to speedup image transitions
  let all_image_paths = background_img_paths.concat(profile_img_paths)
  preload_imgs(new Array(all_image_paths.length), all_image_paths)

    // if change background button is clicked, background image for the body is changed. 
    // Appropriate image label is displayed.
  let current_image_index = 0;
  $("#change-background").click(function(){
      current_image_index = current_image_index % background_img_paths.length;
      $("body").css("background-image", "url(" + background_img_paths[current_image_index] + ")");
      $("#background-caption").html("This image is of "+ labels[current_image_index]);
      current_image_index++;            
  })
  // if mouse enters profile image attribute, change image to animated noogler.gif. Once mouse leaves,
  // change back to normal profile icon
   $(".profile").hover(function(){
        $(".profile").attr("src", "images/noogler.gif")
    },
    function(){
        $(".profile").attr("src", "images/zoebakercircle.png")
    });

});