function renderOptionProduct(select = null) {
    let successCallback = (response) => {
        if (select) {
            select.innerHTML = '';
            select.innerHTML= `<option value="" selected>All</option>`
            response.data.forEach(item => {
                select.innerHTML += `<option value="${item.id}">${item.name}</option>`
            });
        }
    };

    let errorCallback = (error) => {};

    getDataFromApi(PRODUCT_URL, successCallback, errorCallback);
}

function renderOptionCategory(select = null) {
    let successCallback = (response) => {
        if (select) {
            select.innerHTML = '';
            select.innerHTML= `<option value="" selected>All</option>`
            response.data.forEach(item => {
                select.innerHTML += `<option value="${item.id}">${item.name}</option>`
            });
        }
    };

    let errorCallback = (error) => {};

    getDataFromApi(CATEGORY_URL, successCallback, errorCallback);
}

function renderOptionManufacture(select = null) {
    let successCallback = (response) => {
        if (select) {
            select.innerHTML = '';
            select.innerHTML= `<option value="" selected>All</option>`
            response.data.forEach(item => {
                select.innerHTML += `<option value="${item.id}">${item.name}</option>`
            });
        }
    };

    let errorCallback = (error) => {};

    getDataFromApi(MANUFACTURE_URL, successCallback, errorCallback);
}

function showToast(message, type) {
    let toast = document.createElement('div');
    document.body.appendChild(toast);

    toast.className = 'toast';
    //z index: 9999
    toast.style.zIndex = '999999';

    if (type == 'success') {
        toast.style.borderLeft = '5px solid #28a745';
        //innter color and bold and icon success
        toast.innerHTML = `<i class="fas fa-check-circle"></i> <b>Success</b> ${message}`;
    }

    if (type == 'error') {
        toast.style.borderLeft = '5px solid #dc3545';
        //innter color and bold and icon success
        toast.innerHTML = `<i class="fas fa-times-circle" ></i> <b>Error</b> ${message}`;
    }

    // Fade in the toast
    toast.style.display = 'block';
    toast.style.opacity = 1;
    toast.style.color = 'black';

    // Hide the toast after a few seconds (e.g., 3 seconds)
    setTimeout(function () {
        toast.style.opacity = 0;
        setTimeout(function () {
            document.body.classList.remove('body-animation');
            document.body.removeChild(toast);
        }, 500);
    }, 3000);
}

function createToast(status, icon, title, message) {
    let notifications = document.getElementById('notifications');

    let newToast = document.createElement('div');

    newToast.style.zIndex = '9999';
    newToast.innerHTML = `
    <div class="toastPopup ${status}" style=" z-index: 9999999 ">
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

function setFilterBox(select, type) {
    switch (type) {
        case "product":
            renderOptionProduct(select);
            break;
        case "category":
            renderOptionCategory(select);
            break;
        case "manufacture":
            renderOptionManufacture(select);
            break;
        default:
            break;
    }
}

function renderFilterBox(filterAttributes) {
    // Create the main container div
    filter = document.querySelector('#filter');
    const filterContainer = document.createElement('div');
    filterContainer.className = 'filter-container';
    filterContainer.style.width = '100%';
    filterContainer.style.marginBottom = '10px';

    // Create and append labels and select elements
    filterAttributes.forEach((labelText) => {
        if (labelText == 'price') {
            // Create the Price range input and output elements
            const priceLabel = document.createElement('label');
            priceLabel.textContent = 'Price:';
            const priceInput = document.createElement('input');
            priceInput.type = 'range';
            priceInput.min = '1';
            priceInput.max = '10000000';
            priceInput.value = '0';
            priceInput.className = 'slider';
            priceInput.id = 'price';
            priceInput.oninput = function () {
                priceOutput.textContent = this.value; // Update the output value as the input changes
            };

            const priceOutput = document.createElement('output');
            priceOutput.textContent = priceInput.value;

            filterContainer.appendChild(priceLabel);
            filterContainer.appendChild(priceInput);
            filterContainer.appendChild(priceOutput);
            return;
        }

        if (labelText == 'name') {
            let label = document.createElement('label');
            label.textContent = labelText + ':';
            let input = document.createElement('input');
            input.type = 'text';
            input.id = 'name';
            input.placeholder = 'Search by name';
            input.className = 'form-control';
            input.style.width = '100%';
            input.style.marginBottom = '10px';
            filterContainer.appendChild(label);
            filterContainer.appendChild(input);
            return;
        }

        const label = document.createElement('label');
        label.textContent = labelText + ':';
        label.style.marginRight = '10px';
        label.style.marginBottom = '10px';
        select = document.createElement('select');
        select.id = labelText;
        setFilterBox(select, labelText);

        filterContainer.appendChild(label);
        filterContainer.appendChild(select);
        //margin left and uc first

        select.addEventListener('change', function () {
            const selectedValue = select.value;
            console.log(`Selected value for ${labelText}: ${selectedValue}`);
        });

        filterContainer.style.marginLeft = '10px';
        filterContainer.style.textTransform = 'capitalize';
    });

    //submit filter
    const submitBtn = document.createElement('button');
    submitBtn.textContent = 'Submit';
    submitBtn.className = 'btn btn-primary';
    submitBtn.style.marginTop = '10px';
    submitBtn.addEventListener(
        'click',
        function () {
            const currentURL = window.location.href;
            const filterNameParam = 'name=' + 'a';

            const separator = currentURL.includes('?') ? '&' : '?';

            let updatedURL = new URL(currentURL);

            // If the current URL has a query string

            filterAttributes.forEach((filterAttr) => {
                console.log(updatedURL.href);
                // Remove the parameter
                newValue = document.querySelector('#' + filterAttr).value;
                updatedURL.searchParams.set(filterAttr, newValue);
                console.log(updatedURL.href);
            });

            // Redirect to the product page with the filter
            window.location.href = updatedURL.href;
        },
        false
    );

    filterContainer.appendChild(submitBtn);

    filter.appendChild(filterContainer);
}
