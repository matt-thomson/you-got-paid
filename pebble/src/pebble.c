#include <pebble.h>

static Window *s_main_window;
static TextLayer *s_time_layer;

static Window *s_paid_window;
static TextLayer *s_paid_amount_layer;

#if defined(PBL_COLOR)
  #define COLOR_BG GColorFromHEX(0x55AAFF)
#else
  #define COLOR_BG GColorBlack
#endif

#define COLOR_TEXT GColorWhite

#define KEY_AMOUNT 0
#define KEY_PAYER  1

#define WAKEUP_REASON 0

static void wakeup_handler(WakeupId id, int32_t reason) {
  APP_LOG(APP_LOG_LEVEL_INFO, "Woken up!");

  const bool animated = true;
  window_stack_pop(animated);
}

static void main_window_load(Window *window) {
  s_time_layer = text_layer_create(GRect(0, 55, 144, 50));
  text_layer_set_background_color(s_time_layer, COLOR_BG);
  text_layer_set_text_color(s_time_layer, COLOR_TEXT);
  text_layer_set_text(s_time_layer, "00:00");

  text_layer_set_font(s_time_layer, fonts_get_system_font(FONT_KEY_BITHAM_42_BOLD));
  text_layer_set_text_alignment(s_time_layer, GTextAlignmentCenter);

  layer_add_child(window_get_root_layer(window), text_layer_get_layer(s_time_layer));
}

static void main_window_unload(Window *window) {
  text_layer_destroy(s_time_layer);
}

static void paid_window_load(Window *window) {
  s_paid_amount_layer = text_layer_create(GRect(0, 55, 144, 50));
  text_layer_set_background_color(s_paid_amount_layer, COLOR_BG);
  text_layer_set_text_color(s_paid_amount_layer, COLOR_TEXT);
  text_layer_set_text(s_paid_amount_layer, "£££");

  text_layer_set_font(s_paid_amount_layer, fonts_get_system_font(FONT_KEY_BITHAM_42_BOLD));
  text_layer_set_text_alignment(s_paid_amount_layer, GTextAlignmentCenter);

  layer_add_child(window_get_root_layer(s_paid_window), text_layer_get_layer(s_paid_amount_layer));

  time_t future_time = time(NULL) + 15;
  wakeup_schedule(future_time, WAKEUP_REASON, true);
}

static void paid_window_unload(Window *window) {
  text_layer_destroy(s_paid_amount_layer);
}

static void update_time(struct tm *tick_time) {
  static char buffer[] = "00:00";

  if(clock_is_24h_style() == true) {
    strftime(buffer, sizeof("00:00"), "%H:%M", tick_time);
  } else {
    strftime(buffer, sizeof("00:00"), "%I:%M", tick_time);
  }

  text_layer_set_text(s_time_layer, buffer);
}

static void tick_handler(struct tm *tick_time, TimeUnits units_changed) {
  update_time(tick_time);
}

static void inbox_received_callback(DictionaryIterator *iterator, void *context) {
  APP_LOG(APP_LOG_LEVEL_INFO, "Message received!");

  const bool animated = true;
  window_stack_push(s_paid_window, animated);
  vibes_short_pulse();

  Tuple *t = dict_read_first(iterator);

  while (t != NULL) {
    static char amount_buffer[64];
    unsigned int amount;

    switch (t->key) {
      case KEY_AMOUNT:
        amount = t->value->uint32;
        snprintf(amount_buffer, sizeof(amount_buffer), "£%d.%2d", amount / 100, amount % 100);
        text_layer_set_text(s_paid_amount_layer, amount_buffer);
        break;
    }

    t = dict_read_next(iterator);
  }
}

static void inbox_dropped_callback(AppMessageResult reason, void *context) {
  APP_LOG(APP_LOG_LEVEL_ERROR, "Message dropped!");
}

static void outbox_sent_callback(DictionaryIterator *iterator, void *context) {
  APP_LOG(APP_LOG_LEVEL_INFO, "Outbox send success!");
}

static void outbox_failed_callback(DictionaryIterator *iterator, AppMessageResult reason, void *context) {
  APP_LOG(APP_LOG_LEVEL_ERROR, "Outbox send failed!");
}

static void init(void) {
  wakeup_service_subscribe(wakeup_handler);

  app_message_register_inbox_received(inbox_received_callback);
  app_message_register_inbox_dropped(inbox_dropped_callback);
  app_message_register_outbox_sent(outbox_sent_callback);
  app_message_register_outbox_failed(outbox_failed_callback);

  app_message_open(app_message_inbox_size_maximum(), app_message_outbox_size_maximum());

  s_main_window = window_create();
  window_set_window_handlers(s_main_window, (WindowHandlers) {
    .load = main_window_load,
    .unload = main_window_unload,
  });
  window_set_background_color(s_main_window, COLOR_BG);

  s_paid_window = window_create();
  window_set_window_handlers(s_paid_window, (WindowHandlers) {
    .load = paid_window_load,
    .unload = paid_window_unload,
  });
  window_set_background_color(s_paid_window, COLOR_BG);

  const bool animated = true;
  window_stack_push(s_main_window, animated);

  time_t temp = time(NULL);
  struct tm *tick_time = localtime(&temp);
  update_time(tick_time);

  tick_timer_service_subscribe(MINUTE_UNIT, tick_handler);
}

static void deinit(void) {
  window_destroy(s_main_window);
  window_destroy(s_paid_window);
}

int main(void) {
  init();

  APP_LOG(APP_LOG_LEVEL_DEBUG, "Done initializing, pushed window: %p", s_main_window);

  app_event_loop();
  deinit();
}
