const API_URL = 'http://t.hoangdeptrai.online/api';
const API_ADMIN_URL = 'http://t.hoangdeptrai.online/api/admin';
const LIMIT = 10;

function postDataToApi(apiUrl, data, onSuccess, onError) {

    //if not form data then stringify
    let header = createTokenHedaer();
    if (!(data instanceof FormData)) {
        header['Content-Type'] = 'application/json';
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
                showToast(responseData.message, 'error');
                if (onError && typeof onError === 'function') {
                    onError(responseData);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                showToast(responseData.message, 'success');
                if (onSuccess && typeof onSuccess === 'function') {
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            showToast(
                'An error occurred while posting data.',
                'error'
            );
        });
}

function getDataFromApi(apiUrl, onSuccess, onError) {
    //if not form data then stringify
    let header = createTokenHedaer();
    header['Content-Type'] = 'application/json';

    fetch(apiUrl, {
        method: 'GET',
        headers: header,
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
            showToast(
                'An error occurred while posting data.',
                'error'
            );
        });
}

function deleteDataFromApi(apiUrl, onSuccess, onError) {

    header = createTokenHedaer();
    header['Content-Type'] = 'application/json';
    fetch(apiUrl, {
        method: 'DELETE',
        headers: header,
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }

            //status 400
            if (response.status === 400) {
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
                showToast(responseData.message, 'error');
                if (onError && typeof onError === 'function') {
                    onError(responseData);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                showToast(responseData.message, 'success');
                if (onSuccess && typeof onSuccess === 'function') {
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            showToast(
                'An error occurred while posting data.',
                'error'
            );
        });
}

function putDataToApi(apiUrl, data, onSuccess, onError) {
    //if not form data then stringify
    let header = createTokenHedaer()
    if (!(data instanceof FormData)) {
        header['Content-Type'] = 'application/json';
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
                showToast(responseData.message, 'error');
                if (onError && typeof onError === 'function') {
                    onError(responseData);
                }
            } else {
                // If there is no error, call the onSuccess callback with the response data
                showToast(responseData.message, 'success');
                if (onSuccess && typeof onSuccess === 'function') {
                    onSuccess(responseData);
                }
            }
        })
        .catch((error) => {
            showToast(
                'An error occurred while posting data.',
                'error'
            );
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

function createTokenHedaer() {
    const admin = JSON.parse(localStorage.getItem('admin'));
    const user = JSON.parse(localStorage.getItem('user'));
    adminToken = admin ? admin.token : '';
    userToken = user ? user.token : '';

    //if not form data then stringify
    let header = {
        'Admin-Token': adminToken,
        'User-Token': userToken,
    };

    return header;
}