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

import bean.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlstream.SQLStream;
import org.sqlstream.SQLStreamBuilder;
import org.sqlstream.TypeFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangYi
 * @since 2020/5/30
 */
public class SlqStreamTest {

  private SQLStream sqlStream;

  @BeforeEach
  public void init() {
    String jdbcUrl = "jdbc:mysql://localhost:3306/orm_test?useSSL=false&useUnicode=true&characterEncoding=utf-8" +
            "&zeroDateTimeBehavior=convertToNull&allowPublicKeyRetrieval=true&transformedBitIsBoolean=true&serverTimezone=Asia/Shanghai";
    this.sqlStream = SQLStreamBuilder.builder()
            .jdbcUrl(jdbcUrl)
            .username("username")
            .password("password")
            .driver("com.mysql.cj.jdbc.Driver")
            .build();
  }

  @Test
  public void testSelectAllEq() {
    Map<TypeFunction<User, ?>, String> params = new HashMap<>();
    params.put(User::getUsername, null);

    List<User> list = sqlStream.select().from(User.class)
            .allEq(params, true)
            .list();
    System.out.println(list);
  }

  @Test
  public void testSelectAllEqByBiPredicate() {
    Map<TypeFunction<User, ?>, String> params = new HashMap<>();
    params.put(User::getUsername, "xx");

    List<User> list = sqlStream.select().from(User.class)
            .allEq((key, s) -> s.equals("xx1"), params, true)
            .list();
    System.out.println(list);
  }

  @Test
  public void testSelectAllEqByCondition() {
    Map<TypeFunction<User, ?>, String> params = new HashMap<>();
    params.put(User::getUsername, "xx");

    List<User> list = sqlStream.select().from(User.class)
            .allEq(params.size() == 1, params, true)
            .list();
    System.out.println(list);
  }

  @Test
  public void testSelectAllEqByBiPredicateAndCondition() {
    Map<TypeFunction<User, ?>, String> params = new HashMap<>();
    params.put(User::getUsername, "xx");

    List<User> list = sqlStream.select().from(User.class)
            .allEq(params.size() == 1,
                    (key, value) -> value.equals("xx1"), params, true)
            .list();
    System.out.println(list);
  }

  @Test
  public void testSelectCountAll() {
    Long count1 = sqlStream.select().count()
            .from(User.class)
            .size();
    System.out.println(count1);
  }

  @Test
  public void testSelectCountField() {
    Long count = sqlStream.select().count("username")
            .from(User.class)
            .eq(User::getUsername, "xx")
            .size();
    System.out.println(count);
  }

  @Test
  public void testSelectOne() {
    User user = sqlStream.select()
            .from(User.class)
            .eq(User::getUsername, "xx")
            .one();
    System.out.println(user);
  }

  //select *from xx;
  @Test
  public void testSelectAll() {
    List<User> list = sqlStream.select()
            .from(User.class)
            .list();
    System.out.println(list);
  }
}
