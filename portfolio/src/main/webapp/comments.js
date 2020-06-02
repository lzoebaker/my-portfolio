/*
* Purpose: recieves HTTP promise response from CommentsServlet.java, places authors and comments into appropriate html container 
*/

function getComment() {
    const responsePromise = fetch('/comments');
    // when server request complete, pass response into handleResponse
    responsePromise.then(handleResponse);
}

function handleResponse(response) {
    // receives Java Object (ArrayList reponse)
    const commentListPromise = response.json();
    commentListPromise.then(addCommentsToDom);
}

/* Adds authors and comments to html page, displaying author: comment */
function addCommentsToDom(commentList) {
    const commentContainer = document.getElementById('comments-container');
    commentContainer.innerHTML = '';
    for (let i = 0; i < commentList.length; i++){
        commentContainer.appendChild(
            createComment(commentList[i].author, commentList[i].value));
    }
}

/** Creates an <li> element containing author: comment. */
function createComment(author, value) {
  const liElement = document.createElement('li');
  liElement.innerText = author + ": " + value;
  return liElement;
}