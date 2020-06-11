/*
* Purpose: recieves HTTP promise response from Comments servlet, UserAuthentication servlet, modifies comments page accordingly
*/

function loadCommentPage(){
    getUserAuthentication();
    getComment();
}

function getComment() {
    const responsePromise = fetch('/comments');
    responsePromise.then(handleCommentResponse);
}

function handleCommentResponse(response) {
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
  const commentText = createCommentText(author, value);
  commentWrapper.appendChild(commentText);
  commentWrapper.appendChild(createDeleteButton(commentText));
  $('.comment_div p, .comment_div button').css('display', 'inline-block');
  return commentWrapper;
}

function createCommentText(author, value) {
  const commentText = document.createElement('p');
  commentText.classList.add('comment-text');
  commentText.innerText = author + ": " + value;
  return commentText;
}

/** Creates a delete button linked to the deletion of each comment */
function createDeleteButton(commentText) {
  const deleteButton = document.createElement('form');
  deleteButton.action = '/delete-comment';
  deleteButton.method = 'GET';
  deleteButton.appendChild(createSubmitButton());
  deleteButton.appendChild(createKeyValueField(commentText));
  return deleteButton;
}

/** creates the submit button that triggers sending the "author:value" text of comment*/
function createSubmitButton() {
  const submitButton = document.createElement('input');
  submitButton.classList.add('delete-button');
  submitButton.type = 'submit';
  submitButton.value = 'delete';
  submitButton.onclick = removeCommentDiv(this);
  return submitButton;
}

function removeCommentDiv(elem){
      $(elem).closest('div').remove();
}

/** sets the value to be sent as "author:value", which is used to identify comment for deletion */
function createKeyValueField(commentText) {
  const keyValueOfComment = document.createElement('input');
  keyValueOfComment.type = 'hidden';
  keyValueOfComment.value = commentText.innerText;
  keyValueOfComment.name = 'key-value';
  return keyValueOfComment;
}

function getUserAuthentication() {
    const responsePromise = fetch('/login');
    responsePromise.then(handleAuthenticationResponse);
}

function handleAuthenticationResponse(response) {
    const authenticationInfoJSON = response.json();
    authenticationInfoJSON.then(changeAuthenticationDom);
}

function changeAuthenticationDom(authenticationInfo) {
    if (authenticationInfo.isLoggedIn) {
      document.getElementById('comment-form').hidden = false;
    } 
    else {
      document.getElementById('comment-form').hidden = true;
    }
}