#include <pebble.h>

static Window *s_main_window;
static TextLayer *s_time_layer;

#if defined(PBL_COLOR)
  #define BG_COLOR GColorFromHEX(0x55AAFF)
#else
  #define BG_COLOR GColorBlack
#endif

#define TEXT_COLOR GColorWhite

static void main_window_load(Window *window) {
  // Create time TextLayer
  s_time_layer = text_layer_create(GRect(0, 55, 144, 50));
  text_layer_set_background_color(s_time_layer, BG_COLOR);
  text_layer_set_text_color(s_time_layer, TEXT_COLOR);
  text_layer_set_text(s_time_layer, "00:00");

  // Improve the layout to be more like a watchface
  text_layer_set_font(s_time_layer, fonts_get_system_font(FONT_KEY_BITHAM_42_BOLD));
  text_layer_set_text_alignment(s_time_layer, GTextAlignmentCenter);

  // Add it as a child layer to the Window's root layer
  layer_add_child(window_get_root_layer(window), text_layer_get_layer(s_time_layer));
}

static void main_window_unload(Window *window) {
  text_layer_destroy(s_time_layer);
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

static void init(void) {
  s_main_window = window_create();
  window_set_window_handlers(s_main_window, (WindowHandlers) {
    .load = main_window_load,
    .unload = main_window_unload,
  });
  const bool animated = true;
  window_stack_push(s_main_window, animated);
  window_set_background_color(s_main_window, BG_COLOR);

  time_t temp = time(NULL);
  struct tm *tick_time = localtime(&temp);
  update_time(tick_time);

  tick_timer_service_subscribe(MINUTE_UNIT, tick_handler);
}

static void deinit(void) {
  window_destroy(s_main_window);
}

int main(void) {
  init();

  APP_LOG(APP_LOG_LEVEL_DEBUG, "Done initializing, pushed window: %p", s_main_window);

  app_event_loop();
  deinit();
}
