package br.com.vn.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
public class ItemManager {

    private static volatile ItemManager instance = null;
    private ItemManager() { }

    public static synchronized ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    public void deleteAllItems() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.createQuery("delete from Items").executeUpdate();
        session.close();
    }

    public List<Items> getAllItems() {
        return getAllItems(0, 0);
    }
    public List<Items> getAllItems(int firstResult, int maxResult) {
        List<Items> items = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select id, title from Items");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        @SuppressWarnings("unchecked")
        List allUsers = query.list();
        for (Iterator it = allUsers.iterator(); it.hasNext(); ) {
            Object[] itemObject = (Object[]) it.next();
            Items itemInstance = new Items((Integer) itemObject[0]);
            itemInstance.setName((String) itemObject[1]);
            itemInstance.setImageName((String) itemObject[2]);
            itemInstance.setDescription((String) itemObject[3]);
            items.add(itemInstance);
        }
        session.close();
        return items;
    }

    public Items getItem(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Items item = (Items) session.get(Items.class, id);
        session.close();
        return item;
    }

    public void saveOrUpdateItem(Items item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.saveOrUpdate(item);
        session.flush();
        session.close();
    }

    public Items deleteItem(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Items item = getItem(id);
        if (item != null) {
            session.delete(item);
            session.flush();
        }
        session.close();
        return item;
    }

}