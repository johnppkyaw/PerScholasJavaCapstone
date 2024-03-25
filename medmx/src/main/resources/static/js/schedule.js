//Convert to Date portion with timezone adjusted
Date.prototype.toDateInputValue = (function () {
    let local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0, 10);
});

//Set today's date in the date input
const dateInput = document.getElementById('select-date');
const scheduleDateDefault = document.getElementById('schedule-date-1');
console.log(scheduleDateDefault.value);
dateInput.value = new Date().toDateInputValue();
scheduleDateDefault.setAttribute('value', dateInput.value);
dateInput.addEventListener('change', reloadSchedule);

reloadSchedule();

//Function to create a schedule HTML element
function createScheduleElement(schedule) {
    const eachScheduleDiv = document.createElement('div');
    const timeDiv = document.createElement('div');
    const idDiv = document.createElement('div');
    const nameDiv = document.createElement('div');
    const phoneDiv = document.createElement('div');
    const statusDiv = document.createElement('div');
    const noteDiv = document.createElement('div');

    eachScheduleDiv.setAttribute("class", "each-schedule");

    nameDiv.setAttribute("class", "patient-"+schedule.patient.id);
    nameDiv.style.color = "blue";
    nameDiv.addEventListener("mouseover", function(event) {
        event.target.style.cursor = "pointer";
    })
    nameDiv.addEventListener("click", loadPatientPage);

    //edit button
    const editButton = document.createElement('button');
    editButton.setAttribute("class", "edit-schedule-btn");
    editButton.setAttribute("onClick", "editSchedule(event" + "," + schedule.id + ")");

    //delete button
    const deleteButton = document.createElement('button');
    deleteButton.setAttribute("class", "delete-schedule-btn");
    deleteButton.setAttribute("onClick", "deleteSchedule(event" + "," + schedule.id + ")");

    timeDiv.innerText = schedule.startTime;
    idDiv.innerText = schedule.patient.id;
    nameDiv.innerText = schedule.patient.lastName + ", " + schedule.patient.firstName;
    phoneDiv.innerText = schedule.patient.phone;
    statusDiv.innerText = schedule.status;
    noteDiv.innerText = schedule.notes;
    editButton.innerText = "Edit";
    editButton.value = schedule.id;
    deleteButton.innerText = "Delete";

    eachScheduleDiv.appendChild(timeDiv);
    eachScheduleDiv.appendChild(statusDiv);
    eachScheduleDiv.appendChild(idDiv);
    eachScheduleDiv.appendChild(nameDiv);
    eachScheduleDiv.appendChild(phoneDiv);
    eachScheduleDiv.appendChild(noteDiv);
    eachScheduleDiv.appendChild(editButton);
    eachScheduleDiv.appendChild(deleteButton);

    return eachScheduleDiv;
}

function reloadSchedule() {
    //Get all the current logged in physician's schedule with today's date
    const physScheduleURL = 'api/physicians/' + physician.id + '/schedules?date=' + dateInput.value;
    fetch(physScheduleURL)
        .then(response => response.json())
        .then(data => {
            //Grab schedule container element
            const scheduleContainer = document.getElementById('schedule-container');
            //Clear all the schedule
            scheduleContainer.innerHTML = "";
            //reload the schedule
            data.forEach(schedule => {
                const eachScheduleDiv = createScheduleElement(schedule);
                scheduleContainer.appendChild(eachScheduleDiv);
            })
        }).catch(error => console.error('Error fetching schedule data:', error));
}
function loadPatientPage(e) {
    const patientId = parseInt(e.target.className.split("-")[1]);
    console.log(window.location.href);
    const currentURL = window.location.href.split("/");
    const newURL = [currentURL[0], currentURL[1], currentURL[2], "patientDetail/" + patientId].join("/");
    window.location.replace(newURL);
    console.log(newURL);
}


// Function to show or hide a modal
function toggleModal(modalDiv, show) {
    modalDiv.style.display = show ? 'block' : 'none';
}

//get create-schedule modal div
const createScheduleModalDiv = document.getElementById("add-schedule-modal");
//get edit-schedule modal div
const editScheduleModalDiv = document.getElementById("edit-schedule-modal");

