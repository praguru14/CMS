document.addEventListener("DOMContentLoaded", function () {
    // Custom JavaScript code

    // Display a welcome message
    displayWelcomeMessage();

    // Handle form submission
    handleFormSubmission();

    // Add your custom code here
    customCodeExample();
});

function displayWelcomeMessage() {
    console.log("Welcome to Prafull's Eatery! Enjoy your visit.");
}

function handleFormSubmission() {
    const form = document.querySelector("form");
    if (form) {
        form.addEventListener("submit", function (event) {
            event.preventDefault();

            // Get form data
            const formData = new FormData(form);

            // Use AJAX to send form data to the server
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/subscribe", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        // Handle the server response
                        const responseData = JSON.parse(xhr.responseText);
                        console.log("Server response:", responseData);

                        // You can update the DOM or perform additional actions based on the response
                    } else {
                        console.error("Error:", xhr.statusText);
                        // Handle error if needed
                    }
                }
            };

            // Convert form data to URL-encoded format
            const urlEncodedData = new URLSearchParams(formData).toString();

            // Send the request with the encoded form data
            xhr.send(urlEncodedData);
        });
    }
}

function customCodeExample() {
    // Your custom code here
    console.log("Custom code executed!");

    // Example: Change the background color of the body
    document.body.style.backgroundColor = "#f0f0f0";
}
