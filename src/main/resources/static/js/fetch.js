const API_URL = "http://localhost:8081/api";
const API_ADMIN_URL = "http://localhost:8081/api/admin";
const LIMIT = 10;

function postDataToApi(apiUrl, data, onSuccess, onError) {
    fetch(apiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            console.log(response)
            if (response.ok || response.status === 400) {
                return response.json();
            }

            if (response.status === 500) {
                throw new Error("Server error");
            }

            throw new Error("Network error");
        })
        .then((responseData) => {
            if (responseData.status === "error" && responseData.message) {
                // If the response contains an error status and message, call the onError callback
                if (onError && typeof onError === "function") {
                    onError(responseData.message);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                if (onSuccess && typeof onSuccess === "function") {
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            if (onError && typeof onError === "function") {
                onError(error.message); // Call the error callback function with the error message
            } else {
                console.error("Error:", error);
                // Display a default error message on the page
                showError("An error occurred while posting data.");
            }
        });
}

function getDataFromApi(apiUrl, onSuccess, onError) {
    fetch(apiUrl, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }

            if (response.status === 500) {
                throw new Error("Server error");
            }

            throw new Error("Network error");
        })
        .then((responseData) => {
            if (responseData.status === "error" && responseData.message) {
                // If the response contains an error status and message, call the onError callback
                if (onError && typeof onError === "function") {
                    return onError(responseData.message);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                if (onSuccess && typeof onSuccess === "function") {
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            if (onError && typeof onError === "function") {
                onError(error.message); // Call the error callback function with the error message
            } else {
                console.error("Error:", error);
                // Display a default error message on the page
                showError("An error occurred while fetching data.");
            }
        });
}

function deleteDataFromApi(apiUrl, onSuccess, onError) {
    fetch(apiUrl, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }

            if (response.status === 500) {
                throw new Error("Server error");
            }

            throw new Error("Network error");
        })
        .then((responseData) => {
            if (responseData.status === "error" && responseData.message) {
                // If the response contains an error status and message, call the onError callback
                if (onError && typeof onError === "function") {
                    onError(responseData.message);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                if (onSuccess && typeof onSuccess === "function") {
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            if (onError && typeof onError === "function") {
                onError(error.message); // Call the error callback function with the error message
            } else {
                console.error("Error:", error);
                // Display a default error message on the page
                showError("An error occurred while deleting data.");
            }
        });
}

function putDataToApi(apiUrl, data, onSuccess, onError) {
    fetch(apiUrl, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            console.log(response)
            if (response.ok || response.status === 400) {
                return response.json();
            }

            if (response.status === 404) {
                throw new Error("Not found");
            }

            if (response.status === 405) {
                throw new Error("Method not allowed");
            }

            if (response.status === 500) {
                throw new Error("Server error");
            }

            throw new Error("Network error");
        })
        .then((responseData) => {
            if (responseData.status === "error" && responseData.message) {
                // If the response contains an error status and message, call the onError callback
                if (onError && typeof onError === "function") {
                    onError(responseData.message);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                if (onSuccess && typeof onSuccess === "function") {
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            if (onError && typeof onError === "function") {
                onError(error.message); // Call the error callback function with the error message
            } else {
                console.error("Error:", error);
                // Display a default error message on the page
                showError("An error occurred while putting data.");
            }
        });
}

function showError(errorMessage) {
    // Display the error message on the page (e.g., in a div with the id "error-message")
    const errorMessageElement = document.getElementById("error-message");
    if (errorMessageElement) {
        errorMessageElement.textContent = errorMessage;
        errorMessageElement.style.display = "block";
    }
}


