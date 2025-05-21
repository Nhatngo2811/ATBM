function showSection(sectionId, title) {
    // Thay đổi tiêu đề
    document.getElementById('page-title').innerHTML = `
        <label for="nav-toggle">
            <span class="fa-solid fa-bars"></span>
        </label> ${title}`;

    // Ẩn tất cả các phần
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => {
        section.classList.remove('active');
    });

    // Hiển thị phần được chọn
    const selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.classList.add('active');
    }

    // Xóa class active từ tất cả các mục menu
    const menuItems = document.querySelectorAll('.menu-item');
    menuItems.forEach(item => item.classList.remove('active'));

    // Thêm class active vào mục menu được chọn
    const activeMenuItem = document.querySelector(`a[onclick="showSection('${sectionId}', '${title}')"]`);
    if (activeMenuItem) {
        activeMenuItem.classList.add('active');
    }
}

// Mặc định hiển thị phần dashboard khi tải trang
document.addEventListener('DOMContentLoaded', () => {
    showSection('dashboard', 'Dashboard');
});
// dong mo sidebar
document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#nav-toggle").addEventListener("change", function () {
        document.querySelector(".sidebar").classList.toggle("active");
    });
});
// chart
// Cấu hình biểu đồ Doanh thu 6 tháng qua
const ctxMonthly = document.getElementById('monthlyRevenueChart').getContext('2d');
const monthlyRevenueChart = new Chart(ctxMonthly, {
    type: 'bar',
    data: {
        labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6'],
        datasets: [{
            label: 'Doanh thu',
            data: [12000000, 15000000, 13000000, 17000000, 14000000, 16000000],
            backgroundColor: '#AED9EC',
            borderColor: '#AED9EC',
            borderWidth: 1,
            hoverBackgroundColor: '#5fa2e9',
            hoverBorderColor: '#5fa2e9',
            borderRadius: 6, // Rounded corners for bars
        }]
    },
    options: {
        responsive: true,
        plugins: {
            tooltip: {
                callbacks: {
                    label: (context) => `${context.raw.toLocaleString()} VNĐ`
                }
            },
            legend: {
                display: false
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                title: {
                    display: true,
                    text: 'Doanh thu (VNĐ)',
                    font: {
                        size: 12
                    }
                },
                ticks: {
                    callback: function (value) {
                        return value.toLocaleString() + ' đ';
                    }
                }
            },
            x: {
                title: {
                    display: true,
                    text: 'Tháng',
                    font: {
                        size: 12
                    }
                },
                barPercentage: 0.5,
                categoryPercentage: 0.6
            }
        }
    }
});

// Doanh thu theo tháng
const monthlyRevenueCtx = document.getElementById('monthlyRevenueChart1').getContext('2d');

const monthlyRevenueChart1 = new Chart(monthlyRevenueCtx, {
    type: 'line', // Loại biểu đồ đường
    data: {
        labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
        datasets: [{
            label: 'Doanh thu (VND)',
            data: [150000000, 200000000, 180000000, 220000000, 250000000, 300000000, 350000000, 320000000, 280000000, 310000000, 400000000, 450000000],
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderWidth: 2,
            pointRadius: 4, // Độ lớn của các điểm trên biểu đồ
            pointHoverRadius: 6, // Độ lớn của các điểm khi hover
            fill: true,
            tension: 0.4 // Độ cong của đường biểu đồ
        }]
    },
    options: {
        responsive: true,
        plugins: {
            tooltip: {
                callbacks: {
                    label: (context) => `${context.raw.toLocaleString('vi-VN')} VND` // Định dạng tooltip là VND
                }
            },
            legend: {
                display: true,
                position: 'top',
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                title: {
                    display: true,
                    text: 'Doanh thu (VND)',
                    font: {
                        size: 12
                    }
                },
                ticks: {
                    callback: function (value) {
                        return `${value.toLocaleString('vi-VN')} đ`; // Hiển thị "đồng" cho trục y
                    }
                }
            },
            x: {
                title: {
                    display: true,
                    text: 'Tháng',
                    font: {
                        size: 12
                    }
                }
            }
        }
    }
});

// Khi người dùng nhấn vào overlay (bên ngoài thông báo), đóng thông báo
document.getElementById("logoutOverlay").onclick = function () {
    document.getElementById("logoutOverlay").style.display = "none";
    document.getElementById("logoutNotification").style.display = "none";
};
// Khi người dùng nhấn vào "Xóa"
document.querySelectorAll('.delete-button').forEach(button => {
    button.onclick = function () {
        // Lưu dòng sản phẩm cần xóa
        currentRowToDelete = this.closest('tr');

        // Hiển thị cả overlay và thông báo xóa sản phẩm
        document.getElementById("deleteOverlay").style.display = "block";
        document.getElementById("deleteNotification").style.display = "block";
    };
});

// Khi người dùng nhấn "Có" (Xác nhận xóa sản phẩm)
document.getElementById("confirmDeleteBtn").onclick = function () {
    // Xóa dòng sản phẩm
    currentRowToDelete.remove();

    // Ẩn cả overlay và thông báo xóa
    document.getElementById("deleteOverlay").style.display = "none";
    document.getElementById("deleteNotification").style.display = "none";
};

// Khi người dùng nhấn "Không" (Hủy xóa sản phẩm)
document.getElementById("cancelDeleteBtn").onclick = function () {
    // Ẩn cả overlay và thông báo xóa mà không làm gì
    document.getElementById("deleteOverlay").style.display = "none";
    document.getElementById("deleteNotification").style.display = "none";
};

// Khi người dùng nhấn vào overlay (bên ngoài thông báo), đóng thông báo xóa
document.getElementById("deleteOverlay").onclick = function () {
    document.getElementById("deleteOverlay").style.display = "none";
    document.getElementById("deleteNotification").style.display = "none";
};
// modal hóa đơn


