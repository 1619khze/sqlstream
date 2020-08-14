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
package org.sqlstream;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author WangYi
 * @since 2020/6/26
 */
public class SnowFlake implements IdGenerator<Long> {
  /**
   * Start timestamp
   */
  private final long START_TIMESTAMP = 1557489395327L;
  /**
   * Serial number occupied digits
   */
  private final long SEQUENCE_BIT = 12;
  /**
   * Machine logo occupancy
   */
  private final long MACHINE_BIT = 10;
  /**
   * Timestamp shift bits
   */
  private final long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;
  /**
   * Maximum serial number (4095)
   */
  private final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
  /**
   * Maximum machine number (1023)
   */
  private final long MAX_MACHINE_ID = ~(-1L << MACHINE_BIT);
  /**
   * Generate id machine identification part
   */
  private long machineIdPart;
  /**
   * serial number
   */
  private long sequence = 0L;
  /**
   * Last timestamp
   */
  private long lastStamp = -1L;

  /**
   * Constructor initializes machine coding
   */
  private SnowFlake() {
    long count = ThreadLocalRandom.current().nextLong();
    machineIdPart = (count & MAX_MACHINE_ID) << SEQUENCE_BIT;
  }

  /**
   * Obtain IdWork instance object through Holder
   *
   * @return IdWork instance
   */
  public static SnowFlake of() {
    return IdWorkerHolder.SNOW_FLAKE;
  }

  /**
   * Inner class object (singleton pattern)
   */
  private static class IdWorkerHolder {
    private static final SnowFlake SNOW_FLAKE = new SnowFlake();
  }

  /**
   * Get snowflake ID
   *
   * @return Long type id
   */
  @Override
  public synchronized Long generate() {
    long currentStamp = timeGen();
    if (currentStamp < lastStamp) {
      throw new RuntimeException(String.format("Refusing to generate id " +
              "for %d milliseconds", lastStamp - currentStamp));
    }
    if (currentStamp == lastStamp) {
      sequence = (sequence + 1) & MAX_SEQUENCE;
      if (sequence == 0) {
        currentStamp = getNextMill();
      }
    } else {
      sequence = 0L;
    }
    lastStamp = currentStamp;
    return (currentStamp - START_TIMESTAMP) << TIMESTAMP_LEFT | machineIdPart | sequence;
  }

  /**
   * Block until the next millisecond until a new time stamp is obtained
   */
  private long getNextMill() {
    long mill = timeGen();
    while (mill <= lastStamp) {
      mill = timeGen();
    }
    return mill;
  }

  /**
   * Returns the current time in milliseconds
   */
  protected long timeGen() {
    return System.currentTimeMillis();
  }
}
