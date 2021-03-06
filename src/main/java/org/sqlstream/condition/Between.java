/*
 * MIT License
 *
 * Copyright (c) 2020 1619kHz
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

import org.sqlstream.LambdaUtils;

import java.io.Serializable;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface Between<R extends Serializable, RType> extends Serializable {

  default <V> RType between(R typeFunction, V value1, V value2) {
    return this.between(LambdaUtils.getLambdaColumnName(typeFunction), value1, value2);
  }

  default <V> RType between(String fieldName, V value1, V value2) {
    return this.between(true, fieldName, value1, value2);
  }

  default <V> RType between(boolean condition, R typeFunction, V value1, V value2) {
    return this.between(condition, LambdaUtils.getLambdaColumnName(typeFunction), value1, value2);
  }

  default <V> RType notBetween(R typeFunction, V value1, V value2) {
    return this.notBetween(LambdaUtils.getLambdaColumnName(typeFunction), value1, value2);
  }

  default <V> RType notBetween(String fieldName, V value1, V value2) {
    return this.notBetween(true, fieldName, value1, value2);
  }

  default <V> RType notBetween(boolean condition, R typeFunction, V value1, V value2) {
    return this.notBetween(true, LambdaUtils.getLambdaColumnName(typeFunction), value1, value2);
  }

  <V> RType between(boolean condition, String fieldName, V value1, V value2);

  <V> RType notBetween(boolean condition, String fieldName, V value1, V value2);
}
