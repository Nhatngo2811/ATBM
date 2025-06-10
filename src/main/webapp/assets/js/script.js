// Xử lý hiển thị menu user
document.addEventListener('DOMContentLoaded', function() {
    const userMenu = document.getElementById('userMenu');
    const userIcon = document.querySelector('.user-icon');
    
    if (userIcon && userMenu) {
        userIcon.addEventListener('click', function(e) {
            e.preventDefault();
            userMenu.style.display = userMenu.style.display === 'none' ? 'block' : 'none';
        });

        // Đóng menu khi click ra ngoài
        document.addEventListener('click', function(e) {
            if (!userMenu.contains(e.target) && !userIcon.contains(e.target)) {
                userMenu.style.display = 'none';
            }
        });
    }
}); 