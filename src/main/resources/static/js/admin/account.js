const ACCOUNT_URL = API_ADMIN_URL + '/account'

function RenderAccountTemplate() {
    return (`
            <main>
                  <div class="container-fluid px-4">
                      <h1 class="mt-4">Admin</h1>
                      <div class="card mb-4">
                          <div class="card-body">
                 
                          </div>
                      </div>
                      <div class="card mb-4">
                          <div class="card-header">
                                <div id ="filter" class="float-start">
                               </div>
                                 
                             <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                                 data-bs-target="#addAccount">
                                 Add Account
                             </button>
                          </div>
                          <div class="card-body">
                              <table id="content" class="table table-bordered">
                                  <thead>
                                   <tr>
                                       <th>ID</th>
                                       <th>Name</th>
                                       <th>Address</th>
                                       <th>Phone</th>
                                       <th>Role</th>
                                       <th>Created At</th>
                                       <th>Actions</th>
                                   </tr>
                                   </thead>
                                   
                                   <tfoot>
                       
                                   </tfoot>
                                   
                                  <tbody>    
                                         
                                  </tbody>
                              </table>
                          </div>
                          
                            <div class="card-footer">
                            </div>
                       </div>
                  </div>
           </main>
                
            <!-- Modal -->
          <div class="modal fade" id="addAccount" tabindex="-1" aria-labelledby="addAccountLabel" aria-hidden="true">
             <div class="modal-dialog">
                <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="addAccountLabel">Add Account</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Modal Body -->
            <div class="modal-body">
                <form id="addForm">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name *</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email *</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone *</label>
                        <input type="text" class="form-control" id="phone" name="phone" required>
                    </div>
                    <div class="mb-3">
                          <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="role" class="form-label">Role</label>
                        <select class="form-select" aria-label="Default select example" id="role" name="role" required>
                            <option value="0">Admin</option>
                            <option value="1">User</option>
                        </select>
                    </div>
                </form>
            </div>
            <!-- Modal Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="addAccount()">Submit</button>
            </div>
        </div>
          </div>
         </div>

           <!-- Modal edit -->
            <div class="modal fade" id="editAccount" tabindex="-1" aria-labelledby="editAccountLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="editAccountLabel">Edit Account</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="editAccountForm">
                                <div class="mb-3">
                                    <label for="name" class="form-label">Name *</label>                                  
                                    <input type="text" class="form-control" id="name" name="name" required>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email *</label>
                                    <input type="email" class="form-control" id="email" name="email" required>
                                </div>
                                <div class="mb-3">
                                    <label for="phone" class="form-label">Phone *</label>
                                    <input type="text" class="form-control" id="phone" name="phone" required>
                                </div>
                                <div class="mb-3">
                                      <label for="password" class="form-label">Password</label>
                                        <input type="password" class="form-control" id="password" name="password" required>
                                </div>
                                <div class="mb-3">
                                    <label for="role" class="form-label">Role</label>
                                    <select class="form-select" aria-label="Default select example" id="role" name="role" required>
                                        <option value="0">Admin</option>
                                        <option value="1">User</option>
                                    </select>
                           </div>
                            
                         </form>
                     </div>
                     <!-- Modal Footer  have render in js -->
                     <div class="modal-footer">
                     </div>
            </div>
        </div>
            </div>
                        
        `)
}

