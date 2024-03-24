//Display session based on the tab
const infoTab = document.getElementById("information-tab");
const notesTab = document.getElementById("clinic-notes-tab");
const rxTab = document.getElementById("prescriptions-tab");
const historySec = document.getElementById("history-section");
const prescriptionSec = document.getElementById("prescription-section");
const noteSec = document.getElementById("clinic-notes-nav-section");

//On loading the page, hide notes and prescription, tab
toggleDisplay(prescriptionSec, false);
toggleDisplay(noteSec, false);
setTabActive(infoTab);

//When a tab is clicked, only display that section and hide other tabs.
infoTab.addEventListener("click", () => {
    toggleDisplay(prescriptionSec, false);
    toggleDisplay(noteSec, false);
    toggleDisplay(historySec, true);
    setTabActive(infoTab);
})
notesTab.addEventListener("click", () => {
    toggleDisplay(prescriptionSec, false);
    toggleDisplay(noteSec, true);
    toggleDisplay(historySec, false);
    setTabActive(notesTab);
})
rxTab.addEventListener("click", () => {
    toggleDisplay(prescriptionSec, true);
    toggleDisplay(noteSec, false);
    toggleDisplay(historySec, false);
    setTabActive(rxTab);
})
function setTabActive(tab) {
    const tabs = document.getElementsByClassName("patient-tabs");
    for (const tab of tabs) {
        tab.classList.remove("active");
    }
    tab.classList.add("active");
}

//Get today's date with timezone adjusted
Date.prototype.toDateInputValue = (function() {
    const local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});

//the table where the notes will be added as a row
const noteTableBody = document.getElementById('note-table-body');

const createNoteButton = document.getElementById('create-note-button');
const createNoteModal = document.getElementById("create-note-modal");

//The close buttons of all modals
const closeModalButtons = document.getElementsByClassName("close-modal-btn");

//The fields in the create-note modal
const createNoteDate = document.getElementById('today-date');
const createNoteContent = document.getElementById('note-content');
createNoteDate.value = new Date().toDateInputValue();

const visitType = document.getElementById("visit-type");

const createNoteSubmitButton = document.getElementById('create-note-submit-button');

//Load note modal
const loadNoteModal = document.getElementById("load-note-modal");
const loadNoteParent = document.getElementById("load-note-parent");

//Edit note modal
const editNoteModal = document.getElementById("edit-note-modal");
const editNoteDate = document.getElementById("today-date-edit");
const editVisitType = document.getElementById("visit-type-edit");
const editNoteContent = document.getElementById("note-content-edit");
const editNoteButton = document.getElementById("edit-note-submit-button");

const patientName = patient.firstName + " " + patient.lastName;
const patientGender = patient.gender.toLowerCase();

//Fetch all notes for this patient and load them into the table.
loadNoteRows();
function loadNoteRows() {
    notes.forEach(note => {
        const noteRow = createNoteRow(note);
        noteTableBody.appendChild(noteRow);
    })
}

//When create note button is clicked, it will display create note modal
//With templated information
createNoteButton.addEventListener("click", () => {
    toggleDisplay(createNoteModal, true);

    const age = getAge(patient.dateOfBirth);
    console.log(age);

    const chiefComplaint = "Chief Complaint: \n";
    const chiefComplaintLine = `${patientName} is a(n) ${age} old ${patientGender} presenting today for: \n\n`;
    const hpi = "HPI: \n";
    const hpiLine = "The patient states that \n\n";

    const pe = "Physical Exam: \n";
    const peLine = "General: \n HEENT: \n Pulm: \n CV: \n Abdomen: \n Extremities: \n Mus: \n Neuro: \n Psych: \n\n";

    const ap = "Assessment and Plan: \n";
    const apLine = "Diagnosis:  \n Plan: \n ";

    createNoteContent.innerText = chiefComplaint + chiefComplaintLine + hpi + hpiLine + pe + peLine + ap + apLine;

    visitType.value = "Acute complaint";
});

//This will close the currently opened modal when the close button is clicked
for(const button of closeModalButtons) {
    button.addEventListener("click", () => {
        toggleDisplay(createNoteModal, false);
        toggleDisplay(loadNoteModal, false);
        toggleDisplay(editNoteModal, false);
    });
}

