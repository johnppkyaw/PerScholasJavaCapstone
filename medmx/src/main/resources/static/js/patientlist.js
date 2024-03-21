const changePatientSelectOption = (e) => {
    const currOption = e.target.value;
    // Fetch patient data from the backend
    let route = "";
    if(currOption === "all") {
        route = "/api/patients";
    } else {
        route = "api/physicians/"+physicianId+"/patients";
    }
    fetch(route)
        .then(response => response.json())
        .then(data => {
            // Populate table rows patient data
            loadPatient(data);
        })
        .catch(error => console.error('Error fetching patient data:', error));
}
const selectElement = document.getElementById("selectPatient");
selectElement.addEventListener("change", changePatientSelectOption);

//load physician's patient list by default
changePatientSelectOption({target: selectElement});


const loadPatient = (patientList) => {
    const patientTableBody = document.getElementById('patientTableBody');
    // Clear existing table rows
    patientTableBody.innerHTML = '';
    patientList.forEach(patient => {
        const row = document.createElement('tr');
        row.innerHTML = `
                        <td>${patient.id}</td>
                        <td>${patient.firstName}</td>
                        <td>${patient.lastName}</td>
                        <td>${patient.dateOfBirth}</td>
                        <td>${patient.gender}</td>
                        <td>${patient.phone}</td>
                        <td class="align-middle">
                            <button class="patient-action-buttons" onclick="window.location.href='${link}${patient.id}'">View</button>
                            <button class="patient-action-buttons" onClick="openEditModal(${patient.id})">Edit</button>
                            <button class="patient-action-buttons" id="patient-delete-button" onClick="deletePatient(${patient.id})">Delete</button></td>
                    `;
        patientTableBody.appendChild(row);
    });
}
//Search function
const searchField = document.getElementById("search-patient");
const searchFieldButton = document.getElementById("search-patient-button");
const searchPatient = () => {
    const searchString = parseInt(searchField.value);
    let fetchURL = "";
    //if it's number
    if(!isNaN(searchString)) {
        fetchURL = "/patients/"+searchString;
    } else {
        fetchURL = "/patients/byLastName?lastName="+searchString;
    }
}
searchFieldButton.addEventListener("click", searchPatient)



//search by id
//check if it's number
//if not a number, search as a string


// Get the edit modal
const modal = document.getElementById('editPatientModal');

// Get the close button element that closes the modal
const closeModal = document.getElementById('closeModal');

// When the user clicks on the edit button, open the modal
function openEditModal(patientId) {
    // Fetch patient details by ID and pre-fill the form fields
    fetch(`/api/patients/${patientId}`)
        .then(response => response.json())
        .then(patient => {
            //pre-populate the field with target patient
            document.getElementById('firstName').value = patient.firstName;
            document.getElementById('lastName').value = patient.lastName;
            document.getElementById('dateOfBirth').value = patient.dateOfBirth;
            document.getElementById('gender').value = patient.gender;
            document.getElementById('address').value = patient.address;
            document.getElementById('address2').value = patient.address2;
            document.getElementById('phone').value = patient.phone;

            // Show the modal
            modal.style.display = 'block';

            const updatePatientButton = document.getElementById('updatePatient');
            updatePatientButton.addEventListener('click',() => updatePatient(patientId));
        })
        .catch(error => console.error('Error fetching patient details:', error));
}
function updatePatient(patientId) {
    console.log(document.getElementById('phone').value);
    const updatedPatient = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        dateOfBirth: document.getElementById('dateOfBirth').value,
        gender: document.getElementById('gender').value,
        address: document.getElementById('address').value,
        address2: document.getElementById('address2').value,
        phone: document.getElementById('phone').value
    };

    const requestOptions = {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedPatient)
    };
    fetch('/api/patients/'+patientId, requestOptions)
        .then(response => {
            if(response.ok) {
                window.location.reload();
            } else {
                throw new Error('Failed to delete patient');
            }
        }).catch(error => console.error('Error deleting patient: ', error));
}

// When the user clicks on close button, close the modal
closeModal.onclick = function() {
    modal.style.display = 'none';
};

// When the user clicks anywhere outside the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
};

// Delete the patient
function deletePatient(patientId) {
    if(confirm("Are you sure you want to remove this patient from the system?")) {
        fetch('/api/patients/'+ patientId, {
            method: 'DELETE',
        }).then(response => {
            if(response.ok) {
                window.location.reload();
            } else {
                throw new Error('Failed to delete patient');
            }
        }).catch(error => console.error('Error deleting patient: ', error));
    }
}