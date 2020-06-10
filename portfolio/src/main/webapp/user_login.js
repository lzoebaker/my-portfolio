/* Purpose: script that manages front end of user logging in and out */

function getUserAuthentication() {
    const responsePromise = fetch('/login');
    // when server request complete, pass response into handleResponse
    responsePromise.then(handleResponse);
}

function handleResponse(response) {
    // receives Java Object (ArrayList reponse)
    const authenticationInfoJSON = response.json();
    authenticationInfoJSON.then(changeDom);
}

/* Adds log in/ log out options to html page. */
function changeDom(authenticationInfo) {
    if (authenticationInfo.isLoggedIn) {
      document.body.innerHTML += ("<p>Hello " + authenticationInfo.displayName + "!</p>");
      document.body.innerHTML += ("<p>Logout <a href=\"" + authenticationInfo.authenticationUrl + "\">here</a>.</p>");
    } else {
      document.body.innerHTML += ("<p>You must login to comment.</p>");
      document.body.innerHTML += ("<p>Login <a href=\"" + authenticationInfo.authenticationUrl + "\">here</a>.</p>");
    }
}
