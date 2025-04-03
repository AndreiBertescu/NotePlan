// DO NOT PUT IN PRODUCTION - DEBUG PURPOSES ONLY
// window.onload = function () {
//   if (window.history.replaceState) {
//     window.history.replaceState(null, null, window.location.href)
//   }
// }

window.loadAddNewEventOverlay = loadAddNewEventOverlay
function loadAddNewEventOverlay () {
  document.getElementById('add-new-event-overlay').style.display = 'flex'
}

window.unloadAddNewEventOverlay = unloadAddNewEventOverlay
function unloadAddNewEventOverlay () {
  document.getElementById('add-new-event-overlay').style.display = 'none'
}

window.loadAddNewNoteOverlay = loadAddNewNoteOverlay
function loadAddNewNoteOverlay () {
  document.getElementById('add-new-note-overlay').style.display = 'flex'

  const isChecklist = document.getElementById('is-checklist').checked

  document.getElementById('not-checklist').style.display = isChecklist ? 'none' : 'flex'
  document.getElementById('not-checklist-input').required = !isChecklist

  document.getElementById('yes-checklist').style.display = isChecklist ? 'flex' : 'none'
  const elements = document.querySelectorAll('.yes-checklist-input')
  console.log(elements)
  elements.forEach(function (element) {
    element.required = isChecklist
  })
}

window.unloadAddNewNoteOverlay = unloadAddNewNoteOverlay
function unloadAddNewNoteOverlay () {
  document.getElementById('add-new-note-overlay').style.display = 'none'
}

window.loadViewNoteOverlay = loadViewNoteOverlay
function loadViewNoteOverlay () {
  document.getElementById('view-note-overlay').style.display = 'flex'
}

window.unloadViewNoteOverlay = unloadViewNoteOverlay
function unloadViewNoteOverlay () {
  document.getElementById('view-note-overlay').style.display = 'none'
}

window.loadLogoutOverlay = loadLogoutOverlay
function loadLogoutOverlay () {
  document.getElementById('logout-overlay').style.display = 'flex'
}

window.unloadLogoutOverlay = unloadLogoutOverlay
function unloadLogoutOverlay () {
  document.getElementById('logout-overlay').style.display = 'none'
}

window.loadDeleteOverlay = loadDeleteOverlay
function loadDeleteOverlay () {
  document.getElementById('delete-overlay').style.display = 'flex'
}

window.unloadDeleteOverlay = unloadDeleteOverlay
function unloadDeleteOverlay () {
  document.getElementById('delete-overlay').style.display = 'none'
}

window.showNote = showNote
function showNote (eventId) {
  const form = document.getElementById('notes-form')
  form.action = window.location.pathname + '/getNoteDetails/' + eventId
  form.submit()
}

let id = 0
let prevCall = 0

window.addNewCheckItem = addNewCheckItem
function addNewCheckItem (index) {
  const checklistContainer = document.getElementById('checklist-scroll-container-' + index)

  if (prevCall !== 1 && index === 1) {
    id = 1
    prevCall = 1
  } else if (prevCall !== 2 && index === 2) {
    id = document.querySelectorAll('#checklist-scroll-container-' + index + ' .checkitem').length
    prevCall = 2
  }

  const newCheckItem = document.createElement('div')
  newCheckItem.classList.add('checkitem')

  if (index === 1) {
    newCheckItem.innerHTML =
    ' <input type=\'checkbox\' name=\'checkbox' + id + '\' th:checked=\'${isChecklist}\'> ' +
    ' <input type=\'text\' class=\'form-entry yes-checklist-input\' name=\'checkitem' + id + '\' maxlength=\'50\' placeholder=\'New item\' required> ' +
    ' <button type=\'button\' class=\'overlay-button cancel-btn\' onClick=\'deleteCheckitem()\'>Delete</button> '
  } else {
    newCheckItem.innerHTML =
    ' <input type=\'checkbox\' name=\'checkbox' + id + '\' th:checked=\'${isChecklist}\'> ' +
    ' <input type=\'text\' class=\'form-entry\' name=\'checkitem' + id + '\' maxlength=\'50\' placeholder=\'New item\' required> ' +
    ' <button type=\'button\' class=\'overlay-button cancel-btn\' onClick=\'deleteCheckitem()\'>Delete</button> '
  }

  checklistContainer.appendChild(newCheckItem)

  id++
}

