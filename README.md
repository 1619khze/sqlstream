# sqlstream
Simple dsl style sql builder

## Status: unfinished

How to use:

#### Configuration Connection

```java
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
```

#### AllEq
```java
  public void testSelectAllEq() {
    Map<TypeFunction<User, ?>, String> params = new HashMap<>();
    params.put(User::getUsername, null);
    List<User> list = sqlStream.select().from(User.class)
            .allEq(params, true)
            .list();
    System.out.println(list);
  }
```

#### AllEq By BiPredicate
```java
  public void testSelectAllEqByBiPredicate() {
    Map<TypeFunction<User, ?>, String> params = new HashMap<>();
    params.put(User::getUsername, "xx");

    List<User> list = sqlStream.select().from(User.class)
            .allEq((key, s) -> s.equals("xx1"), params, true)
            .list();
    System.out.println(list);
  }
```

#### AllEq By Condition
```java
  public void testSelectAllEqByBiPredicate() {
    Map<TypeFunction<User, ?>, String> params = new HashMap<>();
    params.put(User::getUsername, "xx");

    List<User> list = sqlStream.select().from(User.class)
            .allEq((key, s) -> s.equals("xx1"), params, true)
            .list();
    System.out.println(list);
  }
```

#### AllEq By BiPredicate And Condition
```java
  public void testSelectAllEqByBiPredicateAndCondition() {
    Map<TypeFunction<User, ?>, String> params = new HashMap<>();
    params.put(User::getUsername, "xx");
    List<User> list = sqlStream.select().from(User.class)
            .allEq(params.size() == 1,
                    (key, value) -> value.equals("xx1"), params, true)
            .list();
    System.out.println(list);
  }
```

#### Simple Query
```java
  public void testSelectCountField() {
    Long count = sqlStream.select().count("username")
            .from(User.class)
            .eq(User::getUsername, "xx")
            .size();
    System.out.println(count);
  }

  public void testSelectCountField() {
    Long count = sqlStream.select().count("username")
            .from(User.class)
            .eq(User::getUsername, "xx")
            .size();
    System.out.println(count);
  }

  public void testSelectOne() {
    User user = sqlStream.select()
            .from(User.class)
            .eq(User::getUsername, "xx")
            .one();
    System.out.println(user);
  }

  public void testSelectAll() {
    List<User> list = sqlStream.select()
            .from(User.class)
            .list();
    System.out.println(list);
  }
```