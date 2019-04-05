# JPA Master/Slave DataSource test

## LazyConnectionDataSourceProxy + AbstractRoutingDataSource를 이용한 Master/Slave DataSource 분기

### 기본 아이디어

* `@Transactional(readOnly = true|false)` 를 통해 Master/Slave DataSource 분기를 결정
    * AbstractRoutingDataSource를 상속한 클래스가 트랜잭션 속성을 보고 Master/Slave DataSource 분기
        * `AbstractRoutingDataSource.determineTargetDataSource()`
            * `setTargetDataSources(Map<Object, Object> targetDataSources)`
            * `determineCurrentLookupKey()`
* 그런데, 트랜잭션 동기화(TransactionSynchronization) 전에 DataSource 에서 Connection을 가지고오기 때문에 트랜잭션 속성을 제때 읽지 못함
    * LazyConnectionDataSourceProxy를 이용해서 트랜잭션 동기화 후에 실제 쿼리 요청 시 Connection을 가져오도록 처리
        * `LazyConnectionDataSourceProxy.getConnection()` 메서드는 Proxy를 반환
        * Connection Proxy에 실제 메서드 요청이 발생할 때 targetConnection을 가져와서 해당 요청을 수행

### 문제점

* TransactionManager로 DataSourceTransactionManager를 사용하는 경우에는 (JdbcTemplate, Mybatis 등)
    * `Connection.commit()/rollback()` 메서드를 이용해서 트랜잭션을 관리하기 때문에
    * 트랜잭션 속성에 따라 DataSource를 바꿔주면 Master/Slave Connection을 바꿔줄 수 있다
* 그런데, JPA를 사용하는 경우에는 원하는대로 동작하지 않는다
    * JPA의 경우에는 JpaTransactionManager가 트랜잭션을 관리
    * JpaTransactionManager에서는 실제 트랜잭션 관리를 EntityManager가 수행하고
    * EntityManager는 EntityManagerFactory를 key로 ThreadLocal에 저장되기 때문에
      동일한 EntityManagerFactory를 쓰는 경우에는 동일한 EntityManager가 트랜잭션을 관리한다
    * 거기다 TransactionManager, EntityManager, Repository, dataSource는 셋트로 구성되기 때문에
    * 트랜잭션 속성에 따라 DataSource를 바꿔줘봐야
      EntityManagerFactory가 같다면 ThreadLocal에 저장된 EntityManager는 동일하기 때문에 
      Master/Slave Connection이 바뀌지 않는다

## JPA에서 Master/Slave DataSource를 바꾸려면

### 기본 아이디어

* 서로 다른 DataSource에 대해 EntityMangerFactory, TransactionManager, Repository를 각각 구성하듯
  Master/Slave 각각에 대해 별도의 EntityMangerFactory, TransactionManager, Repository를 구성
    * 다만, Master/Slave는 동일한 entity, repository 코드를 사용하기 때문에 중복을 최소화하기 위한 방법이 필요

### 중복 최소화 방안

* JPA Repository
    * Master/Slave 각각의 JPA Repository 구성 시 custom marker annotation을 이용해서 includeFilter/excludeFilter로 repository를 구분해서 등록
* 기본 Repository
    * `@Transaction(transactionManager="...")`를 이용해서 Master/Slave용 repository를 구분
    * 공통 코드는 Master/Slave 공통의 parent interface에 선언하고 각각 상속받아 사용
        * 공통 parent interface에는 `@NoRepositoryBean` 어노테이션을 지정하여 repository bean으로 등록되지 않도록 설정
* custom Repository 구현
    * 기본 Repository가 Master/Slave용으로 구분되었기 때문에
      Master/Slave 각각의 interface 이름 + `Impl` 형태의 custom Repository 구현 클래스가 필요
        * 기본 Repository와 마찬가지로 공통의 parent abstract class를 상속받도록 구현
    * custom Repository 구현 클래스에도 Master/Slave 각각에 대한 `@Transaction(transactionManager="...")`를 적용
    * `setEntityManager(EntityManager entityManager)` 메서드를 override 해서
      * Master/Slave 각각에 맞는 EntityManagerFactory가 주입되도록 설정         

## References

### Java 에서 DataBase Replication Master/Slave (write/read) 분기 처리하기
* http://egloos.zum.com/kwon37xi/v/5364167

### (Spring Boot) 다중 DataSource 사용하기 with Sharding
* https://supawer0728.github.io/2018/05/06/spring-boot-multiple-datasource/

### Read-only and Read-Write Spring Data JPA Repositories in the same directory
* http://javaninja.net/2017/05/read-read-write-spring-data-jpa-repositories-directory/
