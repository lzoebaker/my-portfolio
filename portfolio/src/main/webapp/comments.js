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

/* Adds authors and comments to html page, displaying author: comment.*/
function addCommentsToDom(commentList) {
    const commentContainer = document.getElementById('comments-container');
    commentContainer.innerHTML = '';
    for (let i = 0; i < commentList.length; i++){
        commentContainer.appendChild(
            createCommentAndDeleteButton(commentList[i].author, commentList[i].value));
    }
}

/** creates a comment element with inline delete button */
function createCommentAndDeleteButton(author, value) {
  const commentWrapper = document.createElement('div');
  commentWrapper.classList.add('comment_div');
  const commentElement = createComment(author, value);
  commentWrapper.appendChild(commentElement);
  commentWrapper.appendChild(createDeleteButton(commentElement));
  $('.comment_div li, .comment_div button').css('display', 'inline-block');
  return commentWrapper;
}

/** Creates an <li> element containing author: comment.*/
function createComment(author, value) {
  const liElement = document.createElement('li');
  liElement.innerText = author + ": " + value;
  return liElement;
}

/** Creates a delete button linked to the deletion of each comment */
function createDeleteButton(commentElement) {
  const deleteButton = document.createElement('form');
  deleteButton.action = '/delete-comment';
  deleteButton.method = 'GET';
  deleteButton.appendChild(createSubmitButton());
  deleteButton.appendChild(createKeyValueField(commentElement));
  return deleteButton;
}

/** creates the submit button that triggers sending the "author:value" text of comment*/
function createSubmitButton() {
  const submitButton = document.createElement('input');
  submitButton.type = 'submit';
  submitButton.value = 'delete';
  return submitButton;
}

/** sets the value to be sent as "author:value", which is used to identify comment for deletion */
function createKeyValueField(commentElement) {
  const keyValueOfComment = document.createElement('input');
  keyValueOfComment.type = 'hidden';
  keyValueOfComment.value = commentElement.innerText;
  keyValueOfComment.name = 'key-value';
  return keyValueOfComment;
}