document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', function (event) {
            event.preventDefault();
            const formData = new FormData(form);
            fetch(form.action, {
                method: 'POST',
                body: formData,
                headers: {
                    'Accept': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Network response is not ok.');
                }
            }).then(data => {
                window.location.href = '/api/v1/user/success_after_creating_user';
            })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    }
});