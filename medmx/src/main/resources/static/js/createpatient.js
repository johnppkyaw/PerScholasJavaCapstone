function submitPatient() {

    // Get form data
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const dateOfBirth = document.getElementById('dateOfBirth').value;
    const gender = document.getElementById('gender').value;
    const address = document.getElementById('address').value;
    const address2 = document.getElementById('address2').value;
    const phone = document.getElementById('phone').value;

    // Create patient object
    const patient = {
        firstName: firstName,
        lastName: lastName,
        dateOfBirth: dateOfBirth,
        gender: gender,
        address: address,
        address2: address2,
        physician: physician,
        phone: phone
    };

    // Send patient data to server
    fetch('/api/patients', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(patient)
    })
        .then(response => {
            if (response.ok) {
                alert('Patient added successfully!');
            } else {
                alert('Failed to add patient');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while adding patient');
        });
}