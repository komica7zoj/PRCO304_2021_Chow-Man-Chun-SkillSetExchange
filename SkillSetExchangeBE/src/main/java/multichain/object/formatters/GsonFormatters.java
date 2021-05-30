/*
 * Copyright (C) 2017 Worldline, Inc.
 *
 * MultiChainJavaAPI code distributed under the GPLv3 license, see COPYING file.
 * https://github.com/SimplyUb/MultiChainJavaAPI/blob/master/LICENSE
 *
 */
package multichain.object.formatters;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Ub - H. MARTEAU
 * @version 2.0.1
 */
public class GsonFormatters {
  protected GsonFormatters() {
    super();
  }

  protected static String formatJson(String[] values) {
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();

    return gson.toJson(values);
  }

  protected static String formatJson(List<Object> values) {
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();

    return gson.toJson(values);
  }

  protected static String formatJson(Object value) {
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();
    return gson.toJson(value);
  }

  protected static String formatJsonWithCustomBuilder(Object value, GsonBuilder builder) {
    return builder.create().toJson(value);
  }

}
