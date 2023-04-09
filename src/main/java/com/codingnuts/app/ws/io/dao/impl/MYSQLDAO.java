package com.codingnuts.app.ws.io.dao.impl;

import com.codingnuts.app.ws.io.dao.DAO;
import com.codingnuts.app.ws.io.entity.UserEntity;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.utils.HibernateUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MYSQLDAO implements DAO {

    Session session;

    @Override
    public void openConnection() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        UserDTO userDto = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();
        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
        //Query roots always reference entities
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("email"), userName));
        // Fetch single result
        Query<UserEntity> query = session.createQuery(criteria);
        List<UserEntity> resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            UserEntity userEntity = resultList.get(0);
            userDto = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDto);
        }

        return userDto;
    }

    @Override
    public UserDTO getUser(String id) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entitie
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("userId"), id));

        // Fetch single result
        UserEntity userEntity = session.createQuery(criteria).getSingleResult();

        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public void updateUser(UserDTO userProfile) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userProfile, userEntity);

        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Create criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        // Query roots always reference entities
        Root<UserEntity> userRoot = criteria.from(UserEntity.class);
        criteria.select(userRoot);

        // Fetch result from start to a number of limit
        List<UserEntity> searchResults = session.createQuery(criteria).setFirstResult(start).setMaxResults(limit).getResultList();

        List<UserDTO> returnValue = new ArrayList<>();
        for (UserEntity entity : searchResults) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(entity, userDTO);
            returnValue.add(userDTO);
        }
        return returnValue;
    }

    @Override
    public void deleteUser(UserDTO storedUserDetails) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(storedUserDetails, userEntity);
        session.beginTransaction();
        session.delete(userEntity);
        session.getTransaction().commit();
    }

    @Override
    public UserDTO getUserByEmailToken(String token) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entities
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("emailVerificationToken"), token));

        // Fetch single result
        UserEntity userEntity = session.createQuery(criteria).getSingleResult();

        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();
        returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public void closeConnection() {
        if (session != null) {
            session.close();
        }
    }
}