async function RenderAccountItems() {
    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;
    GET_URL = `${ACCOUNT_URL}?page=${page}&size=${limit}`;

    filterArr = [
        'name',
    ];
    renderFilterBox(filterArr);
    filterArr.forEach(item => {
        let value = url.searchParams.get(item);
        if (value) {
            GET_URL += `&${item}=${value}`;
        }
    }, GET_URL);

    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows

        const editBtn = (id) => {
            return `<button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editAccount" onclick="editForm(${id})">Edit</button>`
        }

        const deleteBtn = (id) => {
            return `<button type="button" class="btn btn-danger btn-sm" onclick="deleteItem('${ACCOUNT_URL}/${id}')">Delete</button>`
        }

        response.data.forEach(item => {
            const row = tableBody.insertRow();
            let role = item.role == 0 ? 'Admin' : 'User';

            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.email}</td>
                            <td>${item.phone}</td>
                            <td>${role}</td>
                            <td>${item.createdAt || ''}</td>
                            <td>
                                ${editBtn(item.id)}
                                ${deleteBtn(item.id)} 
                             </td>
                        `
        });

        // Pagination
        const pagination = document.querySelector(".card-footer");
        pagination.innerHTML = '';
        totalPage = Math.ceil(response.meta.totalItem / response.meta.limit);
        for (let i = 1; i <= totalPage; i++) {
            urlPage = `?Accountfacture&page=${i}&limit=${LIMIT}`
            pagination.innerHTML += `<a href="${urlPage}" class="btn btn-secondary ${i == page ? 'active' : ''}">${i}</a>`
        }
    }

    let errorCallback = (error) => {
    }


    await getDataFromApi(GET_URL, successCallback, errorCallback);
}


async function addAccount() {

    if (!validateForm("#addForm")) {
        return;
    }

    let successCallback = (response) => {
        // const closeBtn = document.querySelector("#closeBtn");
        // closeBtn.click();
        bootstrap.Modal.getInstance(document.querySelector('#addAccount')).hide();
        //get current page
        window.location.reload();
    }

    let errorCallback = (error) => {
    }


    const form = document.querySelector("#addForm");
    const formData = createFormData(form);
    
    await postDataToApi(ACCOUNT_URL, formData, successCallback, errorCallback);
}

async function deleteItem(url) {
    let successCallback = (response) => {
        window.location.reload();
    }

    let errorCallback = (error) => {
    }

    await deleteDataFromApi(url, successCallback, errorCallback);
}

async function editAccount(id) {
    if (!validateForm("#editAccountForm")) {
        return;
    }

    const errorCallback = (error) => {
        console.log(error);
    }
    const successCallback = async (response) => {
        bootstrap.Modal.getInstance(document.querySelector("#editAccount")).hide();
        window.location.reload();
    }

    editUrl = `${ACCOUNT_URL}/${id}`;

    const form = document.querySelector("#editAccountForm");
    const formData = createFormData(form);

    await putDataToApi(editUrl, formData, successCallback, errorCallback);
    //remove event listener
}

//get detail
async function editForm(id) {

    let successCallback = (response) => {
        console.log(response.data.name);
        const form = document.querySelector("#editAccountForm");
        form.name.value = response.data.name;
        form.email.value = response.data.email;
        form.phone.value = response.data.phone;
        form.role.value = response.data.role;
        //render button in footer
        const footer = document.querySelector("#editAccount .modal-footer");
        footer.innerHTML = `<button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`
        footer.innerHTML += ` <button type="button" class="btn btn-primary" onclick="editAccount(${response.data.id})">Submit</button>`
    }

    let errorCallback = (error) => {
        console.log(error);
    }
    editUrl = `${ACCOUNT_URL}/${id}`;

    await getDataFromApi(editUrl, successCallback, errorCallback);
}

function createFormData(form) {
    const formData = new FormData();
    if (form.name.value){
        formData.append('name', form.name.value);
    }
    if (form.email.value){
        //check email is valid or no
        formData.append('email', form.email.value);
    }

    if (form.phone.value){
        formData.append('phone', form.phone.value);
    }

    if (form.role.value){
        formData.append('role', form.role.value);
    }

    if (form.password.value){
        formData.append('password', form.password.value);
    }

    return formData;
}

function validateForm(formId) {
    form = document.querySelector(formId);
    if (!form.name.value) {
        showToast("Name is required", "error");
        return false;
    }

    if (!form.email.value) {
        showToast("Email is required", "error")
        return false;
    }

    if (form.email.value.indexOf('@') === -1 || form.email.value.indexOf('.') === -1) {
        showToast("Email is invalid", "error")
        return false;
    }

    if (!form.phone.value) {
        showToast("Phone is required", "error");
        return false;
    }

    if (!form.role.value) {
        showToast("Role is required", "error");
        return false;
    }

    if (!form.password.value) {
        showToast("Password is required", "error");
        return false;
    }


    return true;
}
