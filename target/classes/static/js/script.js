//DO NOT PUT IN PRODUCTION - DEBUG PURPOSES ONLY
//window.onload = function () {
//  if (window.history.replaceState) {
//    window.history.replaceState(null, null, window.location.href);
//  }
//};

function loadAddNewEventOverlay() {
  document.getElementById("add-new-event-overlay").style.display = "flex";
}

function unloadAddNewEventOverlay() {
  document.getElementById("add-new-event-overlay").style.display = "none";
}

function loadAddNewNoteOverlay() {
  document.getElementById("add-new-note-overlay").style.display = "flex";

  var isChecklist = document.getElementById("is-checklist").checked;

  document.getElementById("not-checklist").style.display = isChecklist ? "none" : "flex";
  document.getElementById("not-checklist-input").required = !isChecklist;

  document.getElementById("yes-checklist").style.display = isChecklist ? "flex" : "none";
  var elements = document.querySelectorAll(".yes-checklist-input");
  console.log(elements);
  elements.forEach(function (element) {
    element.required = isChecklist;
  });
}

function unloadAddNewNoteOverlay() {
  document.getElementById("add-new-note-overlay").style.display = "none";
}

function loadViewNoteOverlay() {
  document.getElementById("view-note-overlay").style.display = "flex";
}

function unloadViewNoteOverlay() {
  document.getElementById("view-note-overlay").style.display = "none";
}

function loadLogoutOverlay() {
  document.getElementById("logout-overlay").style.display = "flex";
}

function unloadLogoutOverlay() {
  document.getElementById("logout-overlay").style.display = "none";
}

function loadDeleteOverlay() {
  document.getElementById("delete-overlay").style.display = "flex";
}

function unloadDeleteOverlay() {
  document.getElementById("delete-overlay").style.display = "none";
}

function showNote(eventId) {
  var form = document.getElementById("notes-form");
  form.action = window.location.pathname + "/getNoteDetails/" + eventId;
  form.submit();
}

var id = 0;
var prevCall = 0;

function addNewCheckItem(index) {
  var checklistContainer = document.getElementById("checklist-scroll-container-" + index);

  if (prevCall != 1 && index == 1) {
    id = 1;
    prevCall = 1;
  } else if (prevCall != 2 && index == 2) {
    id = document.querySelectorAll('#checklist-scroll-container-' + index + ' .checkitem').length;
    prevCall = 2;
  }

  var newCheckItem = document.createElement('div');
  newCheckItem.classList.add('checkitem');

  if (index == 1)
    newCheckItem.innerHTML =
    " <input type=\"checkbox\" name=\"checkbox" + id + "\" th:checked=\"${isChecklist}\"> "
    + " <input type=\"text\" class=\"form-entry yes-checklist-input\" name=\"checkitem" + id + "\" maxlength=\"50\" placeholder=\"New item\" required> "
    + " <button type=\"button\" class=\"overlay-button cancel-btn\" onClick=\"deleteCheckitem()\">Delete</button> ";
  else
    newCheckItem.innerHTML =
    " <input type=\"checkbox\" name=\"checkbox" + id + "\" th:checked=\"${isChecklist}\"> "
    + " <input type=\"text\" class=\"form-entry\" name=\"checkitem" + id + "\" maxlength=\"50\" placeholder=\"New item\" required> "
    + " <button type=\"button\" class=\"overlay-button cancel-btn\" onClick=\"deleteCheckitem()\">Delete</button> ";

  checklistContainer.appendChild(newCheckItem);

  id++;
}

function deleteCheckitem() {
  event.target.closest('.checkitem').remove();
}

function unshowNote() {
  var form = document.getElementById("view-note-overlay-form");
  var action = /*[[@{/noteplan.ro/dashboard/deleteSmodel}]]*/ window.location.pathname + "/deleteSmodel";

  form.setAttribute("action", action);
  form.submit();

  document.getElementById("view-note-overlay").style.display = "none";
}

function deleteNote() {
  var form = document.getElementById("view-note-overlay-form");
  var action = /*[[@{/noteplan.ro/dashboard/deleteNote}]]*/ window.location.pathname + "/deleteNote";

  form.setAttribute("action", action);
  form.submit();
}

function showEvent(eventId) {
  var form = document.getElementById("events-form");
  form.action = window.location.pathname + "/getEventDetails/" + eventId;
  form.submit();
}

function unshowEvent() {
  var form = document.getElementById("view-event-overlay-form");
  var action = /*[[@{/noteplan.ro/dashboard/deleteSmodel}]]*/ window.location.pathname + "/deleteSmodel";

  form.setAttribute("action", action);
  form.submit();

  document.getElementById("view-event-overlay").style.display = "none";
}

function deleteEvent() {
  var form = document.getElementById("view-event-overlay-form");
  var action = /*[[@{/noteplan.ro/dashboard/deleteEvent}]]*/ window.location.pathname + "/deleteEvent";

  form.setAttribute("action", action);
  form.submit();
}

//register data checker
function validate() {
  var password = document.getElementById("password").value;
  var confirmPassword = document.getElementById("password-confirm").value;
  var invalidPassword = document.getElementById("invalid-password");
  var incorrectPassword = document.getElementById("incorrect-password");

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

//date formater
var todayy = new Date().toISOString().split('T')[0];
var closestFutureDateDifference = Number.MAX_SAFE_INTEGER;
var closestFutureDateElement = null;

var eventDateElements = document.querySelectorAll('.event-date');
var monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

eventDateElements.forEach(function (element) {
  var date = element.textContent.trim();
  var dateObject = new Date(date);

  if (date > todayy) {
    var dateDifference = Math.abs(new Date(date) - new Date(todayy)) / (1000 * 60 * 60 * 24);
    if (dateDifference < closestFutureDateDifference) {
      closestFutureDateElement = element;
      closestFutureDateDifference = dateDifference;
    }
  }

  var formattedDate = dateObject.getDate() + ' of ' + monthNames[dateObject.getMonth()] + ' ' + dateObject.getFullYear();
  element.textContent = formattedDate;
});

if (closestFutureDateElement)
  document.querySelector('.scroll-container').scrollTop = closestFutureDateElement.offsetTop;

//show password methods
function togglePassword() {
  const type = document.getElementById("password").getAttribute("type") === "password" ? "text" : "password";
  document.getElementById("password").setAttribute("type", type);

  document.getElementById("password-eye").classList.toggle("bi-eye");
}

function togglePasswordConfirm() {
  const type = document.getElementById("password-confirm").getAttribute("type") === "password" ? "text" : "password";
  document.getElementById("password-confirm").setAttribute("type", type);

  document.getElementById("password-confirm-eye").classList.toggle("bi-eye");
}

function checkboxHelper(){
	event.preventDefault();
}