window.deleteCheckitem = deleteCheckitem
function deleteCheckitem () {
  event.target.closest('.checkitem').remove()
}

window.unshowNote = unshowNote
function unshowNote () {
  const form = document.getElementById('view-note-overlay-form')
  const action = /* [[@{/noteplan.ro/dashboard/deleteSmodel}]] */ window.location.pathname + '/deleteSmodel'

  form.setAttribute('action', action)
  form.submit()

  document.getElementById('view-note-overlay').style.display = 'none'
}

window.deleteNote = deleteNote
function deleteNote () {
  const form = document.getElementById('view-note-overlay-form')
  const action = /* [[@{/noteplan.ro/dashboard/deleteNote}]] */ window.location.pathname + '/deleteNote'

  form.setAttribute('action', action)
  form.submit()
}

window.showEvent = showEvent
function showEvent (eventId) {
  const form = document.getElementById('events-form')
  form.action = window.location.pathname + '/getEventDetails/' + eventId
  form.submit()
}

window.unshowEvent = unshowEvent
function unshowEvent () {
  const form = document.getElementById('view-event-overlay-form')
  const action = /* [[@{/noteplan.ro/dashboard/deleteSmodel}]] */ window.location.pathname + '/deleteSmodel'

  form.setAttribute('action', action)
  form.submit()

  document.getElementById('view-event-overlay').style.display = 'none'
}

window.deleteEvent = deleteEvent
function deleteEvent () {
  const form = document.getElementById('view-event-overlay-form')
  const action = /* [[@{/noteplan.ro/dashboard/deleteEvent}]] */ window.location.pathname + '/deleteEvent'

  form.setAttribute('action', action)
  form.submit()
}

// register data checker
window.validate = validate
function validate () {
  const password = document.getElementById('password').value
  const confirmPassword = document.getElementById('password-confirm').value
  const invalidPassword = document.getElementById('invalid-password')
  const incorrectPassword = document.getElementById('incorrect-password')

  invalidPassword.style.display = 'none'
  incorrectPassword.style.display = 'none'

  const regex = /^(?=.*\d)(?=.*[A-Z]).{6,}$/ // Password pattern: at least one number and one uppercase letter, minimum length 6

  if (!regex.test(password)) {
    invalidPassword.style.display = 'inherit'
    return false
  } else if (password !== confirmPassword) {
    incorrectPassword.style.display = 'inherit'
    return false
  } else {
    return true
  }
}

// date formater
const todayy = new Date().toISOString().split('T')[0]
let closestFutureDateDifference = Number.MAX_SAFE_INTEGER
let closestFutureDateElement = null

const eventDateElements = document.querySelectorAll('.event-date')
const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']

eventDateElements.forEach(function (element) {
  const date = element.textContent.trim()
  const dateObject = new Date(date)

  if (date > todayy) {
    const dateDifference = Math.abs(new Date(date) - new Date(todayy)) / (1000 * 60 * 60 * 24)
    if (dateDifference < closestFutureDateDifference) {
      closestFutureDateElement = element
      closestFutureDateDifference = dateDifference
    }
  }

  const formattedDate = dateObject.getDate() + ' of ' + monthNames[dateObject.getMonth()] + ' ' + dateObject.getFullYear()
  element.textContent = formattedDate
})

if (closestFutureDateElement) {
  document.querySelector('.scroll-container').scrollTop = closestFutureDateElement.offsetTop
}

// show password methods
window.togglePassword = togglePassword
function togglePassword () {
  const type = document.getElementById('password').getAttribute('type') === 'password' ? 'text' : 'password'
  document.getElementById('password').setAttribute('type', type)

  document.getElementById('password-eye').classList.toggle('bi-eye')
}

window.togglePasswordConfirm = togglePasswordConfirm
function togglePasswordConfirm () {
  const type = document.getElementById('password-confirm').getAttribute('type') === 'password' ? 'text' : 'password'
  document.getElementById('password-confirm').setAttribute('type', type)

  document.getElementById('password-confirm-eye').classList.toggle('bi-eye')
}

window.checkboxHelper = checkboxHelper
function checkboxHelper () {
  event.preventDefault()
}
