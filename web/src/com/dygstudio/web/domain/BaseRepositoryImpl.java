package com.dygstudio.web.domain;

import com.dygstudio.web.commons.GenericsUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by diyaguang on 2017/5/31.
 * 封装 Hibernate 原生 API 的 DAO 泛型基类
 * 可以再 Service 层直接使用，也可以扩展泛型 DAO 子类使用，取消了 HibernateTemplate，直接使用 Hibernate 原生 API
 * 参数： T DAO操作的对象类型，PK 主键类型
 */
@Repository
@Transactional
public class BaseRepositoryImpl<T,PK extends Serializable> implements BaseRepository<T,PK> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SessionFactory sessionFactory;

    protected Class<T> entityClass;

    /**
     * 用于 Dao层子类使用的构造函数，通过子类的泛型定义取得对象类型 class
     * eg. BaseRepositoryImpl<User,Long>
     */
    public BaseRepositoryImpl(){
        //this.entityClass = (Class<T>) getSuperClassGenrictype(getClass(),0);
        this.entityClass = GenericsUtils.getSuperClassGenricType(getClass(),0);
    }
    /**
     * 用于省略 Dao 层，在 Service层直接使用通用的 BaseRepositoryImpl 的构造函数，在构造函数中定义对象类型 Class
     * eg. BaseRepositoryImpl<User,Long> userDao = new SimpleHibernateDao<user,Long>(sessionFactory,User.class)
     */
    public BaseRepositoryImpl(final SessionFactory sessionFactory, final Class<T> entityClass){
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }
    /**
    * 取得 SessionFactory
    */
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    /**
     * 采用 @Autowire 按类型注入 SessionFactory，当有多个 SessionFactory 的时候，在子类重载本函数
     * @param sessionFactory
     */
    @Autowired
    public void setSessionFactory(final SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     * 取得当前 Session
     * @return
     */
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    /**
     * 重新打开一个新的 Session
     * @return
     */
    public Session openSession(){
        return sessionFactory.openSession();
    }

    /**
     * 保存新增或修改的对象
     * @param entity
     */
    @Transactional
    public void save(final T entity){
        getSession().saveOrUpdate(entity);
        getSession().flush();;
        logger.debug("save entity:{}",entity);
    }

    /**
     * 删除对象
     * @param entity 对象必须是 session 中的对象或含 id 属性的 transient 对象
     */
    public void delete(final T entity){
        getSession().delete(entity);
        logger.debug("delete entity:{}",entity);
    }

    /**
     * 按 id 删除对象
     * @param id
     */
    public void delete(final PK id){
        delete(get(id));
    }

    /**
     * 按 id 获取对象
     * @param id
     * @return
     */
    public T get(final PK id){
        return (T)getSession().get(entityClass,id);
    }

    /**
     * 按 id 列表获取对象列表
     * @param ids
     * @return
     */
    public List<T> get(final Collection<PK> ids){
        return find(Restrictions.in(getIdName(),ids));
    }

    /**
     * 获取全部对象
     * @return
     */
    public List<T> getAll(){
        return find();
    }

    /**
     * 获取全部对象，支持按照属性排序
     * @param orderByProperty
     * @param isAsc
     * @return
     */
    public List<T> getAll(String orderByProperty,boolean isAsc){
        Criteria c = createCriteria();
        if(isAsc)
            c.addOrder(Order.asc(orderByProperty));
        else
            c.addOrder(Order.desc(orderByProperty));
        return c.list();
    }

    /**
     * 按照属性查找对象列表，默认匹配方式为 Like
     * @param propertyName
     * @param value
     * @return
     */
    public List<T> findByProperty(final String propertyName,final Object value){
        Assert.hasText(propertyName,"propertyName 不能为空");
        Criterion criterion = Restrictions.like(propertyName,(String)value, MatchMode.ANYWHERE);
        return find(criterion);
    }

    /**
     * 按属性查找唯一对象，默认匹配方式为 Like
     * @param propertyName
     * @param value
     * @return
     */
    public T findUniqueByProperty(final String propertyName,final Object value){
        Assert.hasText(propertyName,"propertyName 不能为空");
        Criterion criterion = Restrictions.like(propertyName,(String)value,MatchMode.ANYWHERE);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /**
     * 按 HQL 查找对象列表
     * @param hql
     * @param values 数量可变的参数，按照顺序绑定
     * @param <X>
     * @return
     */
    public <X> List<X> find(final String hql,final Object... values){
        return createQuery(hql,values).list();
    }

    /**
     *按 HQL 查找对象列表
     * @param hql
     * @param values 命名参数，按名称绑定
     * @param <X>
     * @return
     */
    public <X> List<X> find(final String hql,final Map<String,?> values){
        return createQuery(hql,values).list();
    }

    /**
     * 按 HQL 查询唯一对象
     * @param hql
     * @param values 数量可变的参数，按顺序绑定
     * @param <X>
     * @return
     */
    public <X> X findUnique(final String hql,final Object... values){
        return (X)createQuery(hql,values).uniqueResult();
    }

    /**
     * 按 HQL 查询唯一对象
     * @param hql
     * @param values 命名参数，按名称绑定
     * @param <X>
     * @return
     */
    public <X> X findUnique(final String hql,final Map<String,?> values){
        return (X)createQuery(hql,values).uniqueResult();
    }

    /**
     * 执行 HQL 进行批量修改、删除操作
     * @param hql
     * @param values 数量可变的参数，按照顺序绑定
     * @return  更新记录数
     */
    public int batchExecute(final String hql,final Object... values){
        return createQuery(hql,values).executeUpdate();
    }

    /**
     * 执行 HQL 进行批量修改，删除操作
     * @param hql
     * @param values  命名参数，按名称绑定
     * @return  更新记录数
     */
    public int batchExecute(final String hql,final Map<String,?> values){
        return createQuery(hql,values).executeUpdate();
    }

    /**
     * 根据查询 HQL 与参数列表创建 Query 对象，与 find() 函数可进行更加灵活的操作
     * @param queryString
     * @param values  数量可变的参数，按照顺序绑定
     * @return
     */
    public Query createQuery(final String queryString,final Object... values){
        Assert.hasText(queryString,"queryString 不能为空");
        Query query = getSession().createQuery(queryString);
        if(values!=null){
            for (int i=0;i<values.length;i++){
                query.setParameter(i,values[i]);
            }
        }
        return query;
    }

    /**
     * 根据查询 HQL 与参数列表创建 Query 对象，与 find() 函数可进行更加灵活的操作
     * @param queryString
     * @param values  命名参数，按照名称绑定
     * @return
     */
    public Query createQuery(final String queryString,final Map<String,?> values){
        Query query = getSession().createQuery(queryString);
        if(values != null){
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 按 Criteria 查询对象列表
     * @param criterions  数量可变的 Criterion
     * @return
     */
    public List<T> find(final Criterion... criterions){
        return createCriteria(criterions).list();
    }

    /**
     * 按 Criteria 查询唯一对象
     * @param criterions  数量可变的 Criterion
     * @return
     */
    public T findUnique(final Criterion... criterions){
        return (T)createCriteria(criterions).uniqueResult();
    }

    /**
     * 根据 Criterion 条件创建 Criteria，与 find() 函数可进行更加灵活的操作
     * @param criterions  数量可变的 Criterion
     * @return
     */
    public Criteria createCriteria(final Criterion... criterions){
        Criteria criteria = getSession().createCriteria(entityClass);
        for(Criterion c : criterions)
            criteria.add(c);
        return criteria;
    }

    /**
     * 初始化对象，使用 load() 方法得到的仅是对象 Proxy，在传导 View 层前需要进行初始化，如果传入 entity
     * 则只初始化 entity的直接属性，但不会初始化延迟加载的关联集合和属性，如果初始化冠梁属性，需执行
     * Hibernate.initialize(user.getRoles())，初始化 User 的直接属性和关联集合
     * Hibernate.initialize(User.getDescription())，初始化 User 的直接属性和延迟加载的 Description 属性
     * @param proxy
     */
    public void initProxyObject(Object proxy){
        Hibernate.initialize(proxy);
    }

    /**
     * Flush 当前 Session
     */
    public void flush(){
        getSession().flush();
    }

    /**
     * 为 Query 添加 distinct transformer，预加载关联对象的 HQL 会引起主对象重复，需要进行 distinct 处理
     * @param query
     * @return
     */
    public Query distinct(Query query){
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 为 Criteria 添加 distinct transformer，预加载关联对象的 HQL 会引起主对象重复，需要进行 distinct 处理
     * @param criteria
     * @return
     */
    public Criteria distinct(Criteria criteria){
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * 取得对象的主键名
     * @return
     */
    public String getIdName(){
        ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 判断对象的属性值在数据库内是否唯一，在修改对象的情景下，如果属性新修改的值（value）等于属性原来的值（orgValue）则不作比较
     * @param propertyName
     * @param newValue
     * @param oldValue
     * @return
     */
    public boolean isPropertyUnique(final String propertyName,final Object newValue,final Object oldValue){
        if(newValue==null || newValue.equals(oldValue)){
            return true;
        }
        Object object = findUniqueByProperty(propertyName,newValue);
        return (object == null);
    }
}
