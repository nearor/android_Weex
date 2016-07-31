/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nearor.mylibrary.util;

import android.util.Log;

import com.nearor.mylibrary.activity.MyApplication;


public class Lg {
	private static final String LOG_PREFIX = "Ihome";
	private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
	private static final int MAX_LOG_TAG_LENGTH = 23;

	public static String makeLogTag(String str) {
		if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
			return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
		}

		return LOG_PREFIX + str;
	}

	/**
	 * Don't use this when obfuscating class names!
	 */
	public static String makeLogTag(Class cls) {
		return makeLogTag(cls.getSimpleName());
	}

	public static void d(final String tag, String message) {
		//noinspection PointlessBooleanExpression,ConstantConditions
		if (MyApplication.getSharedInstance().logLevel() <= Log.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
			Log.d(tag, message==null ? "null" : message);
		}
	}

	public static void d(final String tag, String message, Throwable cause) {
		//noinspection PointlessBooleanExpression,ConstantConditions
		if (MyApplication.getSharedInstance().logLevel() <= Log.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
			Log.d(tag, message == null ? "null" : message, cause);
		}
	}

	public static void v(final String tag, String message) {
		//noinspection PointlessBooleanExpression,ConstantConditions
		if (MyApplication.getSharedInstance().logLevel() <= Log.VERBOSE || Log.isLoggable(tag, Log.VERBOSE)) {
			Log.v(tag, message == null ? "null" : message);
		}
	}

	public static void v(final String tag, String message, Throwable cause) {
		//noinspection PointlessBooleanExpression,ConstantConditions
		if (MyApplication.getSharedInstance().logLevel() <= Log.VERBOSE || Log.isLoggable(tag, Log.VERBOSE)) {
			Log.v(tag, message == null ? "null" : message, cause);
		}
	}

	public static void i(final String tag, String message) {
		if (MyApplication.getSharedInstance().logLevel() <= Log.INFO && Log.isLoggable(tag, Log.INFO)) {
			Log.i(tag, message == null ? "null" : message);
		}
	}

	public static void i(final String tag, String message, Throwable cause) {
		if (MyApplication.getSharedInstance().logLevel() <= Log.INFO && Log.isLoggable(tag, Log.INFO)) {
			Log.i(tag, message == null ? "null" : message, cause);
		}
	}

	public static void w(final String tag, String message) {
		if (MyApplication.getSharedInstance().logLevel() <= Log.WARN && Log.isLoggable(tag, Log.WARN)) {
			Log.w(tag, message == null ? "null" : message);
		}
	}

	public static void w(final String tag, String message, Throwable cause) {
		if (MyApplication.getSharedInstance().logLevel() <= Log.WARN && Log.isLoggable(tag, Log.WARN)) {
			Log.w(tag, message == null ? "null" : message, cause);
		}
	}

	public static void e(final String tag, String message) {
		if (MyApplication.getSharedInstance().logLevel() <= Log.ERROR && Log.isLoggable(tag, Log.ERROR)) {
			Log.e(tag, message == null ? "null" : message);
		}
	}

	public static void e(final String tag, String message, Throwable cause) {
		if (MyApplication.getSharedInstance().logLevel() <= Log.ERROR && Log.isLoggable(tag, Log.ERROR)) {
			Log.e(tag, message == null ? "null" : message, cause);
		}
	}

	public static void e(final String tag, Throwable cause) {
		if (MyApplication.getSharedInstance().logLevel() <= Log.ERROR && Log.isLoggable(tag, Log.ERROR)) {
			Log.e(tag, cause.getMessage(), cause);
		}
	}

	private Lg() {
	}


}
