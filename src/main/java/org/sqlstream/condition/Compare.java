/*
 * MIT License
 *
 * Copyright (c) 2019 1619kHz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.sqlstream.condition;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface Compare<R, RType> extends Serializable {
  default <V> RType allEq(Map<R, V> params) {
    return this.allEq(params, true);
  }

  <V> RType allEq(Map<R, V> params, boolean null2IsNull);

  <V> RType allEq(BiPredicate<R, V> filter, Map<R, V> params);

  <V> RType allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull);

  <V> RType allEq(boolean condition, Map<R, V> params, boolean null2IsNull);

  <V> RType allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull);

  <V> RType ne(boolean condition, R typeFunction, V value);

  <V> RType ne(boolean condition, String fieldName, V value);

  <V> RType ne(R typeFunction, V value);

  <V> RType ne(String fieldName, V value);

  <V> RType eq(boolean condition, R typeFunction, V value);

  <V> RType eq(R typeFunction, Object value);

  <V> RType eq(String fieldName, V value);

  <V> RType eq(boolean condition, String fieldName, V value);
}