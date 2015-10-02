# You Got Paid!

[Pebble](https://www.getpebble.com) watchface that alerts you when you've received a payment.

![Time](https://raw.githubusercontent.com/matt-thomson/you-got-paid/master/screenshots/time.png)
![Paid](https://raw.githubusercontent.com/matt-thomson/you-got-paid/master/screenshots/paid.png)

## How it works

The watchface opens a [WebSocket](https://en.wikipedia.org/wiki/WebSocket) connection to a server, which sends notifications of new payments.  The server is a Java app, which uses the [Dropwizard](https://dropwizard.io) framework and the [GoCardless Pro Client](https://www.github.com/gocardless/gocardless-pro-java).

Currently, this shows payments for a hard-coded test organisation.
