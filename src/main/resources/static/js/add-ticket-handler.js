document.addEventListener('DOMContentLoaded', function() {
    const dropdownItems = document.querySelectorAll('.dropdown-item');

    dropdownItems.forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            const value = this.getAttribute('data-city');
            const inputId = this.getAttribute('data-input');
            const dropdownButton = document.getElementById(this.getAttribute('data-target'));

            document.getElementById(inputId).value = value;
            dropdownButton.textContent = value;
        });
    });
});

const calculatePriceForm = document.getElementById('calculate-price-form');
if (calculatePriceForm) {
    calculatePriceForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(calculatePriceForm);
        fetch(calculatePriceForm.action, {
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
            }).catch(error => {
            console.error('Error:', error);
        });
    });
}

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
            }).then(response => {
                if (response.ok) {
                    const redirectHeader = response.headers.get('X-Redirect');
                    let redirectUrl = '/main';
                    switch (redirectHeader) {
                        case 'invalid_data':
                            redirectUrl = '/redirect_to_main?status=invalid_data';
                            break;
                        case 'out_of_money':
                            redirectUrl = '/redirect_to_main?status=out_of_money';
                            break;
                        case 'success':
                            redirectUrl = '/redirect_to_main?status=success';
                            break;
                        default:
                            console.warn('Unknown redirect header:', redirectHeader);
                            break;
                    }
                    window.location.href = redirectUrl;
                } else {
                    throw new Error('Network response is not ok.');
                }
            }).catch(error => {
                console.error('Error:', error);
            });
        });
    }
});