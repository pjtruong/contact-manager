package com.pjtruong.contactmgr;

import com.pjtruong.contactmgr.model.Contact;
import com.pjtruong.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

/**
 * Created by pjtruong on 2/10/17.
 */


public class Application {
    //Hold a reusable reference to a SessionFactory
   private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        //Create a StandardServiceRegistry

        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }


    public static void main(String[] args){


        Contact contact = new ContactBuilder("Peter", "Truong")
                .withEmail("hp5340@gmail.com")
                .withPhone(9092345679L)
                .build();


        int id = save(contact);

        //display all contacts
        for(Contact c : fetchAllContacts())
        {
            System.out.println(c);

        }

        //get the persisted contact
        Contact c = findContactById(id);

        //update the contact
        c.setFirstName("Holly");


        //persist the changes
        update(c);
        //display a list of contacts after change
        for(Contact d : fetchAllContacts())
        {
            System.out.println(d);

        }

        delete(c);
        //display a list of contacts after change
        for(Contact d : fetchAllContacts())
        {
            System.out.println(d);

        }


    }

    private static Contact findContactById(int id){

        Session session = sessionFactory.openSession();

        Contact contact = session.get(Contact.class,id);

        session.close();


        return contact;


    }


    private static void update(Contact contact){
        //open a session
        Session session = sessionFactory.openSession();


        //begin a transaction
        session.beginTransaction();

        //use the session to update the contact
        session.update(contact);

        //commit the transaction
        session.getTransaction().commit();

        //close the session
        session.close();


    }



    private static void delete(Contact contact){
        //open a session
        Session session = sessionFactory.openSession();


        //begin a transaction
        session.beginTransaction();

        //use the session to update the contact
        session.delete(contact);

        //commit the transaction
        session.getTransaction().commit();

        //close the session
        session.close();


    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts(){

        //open a session
        Session session = sessionFactory.openSession();

        //create criteria
        Criteria criteria = session.createCriteria(Contact.class);

        //get a list of contact objects according ot the criteria object
        List<Contact> contacts = criteria.list();

        //close the session
        session.close();

        return contacts;

    }


    private static int save(Contact contact){

        //open a session
        Session session = sessionFactory.openSession();


        //begin a transaction
        session.beginTransaction();

        //use the session to save the contact
        int id = (Integer)session.save(contact);

        //commit the transaction
        session.getTransaction().commit();

        //close the session
        session.close();

        return id;


    }

}
