const calendar = document.querySelector(".calendar"),
  date = document.querySelector(".date"),
  daysContainer = document.querySelector(".days"),
  prev = document.querySelector(".prev"),
  next = document.querySelector(".next"),
  todayBtn = document.querySelector(".today-btn"),
  gotoBtn = document.querySelector(".goto-btn"),
  dateInput = document.querySelector(".date-input"),
  eventDay = document.querySelector(".event-day"),
  eventDate = document.querySelector(".event-date"),
  eventsContainer = document.querySelector(".events"),
  addEventBtn = document.querySelector(".add-event"),
  addEventWrapper = document.querySelector(".add-event-wrapper "),
  addEventCloseBtn = document.querySelector(".close "),
  addEventTitle = document.querySelector(".event-name "),
  addEventFrom = document.querySelector(".event-time-from "),
  addEventTo = document.querySelector(".event-time-to "),
  addEventSubmit = document.querySelector(".add-event-btn ");

let today = new Date();
let activeDay;
let month = today.getMonth();
let year = today.getFullYear();

const months = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const eventsArr = [{
  day: 1,
  month: 1,
  year: 2002,
  color: "#000000",
  events: [{
      title: "Event 1",
      time: "10:00 AM",
    },
  ],
}, ];

getEvents();
//console.log(eventsArr);

//function to add days in days with class day and prev-date next-date on previous month and next month days and active on today
function initCalendar() {
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);
  const prevLastDay = new Date(year, month, 0);
  const prevDays = prevLastDay.getDate();
  const lastDate = lastDay.getDate();
  const day = firstDay.getDay();
  const nextDays = 7 - lastDay.getDay() - 1;

  date.innerHTML = months[month] + " " + year;

  let days = "";

  for (let x = day; x > 0; x--) {
    days += `<div class="day prev-date">${prevDays - x + 1}</div>`;
  }

  for (let i = 1; i <= lastDate; i++) {
    //check if event is present on that day
    let event = false;
    let color = "";
    eventsArr.forEach((eventObj) => {
      if (
        eventObj.day === i
        && eventObj.month === month + 1
        && eventObj.year === year
      ) {
        event = true;
        color = eventObj.color;
      }
    });
    if (
      i === new Date().getDate()
      && year === new Date().getFullYear()
      && month === new Date().getMonth()
    ) {
      activeDay = i;
      if (event) {
        days += `<div class="day today active event" id="day${i}">${i}<style>#day${i}::after{ background: ${color};}</style></div>`;
      } else {
        days += `<div class="day today active">${i}</div>`;
      }
    } else {
      if (event) {
        days += `<div class="day event" id="day${i}">${i}<style>#day${i}::after{ background: ${color};}</style></div>`;
      } else {
        days += `<div class="day">${i}</div>`;
      }
    }
  }

  for (let j = 1; j <= nextDays; j++) {
    days += `<div class="day next-date">${j}</div>`;
  }
  daysContainer.innerHTML = days;
  addListner();
}

//function to add month and year on prev and next button
function prevMonth() {
  month--;
  if (month < 0) {
    month = 11;
    year--;
  }
  initCalendar();
}

function nextMonth() {
  month++;
  if (month > 11) {
    month = 0;
    year++;
  }
  initCalendar();
}

prev.addEventListener("click", prevMonth);
next.addEventListener("click", nextMonth);

initCalendar();

//function to add active on day
function addListner() {
  const days = document.querySelectorAll(".day");
  days.forEach((day) => {
    day.addEventListener("click", (e) => {
      activeDay = Number(e.target.innerHTML);
      //remove active
      days.forEach((day) => {
        day.classList.remove("active");
      });
      //if clicked prev-date or next-date switch to that month
      if (e.target.classList.contains("prev-date")) {
        prevMonth();
        //add active to clicked day afte month is change
        setTimeout(() => {
          //add active where no prev-date or next-date
          const days = document.querySelectorAll(".day");
          days.forEach((day) => {
            if (
              !day.classList.contains("prev-date")
              && day.innerHTML === e.target.innerHTML
            ) {
              day.classList.add("active");
            }
          });
        }, 100);
      } else if (e.target.classList.contains("next-date")) {
        nextMonth();
        //add active to clicked day afte month is changed
        setTimeout(() => {
          const days = document.querySelectorAll(".day");
          days.forEach((day) => {
            if (
              !day.classList.contains("next-date")
              && day.innerHTML === e.target.innerHTML
            ) {
              day.classList.add("active");
            }
          });
        }, 100);
      } else {
        e.target.classList.add("active");
        
        if(e.target.classList.contains("event"))
        	jump(e.target.innerHTML.split('<')[0], month, year);
      }
    });
  });
}

todayBtn.addEventListener("click", () => {
  today = new Date();
  month = today.getMonth();
  year = today.getFullYear();
  initCalendar();
});

dateInput.addEventListener("input", (e) => {
  dateInput.value = dateInput.value.replace(/[^0-9/]/g, "");
  if (dateInput.value.length === 2) {
    dateInput.value += "/";
  }
  if (dateInput.value.length > 7) {
    dateInput.value = dateInput.value.slice(0, 7);
  }
  if (e.inputType === "deleteContentBackward") {
    if (dateInput.value.length === 3) {
      dateInput.value = dateInput.value.slice(0, 2);
    }
  }
});

gotoBtn.addEventListener("click", gotoDate);

function gotoDate() {
  const dateArr = dateInput.value.split("/");
  if (dateArr.length === 2) {
    if (dateArr[0] > 0 && dateArr[0] < 13 && dateArr[1].length === 4) {
      month = dateArr[0] - 1;
      year = dateArr[1];
      initCalendar();
      return;
    }
  }
  alert("Invalid Date");
}

//function to get events from local storage
function getEvents() {
  var eventDateElements = document.querySelectorAll('.eventDay');
	  
  eventDateElements.forEach(function(eventDay) {
	var element = eventDay.querySelector('.eventDate');
	  
    var date = element.textContent.trim();
	var parts = date.split(' of ');

    var day = parseInt(parts[0], 10);
    var month = months.indexOf(parts[1].split(' ')[0]) + 1;
    var year = parseInt(parts[1].split(' ')[1], 10);
    var color = eventDay.querySelector('.smallHr').style.backgroundColor;

	eventsArr.push({
	  day: day,
	  month: month,
	  year: year,
	  color: color,
	  events: [{
      title: "Event 1",
      time: "10:00 AM",
    },],
	});
  });
}

function convertTime(time) {
  //convert time to 24 hour format
  let timeArr = time.split(":");
  let timeHour = timeArr[0];
  let timeMin = timeArr[1];
  let timeFormat = timeHour >= 12 ? "PM" : "AM";
  timeHour = timeHour % 12 || 12;
  time = timeHour + ":" + timeMin + " " + timeFormat;
  return time;
}

function jump(day, month, year){
	var foundElement = null;
	var todayy = new Date(year, month, day);
	var eventDateElements = document.querySelectorAll('.eventDate');
	
	eventDateElements.forEach(function(element) {
	    var date = element.textContent.trim();
		var parts = date.split(' of ');
	
	    var day2 = parseInt(parts[0], 10);
	    var month2 = months.indexOf(parts[1].split(' ')[0]);
	    var year2 = parseInt(parts[1].split(' ')[1], 10);
	    var dateObject = new Date(year2, month2, day2);
	    
	    if (dateObject.getTime() == todayy.getTime()){ 
			foundElement = element;
		}
	});
	
	if (foundElement)
	    document.querySelector('.scrollContainer').scrollTop = foundElement.offsetTop - 121;
}
