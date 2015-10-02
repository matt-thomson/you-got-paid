var ws = new WebSocket('ws://you-got-paid.herokuapp.com/ws');

ws.onmessage = function (event) {
  console.log('Received WebSocket message!');
  var payment = JSON.parse(event.data);

  var dict = {
    'KEY_AMOUNT': payment.amount,
    'KEY_GIVEN_NAME': payment.given_name,
    'KEY_FAMILY_NAME': payment.family_name
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

Pebble.addEventListener('appmessage',
  function(e) {
    console.log('AppMessage received!');

    ws.send('ping');
  }
);
