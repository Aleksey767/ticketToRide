const button = document.getElementById('buy-button');
function updateButtonState() {
    const priceField = document.getElementById('saved-price');
    const userAuthenticated = document.getElementById('user-authenticated');
    if (userAuthenticated.value === 'false') {
        button.disabled = true;
        return;
    }
    button.disabled = !priceField.value.trim();
}

window.onload = () => {
    updateButtonState();
}

document.addEventListener('DOMContentLoaded', function () {
    const dropdownItems = document.querySelectorAll('.dropdown-item');

    dropdownItems.forEach(item => {
        item.addEventListener('click', function (event) {
            event.preventDefault();
            const value = this.getAttribute('data-city');
            const inputId = this.getAttribute('data-input');
            const dropdownButton = document.getElementById(this.getAttribute('data-target'));

            document.getElementById(inputId).value = value;
            dropdownButton.textContent = value;
            button.disabled=true;
        });
    });
});

function areFieldsValid() {
    const departure = document.getElementById('departure').value.trim();
    const arrival = document.getElementById('arrival').value.trim();
    const travellerAmount = document.getElementById('travellerAmount').value.trim();
    return departure !== '' && arrival !== '' && travellerAmount !== '';
}

document.getElementById('calculate-price-form').addEventListener('submit', function (event) {
    event.preventDefault();

    if (areFieldsValid()) {
        const formData = new FormData(this);

        fetch(this.action, {
            method: 'POST',
            body: formData,
            headers: {
                'Accept': 'application/json'
            }
        }).then(response => response.json())
            .then(data => {
                document.querySelector('#dropdownMenuButton-1').textContent = data.departure;
                document.querySelector('#dropdownMenuButton-2').textContent = data.arrival;
                document.querySelector('#dropdownMenuButton-3').textContent = data.travellerAmount;

                const priceElement = document.querySelector('h1.mt-3.display-5');
                if (priceElement) {
                    priceElement.textContent = `${data.price} GBP`;
                }

                document.getElementById('departure').value = data.departure;
                document.getElementById('arrival').value = data.arrival;
                document.getElementById('travellerAmount').value = data.travellerAmount;
                document.getElementById('price').value = data.price;

                document.getElementById('selected-departure').value = data.departure;
                document.getElementById('selected-arrival').value = data.arrival;
                document.getElementById('selected-travellerAmount').value = data.travellerAmount;
                document.getElementById('saved-price').value = data.price;
                updateButtonState();
            }).catch(error => {
                console.error('Error:', error);
            });
    } else {
        console.log('Form fields are invalid or empty.');
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('save_ticket-form');
    if (form) {
        form.addEventListener('submit', function (event) {
            event.preventDefault();

            const formData = new FormData(form);
            const jsonData = {};

            formData.forEach((value, key) => {
                jsonData[key] = value;
            });

            fetch(form.action, {
                method: 'POST',
                body: JSON.stringify(jsonData),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    const redirectHeader = response.headers.get('X-Redirect');
                    let redirectUrl = '/api/v1/tickets';
                    switch (redirectHeader) {
                        case 'invalid_data':
                            redirectUrl = '/api/v1/tickets/redirect?status=invalid_data';
                            break;
                        case 'out_of_money':
                            redirectUrl = '/api/v1/tickets/redirect?status=out_of_money';
                            break;
                        case 'success':
                            redirectUrl = '/api/v1/tickets/redirect?status=success';
                            break;
                        default:
                            console.warn('Unknown redirect header:', redirectHeader);
                            break;
                    }
                    window.location.href = redirectUrl;
                }
            }).catch(error => {
                console.error('Error:', error);
            });
        });
    }
});