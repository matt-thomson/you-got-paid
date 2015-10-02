// Listen for when the watchface is opened
Pebble.addEventListener('ready',
  function(e) {
    console.log('PebbleKit JS ready!');

    var dict = {
      'KEY_AMOUNT': 10,
      'KEY_PAYER': 'Tom Jones'
    };

    Pebble.sendAppMessage(dict,
      function(e) {
        console.log('Send successful.');
      },
      function(e) {
        console.log('Send failed!');
      }
    );
  }
);
