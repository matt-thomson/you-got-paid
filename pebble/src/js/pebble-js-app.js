var ws = new WebSocket('ws://you-got-paid.herokuapp.com/ws');

ws.onmessage = function (event) {
  console.log('Received WebSocket message!');
  var payment = JSON.parse(event.data);

  var dict = {
    'KEY_AMOUNT': payment.amount,
    'KEY_PAYER': payment.name
  };

  Pebble.sendAppMessage(dict,
    function(e) {
      console.log('Send successful.');
    },
    function(e) {
      console.log('Send failed!');
    }
  );
};

// Listen for when the watchface is opened
Pebble.addEventListener('ready',
  function(e) {
    console.log('PebbleKit JS ready!');
  }
);
