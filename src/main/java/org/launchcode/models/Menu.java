package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @ManyToMany
    private List<Cheese> cheeses;

    public Menu() {
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Cheese> getCheeses() { return cheeses; }

    public void addItem(Cheese item) { cheeses.add(item); }

    public Menu(String name) { this.name = name; }
    /**
     * It removes a given cheese from the menu (from List<Cheese> cheeses)
     *
     * @param item is a cheese for removing
     *
     */
    public void removeItem(Cheese item) {    //It added for fixing a bug with 'remove cheese' functionality
        while (cheeses.contains(item))       //It added for fixing a bug with 'remove cheese' functionality
            cheeses.remove(cheeses.indexOf(item))   //It added for fixing a bug with 'remove cheese' functionality
            ;                                       //It added for fixing a bug with 'remove cheese' functionality
    }
}
