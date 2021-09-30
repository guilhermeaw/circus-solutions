package entities;

// Não utiliza o hibernate pois utilizará o arquivo 'states-cities.json' para consultas
public class State {
    private Long id;
    private String name;

    public State() {}

    public State(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
