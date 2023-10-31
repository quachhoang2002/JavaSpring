const API_URL = 'http://localhost:8081/api';
const API_ADMIN_URL = 'http://localhost:8081/api/admin';
const LIMIT = 10;

function postDataToApi(apiUrl, data, onSuccess, onError) {
    console.log(apiUrl)
    //if not form data then stringify
    let header = {};
    if (!(data instanceof FormData)) {
        header = {
            'Content-Type': 'application/json',
        };
        data = JSON.stringify(data);
    }

    fetch(apiUrl, {
        method: 'POST',
        headers: header,
        body: data,
    })
        .then((response) => {
            console.log(response);
            if (response.ok || response.status === 400) {
                return response.json();
            }

            if (response.status === 500) {
                throw new Error('Server error');
            }

            throw new Error('Network error');
        })
        .then((responseData) => {
            if (
                responseData.status === 'error' &&
                responseData.message
            ) {
                // If the response contains an error status and message, call the onError callback
                if (onError && typeof onError === 'function') {
                    showToast(responseData.message, 'error');
                    onError(responseData);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                if (onSuccess && typeof onSuccess === 'function') {
                    showToast(responseData.message, 'success');
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            if (onError && typeof onError === 'function') {
                onError(error.message); // Call the error callback function with the error message
            } else {
                console.error('Error:', error);
                // Display a default error message on the page
                showToast(
                    'An error occurred while getting data.',
                    'error'
                );
            }
        });
}

function getDataFromApi(apiUrl, onSuccess, onError) {
    fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }

            if (response.status === 500) {
                throw new Error('Server error');
            }

            throw new Error('Network error');
        })
        .then((responseData) => {
            if (
                responseData.status === 'error' &&
                responseData.message
            ) {
                // If the response contains an error status and message, call the onError callback
                if (onError && typeof onError === 'function') {
                    return onError(responseData);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                if (onSuccess && typeof onSuccess === 'function') {
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            if (onError && typeof onError === 'function') {
                onError(error.message); // Call the error callback function with the error message
            } else {
                console.error('Error:', error);
                // Display a default error message on the page
                showToast(
                    'An error occurred while getting data.',
                    'error'
                );
            }
        });
}

function deleteDataFromApi(apiUrl, onSuccess, onError) {
    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }

            if (response.status === 500) {
                throw new Error('Server error');
            }

            throw new Error('Network error');
        })
        .then((responseData) => {
            if (
                responseData.status === 'error' &&
                responseData.message
            ) {
                // If the response contains an error status and message, call the onError callback
                if (onError && typeof onError === 'function') {
                    showToast(responseData.message, 'error');
                    onError(responseData);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                if (onSuccess && typeof onSuccess === 'function') {
                    showToast(responseData.message, 'success');
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            if (onError && typeof onError === 'function') {
                onError(error.message); // Call the error callback function with the error message
            } else {
                console.error('Error:', error);
                // Display a default error message on the page
                showToast(
                    'An error occurred while deleting data.',
                    'error'
                );
            }
        });
}

function putDataToApi(apiUrl, data, onSuccess, onError) {
    //if not form data then stringify
    let header = {};
    if (!(data instanceof FormData)) {
        header = {
            'Content-Type': 'application/json',
        };
        data = JSON.stringify(data);
    }

    fetch(apiUrl, {
        method: 'PUT',
        headers: header,
        body: data,
    })
        .then((response) => {
            console.log(response);
            if (response.ok || response.status === 400) {
                return response.json();
            }

            if (response.status === 404) {
                throw new Error('Not found');
            }

            if (response.status === 405) {
                throw new Error('Method not allowed');
            }

            if (response.status === 500) {
                throw new Error('Server error');
            }

            throw new Error('Network error');
        })
        .then((responseData) => {
            if (
                responseData.status === 'error' &&
                responseData.message
            ) {
                // If the response contains an error status and message, call the onError callback
                if (onError && typeof onError === 'function') {
                    showToast(responseData.message, 'error');
                    onError(responseData);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                if (onSuccess && typeof onSuccess === 'function') {
                    showToast(responseData.message, 'success');
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            if (onError && typeof onError === 'function') {
                onError(error.message); // Call the error callback function with the error message
            } else {
                console.error('Error:', error);
                // Display a default error message on the page
                showToast(
                    'An error occurred while posting data.',
                    'error'
                );
            }
        });
}

function buildImageBlobFromURL(imageURL) {
    return fetch(imageURL)
        .then((response) => {
            console.log(response);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.blob();
        })
        .then((imageBlob) => {
            return imageBlob;
        })
        .catch((error) => {
            console.error('Error fetching image:', error);
        });
}