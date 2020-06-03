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

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface Like<R, RType> extends Serializable {
  <V> RType like(boolean condition, R typeFunction, V value);

  <V> RType like(boolean condition, String fieldName, V value);

  <V> RType like(R typeFunction, V value);

  <V> RType like(String fieldName, V value);

  <V> RType notLike(boolean condition, R typeFunction, V value);

  <V> RType notLike(boolean condition, String fieldName, V value);

  <V> RType notLike(R typeFunction, V value);

  <V> RType notLike(String fieldName, V value);

  <V> RType likeLeft(boolean condition, R typeFunction, V value);

  <V> RType likeLeft(boolean condition, String fieldName, V value);

  <V> RType likeLeft(R typeFunction, V value);

  <V> RType likeLeft(String fieldName, V value);

  <V> RType likeRight(boolean condition, R typeFunction, V value);

  <V> RType likeRight(boolean condition, String fieldName, V value);

  <V> RType likeRight(R typeFunction, V value);

  <V> RType likeRight(String fieldName, V value);
}
