let img_arr = new Array(5);
img_arr = ["../images/mountbierstadt.jpg", 
           "../images/mountyale.jpg",
           "../images/gardenofgods.jpg",
           "../images/lakedillon.jpg",
           "../images/mountelbert.jpg"
           ];
labels = ["the summit of Mount Bierdstadt.", 
          "the summit of Mount Yale.", 
          "the Garden of the Gods.", 
          "Lake Dillon, seen from the vantage point of driving along Dillon Dam road.",
          "the summit of Mount Elbert, the tallest point in Colorado."];


$(document).ready(function(){
    // if change background button is clicked, background image for the body is changed. 
    // Appropriate image label is displayed.
  let index = 0;
  $("#change-background").click(function(){
      index++;
      if (index == img_arr.length){
        index = 0;
      }
      $("body").css("background-image", "url(" + img_arr[index] + ")");
      $("#background-caption").html("This image is of "+ labels[index]);
  })
});