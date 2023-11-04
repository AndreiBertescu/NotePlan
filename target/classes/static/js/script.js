//DO NOT PUT IN PRODUCTION
//window.onload = function () {
//  if (window.history.replaceState) {
//    window.history.replaceState(null, null, window.location.href);
//  }
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

function showNote(eventId) {
  var form = document.getElementById("notesForm");
  form.action = "/getNoteDetails/" + eventId;
  form.submit();
}

function unshowNote() {
  document.getElementById("viewNoteOverlay").style.display = "none";
}

function deleteNote() {
  var form = document.getElementById("viewNoteOverlayForm");
  var action = /*[[@{/dashboard/deleteNote}]]*/ "/dashboard/deleteNote";

  form.setAttribute("action", action);
  form.submit();
}

function showEvent(eventId) {
  var form = document.getElementById("eventsForm");
  form.action = "/getEventDetails/" + eventId;
  form.submit();
}

function unshowEvent() {
  document.getElementById("viewEventOverlay").style.display = "none";
}

function deleteEvent() {
  var form = document.getElementById("viewEventOverlayForm");
  var action = /*[[@{/dashboard/deleteEvent}]]*/ "/dashboard/deleteEvent";

  form.setAttribute("action", action);
  form.submit();
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
