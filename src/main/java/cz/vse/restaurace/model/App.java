package cz.vse.restaurace.model;

import java.util.*;

import cz.vse.restaurace.persistence.JsonPersistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class App {

    private Set<Food> food;
    private Set<Drink> drinks;
    private List<Table> availableTables;
    private List<Table> occupiedTables;

    private Order currentOrder;

    private JSONArray usersArray = new JSONArray();
    private JSONArray orderArray = new JSONArray();
    private JSONObject loggedUser;

    public App() {
        this.food = new HashSet<Food>();
        this.drinks = new HashSet<Drink>();
        this.availableTables = new ArrayList<>();
        this.occupiedTables = new ArrayList<>();
        this.usersArray = JsonPersistence.readLoginData();
        createData();
    }

    public void addUser(JSONObject user) {
        usersArray.add(user);
    }

    public void updateUser(JSONObject newVersion) {
        //JSONObject newUser = (JSONObject) newVersion.get("user");
        System.out.println(newVersion);
        String newUserName = (String) newVersion.get("userName");
        String newUserPassword = (String) newVersion.get("userPassword");

        JSONObject oldUser;
        String oldUserName;
        String oldUserPassword;

        for(int i = 0; i < usersArray.size(); i++) {
            oldUser = (JSONObject) usersArray.get(i);
            oldUserName = (String) oldUser.get("userName");
            oldUserPassword = (String) oldUser.get("userPassword");
            if ((newUserName.equals(oldUserName)) && (newUserPassword.equals(oldUserPassword))) {
                ((JSONObject) usersArray.get(i)).replace("user", newVersion);
            }
            System.out.println(usersArray.get(i));
        }
    }

    public boolean usersArrayContainsUser(JSONObject user) {
        boolean ret = false;
        for(int i = 0; i < usersArray.size(); i++) {
            if (user.equals(usersArray.get(i))) {
                ret = true;
            }
        }
        return ret;
    }

    public void setLoggedUser(JSONObject user) {
        this.loggedUser = user;
    }

    public JSONObject getLoggedUser() {
        return loggedUser;
    }

    public JSONArray getUsers() {
        return usersArray;
    }

    public JSONArray getOrders() { return orderArray; }

    public void addOrderToUser(JSONObject order) { orderArray.add(order); }
    
    public void createData() {
            Table t1 = new Table(1);
            Table t2 = new Table(2);
            Table t3 = new Table(3);
            Table t4 = new Table(4);
            Table t5 = new Table(5);
            Table t6 = new Table(6);
            Table t7 = new Table(7);
            Table t8 = new Table(8);
            
            Drink d1 = new Drink("Kofola",25);
            Drink d2 = new Drink("Fanta", 35);
            Drink d3 = new Drink("Sprite", 40);
    
            Food f1 = new Food("BigMac", 85);
            Food f2 = new Food("Kuřecí Řízek", 125);
            Food f3 = new Food("Hovězí steak", 150);

            createDatabase(t1);
            createDatabase(t2);
            createDatabase(t3);
            createDatabase(t4);
            createDatabase(t5);
            createDatabase(t6);
            createDatabase(t7);
            createDatabase(t8);

            createDatabase(d1);
            createDatabase(d2);
            createDatabase(d3);

            createDatabase(f1);
            createDatabase(f2);
            createDatabase(f3);
    }

    public void createDatabase(Object o) {
        if (o instanceof Table) {
            Table table = (Table)o;
            availableTables.add(table);
        }
        else if (o instanceof Drink) {
            Drink drink = (Drink)o;
            drinks.add(drink);
        }
        else {
            Food f = (Food)o;
            food.add(f);
        }
    }

    public Collection<Food> getFood() {
        return Collections.unmodifiableCollection(food);
    }

    public Collection<Drink> getDrinks() {
        return Collections.unmodifiableCollection(drinks);
    }

    public Collection<Table> getAvailableTables() {
        return Collections.unmodifiableCollection(availableTables);
    }

    public Collection<Table> getOccupiedTables() {
        return Collections.unmodifiableCollection(occupiedTables);
    }

    public void occupyTable(Table table) {
        occupiedTables.add(table);
        availableTables.remove(table);
    }

    public void freeTable(Table table) {
        occupiedTables.remove(table);
        availableTables.add(table);
    }

    public Table getTableByNumber(Integer number, String string) {
            if (number != null) {
                if (string.equals("occupied")) {
                    for (Table t : occupiedTables) {
                        if (t.getTableNumber() == number) {
                            return t;
                        }
                    }
                }
                else if (string.equals("available")) {
                    for (Table t : availableTables) {
                        if (t.getTableNumber() == number) {
                            return t;
                        }
                    }
                }
                else {
                    return null;
                }
            }
            return null;
    }
    
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public Food getFoodByName(String string) {
        if (string != null) {
            for (Food f : food) {
                if (f.getName().equals(string)) {
                    return f;
                }
            }
        }
        else {
            return null;
        }
        return null;
    }

    public Drink getDrinkByName(String string) {
        if (string != null) {
            for (Drink d : drinks) {
                if (d.getName().equals(string)) {
                    return d;
                }
            }
        }
        else {
            return null;
        }
        return null;
    }
}