//when +Create Schedule div is clicked, show create schedule modal
const createScheduleDiv = document.getElementById('create-schedule');
createScheduleDiv.addEventListener('click', showAddScheduleModal);

//function that shows create schedule modal
function showAddScheduleModal(e) {
    toggleModal(createScheduleModalDiv, true);
    scheduleDateDefault.value = dateInput.value;
}

//close button
const closeModalBtn = document.getElementsByClassName("close-schedule-btn");
for (const button of closeModalBtn) {
    button.addEventListener('click', closeAddScheduleModal);
}

function closeAddScheduleModal(e) {
    createScheduleModalDiv.style.display = 'none';
    editScheduleModalDiv.style.display = 'none';
}

//create schedule button
const addScheduleButton = document.getElementById("add-schedule-btn");
addScheduleButton.addEventListener('click', addSchedule);

function addSchedule(e) {
    e.preventDefault();
    const dateField = document.getElementById("schedule-date-1").value;
    const timeField = document.getElementById('schedule-time-1').value;
    const patientId = document.getElementById('patient-list').value;
    const patient = patients.find(patient => patient.id === parseInt(patientId));
    const route = '/api/schedules';

    const newSchedule = {
        date: dateField,
        startTime: timeField,
        patient: patient,
        physician: physician,
        status: "Scheduled",
        notes: "Add Notes"
    };

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(newSchedule)
    };

    fetch(route, requestOptions)
        .then(response => {
            if (response.ok) {
                createScheduleModalDiv.style.display = 'none';
                window.location.reload();
            } else {
                alert("Do not double book!");
                throw new Error('Failed to add the Schedule');
            }
        }).catch(error => console.error('Error Scheduling', error));

}

//Edit schedule function
function editSchedule(event, id) {
    editScheduleModalDiv.style.display = "block";
    //get parent node
    const parent = event.target.parentNode;
    const childNodes = parent.childNodes;
    const patientId = childNodes[2].textContent;
    const dateField = document.getElementById("edit-schedule-date");
    const timeField = document.getElementById("edit-schedule-time");
    const statusField = document.getElementById("edit-schedule-status");
    const noteField = document.getElementById("edit-schedule-note");
    const patientField = document.getElementById('edit-patient-list');
    const patientOption = document.querySelector(`#edit-patient-list option[value="${patientId}"]`)
    const updateBtn = document.getElementById("edit-schedule-btn");

    dateField.value = dateInput.value;
    timeField.value = childNodes[0].textContent;
    statusField.value = childNodes[1].textContent;
    noteField.value = childNodes[5].textContent;
    if (patientOption) {
        patientOption.selected = true;
    }

    let patient = {};

    // Event listener to update selected patient
    patientField.addEventListener("change", () => {
        const selectedPatientId = patientField.value;
        const selectedPatient = patients.find(patient => patient.id === parseInt(selectedPatientId));
        // You might want to do additional validation here
        if (selectedPatient) {
            // Update the patient object in the updatedSchedule
            patient = selectedPatient;
        }
    });

    updateBtn.addEventListener("click", () => {
        editScheduleModalDiv.style.display = "block";
        const route = '/api/schedules/'+id;

        const updatedSchedule = {
            date: dateField.value,
            startTime: timeField.value,
            patient: patient,
            physician: physician,
            status: statusField.value,
            notes: noteField.value
        };

        const requestOptions = {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(updatedSchedule)
        };

        fetch(route, requestOptions)
            .then(response => {
                if (response.ok) {
                    createScheduleModalDiv.style.display = 'none';
                    window.location.reload();
                } else {
                    alert("Do not double book!");
                    throw new Error('Failed to edit the Schedule');
                }
            }).catch(error => console.error('Error Scheduling', error));
    });
}

function deleteSchedule(event, id) {
    const deleteRoute = "/api/schedules/" + id;
    if(confirm("Are you sure you want to remove this schedule from the system?")) {
        fetch(deleteRoute, {
            method: 'DELETE',
        }).then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                throw new Error('Failed to delete schedule');
            }
        }).catch(error => console.error('Error deleting schedule: ', error));
    }
}