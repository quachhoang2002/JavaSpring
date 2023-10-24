function renderOptionProduct(select = null) {
    let successCallback = (response) => {
        if (select) {
            select.innerHTML = '';
            response.data.forEach(item => {
                select.innerHTML += `<option value="${item.id}">${item.name}</option>`
            });
        }
    }

    let errorCallback = (error) => {
    }

    getDataFromApi(PRODUCT_URL, successCallback, errorCallback);
}

function renderOptionCategory(select = null) {
    let successCallback = (response) => {
        if (select) {
            select.innerHTML = '';
            response.data.forEach(item => {
                select.innerHTML += `<option value="${item.id}">${item.name}</option>`
            });
        }
    }

    let errorCallback = (error) => {
    }

    getDataFromApi(CATEGORY_URL, successCallback, errorCallback);
}

function renderOptionManufacture(select = null) {
    let successCallback = (response) => {
        if (select) {
            select.innerHTML = '';
            response.data.forEach(item => {
                select.innerHTML += `<option value="${item.id}">${item.name}</option>`
            });
        }
    }

    let errorCallback = (error) => {
    }

    getDataFromApi(MANUFACTURE_URL, successCallback, errorCallback);
}

function showToast(message,type) {
    let toast = document.createElement("div");
//    document.body.appendChild(toast);
    const Test = document.querySelector(".container");
    Test.appendChild(toast);

    toast.className = "toast";
    //z index: 9999
    toast.style.zIndex = "999999";

    if (type == "success") {
        toast.style.borderLeft = "5px solid #28a745";
        //innter color and bold and icon success
        toast.innerHTML = `<i class="fas fa-check-circle"></i> <b>Success</b> ${message}`;

    }

    if (type == "error") {
        toast.style.borderLeft = "5px solid #dc3545";
        //innter color and bold and icon success
        toast.innerHTML = `<i class="fas fa-times-circle" ></i> <b>Error</b> ${message}`;
    }

    // Fade in the toast
    toast.style.display = "block";
    toast.style.opacity = 1;
    toast.style.color = "black";

    // Hide the toast after a few seconds (e.g., 3 seconds)
    setTimeout(function () {
        toast.style.opacity = 0;
        setTimeout(function () {
//            toast.style.display = "none";
            document.body.classList.remove("body-animation");
        }, 500);
    }, 3000);

}

function createToast(status, icon, title, message) {
    let notifications = document.getElementById("notifications");

    let newToast = document.createElement('div');

    newToast.style.zIndex = "9999";
    newToast.innerHTML = `
    <div class="toastPopup ${status}" style="background-color: blue; z-index: 9999999 ">
        <i class="bi ${icon}"></i>
        <div class="content">
            <div class="title">${title}</div>
            <span>${message}</span>
        </div>
        <i class="bi bi-x" onclick="this.parentElement.remove()"></i>
    </div>
    `;
    notifications.appendChild(newToast);
    setTimeout(() => newToast.remove(), 5000);
}

