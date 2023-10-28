//DO NOT PUT IN PRODUCTION
//window.onload = function() {
//    if (window.history.replaceState) {
//        window.history.replaceState(null, null, window.location.href);
//    }
//};

function loadAddNewEventOverlay() {
  document.getElementById("addNewEventOverlay").style.display = "flex";
}

function unloadAddNewEventOverlay() {
  document.getElementById("addNewEventOverlay").style.display = "none";
}

function loadAddNewNoteOverlay() {
  document.getElementById("addNewNoteOverlay").style.display = "flex";
}

function unloadAddNewNoteOverlay() {
  document.getElementById("addNewNoteOverlay").style.display = "none";
}

function loadViewNoteOverlay() {
  document.getElementById("viewNoteOverlay").style.display = "flex";
}

function unloadViewNoteOverlay() {
  document.getElementById("viewNoteOverlay").style.display = "none";
}

function loadLogoutOverlay() {
  document.getElementById("logoutOverlay").style.display = "flex";
}

function unloadLogoutOverlay() {
  document.getElementById("logoutOverlay").style.display = "none";
}

//register data checker
function validate() {
  var password = document.getElementById("password").value;
  var confirmPassword = document.getElementById("passwordConfirm").value;
  var invalidPassword = document.getElementById("invalidPassword");
  var incorrectPassword = document.getElementById("incorrectPassword");

  invalidPassword.style.display = "none";
  incorrectPassword.style.display = "none";

  var regex = /^(?=.*\d)(?=.*[A-Z]).{6,}$/; // Password pattern: at least one number and one uppercase letter, minimum length 6

  if (!regex.test(password)) {
    invalidPassword.style.display = "inherit";
    return false;
  } else if (password !== confirmPassword) {
    incorrectPassword.style.display = "inherit";
    return false;
  } else {
    return true;
  }
}
