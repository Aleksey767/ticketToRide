document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('save_ticket-form');
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
            })
                .then(response => {
                    if (response.ok) {
                        const redirectHeader = response.headers.get('X-Redirect');
                        let redirectUrl = '/main';
                        switch (redirectHeader) {
                            case 'invalid_data':
                                redirectUrl = '/redirect_invalid_data';
                                break;
                            case 'lack_of_money':
                                redirectUrl = '/redirect_lack_of_money';
                                break;
                            case 'success':
                                redirectUrl = '/redirect_success';
                                break;
                            default:
                                console.warn('Unknown redirect header:', redirectHeader);
                                break;
                        }
                        window.location.href = redirectUrl;
                    } else {
                        throw new Error('Network response is not ok.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    }
});