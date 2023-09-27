function RenderManufactureTemplate() {
    return (
        `
            <main>
                  <div class="container-fluid px-4">
                      <h1 class="mt-4">Manufacture</h1>
                      <div class="card mb-4">
                          <div class="card-body">
                 
                          </div>
                      </div>
                      <div class="card mb-4">
                          <div class="card-header">
                              <i class="fas fa-table me-1"></i>
                                 DataTable Example
                                 
                             <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                                 data-bs-target="#addManu">
                                 Add Manufacture
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
                                       <th>Created At</th>
                                   </tr>
                                   </thead>
                                   
                                   <tfoot>
                       
                                   </tfoot>
                                   
                                  <tbody>    
                                         
                                  </tbody>
                              </table>
                          </div>
                       </div>
                  </div>
           </main>
                
            <!-- Modal -->
            <div class="modal fade" id="addManu" tabindex="-1" aria-labelledby="addManuLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="addManuLabel">Add Manufacturer</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="manufacturerForm">
                             <div class="mb-3">
                                 <label for="name" class="form-label">Name</label>
                                 <input type="text" class="form-control" id="name" name="name" required>
                             </div>
                             <div class="mb-3">
                                 <label for="address" class="form-label">Address</label>
                                 <input type="text" class="form-control" id="address" name="address" required>
                             </div>
                             <div class="mb-3">
                                 <label for="phone" class="form-label">Phone</label>
                                 <input type="text" class="form-control" id="phone" name="phone" required>
                             </div>
                             <button type="submit" class="btn btn-primary">Submit</button>
                         </form>
                     </div>
                     <!-- Modal Footer -->
                     <div class="modal-footer">
                         <button type="button" class="btn btn-secondary" id="closeBtn" data-bs-dismiss="modal">Close</button>
                     </div>
            </div>
        </div>
    </div>
        `
    )
}


async function renderManufactureItems(url) {
    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows

        response.forEach(item => {
            const row = tableBody.insertRow();
            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.address}</td>
                            <td>${item.phone}</td>
                            <td>${item.createat || ''}</td>
                        `
        });
    }

    let errorCallback = (error) => {
        console.log(error);
    }

    await getDataFromApi(url, successCallback, errorCallback);
}

async function addManuListener(url) {
    let successCallback = (response) => {
        alert(response.message);
        // const modal = document.querySelector("#addManu");
        // const modalBS = bootstrap.Modal.getInstance(modal);
        // modalBS.hide();
        //click close to hide
        const closeBtn = document.querySelector("#closeBtn");
        closeBtn.click();

        renderManufactureItems(url);
    }

    let errorCallback = (error) => {
        console.log(error);
    }

    const addManu = async (e) => {
        e.preventDefault();
        const form = document.querySelector("#manufacturerForm");
        const data = {
            name: form.name.value,
            address: form.address.value,
            phone: form.phone.value
        }
        console.log(data);
        await postDataToApi(url, data, successCallback, errorCallback);
    }

    const form = document.querySelector("#manufacturerForm");
    form.addEventListener('submit', addManu);
}



