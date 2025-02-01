document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            userName: e.target.userName.value,
            password: e.target.password.value
        })
    });

    const data = await response.json();
    if (response.ok) {
        // Store the token in local storage
        localStorage.setItem('authToken', data.token);
        // Redirect to the welcome page
        window.location.href = '/view/welcome';
    } else {
        // Handle the error
        alert('Login failed!');
    }
});


// Registration Form Handler
document.getElementById('registerForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = {
        userName: e.target.userName.value,
        phoneNumber: e.target.phoneNumber.value,
        firstName: e.target.firstName.value,
        lastName: e.target.lastName.value,
        email: e.target.email.value,
        password: e.target.password.value
    };

    const response = await fetch('/api/auth/register', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(formData)
    });

    const data = await response.json();
    const responseDiv = document.getElementById('response');

    if (response.ok) {
        responseDiv.innerHTML = 'Registration successful! Redirecting...';
        responseDiv.style.color = 'green';
        setTimeout(() => window.location.href = '/view/login', 1000);
    } else {
        responseDiv.innerHTML = `Error: ${data.message || 'Registration failed'}`;
        responseDiv.style.color = 'red';
    }
});


if (window.location.pathname === '/view/welcome') {
    const token = localStorage.getItem('authToken');
    if (!token) {
        // If no token, redirect to login
        window.location.href = '/view/login';
    } else {
        // Fetch user profile with Authorization header
        fetch('/view/welcome', {
            headers: {'Authorization': `Bearer ${token}`}
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(data => {
                console.log('Profile data fetched successfully');
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
}


// Logout Function
function logout() {
    localStorage.removeItem('authToken');
    window.location.href = '/view/login';
}