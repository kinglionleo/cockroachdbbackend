package com.example.restservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class Coordinator {
    
    private SessionFactory sessionFactory;

    public Coordinator() {
        sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Review.class)
            .buildSessionFactory();
    }

    // Gets a review by id
    public Review getReview(long id) throws Exception {
        
        try (Session s = sessionFactory.openSession()) {
            Review review;
            Transaction txn = s.beginTransaction();
            review = (Review) s.get(Review.class, id);
            txn.commit();
            
            System.out.println("APP: getReview() --> Review retrieved!");
            return review;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }

     // Adds a review
     public boolean addReview(String location, int toiletRating, int sinkRating, int noiseRating, String comment) {
    
        try (Session s = sessionFactory.openSession()) {
            Transaction txn = s.beginTransaction();
            s.createNativeQuery("INSERT INTO REVIEW (location, toiletRating, sinkRating, noiseRating, comment)" +
                                "VALUES (" + "'" + location + "'" + ", " + toiletRating + ", " + sinkRating + ", " + noiseRating + ", " + "'" + comment + "'" + ")").executeUpdate();
            txn.commit();
            
            System.out.println("APP: addReview() --> Review added!");
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    // Creates the table if it's not there
    public boolean createReviewTableIfMissing() {

        try (Session s = sessionFactory.openSession()){
            Transaction txn = s.beginTransaction();
            s.createNativeQuery("CREATE TABLE IF NOT EXISTS REVIEW (id serial, location text, toiletRating int, sinkRating int, noiseRating int, comment text, PRIMARY KEY (id))").executeUpdate();
            txn.commit();
            
            System.out.println("APP: createReviewTableIfMissing() - Table created!");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Deletes the table
    public boolean deleteReviewTable() {

        try (Session s = sessionFactory.openSession()) {
            Transaction txn = s.beginTransaction();
            s.createNativeQuery("DROP TABLE REVIEW").executeUpdate();
            txn.commit();
            
            System.out.println("APP: deleteReviewTable() - Table delete!");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Gets all reviews
    public List<Object[]> getReviews() throws Exception {
        
        try (Session s = sessionFactory.openSession()) {
            List<Object[]> review;
            Transaction txn = s.beginTransaction();
            review = s.createNativeQuery("SELECT * FROM REVIEW").list();
            //review = (Review) s.get(Review.class, id);
            txn.commit();
            
            System.out.println("APP: getReviews() --> Reviews retrieved!");
            return review;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Gets all reviews from a location
    public List<Object[]> getReviews(String location) throws Exception {
        
        try (Session s = sessionFactory.openSession()) {
            List<Object[]> review;
            Transaction txn = s.beginTransaction();
            review = s.createNativeQuery("SELECT * FROM REVIEW WHERE LOCATION = '" + location + "'").list();
            //review = (Review) s.get(Review.class, id);
            txn.commit();
            
            System.out.println("APP: getReviews() --> Reviews retrieved!");
            return review;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
   
    // Deletes a review
    public boolean deleteReview(long id){
        
        try (Session s = sessionFactory.openSession()) {
            Transaction txn = s.beginTransaction();
            s.createNativeQuery("DELETE FROM REVIEW WHERE ID = " + id).executeUpdate();
            txn.commit();
            
            System.out.println("APP: deleteReview() --> Review(s) deleted!");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Gets the average rating for a location
    public float getAverageRating(String location) throws Exception {
    
        try (Session s = sessionFactory.openSession()) {
            List<Object[]> reviews;
            Transaction txn = s.beginTransaction();
            reviews = s.createNativeQuery("SELECT * FROM REVIEW WHERE LOCATION = '" + location + "'").list();
            //review = (Review) s.get(Review.class, id);
            txn.commit();
            
            float sum = 0;
            for(Object[] review : reviews) {
                sum += Integer.parseInt(review[2].toString()) + Integer.parseInt(review[3].toString()) + Integer.parseInt(review[4].toString());
            }
            sum /= reviews.size() * 3;

            System.out.println("APP: getReviews() --> Reviews retrieved!");
            return sum;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Gets at most 3 random comments for a location
    public String[] getRandomComments(String location) throws Exception {
    
        try (Session s = sessionFactory.openSession()) {
            List<Object[]> reviews;
            Transaction txn = s.beginTransaction();
            reviews = s.createNativeQuery("SELECT * FROM REVIEW WHERE LOCATION = '" + location + "'" +
                                          "ORDER BY RANDOM()" +
                                          "LIMIT 3").list();
            //review = (Review) s.get(Review.class, id);
            txn.commit();
            
            String[] comments = new String[reviews.size()];
            int index = 0;
            for(Object[] review : reviews) {
                comments[index] = review[5].toString();
                index++;
            }

            System.out.println("APP: getRandomComments() --> Comments retrieved!");
            return comments;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Gets the latest 5 comments for a location
    public String[] getLatestComments() throws Exception {
    
        try (Session s = sessionFactory.openSession()) {
            List<Object[]> reviews;
            Transaction txn = s.beginTransaction();
            reviews = s.createNativeQuery("SELECT * FROM REVIEW " +
                                          "ORDER BY id DESC " +
                                          "LIMIT 5").list();
            //review = (Review) s.get(Review.class, id);
            txn.commit();
            
            String[] comments = new String[reviews.size()];
            int index = 0;
            for(Object[] review : reviews) {
                comments[index] = review[5].toString();
                index++;
            }

            System.out.println("APP: getLatestComments() --> Comments retrieved!");
            return comments;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}