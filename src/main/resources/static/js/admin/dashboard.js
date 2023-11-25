const StatisticURL = API_ADMIN_URL + "/statistic";

function RenderDashboard() {
    return (
        `
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Dashboard</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Dashboard</li>
                        </ol>
                        <div class="row">
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-primary text-white mb-4">
                                    <div class="card-body">Total Profit</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                         <div class="text-center text-white" id="total-profit"> <i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-warning text-white mb-4">
                                    <div class="card-body">Total Product</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <div class="small text-white" id="total-products"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-success text-white mb-4">
                                    <div class="card-body">Total Customer</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <div class="small text-white" id="total-customers"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-danger text-white mb-4">
                                    <div class="card-body">Total Order</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <div class="small text-white" id="total-orders"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                        </div>                                                        
                       
                       <div>
                            <div id = "year-select-box" class="d-flex justify-content-end"> </div>
                            <canvas id="salesChart" style="max-height:500px;" width="400" height="200"></canvas>
                        </div>
  
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                DataTable
                            </div>
                            <div class="card-body d-flex">
                                    <!-- Top 10 Customers Table -->
                                    <div class="top-10-customers flex-grow-1">
                                        <table id="c-table" class="table table-bordered">
                                            <thead>
                                             <tr >
                                                <th colspan="3" class="text-center">Top 10 Customers</th>
                                           </tr>
                                                <tr>
                                                    <th>Customer ID</th>
                                                    <th>Name</th>
                                                    <th>Purchases</th>
                                                    <!-- More columns as needed -->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Customer data rows -->
                                            </tbody>
                                        </table>
                                    </div>
                                
                                    <!-- Top 10 Employees Table -->
                                    <div class="top-10-employees flex-grow-1">
                                        <table id="e-table" class="table table-bordered">
                                            <thead>
                                               <tr>
                                                <th colspan="3" class="text-center">Top 10 Employees</th>
                                               </tr>
                                                <tr>
                                                    <th>Employee ID</th>
                                                    <th>Name</th>
                                                    <th>Revenue</th>
                                                    <!-- More columns as needed -->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Employee data rows -->
                                            </tbody>
                                        </table>
                                    </div>
                                
                                    <!-- Top 10 Products Table -->
                                    <div class="top-10-products border-start-0 flex-grow-1">
                                        <table id="p-table" class="table table-bordered">
                                            <thead>
                                                <tr>
                                                    <th colspan="3" class="text-center">Top 10 Products</th>
                                                </tr>
                                                <tr>
                                                    <th>Product ID</th>
                                                    <th>Name</th>
                                                    <th>Sales</th>
                                                    <!-- More columns as needed -->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Product data rows -->
                                            </tbody>
                                        </table>
                                    </div>
                                    
                            </div>
                        </div>
                    </div>
                </main>
        `
    )
}

function renderStatistics() {
    //render select box year
    let yearSelectBox = document.querySelector("#year-select-box")
    let yearSelect = document.createElement("select")
    yearSelect.className = "form-select"
    yearSelect.id = "year-select"
    yearSelectBox.appendChild(yearSelect)
    //render option
    let now = new Date()
    for (let i = 2021; i <= now.getFullYear(); i++) {
        let option = document.createElement("option")
        if (i == now.getFullYear()) {
            option.selected = true
        }
        option.value = i
        option.innerText = i
        yearSelect.appendChild(option)
    }


    //select event
    yearSelect.addEventListener("change", () => {
        renderChart()
    })


    renderTop10Products()
    renderTop10Customer()
    renderTop10Employee()
    renderChart()
    const total_box_type = [
        "profit",
        "products",
        "customers",
        "orders"
    ]

    total_box_type.forEach(type => {
        renderTotalBoxOnHeader(type)
    }, this);
}

function renderTotalBoxOnHeader(type) {
    let url = StatisticURL + "/total-" + type
    let onSuccess = (resp) => {
        //create h element and append to total box
        let totalBox = document.querySelector("#total-" + type)
        total = document.createElement("h5")
        //text in middle
        total.className = "fw-bold text-center"
        total.innerText = resp.data
        if (type == "profit") {
            total.innerText = "$" + resp.data
        }

        totalBox.appendChild(total)
    }

    let onFailure = (error) => {
    }

    getDataFromApi(url, onSuccess, onFailure)
}

function renderTop10Products() {

    let url = StatisticURL + "/top-products"
    let onSuccess = (resp) => {
        let tbody = document.querySelector("#p-table tbody")
        resp.data.forEach(item => {
            let row = tbody.insertRow()

            row.innerHTML = `
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.quantity}</td>
            `
        })
    }

    let onFailure = (error) => {
    }

    getDataFromApi(url, onSuccess, onFailure)
}

function renderTop10Customer() {

    let url = StatisticURL + "/top-customers"
    let onSuccess = (resp) => {
        let tbody = document.querySelector("#c-table tbody")
        resp.data.forEach(item => {
            let row = tbody.insertRow()

            row.innerHTML = `
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.purchase}$</td>
            `
        })
    }

    let onFailure = (error) => {
    }

    getDataFromApi(url, onSuccess, onFailure)
}

function renderTop10Employee() {
    let url = StatisticURL + "/top-employees"
    let onSuccess = (resp) => {
        let tbody = document.querySelector("#e-table tbody")
        resp.data.forEach(item => {
            let row = tbody.insertRow()

            row.innerHTML = `
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.quantity}$</td>
                `
        })
    }

    let onFailure = (error) => {
    }

    getDataFromApi(url, onSuccess, onFailure)
}

function renderChart() {
    const ctx = document.getElementById('salesChart').getContext('2d');

    let url = StatisticURL + "/profit"
    let year = document.querySelector("#year-select").value ?? new Date().getFullYear()
    url += "?year=" + year

    getDataFromApi(url, onSuccess, {})

    data = []
    for (let i = 1; i <= 12; i++) {
        data.push({
            month: i,
            total: 0
        })
    }


    chart = newChart(ctx, data)

    function onSuccess(resp) {
        console.log(resp)
        resp.data.forEach(item => {
            data[item.month - 1].total = item.total
        })

        console.log(data)

        if (window.chart) {
            window.chart.destroy();
        }

        chart = newChart(ctx, data)
    }

    function onFailure(error) {
        if (window.chart) {
            window.chart.destroy();
        }
        chart = newChart(ctx, data)
    }
}

function newChart(ctx, data) {
    let chart_data = {
        labels: data.map(item => `Month ${item.month}`),
        datasets: [{
            label: 'Total Sales',
            data: data.map(item => item.total),
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)'
            ],
            borderWidth: 1
        }],
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    }

    return new Chart(ctx, {
        type: 'bar',
        data: chart_data,
    })
}


