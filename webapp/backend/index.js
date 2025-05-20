const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const PORT = 5000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// Dữ liệu giả (có thể thay bằng cơ sở dữ liệu sau)
let users = [
    { id: 1, email: "test@example.com", password: "123456" },
];

// GET – ví dụ lấy danh sách người dùng
app.get('/api/users', (req, res) => {
    res.json(users);
});

// POST – thêm người dùng mới (đăng ký)
app.post('/api/users', (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(400).json({ message: 'Thiếu email hoặc password' });
    }

    // Kiểm tra trùng email
    const existingUser = users.find(user => user.email === email);
    if (existingUser) {
        return res.status(409).json({ message: 'Email đã tồn tại' });
    }

    const newUser = {
        id: users.length + 1,
        email,
        password,
    };

    users.push(newUser);

    res.status(201).json({ message: 'Tạo người dùng thành công', user: newUser });
});

// POST – ví dụ đổi mật khẩu
app.post('/api/change-password', (req, res) => {
    const { email, currentPassword, newPassword } = req.body;
    const user = users.find(u => u.email === email && u.password === currentPassword);

    if (!user) {
        return res.status(400).json({ message: 'Sai email hoặc mật khẩu' });
    }

    user.password = newPassword;
    res.json({ message: 'Đổi mật khẩu thành công' });
});

// POST – ví dụ quên mật khẩu
app.post('/api/forgot-password', (req, res) => {
    const { email } = req.body;
    const user = users.find(u => u.email === email);

    if (!user) {
        return res.status(200).json({ message: 'Nếu email tồn tại, hệ thống sẽ gửi liên kết khôi phục.' });
    }

    // Gửi email ở đây (giả lập)
    res.json({ message: 'Đã gửi liên kết khôi phục mật khẩu' });
});

// POST – đặt hàng
app.post('/api/checkout', (req, res) => {
    console.log('Đơn hàng:', req.body);
    res.json({ message: 'Đặt hàng thành công' });
});

// Khởi động server
app.listen(PORT, () => {
    console.log(`API server đang chạy tại http://localhost:${PORT}`);
});
