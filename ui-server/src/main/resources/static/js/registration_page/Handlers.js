const checkbox = document.getElementById('permission')
const submitButton = document.getElementById("submitButton")
checkbox.addEventListener('change', (event) => {
    submitButton.disabled = !event.target.checked;
})