/*
 * Copyright 2013 Ernests Karlsons
 * https://github.com/bez4pieci
 * http://www.karlsons.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package com.bez4pieci.cookies;

import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;
import android.webkit.CookieManager;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cookies extends CordovaPlugin {

    private final static String TAG = "CookiesPlugin";

    private final static String DEFAULT_PROTOCOL = "http";
    private final static int DEFAULT_PORT = 80;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("clear".equals(action)) {
            this.clear();
            callbackContext.success();
            return true;
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    public void clear() {
        Log.v(TAG, "Clearing cookies...");
        CookieManager.getInstance().removeAllCookie();
    }

    public void setCookie(String action, JSONArray args, CallbackContext callbackContext) {
        Log.v(TAG, "Setting cookie");
        if (args.length() == 0) {
            System.err.println("Exception: No Arguments passed");
        } else {
            try {
                JSONObject options = args.getJSONObject(0);

                String protocol = DEFAULT_PROTOCOL;
                String host = null;
                int port = DEFAULT_PORT;
                String path = null;

                // Set the protocol
                if ( options.has("protocol") ) {
                    protocol = options.getString("protocol");
                }

                // Set the domain/host
                if (options.has("domain")) {
                    host = options.getString("domain");
                } else if (options.has("host")) {
                    host = options.getString("host");
                }

                // Set the port
                if (options.has("port")) {
                    port = options.getInt("port");
                }

                // Set the file/path
                if (options.has("file")) {
                    path = options.getString("file");
                } else if (options.has("path")) {
                    path = options.getString("path");
                }

                URL url = new URL(protocol, host, port, path);

                CookieManager.getInstance().setCookie(
                    url.toString(),
                    options.optString("value")
                );
            } catch (JSONException e) {
                Log.e(TAG, "Exception while setting cookie", e);
            } catch (MalformedURLException e) {
                Log.e(TAG, "Exception while setting cookie", e);
            }
        }
    }
}
