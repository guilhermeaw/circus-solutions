package entities;

// Não utiliza o hibernate pois utilizará o arquivo 'states-cities.json' para consultas
public class State {
    private String name;
    private String initials;

    public State() {}

    public State(String name, String initials) {
        this.name = name;
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
}
