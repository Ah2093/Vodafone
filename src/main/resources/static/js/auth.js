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
        localStorage.setItem('authToken', data.token);

        console.log(" Now check authentication and fetch the profile before redirecting")
        fetchAndRenderHTML();
    } else {
        alert('Login failed!');
    }
});

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
        console.log(" Now check authentication and fetch the profile before redirecting")
        responseDiv.innerHTML = 'Registration successful! Redirecting...';
        responseDiv.style.color = 'green';
        setTimeout(() => window.location.href = '/view/login', 3000);
    } else {
        responseDiv.innerHTML = `Error: ${data.message || 'Registration failed'}`;
        responseDiv.style.color = 'red';
    }
});


function fetchAndRenderHTML() {
    const token = localStorage.getItem('authToken');
    console.log(token )

    fetch('/view/welcome', {
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch content');
            }
            return response.text();
        })
        .then(html => {
            const newWindow = window.open('', '_blank');
            if (newWindow) {
                newWindow.document.write(html);
                newWindow.document.close();
            } else {
                console.error("Popup blocked! Allow popups for this site.");
            }
        })
        .catch(error => {
            console.error('Error fetching HTML:', error);
            window.location.href = '/view/login';
        });
}



function logout() {
    localStorage.removeItem('authToken');
    window.location.href = '/view/login';
}
