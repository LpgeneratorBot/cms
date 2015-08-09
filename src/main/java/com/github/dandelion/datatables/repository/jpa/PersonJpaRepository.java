package com.github.dandelion.datatables.repository.jpa;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.dataimport.mail.MailChecker;
import com.github.dandelion.datatables.model.Person;
import com.github.dandelion.datatables.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p/>
 * JPA implementation of the {@link PersonRepository}.
 *
 * @author Thibault Duchateau
 */
@Repository
public class PersonJpaRepository implements PersonRepository {

    private static final Logger log = LoggerFactory.getLogger(PersonJpaRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MailChecker mailChecker;


//    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//
//    @PostConstruct
//    private void afterPropertiesSet() {
//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    log.info("Checking mail");
//                    String[] persons = mailChecker.check();
//                    List<Person> personCollection = PersonExtractor.extract(persons);
//                    addPersons(personCollection);
//                } catch (Exception e) {
//                    log.error("Checking mail failed", e);
//                }
//            }
//        }, 2, 90*2, TimeUnit.SECONDS);
//    }

    /**
     * @return the complete list of persons.
     */
    public List<Person> findAll() {
        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
        return query.getResultList();
    }

    /**
     * @param maxResult Max number of persons.
     * @return a maxResult limited list of persons.
     */
    public List<Person> findLimited(int maxResult) {
        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    /**
     * <p/>
     * Query used to populate the DataTables that display the list of persons.
     *
     * @param criterias The DataTables criterias used to filter the persons. (maxResult,
     *                  filtering, paging, ...)
     * @return a filtered list of persons.
     */
    @Transactional(readOnly = false)
    public List<Person> findPersonWithDatatablesCriterias(DatatablesCriterias criterias) {

        try {
            log.info("Checking mail");
//            String[] persons = mailChecker.check();
//            List<Person> personCollection = PersonExtractor.extract(persons);
//            addPersons(personCollection);
        } catch (Exception e) {
            log.error("Checking mail failed", e);
        }

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Person p");

        /**
         * Step 1: global and individual column filtering
         */
        queryBuilder.append(PersonRepositoryUtils.getFilterQuery(criterias));

        /**
         * Step 2: sorting
         */
        if (criterias.hasOneSortedColumn()) {

            List<String> orderParams = new ArrayList<String>();
            queryBuilder.append(" ORDER BY ");
            for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
                orderParams.add("p." + columnDef.getName() + " " + columnDef.getSortDirection());
            }

            Iterator<String> itr2 = orderParams.iterator();
            while (itr2.hasNext()) {
                queryBuilder.append(itr2.next());
                if (itr2.hasNext()) {
                    queryBuilder.append(" , ");
                }
            }
        }

        TypedQuery<Person> query = entityManager.createQuery(queryBuilder.toString(), Person.class);

        /**
         * Step 3: paging
         */
        query.setFirstResult(criterias.getStart());
        query.setMaxResults(criterias.getLength());

        return query.getResultList();
    }

    /**
     * <p/>
     * Query used to return the number of filtered persons.
     *
     * @param criterias The DataTables criterias used to filter the persons. (maxResult,
     *                  filtering, paging, ...)
     * @return the number of filtered persons.
     */
    public Long getFilteredCount(DatatablesCriterias criterias) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Person p");

        queryBuilder.append(PersonRepositoryUtils.getFilterQuery(criterias));

        Query query = entityManager.createQuery(queryBuilder.toString());
        return Long.parseLong(String.valueOf(query.getResultList().size()));
    }

    /**
     * @return the total count of persons.
     */
    public Long getTotalCount() {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Per" +
                "son p");
        return (Long) query.getSingleResult();
    }

    @Override
//    @Transactional(readOnly = false)
    public void addPersons(List<Person> persons) {
        log.info("Storing " + persons);
        for (Person person : persons) {
            entityManager.persist(person);
        }
        entityManager.flush();
    }
}