//Save the note to database after create note button inside the create note model is clicked.
createNoteSubmitButton.addEventListener("click", () => {
    const newNote = {};
    newNote.date = createNoteDate.value;
    newNote.patient = patient;
    newNote.physician = physician;
    newNote.visitType = visitType.value;
    newNote.noteContent = createNoteContent.innerText;
    const fetchOptions = {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newNote)
    };
    const fetchURL = '/api/notes';

    fetch(fetchURL, fetchOptions)
        .then(response => {
            if(response.ok) {
                window.location.reload();
            } else {
                throw new Error('Failed to create the note');
            }
        }).catch(error => console.error('Error create the note: ', error));
});

//Function that creates a row to add to note-table-body
function createNoteRow(eachNote) {
    const tr = document.createElement("tr");
    const td1 = document.createElement("td");
    const td2 = document.createElement("td");
    const td3 = document.createElement("td");
    const editDiv = document.createElement("div");
    const deleteDiv = document.createElement("div");

    td2.setAttribute("id", "notes-row-" + eachNote.id);
    td2.setAttribute("class", "notes-row");
    td3.setAttribute("class", "notes-action-row");
    td1.textContent = eachNote.date;
    td2.textContent = eachNote.visitType;
    editDiv.setAttribute("id", "notes-row-edit-" + eachNote.id);
    editDiv.setAttribute("class", "notes-edit-btn");
    deleteDiv.setAttribute("id", "notes-row-delete-" + eachNote.id);
    deleteDiv.setAttribute("class", "notes-delete-btn");
    editDiv.innerText = "Edit";
    deleteDiv.innerText = "Delete";
    td3.appendChild(editDiv);
    td3.appendChild(deleteDiv);

    td2.addEventListener("click", loadNote);
    editDiv.addEventListener("click", editNote);
    deleteDiv.addEventListener("click", deleteNote);

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    return tr;
}

function loadNote(e) {
    const id = parseInt(e.target.id.split("-")[2]);
    const note = notes.filter(note => note.id === id)[0];
    loadNoteParent.innerText = note.noteContent;
    toggleDisplay(loadNoteModal, true);
}

//A function that will bring in edit note modal
//with prefilled details of the note.
function editNote(e) {
    const noteId = parseInt(e.target.id.split("-")[3]);
    const note = notes.filter(note => note.id === noteId)[0];
    const patientId = patient.id;
    editNoteDate.value = note.date;
    editVisitType.value = note.visitType;
    editNoteContent.innerText = note.noteContent;

    editNoteModal.style.display = "block";
    //When the edit button is clicked in the edit note modal
    //it will send a PUT request to the database with the edited note object.
    editNoteButton.addEventListener("click", function() {
        updateNote(noteId, patientId);
    });
}

function updateNote(noteId, patientId) {
    const updatedNote = {
        date: editNoteDate.value,
        visitType: editVisitType.value,
        noteContent: editNoteContent.innerText,
        patient: patient,
        physician: physician
    };
    const URL = `/api/notes/${noteId}`;
    const option = {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedNote)
    }
    fetch(URL, option)
        .then(response => {
            if(response.ok) {
                window.location.reload();
            } else {
                throw new Error('Failed to edit the note');
            }
        }).catch(error => console.error('Error editing the note: ', error));
}

//Function that deletes the note from the database
function deleteNote(e) {
    const id = parseInt(e.target.id.split("-")[3]);
    if(confirm("Are you sure you want to delete this note?  It cannot be recovered")) {
        fetch(`/api/notes/${id}`, {
            method: 'DELETE',
        }).then(response => {
            if(response.ok) {
                window.location.reload();
            } else {
                throw new Error('Failed to delete the note');
            }
        }).catch(error => console.error('Error deleting the note: ', error));
    }
}

//Function to display a modal or not.
function toggleDisplay(modalDiv, show) {
    if(show === true) {
        modalDiv.style.display = "block";
    } else {
        modalDiv.style.display = "none";
    }
}

//Calculate age based on date of birth
function getAge(dateString) {
    const today = new Date();
    const birthDate = new Date(dateString);

    let age = today.getFullYear() - birthDate.getFullYear();
    let m = today.getMonth() - birthDate.getMonth();
    let d = today.getDate() - birthDate.getDate();

    // If birth month is after current month or birth month is the same as current month
    // but birth day is after current day, decrement age
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    // Adjust months if current day is before birth day in the same month
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        m += 12;
    }

    // Adjust days if current day is before birth day in the same month
    if (d < 0) {
        const prevMonthLastDay = new Date(today.getFullYear(), today.getMonth(), 0).getDate();
        d += prevMonthLastDay;
        m--;
    }

    return age + " years " + m + " months " + d + " days";
}